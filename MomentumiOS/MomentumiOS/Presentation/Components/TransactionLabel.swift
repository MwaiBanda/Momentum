//
//  TransactionLabel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct TransactionLabel: View {
    var description: String
    var date: String
    var amount: Double

    var body: some View {
        HStack {
            Image("momentum")
                .resizable()
                .aspectRatio( contentMode: .fit)
                .frame(width: 45, height: 45)
            HStack {
                VStack(alignment: .leading) {
                    Text("Momentum")
                        .font(.headline)
                        .fontWeight(.heavy)
                    Text("Paid \(description)")
                        .font(.subheadline)
                        .foregroundColor(Color(.systemGray2))

                }
                Spacer()
                VStack(alignment: .trailing) {
                    Text("$\(amount, specifier: "%.2f")")
                        .font(.headline)
                        .fontWeight(.heavy)
                    Text(date)
                        .font(.subheadline)
                        .foregroundColor(Color(.systemGray2))
                }
            }
        }
        .padding()
    }
}
