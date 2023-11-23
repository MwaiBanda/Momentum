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

class EventViewModel: ObservableObject {
    @Inject private var eventController: EventController

    func getEvents(onCompletion: @escaping ([GroupedEvent]) -> Void) {
        eventController.getAllEvents { res in
            if let events = res.data as? [GroupedEvent] {
                onCompletion(events)
            }
        }
    }
}
