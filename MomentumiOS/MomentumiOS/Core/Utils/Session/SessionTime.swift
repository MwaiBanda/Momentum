//
//  SessionTime.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/27/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct SessionTime {
    static func getCurrentTime() -> String {
        let date = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "mm/dd/yyyy"
        return dateFormatter.string(from: date)
    }
}
