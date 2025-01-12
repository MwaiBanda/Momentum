//
//  DescriptionCard.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 1/11/25.
//  Copyright © 2025 Momentum. All rights reserved.
//
import SwiftUI


struct DescriptionCard: View {
    var title: String
    var description: String
    
    var body: some View {
        Card {
            HStack {
                VStack(alignment: .leading) {
                    Text(title)
                        .font(.title3)
                        .fontWeight(.bold)
                        .foregroundColor(Color.black)
                    Text(description)
                        .foregroundColor(.gray)
                }
                Spacer()
                Image(systemName: "chevron.right")
                    .foregroundColor(Color.black)
            }
            .padding(5)
            .padding(.horizontal, 10)
            .padding(.vertical, 10)
        }
        .frame(maxHeight: 70)
        .padding(.horizontal, 10)
        .padding(.top, 5)
    }
}
