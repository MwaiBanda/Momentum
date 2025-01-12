//
//  RecipentInfo.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 1/11/25.
//  Copyright © 2025 Momentum. All rights reserved.
//
import SwiftUI

struct RecipentInfo: View {
    var title: String
    var description: String
    var icon: String
    
    var body: some View {
        HStack(alignment: .firstTextBaseline) {
            Image(systemName: icon)
                .imageScale(.large)
                .frame(width: 40)
            VStack(alignment: .leading) {
                Text(title)
                    .font(.headline)
                    .bold()
                Text(description)
                    .font(.subheadline)
                
            }
        }.padding(.top, 10)
    }
}
