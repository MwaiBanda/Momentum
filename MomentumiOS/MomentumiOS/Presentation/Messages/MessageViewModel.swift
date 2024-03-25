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
    @Inject private var messageNotesUseCases: MessageUseCases
    @Published var messages = [MessageGroup]()
    @Published var series = [String]()
    @Published var searchTerm = ""
    @Published var searchTag = ""
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
        
    func getAllMessages(userId: String, isRefreshing: Bool = false,  onCompletion: @escaping ([MessageGroup]) -> Void = { _ in }) {
        if isRefreshing {
           clearMessagesCache()
        }
        Task { @MainActor in
            do {
                try await messageNotesUseCases.read.execute(userId: userId).collect { [weak self] res in
                    guard let status = res?.status else { fatalError("No Result Found") }
                    switch(status) {
                    case .loading:
                        os_log("[MessageGroup[Loading]]")
                        break
                    case .error:
                        guard let message = res?.message  else { return }
                        os_log("[MessageGroup[Error]]: \(message)")
                        break
                    case .data:
                        guard let messages = res?.data as? [MessageGroup] else { return }
                        DispatchQueue.main.async {
                            self?.messages = messages
                            self?.series = messages.map({ $0.series })
                        }
                        os_log("[MessageGroup[Data]]")
                        onCompletion(messages)
                        break
                    default: break
                    }
                }
            } catch {
                os_log("\(error.localizedDescription)")
            }
        }
    }
    
    func clearMessagesCache() {
        URLCache.shared.removeAllCachedResponses()
        let cookieStore = HTTPCookieStorage.shared
        for cookie in cookieStore.cookies ?? [] {
            cookieStore.deleteCookie(cookie)
        }
        messageNotesUseCases.clearCache.execute(key: MultiplatformConstants.shared.MESSAGE_KEY)
    }
    
    func shiftSearchTag() {
        MessageViewModel.searchTags.append(MessageViewModel.searchTags.removeFirst())
        searchTag = MessageViewModel.searchTags.first ?? ""
    }

    
    func addNote(note: NoteRequest, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            do {
                try await messageNotesUseCases.create.execute(note: note).collect { res in
                    guard let status = res?.status else { fatalError("No Result Found") }
                    switch(status) {
                    case .loading:
                        os_log("[NoteRequest[Loading]]")
                        break
                    case .error:
                        guard let message = res?.message else { return }
                        os_log("[NoteRequest[Error]]: \(message)")
                        break
                    case .data:
                        guard let note = res?.data else { return }
                        os_log("[NoteRequest[Data]] \(note)")
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
                try await messageNotesUseCases.update.execute(note: note).collect { res in
                    guard let status = res?.status else { fatalError("No Result Found") }
                    switch(status) {
                    case .loading:
                        os_log("[UserNote[Loading]] \(res)")
                        break
                    case .error:
                        guard let message = res?.message else { return }
                        os_log("[UserNote[Error]]: \(message)")
                        break
                    case .data:
                        guard let note = res?.data else { return }
                        os_log("[UserNote[Data]] \(note)")
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
    
    func deleteNote(noteId: String, onCompletion: @escaping () -> Void) {
        Task { @MainActor in
            do {
                try await messageNotesUseCases.delete_.execute(noteId: noteId).collect { res in
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
    
    static var searchTags = [
        "For Message",
        "By Series",
        "For Preachers",
        "By Date"
    ]
}


