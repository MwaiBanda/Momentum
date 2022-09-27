//
//  SermonsView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import MomentumSDK
import AVFoundation
import MediaPlayer

class SermonsViewModel: AVPlayer, ObservableObject  {
    private var controller: SermonController
    @Published var sermons = [Sermon]()
    @Published var canLoadMoreSermons = true
    @Published private var currentPage = 1
    @Published private var isPlaying = false
    @Published private var currentSermonTitle = ""
    @Published var currentSermon: Sermon? = nil

    @Inject var player: AVPlayer

    init(controller: SermonController = SermonControllerImpl()) {
        self.controller = controller
        super.init()
        setupNotifications()
        setupNowPlaying()
        setupRemoteTransportControls()
        
    }
    
    
    private func changeMedia() {
        let metaOutput = AVPlayerItemMetadataOutput(identifiers: nil)
        metaOutput.setDelegate(self, queue: DispatchQueue.main)
        self.player.currentItem?.add(metaOutput)
    }
    
    func fetchSermons() {
        currentPage = 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons = sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
            }
        }
    }
    
    func pauseSermon() {
        player.pause()
        isPlaying = false
        changeMedia()
    }
    
    func playSermon() {
        player.play()
        isPlaying = true
        changeMedia()
    }
    
    func loadMoreSermons() {
        currentPage += 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons += sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
                self.canLoadMoreSermons = false
            }
        }
    }
    
    private func setupRemoteTransportControls() {
        let commandCenter = MPRemoteCommandCenter.shared()
        
        commandCenter.playCommand.addTarget { [unowned self] event in
            if !self.isPlaying {
                play()
                return .success
            }
            return .commandFailed
        }
        
        commandCenter.pauseCommand.addTarget { [unowned self] event in
            if self.isPlaying {
                pause()
                return .success
            }
            return .commandFailed
        }
    }
    
    
    private func setupNowPlaying() {
        var nowPlayingInfo = [String : Any]()

        nowPlayingInfo[MPMediaItemPropertyTitle] =  "Tune In..."
        
        if let image = UIImage(named: "momentumLaunch") {
            nowPlayingInfo[MPMediaItemPropertyArtwork] = MPMediaItemArtwork(boundsSize: image.size) { size in
                return image
            }
        }
        nowPlayingInfo[MPNowPlayingInfoPropertyElapsedPlaybackTime] = player.currentTime
        nowPlayingInfo[MPMediaItemPropertyPlaybackDuration] = player.currentItem?.duration
        nowPlayingInfo[MPNowPlayingInfoPropertyPlaybackRate] = player.rate
    
        MPNowPlayingInfoCenter.default().nowPlayingInfo = nowPlayingInfo
    }
    
    
    func updateNowPlaying(sermon: Sermon) {
        var nowPlayingInfo = MPNowPlayingInfoCenter.default().nowPlayingInfo!
        nowPlayingInfo[MPMediaItemPropertyArtist] = sermon.preacher
        nowPlayingInfo[MPMediaItemPropertyTitle] = sermon.title
        if let image = UIImage(named: "momentumLaunch") {
            nowPlayingInfo[MPMediaItemPropertyArtwork] = MPMediaItemArtwork(boundsSize: image.size) { size in
                return image
            }
        }
        nowPlayingInfo[MPNowPlayingInfoPropertyElapsedPlaybackTime] = player.currentTime
        nowPlayingInfo[MPNowPlayingInfoPropertyPlaybackRate] = isPlaying ? 1 : 0
        
        MPNowPlayingInfoCenter.default().nowPlayingInfo = nowPlayingInfo
    }
    
    
    @objc func handleInterruption(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let typeValue = userInfo[AVAudioSessionInterruptionTypeKey] as? UInt,
              let type = AVAudioSession.InterruptionType(rawValue: typeValue) else {
                  return
              }
        
        if type == .began {
            print("Interruption began")
            // Interruption began, take appropriate actions
        }
        else if type == .ended {
            if let optionsValue = userInfo[AVAudioSessionInterruptionOptionKey] as? UInt {
                let options = AVAudioSession.InterruptionOptions(rawValue: optionsValue)
                if options.contains(.shouldResume) {
                    // Interruption Ended - playback should resume
                    print("Interruption Ended - playback should resume")
                    play()
                } else {
                    // Interruption Ended - playback should NOT resume
                    print("Interruption Ended - playback should NOT resume")
                }
            }
        }
    }
    
    
    @objc func handleRouteChange(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let reasonValue = userInfo[AVAudioSessionRouteChangeReasonKey] as? UInt,
              let reason = AVAudioSession.RouteChangeReason(rawValue:reasonValue) else {
                  return
              }
        switch reason {
        case .newDeviceAvailable:
            let session = AVAudioSession.sharedInstance()
            for output in session.currentRoute.outputs where output.portType == AVAudioSession.Port.headphones {
                print("headphones connected")
                DispatchQueue.main.sync {
                    play()
                }
                break
            }
        case .oldDeviceUnavailable:
            if let previousRoute =
                userInfo[AVAudioSessionRouteChangePreviousRouteKey] as? AVAudioSessionRouteDescription {
                for output in previousRoute.outputs where output.portType == AVAudioSession.Port.headphones {
                    print("headphones disconnected")
                    DispatchQueue.main.sync {
                        pause()
                    }
                    break
                }
            }
        default: ()
        }
    }
    
    
    private func setupNotifications() {
        let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(self,
                                       selector: #selector(handleInterruption),
                                       name: AVAudioSession.interruptionNotification,
                                       object: nil)
        notificationCenter.addObserver(self,
                                       selector: #selector(handleRouteChange),
                                       name: AVAudioSession.routeChangeNotification,
                                       object: nil)
    }
}

extension SermonsViewModel: AVPlayerItemMetadataOutputPushDelegate {
    func metadataOutput(
        _ output: AVPlayerItemMetadataOutput,
        didOutputTimedMetadataGroups groups: [AVTimedMetadataGroup],
        from track: AVPlayerItemTrack?
    ) {
        if  let item = groups.first?.items.last {
            let sermon = item.value(forKeyPath: "value")!
            DispatchQueue.main.async { [unowned self] in
                self.currentSermonTitle = String(describing: sermon)
                print(sermon)
            }
        } else {
            print("MetaData Error")
        }
    }
}
