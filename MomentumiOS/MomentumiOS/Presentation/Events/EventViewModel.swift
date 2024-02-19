//
//  EventViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/23/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import Foundation
import TinyDi
import MomentumSDK
import Combine

class EventViewModel: ObservableObject {
    @Inject private var eventController: EventController
    @Published var events = [EventGroup]()
    @Published var searchTerm = ""
    var filtered: AnyPublisher<[EventGroup], Never> {
        Publishers.CombineLatest($events, $searchTerm)
            .map { [weak self] events, term in
                events.filter({
                    $0.containsTerm(term: self?.searchTerm ?? "")
                }).map({
                    EventGroup(monthAndYear: $0.monthAndYear, events: $0.events.filter({
                        $0.containsTerm(term: self?.searchTerm ?? "")
                    }))
                })
            }
            .eraseToAnyPublisher()
    }
    
    
    
    func getEvents() {
        eventController.getAllEvents { res in
            if let events = res.data as? [EventGroup] {
                self.events = events
            }
        }
    }
}
