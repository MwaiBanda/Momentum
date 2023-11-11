//
//  PlayerViewController.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/20/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import AVKit


struct PlayerView: UIViewControllerRepresentable {
    var player: AVPlayer
    var playbackURL: String
    var lastPlayedTime: () -> CMTime
    
    typealias UIViewControllerType = AVPlayerViewController

    func makeUIViewController(context: Context) -> AVPlayerViewController {
        let avPlayerController = AVPlayerViewController()
        DispatchQueue.main.async {
            if let contentURL = URL(string: playbackURL)  {
                player.replaceCurrentItem(with: AVPlayerItem(url: contentURL))
                avPlayerController.player = player
                avPlayerController.updatesNowPlayingInfoCenter = false
                player.seek(to: lastPlayedTime(), toleranceBefore: .zero, toleranceAfter: .zero)
                avPlayerController.player?.play()
            }
        }
        return avPlayerController
    }
    func updateUIViewController(_ uiViewController: AVPlayerViewController, context: Context) {
        
    }
    
    func makeCoordinator() -> Coordinator {
       return Coordinator(self)
    }
    class Coordinator: NSObject, AVPlayerPlaybackCoordinatorDelegate {
        let parent: PlayerView

        init(_ parent: PlayerView) {
            self.parent = parent
        }
        
    }
}
