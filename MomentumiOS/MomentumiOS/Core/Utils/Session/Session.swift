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
    @Inject private var authController: AuthController
    var didChange = PassthroughSubject<Session, Never>()
    var handle: AuthStateDidChangeListenerHandle?
    @Published var currentUser: User? {
        didSet {
            didChange.send(self)
        }
    }
    
    func signIn(
        email: String,
        password: String,
        onCompletion: @escaping () -> Void
    ){
        authController.signInWithEmail(
            email: email,
            password: password
        ) { [unowned self] res in
            if let user = res.data {
                self.currentUser = User(
                    email: user.email ?? "",
                    id: user.uid,
                    isGuest: user.isAnonymous
                )
                onCompletion()
            } else if let error = res.message {
                Log.d(tag: "Auth", message: error)
            }
        }
    }
    func signUp(
        email: String,
        password: String,
        onCompletion: @escaping () -> Void
    ){
        authController.signUpWithEmail(
            email: email,
            password: password
        ) { [unowned self] res in
            if let user = res.data {
                self.currentUser = User(
                    email: user.email ?? "",
                    id: user.uid,
                    isGuest: user.isAnonymous
                )
                onCompletion()
            } else if let error = res.message {
                Log.d(tag: "Auth", message: error)
            }
        }
    }
    func signInAsGuest() {
        authController.signInAsGuest { res in
            if let user = res.data {
                self.currentUser = User(
                    email: user.email ?? "",
                    id: user.uid,
                    isGuest: user.isAnonymous
                )
            } else if let error = res.message {
                Log.d(tag: "Auth", message: error)
            }
        }
    }
    func checkAndSignInAsGuest() {
        authController.checkAuthAndSignAsGuest(onCompletion: { res in
            if let user = res.data {
                self.currentUser = User(
                    email: user.email ?? "",
                    id: user.uid,
                    isGuest: user.isAnonymous
                )
                Log.d(tag: "Auth", message: user)
            } else if let error = res.message {
                Log.d(tag: "Auth", message: error)
            }
        })
    }
    
    func deleteCurrentUser(onCompletion: @escaping () -> Void){
        authController.deleteUser()
        onCompletion()
    }
    
    func signOut(onCompletion: @escaping () -> Void) {
        authController.signOut()
        onCompletion()
    }
    


    struct User {
        var email: String
        var id: String
        var isGuest: Bool
    }
    
  
}
