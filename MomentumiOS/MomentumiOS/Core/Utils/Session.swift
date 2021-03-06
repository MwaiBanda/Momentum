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

final class Session: ObservableObject {
    @Inject private var auth: Auth
    @Inject private var authController: AuthController
    var didChange = PassthroughSubject<Session, Never>()
    var handle: AuthStateDidChangeListenerHandle?
    @Published var currentUser: User? {
        didSet {
            didChange.send(self)
        }
    }
    func observerAuth() {
        handle = auth.addStateDidChangeListener({ [weak self] auth, user in
            if let user = user {
                self?.currentUser = User(
                    email: user.email ?? "",
                    id: user.uid,
                    isGuest: user.isAnonymous
                )
            }
        })
    }
    func checkAndSignInAsGuest() {
        authController.checkAuthAndSignAsGuest(onCompletion: { _ in })
    }
    func unbind() {
        if let handle = handle {
            auth.removeStateDidChangeListener(handle)
        }
    }

    struct User {
        var email: String
        var id: String
        var isGuest: Bool
    }
    
    deinit {
        unbind()
    }
}
