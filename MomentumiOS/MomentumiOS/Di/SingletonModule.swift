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
import FirebaseAuth
import AVFoundation


@TinyModule
func singletonModule(){
    Module(
        Single(Auth.auth()),
        Single(DatabaseDriverFactory()),
        Single(AVPlayer())
    )
}
