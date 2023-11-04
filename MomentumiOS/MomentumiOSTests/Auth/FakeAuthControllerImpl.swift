//
//  AuthController.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable
import MomentumSDK

class FakeAuthControllerImpl: AuthController {

  

    
    var currentUser: AuthenticationUserResponse? = .init(uid: "1001-00", email: "mwai.developer@gmai.com", isAnonymous: false)
    func checkAuthAndSignAsGuest(onCompletion: @escaping (AuthenticationAuthResult<AuthenticationUserResponse>) -> Void) {
        
    }
    
    func deleteUser() {
        currentUser = nil
    }
    
    func getCurrentUser(onCompletion: @escaping (AuthenticationAuthResult<AuthenticationUserResponse>) -> Void) {
    }
    
    func isUserSignedIn(onCompletion: @escaping (KotlinBoolean) -> Void) {
        if currentUser != nil {
            onCompletion(true)
        } else {
            onCompletion(false)
        }
    }
    
    func signInAsGuest(onCompletion: @escaping (AuthenticationAuthResult<AuthenticationUserResponse>) -> Void) {
        currentUser = .init(uid: "1002-01", email: "", isAnonymous: true)
    }
    
    func signInWithEmail(email: String, password: String, onCompletion: @escaping (AuthenticationAuthResult<AuthenticationUserResponse>) -> Void) {
        currentUser = .init(uid: "1003-02", email: email, isAnonymous: false)
    }
    
    func signOut() {
        currentUser = nil
    }
    
    func signUpWithEmail(email: String, password: String, onCompletion: @escaping (AuthenticationAuthResult<AuthenticationUserResponse>) -> Void) {
        currentUser = .init(uid: "1004-03", email: email, isAnonymous: false)
    }
}
