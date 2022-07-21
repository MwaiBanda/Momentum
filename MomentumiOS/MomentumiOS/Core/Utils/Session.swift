//
//  session.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MomentumSDK
import FirebaseAuth
import Combine

final class Session {
    private var authController: AuthController
    var didChange = PassthroughSubject<Session, Never>()
    var handle: AuthStateDidChangeListenerHandle?
    init(authController: AuthController) {
        self.authController = authController
    }
}
