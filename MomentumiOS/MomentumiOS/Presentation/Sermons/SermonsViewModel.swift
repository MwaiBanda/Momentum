//
//  SermonsView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import MomentumSDK

class SermonsViewModel: ObservableObject  {
    private var controller: SermonController
    @Published var sermons = [Sermon]()
    
    init(controller: SermonController = SermonControllerImpl()) {
        self.controller = controller
    }
    
    func fetchSermons() {
        controller.getSermon(pageNumber: 1) { res in
            if let sermons = res.data {
                self.sermons = sermons as? [Sermon] ?? []
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
            }
        }
    }
    
}
