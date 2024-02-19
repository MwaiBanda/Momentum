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
import Combine

@MainActor
class MessageViewModel: ObservableObject {
    @Inject private var messageController: MessageController
    @Inject private var postNoteUseCase: PostNoteUseCase
    @Inject private var updateNoteUseCase: UpdateNoteUseCase
    @Published private var messages = [MessageGroup]()
    @MainActor var series = [String]()
    @Published var searchTerm = ""
    @Published var filteredSeries = ""
    var filtered: AnyPublisher<[MessageGroup], Never> {
        Publishers.CombineLatest3($messages, $searchTerm, $filteredSeries)
            .map { events, term, series in
                return events.filter {
                    $0.containsTerm(term: term)
                }.map {
                    MessageGroup(series: $0.series, messages: $0.messages.filter {
                        $0.containsTerm(term: term)
                    })
                }.filter {
                    $0.series.lowercased().contains(series.lowercased()) || series.isEmpty
                }
            }
            .eraseToAnyPublisher()
    }
        
    func getAllMessages(userId: String, isRefreshing: Bool = false) {
        Task { @MainActor in
            if isRefreshing {
                messageController.clearMessagesCache()
            }
            do {
                try await messageController.getAllMessages(userId: userId) { [weak self] res in
                    if let messages = res.data as? [MessageGroup] {
                        self?.series = messages.map({ $0.series })
                        self?.messages = messages
                    }
                }
            } catch {
                print(error.localizedDescription)
            }
        }
    }
    
    func clearMessagesCache() {
        messageController.clearMessagesCache()
    }
    
    func addNote(note: NoteRequest, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            do {
                try await postNoteUseCase.post(note: note).collect { res in
                    guard let status = res?.status else { fatalError("No Result Found") }
                    switch(status) {
                    case .loading:
                        os_log("[Event[Loading]]")
                        break
                    case .error:
                        guard let message = res?.message else { return }
                        os_log("[Event[Error]]: \(message)")
                        break
                    case .data:
                        guard let note = res?.data else { return }
                        os_log("[Event[Data]] \(note)")
                        onCompletion()
                        break
                    default: break
                    }
                }
            } catch {
                os_log("\(error.localizedDescription)")
            }
        }
    }
    
    
    func updateNote(note: Note.UserNote, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            do {
                try await updateNoteUseCase.update(note: note).collect { res in
                    guard let status = res?.status else { fatalError("No Result Found") }
                    switch(status) {
                    case .loading:
                        os_log("[Event[Loading]] \(res)")
                        break
                    case .error:
                        guard let message = res?.message else { return }
                        os_log("[Event[Error]]: \(message)")
                        break
                    case .data:
                        guard let note = res?.data else { return }
                        os_log("[Event[Data]] \(note)")
                        onCompletion()
                        break
                    default: break
                    }
                }
            } catch {
                os_log("\(error.localizedDescription)")
            }
        }
    }
}


