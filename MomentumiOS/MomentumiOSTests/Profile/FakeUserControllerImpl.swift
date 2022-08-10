//
//  FakeUserControlller.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable
import MomentumSDK

class FakeUserControllerImpl: UserController {
    var remoteUsers = [User]()
    var localUsers = [MomentumUser]()
    
    func addMomentumUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String,
        onCompletion: @escaping () -> Void
    ) {
        localUsers.append(
            MomentumUser(
                id: Int64(localUsers.count + 1),
                fullname: fullname,
                phone: phone,
                password: password,
                email: email,
                created_on: email,
                user_id: userId
            )
        )
        onCompletion()
    }
    
    func deleteMomentumUserByUserId(
        userId: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        localUsers.remove(at: index)
    }
    
    func deleteUser(
        userID: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = remoteUsers.firstIndex(where: { $0.userId == userID })!
        remoteUsers.remove(at: index)
        onCompletion()
    }
    
    func getMomentumUserById(
        userId: String,
        onCompletion: @escaping (MomentumUser?) -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        onCompletion(localUsers[index])

    }
    
    func getUser(
        userId: String,
        onCompletion: @escaping (User) -> Void
    ) {
        let index = remoteUsers.firstIndex(where: { $0.userId == userId })!
        onCompletion(remoteUsers[index])
    }
    
    func postUser(user: User) {
        remoteUsers.append(user)
    }
    
    func updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        let user = localUsers.remove(at: index)
        localUsers.insert(MomentumUser(
            id: user.id,
            fullname: user.fullname,
            phone: user.phone,
            password: user.password,
            email: email,
            created_on: user.created_on,
            user_id: user.user_id
        ), at: index)
        onCompletion()
    }
    
    func updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        let user = localUsers.remove(at: index)
        localUsers.insert(MomentumUser(
            id: user.id,
            fullname: fullname,
            phone: user.phone,
            password: user.password,
            email: user.email,
            created_on: user.created_on,
            user_id: user.user_id
        ), at: index)
        onCompletion()
    }
    
    func updateMomentumUserPasswordUserId(
        userId: String,
        password: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        let user = localUsers.remove(at: index)
        localUsers.insert(MomentumUser(
            id: user.id,
            fullname: user.fullname,
            phone: user.phone,
            password: password,
            email: user.email,
            created_on: user.created_on,
            user_id: user.user_id
        ), at: index)
        onCompletion()
    }
    
    
    func updateMomentumUserPhoneByUserId(
        userId: String,
        phone: String,
        onCompletion: @escaping () -> Void
    ) {
        let index = localUsers.firstIndex(where: { $0.user_id == userId })!
        let user = localUsers.remove(at: index)
        localUsers.insert(MomentumUser(
            id: user.id,
            fullname: user.fullname,
            phone: phone,
            password: user.password,
            email: user.email,
            created_on: user.created_on,
            user_id: user.user_id
        ), at: index)
        onCompletion()
    }
    
    func updatePhoneByUserId(userId: String, phone: String) {
        let index = remoteUsers.firstIndex(where: { $0.userId == userId })!
        let user = remoteUsers.remove(at: index)
        remoteUsers.insert(User(
            fullname: user.fullname,
            email: user.email,
            phone: phone,
            userId: user.userId,
            createdOn: user.createdOn
        ), at: index)
    }
    
    func updateUserEmail(userID: String, email: String) {
        let index = remoteUsers.firstIndex(where: { $0.userId == userID })!
        let user = remoteUsers.remove(at: index)
        remoteUsers.insert(User(
            fullname: user.fullname,
            email: email,
            phone: user.phone,
            userId: user.userId,
            createdOn: user.createdOn
        ), at: index)
    }
    
    func updateUserFullname(userID: String, fullname: String) {
        let index = remoteUsers.firstIndex(where: { $0.userId == userID }) ?? 0
        let user = remoteUsers.remove(at: index)
        remoteUsers.insert(User(
            fullname: fullname,
            email: user.email,
            phone: user.phone,
            userId: user.userId,
            createdOn: user.createdOn
        ), at: index)
    }
}

