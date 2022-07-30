//
//  PaymentSummaryContentView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/12/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI
import MomentumSDK

struct PaymentSummaryContentView: View {
    @ObservedObject var offerViewModel: OfferViewModel
    @ObservedObject var contentViewModel: PaymentSummaryContentViewModel

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("SELECT MULTIPLE OPTIONS TO EDIT AMOUNTS")
                .font(.caption)
                .foregroundColor(Color(hex: Constants.momentumOrange))
                .padding(.leading)
            VStack {
                
                ToggleAmountLabel(
                    title: MultiplatformConstants.shared.OFFERING,
                    amount: $contentViewModel.offeringAmount,
                    isSelected: $contentViewModel.offeringIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    DispatchQueue.main.async {
                    contentViewModel.processToggle(isActive: isActive, type: .offering)
                    }
                } onAmountChanged: { amount in
                    DispatchQueue.main.async {
                        contentViewModel.processAmount(amount: amount, type: .offering)
                    }
                }.disabled(
                    contentViewModel.selectedLabels.count == 2 &&
                    !contentViewModel.selectedLabels.contains(where: { $0 == .offering})
                )
                
                Divider()
                ToggleAmountLabel(
                    title: MultiplatformConstants.shared.TITHE,
                    amount: $contentViewModel.titheAmount,
                    isSelected: $contentViewModel.titheIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    DispatchQueue.main.async {
                    contentViewModel.processToggle(isActive: isActive, type: .tithe)
                    }
                } onAmountChanged: { amount in
                    DispatchQueue.main.async {
                    contentViewModel.processAmount(amount: amount, type: .tithe)
                    }
                }.disabled(
                    contentViewModel.selectedLabels.count == 2 &&
                    !contentViewModel.selectedLabels.contains(where: { $0 == .tithe})
                )
                
                Divider()
                ToggleAmountLabel(
                    title: MultiplatformConstants.shared.MISSIONS,
                    amount: $contentViewModel.missionsAmount,
                    isSelected: $contentViewModel.missionsIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    contentViewModel.processToggle(isActive: isActive, type: .missions)
                } onAmountChanged: { amount in
                    DispatchQueue.main.async {
                    contentViewModel.processAmount(amount: amount, type: .missions)
                    }
                }.disabled(
                    contentViewModel.selectedLabels.count == 2 &&
                    !contentViewModel.selectedLabels.contains(where: { $0 == .missions})
                )
                
                Divider()
                ToggleAmountLabel(
                    title: MultiplatformConstants.shared.SPECIAL_SPEAKER,
                    amount: $contentViewModel.speakersAmount,
                    isSelected: $contentViewModel.speakersIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    DispatchQueue.main.async {
                    contentViewModel.processToggle(isActive: isActive, type: .specialSpeaker)
                    }
                } onAmountChanged: { amount in
                    DispatchQueue.main.async {
                    contentViewModel.processAmount(amount: amount, type: .specialSpeaker)
                    }
                }.disabled(
                    contentViewModel.selectedLabels.count == 2 &&
                    !contentViewModel.selectedLabels.contains(where: { $0 == .specialSpeaker})
                )
                
                Divider()
                ToggleAmountLabel(
                    title: MultiplatformConstants.shared.OTHER,
                    amount: $contentViewModel.otherAmount,
                    isSelected: $contentViewModel.otherIsSelected,
                    showLabel: !contentViewModel.selectedLabels.isEmpty
                ) { isActive in
                    DispatchQueue.main.async {
                    contentViewModel.processToggle(isActive: isActive, type: .other)
                    }
                } onAmountChanged: { amount in
                    DispatchQueue.main.async {
                    contentViewModel.processAmount(amount: amount, type: .other)
                    }
                }.disabled(
                    contentViewModel.selectedLabels.count == 2 &&
                    !contentViewModel.selectedLabels.contains(where: { $0 == .other})
                )
            }
            Spacer()
            VStack {
                Divider()
                HStack {
                    Text("Total")
                        .font(.title)
                        .bold()
                    
                    Spacer()
                    
                    Text(offerViewModel.isDecimalMode ? offerViewModel.displayNumber : offerViewModel.displayNumber + ".00")
                    .fontWeight(.light)
                    .font(.title)
                    
                }.padding()
                Divider()
            }
        }
        .onAppear{
            contentViewModel.totalAmount = offerViewModel.isDecimalMode ? String(offerViewModel.number.dropLast(3)) :  offerViewModel.number
        }
    }
}

struct PaymentSummaryContentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSummaryContentView(offerViewModel: OfferViewModel(), contentViewModel: PaymentSummaryContentViewModel())
    }
}
