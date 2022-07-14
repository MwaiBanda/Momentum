//
//  PaymentSummaryContentView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/12/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MapKit

struct PaymentSummaryContentView: View {
    @ObservedObject var offerViewModel: OfferViewModel
    @StateObject private var contentViewModel = PaymentSummaryContentViewModel()
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("SELECT MULTIPLE OPTIONS TO EDIT AMOUNTS")
                .font(.caption)
                .foregroundColor(Color(hex: Constants.momentumOrange))
                .padding(.leading)
            VStack {
                ToggleAmountLabel(
                    title: "Offering",
                    amount: $contentViewModel.offeringAmount,
                    isSelected: $contentViewModel.offeringIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .offering)
                } onAmountChanged: { amount in
                    contentViewModel.processAmount(amount: amount, type: .offering)
                }
                
                Divider()
                ToggleAmountLabel(
                    title: "Tithe",
                    amount: $contentViewModel.titheAmount,
                    isSelected: $contentViewModel.titheIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .tithe)
                } onAmountChanged: { amount in
                    contentViewModel.processAmount(amount: amount, type: .tithe)
                }
                Divider()
                ToggleAmountLabel(
                    title: "Missions",
                    amount: $contentViewModel.missionsAmount,
                    isSelected: $contentViewModel.missionsIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .missions)
                } onAmountChanged: { amount in
                    
                }
                Divider()
                ToggleAmountLabel(
                    title: "Special Speaker",
                    amount: $contentViewModel.speakersAmount,
                    isSelected: $contentViewModel.speakersIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .specialSpeaker)
                } onAmountChanged: { amount in
                    
                }
                
                Divider()
                ToggleAmountLabel(
                    title: "Other",
                    amount: $contentViewModel.otherAmount,
                    isSelected: $contentViewModel.otherIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .other)
                } onAmountChanged: { amount in
                    
                }
            }
            Spacer()
            VStack {
                Divider()
                HStack {
                    Text("Total")
                        .font(.title)
                        .bold()
                    
                    Spacer()
                    HStack(spacing: 0) {
                        Text("$")
                            .fontWeight(.light)
                            .font(.title)
                        Text(contentViewModel.totalAmount)
                            .fontWeight(.light)
                            .font(.title)
                    }
                }.padding()
                Divider()
            }
        }.onAppear{
            contentViewModel.totalAmount = offerViewModel.number
        }
    }
}

struct PaymentSummaryContentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSummaryContentView(offerViewModel: OfferViewModel())
    }
}
