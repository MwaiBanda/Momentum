//
//  MessageViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/6/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import Foundation
import MomentumSDK
import TinyDi

class MessageViewModel: ObservableObject {
    @Inject private var messageController: MessageController
    
    func getAllMessages(userId: String, isRefreshing: Bool = false, onCompletion: @escaping ([Message]) -> Void) {
        if isRefreshing {
            messageController.clearMessagesCache()
        }
        messageController.getAllMessages(userId: userId) { res in
            if let meals = res.data as? [Message] {
                onCompletion(meals)
            }
        }
    }
}
