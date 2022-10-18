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
import Combine

class SermonsViewModel: ObservableObject  {
    @Inject private var controller: SermonController
    @Inject var player: AVPlayer
    
    @Published var sermons = [Sermon]()
    @Published var filtered = [Sermon]()
    @Published var watchedSermons = [MomentumSermon]()

    @Published var currentSermon: Sermon? = nil
    @Published var canLoadMoreSermons = true
    @Published private var currentPage = 1
    
    @Published private var isPlaying = false
    
    @Published var filterFavourites = false
    @Published var filterNewest = false
    @Published var filterOldest = false
    
    @Published var favourites = [SermonFavourite]()


    @Published private var currentSermonTitle = ""
    @Published var searchTerm = ""

    var filteredSermons: AnyPublisher<[Sermon], Never> {
        Publishers
            .CombineLatest4($sermons, $searchTerm, $favourites, $filterFavourites)
            .map { sermons, searchTerm, favourites, filterFavourites in
                DispatchQueue.main.asyncAfter(deadline: .now() + 2) { [unowned self] in
                    if canLoadMoreSermons && !searchTerm.isEmpty {
                        loadMoreSermons()
                    }
                }
                return sermons.filter({
                    $0.series.lowercased().contains(searchTerm.lowercased()) ||
                    $0.title.lowercased().contains(searchTerm.lowercased()) ||
                    $0.date.lowercased().contains(searchTerm.lowercased()) ||
                    $0.preacher.lowercased().contains(searchTerm.lowercased()) ||
                    searchTerm.isEmpty
                }).filter({ sermon in
                    filterFavourites  && (favourites.first(where: { $0.id == sermon.id }) != nil) ||
                    favourites.isEmpty ||
                    !favourites.isEmpty && !filterFavourites
                })
            }
            .eraseToAnyPublisher()
    }
    
    var sortedSermons: AnyPublisher<[Sermon], Never> {
        Publishers
            .CombineLatest3(filteredSermons, $filterNewest, $filterOldest)
            .map { sermons, filterNewest, filterOldest in
                var res = sermons
                if filterOldest {
                    res = sermons.sorted(by: { $0.dateMillis < $1.dateMillis })
                } else {
                    res = sermons.sorted(by: { $0.dateMillis > $1.dateMillis })
                }
                return res
            }.eraseToAnyPublisher()
    }


    init() {
        setupNotifications()
        setupRemoteTransportControls()
    }
    
    
   
    
    func fetchSermons() {
        currentPage = 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons = sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
                self.currentPage = Int(sermonsResponse.pageNumber)
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
                self.canLoadMoreSermons = false
            }
        }
    }
    
    func getWatchedSermons(onCompletion: @escaping () -> Void) {
        controller.getWatchedSermons { sermons in
            self.watchedSermons = sermons
        }
        onCompletion()
    }
    
    func getFavouriteSermons() {
        controller.getFavouriteSermons { sermons in
            self.favourites = sermons
        }
    }
    func addFavouriteSermon(id: String) {
        controller.addFavouriteSermon(id: id)
        favourites.append(SermonFavourite(id: id, name: ""))
    }
    
    func addSermon(sermon: MomentumSermon) {
        controller.addSermon(sermon: sermon)
        watchedSermons.removeAll(where: { $0.id == sermon.id })
        watchedSermons.append(sermon)
    }
    
    func removeFavouriteSermon(id: String) {
        controller.removeFavouriteSermon(id: id)
        favourites.removeAll(where: { $0.id == id })
    }
    func pauseSermon() {
        player.pause()
        isPlaying = false
    }
    
    func playSermon() {
        player.play()
        isPlaying = true
    }
    
    func loadMoreSermons() {
        currentPage += 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons += sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
                self.currentPage = Int(sermonsResponse.pageNumber)
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
                playSermon()
                return .success
            }
            return .commandFailed
        }
        
        commandCenter.pauseCommand.addTarget { [unowned self] event in
            if self.isPlaying {
                pauseSermon()
                return .success
            }
            return .commandFailed
        }
        commandCenter.skipForwardCommand.addTarget { [unowned self] event in
            let seconds15 = CMTime(seconds: player.currentTime().seconds.advanced(by: 10), preferredTimescale: 1)
            player.seek(to: seconds15, toleranceBefore: .zero, toleranceAfter: .zero)
            
            return .success
        }
        commandCenter.skipBackwardCommand.addTarget { [unowned self] event in
            let seconds15 = CMTime(seconds: player.currentTime().seconds.advanced(by: -10), preferredTimescale: 1)
            player.seek(to: seconds15, toleranceBefore: .zero, toleranceAfter: .zero)

            return .success
        }
        commandCenter.seekForwardCommand.addTarget { [unowned self] event in
            let interval = CMTime(seconds: event.timestamp, preferredTimescale: 1)
            player.seek(to: interval, toleranceBefore: .zero, toleranceAfter: .zero)

            return .success
        }
        commandCenter.seekBackwardCommand.addTarget { [unowned self] event in
            let interval = CMTime(seconds: event.timestamp, preferredTimescale: 1)
            player.seek(to: interval, toleranceBefore: .zero, toleranceAfter: .zero)

            return .success
        }
        
    }
    
    func resetNowPlaying() {
        var nowPlayingInfo = [String: Any]()
        nowPlayingInfo[MPMediaItemPropertyArtist] = "Momentum Church"
        nowPlayingInfo[MPMediaItemPropertyTitle] = "Watch Sermons"
        if let image = UIImage(named: "momentumLaunch") {
            nowPlayingInfo[MPMediaItemPropertyArtwork] = MPMediaItemArtwork(boundsSize: image.size) { size in
                return image
            }
        }
        MPNowPlayingInfoCenter.default().nowPlayingInfo = nowPlayingInfo
        player.replaceCurrentItem(with: nil)
    }

    func updateNowPlaying(sermon: Sermon) {
        var nowPlayingInfo = [String: Any]()
        nowPlayingInfo[MPMediaItemPropertyArtist] = sermon.preacher
        nowPlayingInfo[MPMediaItemPropertyTitle] = sermon.title
        if let image = UIImage(named: "momentumLaunch") {
            nowPlayingInfo[MPMediaItemPropertyArtwork] = MPMediaItemArtwork(boundsSize: image.size) { size in
                return image
            }
        }
        nowPlayingInfo[MPNowPlayingInfoPropertyElapsedPlaybackTime] = player.currentItem?.currentTime()
        nowPlayingInfo[MPMediaItemPropertyPlaybackDuration] = player.currentItem?.asset.duration.seconds
        nowPlayingInfo[MPNowPlayingInfoPropertyPlaybackRate] = player.rate


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
                    playSermon()
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
                    playSermon()
                }
                break
            }
        case .oldDeviceUnavailable:
            if let previousRoute =
                userInfo[AVAudioSessionRouteChangePreviousRouteKey] as? AVAudioSessionRouteDescription {
                for output in previousRoute.outputs where output.portType == AVAudioSession.Port.headphones {
                    print("headphones disconnected")
                    DispatchQueue.main.sync {
                        pauseSermon()
                    }
                    break
                }
            }
        default: ()
        }
    }
    
    
    private func setupNotifications() {
        let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(
            self,
            selector: #selector(handleInterruption),
            name: AVAudioSession.interruptionNotification,
            object: nil
        )
        notificationCenter.addObserver(
            self,
            selector: #selector(handleRouteChange),
            name: AVAudioSession.routeChangeNotification,
            object: nil
        )
    }
}


