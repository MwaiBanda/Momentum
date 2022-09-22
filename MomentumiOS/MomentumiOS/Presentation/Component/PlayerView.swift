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
    

    var playbackURL: String

    typealias UIViewControllerType = AVPlayerViewController

    func makeUIViewController(context: Context) -> AVPlayerViewController {
        let avPlayerController = AVPlayerViewController()
        if let contentURL = URL(string: playbackURL)  {
            let player = AVPlayer(url: contentURL)
            avPlayerController.player = player
            avPlayerController.player?.play()
        }
        return avPlayerController
    }
    func updateUIViewController(_ uiViewController: AVPlayerViewController, context: Context) {
        
    }
    
    func makeCoordinator() -> Coordinator {
       return Coordinator(self)
    }
    class Coordinator: NSObject, UIViewControllerTransitioningDelegate {
        let parent: PlayerView

        init(_ parent: PlayerView) {
            self.parent = parent
        }
       
        
      
    }
    
}
