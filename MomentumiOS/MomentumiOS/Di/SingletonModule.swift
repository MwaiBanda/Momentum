//
//  SingletonModule.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/14/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import Foundation
import TinyDi
import MomentumSDK
import AVFoundation


@TinyModule
func singletonModule(){
    Module(
        Single(AVPlayer()),
        Single(DatabaseDriverFactory()),
        Single(Momentum.shared.auth.controller),
        Single(Momentum.shared.messageUseCases)
    )
}
