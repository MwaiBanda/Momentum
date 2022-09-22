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
    @Published var canLoadMoreSermons = true
    @Published private var currentPage = 1
    init(controller: SermonController = SermonControllerImpl()) {
        self.controller = controller
    }
    
    func fetchSermons() {
        currentPage = 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons = sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
            }
        }
    }
    
    func loadMoreSermons() {
        currentPage += 1
        controller.getSermon(pageNumber: Int32(currentPage)) { res in
            if let sermonsResponse = res.data {
                self.sermons += sermonsResponse.sermons
                self.canLoadMoreSermons = sermonsResponse.canLoadMoreSermons
            } else if let error = res.message {
                Log.d(tag: "Sermon", message: error)
                self.canLoadMoreSermons = false
            }
        }
    }
}
