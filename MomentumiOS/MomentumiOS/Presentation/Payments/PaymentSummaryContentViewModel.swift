//
//  PaymentSummaryContentViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/13/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

final class PaymentSummaryContentViewModel: ObservableObject {
    
    @Published var selectedLabels = [ToggleLabel]()
    @Published var previousOption: ToggleOption = ToggleOption(amount: 0)
    /* isSelected State */
    @Published var offeringIsSelected: Bool = false
    @Published var titheIsSelected: Bool = false
    @Published var missionsIsSelected: Bool = false
    @Published var speakersIsSelected: Bool = false
    @Published var otherIsSelected: Bool = false
    
    /* Amount State */
    @Published var offeringAmount: String = "0"
    @Published var titheAmount: String = "0"
    @Published var missionsAmount: String = "0"
    @Published var speakersAmount: String = "0"
    @Published var otherAmount: String = "0"
    @Published var totalAmount: String = "0"
    
    struct ToggleOption {
        var amount: Int
        var type: ToggleLabel = .offering
    }
    
    func processToggle(isActive: Bool, type: ToggleLabel){
        if selectedLabels.contains(where: { $0 == type }) {
            let index = selectedLabels.firstIndex(where: { $0 == type}) ?? 0
            if !isActive {
                selectedLabels.remove(at: index)
                if selectedLabels.isEmpty {
                    resetAmounts()
                } else {
                    switch selectedLabels.last {
                    case .offering:
                        offeringAmount = String((Int(offeringAmount) ?? 0) + previousOption.amount)
                    case .tithe:
                        break
                    case .missions:
                        break
                    case .specialSpeaker:
                        break
                    case .other:
                        break
                    case .none:
                        break
                    }
                    resetPrevious()
                }
            }
        }else {
                selectedLabels.append(type)
                if selectedLabels.count == 1 {
                    switch type {
                    case .offering:
                        offeringAmount = totalAmount
                    case .tithe:
                        titheAmount = totalAmount
                    case .missions:
                        missionsAmount = totalAmount
                    case .specialSpeaker:
                        speakersAmount = totalAmount
                    case .other:
                        otherAmount = totalAmount
                    }
                }
                Log.d(tag: "ADD/\(type)", message: selectedLabels)
            }
        }
    
    func processAmount(amount: String, type: ToggleLabel) {
        previousOption = ToggleOption(amount: Int(amount) ?? 0, type: type)
        switch selectedLabels.count {
        case 2:
            switch selectedLabels[0] {
            case .offering:
                switch selectedLabels[1] {
                case .offering:
                    break
                case .tithe:
                    offeringAmount = getReminder(amount: amount)
                case .missions:
                    break
                case .specialSpeaker:
                    break
                case .other:
                    break
                }
            case .tithe:
                break
            case .missions:
                break
            case .specialSpeaker:
                break
            case .other:
                break
            }
            
        default:
            break
        }
    }
    func getReminder(amount: String) -> String {
        let reminder = (Int(totalAmount) ?? 0) - (Int(amount) ?? 0)
        return String(reminder)
    }
    
    func resetPrevious() {
        switch previousOption.type {
        case .offering:
            break
        case .tithe:
            titheAmount = "0"
        case .missions:
            break
        case .specialSpeaker:
            break
        case .other:
            break
        }
    }
    
    func resetAmounts() {
        offeringAmount = "0"
        titheAmount = "0"
        missionsAmount = "0"
        speakersAmount = "0"
        otherAmount = "0"
    }
}

enum ToggleLabel {
    case offering, tithe, missions, specialSpeaker, other
}
