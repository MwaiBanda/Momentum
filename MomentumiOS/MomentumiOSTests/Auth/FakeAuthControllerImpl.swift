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
    var currentUser: UserResponse? = .init(uid: "1001-00", email: "mwai.developer@gmai.com", isAnonymous: false)
    func checkAuthAndSignAsGuest(onCompletion: @escaping (Result<UserResponse>) -> Void) {
        
        onCompletion(ResultSuccess(data: currentUser))
    }
    
    func deleteUser() {
        currentUser = nil
    }
    
    func getCurrentUser(onCompletion: @escaping (Result<UserResponse>) -> Void) {
        onCompletion(ResultSuccess(data: currentUser))
    }
    
    func isUserSignedIn(onCompletion: @escaping (KotlinBoolean) -> Void) {
        if currentUser != nil {
            onCompletion(true)
        } else {
            onCompletion(false)
        }
    }
    
    func signInAsGuest(onCompletion: @escaping (Result<UserResponse>) -> Void) {
        currentUser = .init(uid: "1002-01", email: "", isAnonymous: true)
        onCompletion(ResultSuccess(data: currentUser))
    }
    
    func signInWithEmail(email: String, password: String, onCompletion: @escaping (Result<UserResponse>) -> Void) {
        currentUser = .init(uid: "1003-02", email: email, isAnonymous: false)
        onCompletion(ResultSuccess(data: currentUser))
    }
    
    func signOut() {
        currentUser = nil
    }
    
    func signUpWithEmail(email: String, password: String, onCompletion: @escaping (Result<UserResponse>) -> Void) {
        currentUser = .init(uid: "1004-03", email: email, isAnonymous: false)
        onCompletion(ResultSuccess(data: currentUser))
    }
}
