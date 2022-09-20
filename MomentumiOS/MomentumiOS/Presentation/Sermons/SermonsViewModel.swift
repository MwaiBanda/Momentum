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
    init(controller: SermonController = SermonControllerImpl()) {
        self.controller = controller
    }
    
    func fetch() {
        controller.getSermon(pageNumber: 1) { res in
            if let sermons = res.data {
                print(sermons)
            }
        }
    }
    
}
