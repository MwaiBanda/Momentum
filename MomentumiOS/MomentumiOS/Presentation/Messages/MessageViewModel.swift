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
import os

@MainActor
class MessageViewModel: ObservableObject {
    @Inject private var messageController: MessageController
    @Inject private var postNoteUseCase: PostNoteUseCase
    @Inject private var updateNoteUseCase: UpdateNoteUseCase

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
    func clearMessagesCache() {
        messageController.clearMessagesCache()
    }
    func addNote(note: NoteRequest, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            await addNote(note: note, onCompletion: onCompletion)
        }
    }
    
    private func addNote(note: NoteRequest, onCompletion: @escaping () -> Void) async {
        do {
            try await postNoteUseCase.invoke(note: note).collect { res in
                if let note = res?.data {
                    os_log("[Event[Data]] \(note)")
                    onCompletion()
                } else if let message = res?.message {
                    os_log("[Event[Error]]: \(message)")
                    
                } else  {
                    os_log("[Event[Loading]]")
                }
            }
        } catch {
            os_log("\(error.localizedDescription)")
        }
        
    }
    
    func updateNote(note: Note.UserNote, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            await updateNote(note: note, onCompletion: onCompletion)
        }
    }
    
    private func updateNote(note: Note.UserNote, onCompletion: @escaping () -> Void) async {
        do {
            try await updateNoteUseCase.invoke(note: note).collect { res in
                if let note = res?.data {
                    os_log("[Event[Data]] \(note)")
                    onCompletion()
                } else if let message = res?.message {
                    os_log("[Event[Error]]: \(message)")
                } else  {
                    os_log("[Event[Loading]] \(res)")
                }
            }
        } catch {
            os_log("\(error.localizedDescription)")
        }
        
    }
}
