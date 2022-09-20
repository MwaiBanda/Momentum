//
//  PlayerViewController.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/20/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import AVKit


struct PlayerViewController: UIViewControllerRepresentable {
    var playbackURL: String
    func makeUIViewController(context: Context) -> some UIViewController {
        let avPlayerController = AVPlayerViewController()
        if let contentURL = URL(string: playbackURL)  {
            let player = AVPlayer(url: contentURL)
            avPlayerController.player = player
            avPlayerController.player?.play()
        }
        return avPlayerController
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
    
}
