//
//  PaymentSummaryContentViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/13/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
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
            if isActive == false {
                selectedLabels.remove(at: index)
                resetPrevious()
                if selectedLabels.isEmpty {
                    resetAmounts()
                }
            }
        }else {
            selectedLabels.append(type)
            if selectedLabels.count > 1 {
                previousOption = ToggleOption(amount:  0, type: type)
                resetPrevious()
            }
            Log.d(tag: "ADD/\(type)", message: selectedLabels)
            Log.d(tag: "Previous/\(previousOption.type)", message: previousOption)
            
        }
        if selectedLabels.count == 1 {
            resetAmounts()
            switch selectedLabels.first {
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
                
            case .none:
                break
            }
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
                    if previousOption.type == .tithe {
                        setOfferingAmount(amount: amount)
                    } else {
                        setTitheAmount(amount: amount)
                    }
                case .missions:
                    if previousOption.type == .missions {
                        setOfferingAmount(amount: amount)
                    } else {
                        setMissionsAmount(amount: amount)
                    }
                case .specialSpeaker:
                    if previousOption.type == .specialSpeaker {
                        setOfferingAmount(amount: amount)
                    } else {
                        setSpeakersAmount(amount: amount)
                    }
                case .other:
                    if previousOption.type == .other {
                        setOfferingAmount(amount: amount)
                    } else {
                        setOtherAmount(amount: amount)
                    }
                }
                
                
            case .tithe:
                switch selectedLabels[1] {
                case .offering:
                    if previousOption.type == .offering {
                        setTitheAmount(amount: amount)
                    } else {
                        setOfferingAmount(amount: amount)
                    }
                case .tithe:
                    break
                case .missions:
                    if previousOption.type == .missions {
                        setTitheAmount(amount: amount)
                    } else {
                        setMissionsAmount(amount: amount)
                    }
                case .specialSpeaker:
                    if previousOption.type == .specialSpeaker {
                        setTitheAmount(amount: amount)
                    } else {
                        setSpeakersAmount(amount: amount)
                    }
                case .other:
                    if previousOption.type == .other {
                        setTitheAmount(amount: amount)
                    } else {
                        setOtherAmount(amount: amount)
                    }
                }
                
                
            case .missions:
                switch selectedLabels[1] {
                case .offering:
                    if previousOption.type == .offering {
                        setMissionsAmount(amount: amount)
                    } else {
                        setOfferingAmount(amount: amount)
                    }
                case .tithe:
                    if previousOption.type == .tithe {
                        setMissionsAmount(amount: amount)
                    } else {
                        setTitheAmount(amount: amount)
                    }
                case .missions:
                    break
                case .specialSpeaker:
                    if previousOption.type == .specialSpeaker {
                        setMissionsAmount(amount: amount)
                    } else {
                        setSpeakersAmount(amount: amount)
                    }
                case .other:
                    if previousOption.type == .other {
                        setMissionsAmount(amount: amount)
                    } else {
                        setOtherAmount(amount: amount)
                    }
                }
                
                
            case .specialSpeaker:
                switch selectedLabels[1] {
                case .offering:
                    if previousOption.type == .offering {
                        setSpeakersAmount(amount: amount)
                    } else {
                        setOfferingAmount(amount: amount)
                    }
                case .tithe:
                    if previousOption.type == .tithe {
                        setSpeakersAmount(amount: amount)
                    } else {
                        setTitheAmount(amount: amount)
                    }
                case .missions:
                    if previousOption.type == .missions {
                        setSpeakersAmount(amount: amount)
                    } else {
                        setMissionsAmount(amount: amount)
                    }
                case .specialSpeaker:
                    break
                case .other:
                    if previousOption.type == .other {
                        setSpeakersAmount(amount: amount)
                    } else {
                        setOtherAmount(amount: amount)
                    }
                }
                
                
            case .other:
                switch selectedLabels[1] {
                case .offering:
                    if previousOption.type == .offering {
                        setOtherAmount(amount: amount)
                    } else {
                        setOfferingAmount(amount: amount)
                    }
                case .tithe:
                    if previousOption.type == .tithe {
                        setOtherAmount(amount: amount)
                    } else {
                        setTitheAmount(amount: amount)
                    }
                case .missions:
                    if previousOption.type == .missions {
                        setOtherAmount(amount: amount)
                    } else {
                        setMissionsAmount(amount: amount)
                    }
                case .specialSpeaker:
                    if previousOption.type == .specialSpeaker {
                        setOtherAmount(amount: amount)
                    } else {
                        setSpeakersAmount(amount: amount)
                    }
                case .other:
                    break
                }
            }
            
        default:
            break
        }
    }
    func getTransactionDescription() -> String {
        if selectedLabels.count < 2 {
            var description = ""
            switch selectedLabels[0] {
            case .offering:
                description = "$\(offeringAmount): \(selectedLabels[0].rawValue)"
            case .tithe:
                description = "$\(titheAmount): \(selectedLabels[0].rawValue)"
            case .missions:
                description = "$\(missionsAmount): \(selectedLabels[0].rawValue)"
            case .specialSpeaker:
                description = "$\(speakersAmount): \(selectedLabels[0].rawValue)"
            case .other:
                description = "$\(otherAmount): \(selectedLabels[0].rawValue)"
            }
            return description
        } else {
            var description = ""
            selectedLabels.forEach { toggle in
                if selectedLabels.last == toggle {
                    switch toggle {
                    case .offering:
                        description += "$\(offeringAmount): \(toggle.rawValue)"
                    case .tithe:
                        description += "$\(titheAmount): \(toggle.rawValue)"
                    case .missions:
                        description += "$\(missionsAmount): \(toggle.rawValue)"
                    case .specialSpeaker:
                        description += "$\(speakersAmount): \(toggle.rawValue)"
                    case .other:
                        description += "$\(otherAmount): \(toggle.rawValue)"
                    }
                } else {
                    switch toggle {
                    case .offering:
                        description += "$\(offeringAmount): \(toggle.rawValue), "
                    case .tithe:
                        description += "$\(titheAmount): \(toggle.rawValue), "
                    case .missions:
                        description += "$\(missionsAmount): \(toggle.rawValue), "
                    case .specialSpeaker:
                        description += "$\(speakersAmount): \(toggle.rawValue), "
                    case .other:
                        description += "$\(otherAmount): \(toggle.rawValue), "
                    }
                    
                }
            }
            return description
        }
    }

    private func subtractAmounts(amounts: String...) -> String {
        let reminder = (Int(amounts[0]) ?? 0) - (Int(amounts[1]) ?? 0)
        return String(reminder)
    }
    
    private func addAmounts(amounts: String...) -> String {
        let reminder = (Int(amounts[0]) ?? 0) + (Int(amounts[1]) ?? 0)
        return String(reminder)
    }
    
    private func setOtherAmount(amount: String) {
        if previousOption.amount > (Int(amount) ?? 0) {
            otherAmount = addAmounts(amounts: totalAmount, amount)
        } else {
            otherAmount = subtractAmounts(amounts: totalAmount, amount)
        }
    }
    
    private func setOfferingAmount(amount: String) {
        if previousOption.amount > (Int(amount) ?? 0) {
            offeringAmount = addAmounts(amounts: totalAmount, amount)
        } else {
            offeringAmount = subtractAmounts(amounts: totalAmount, amount)
        }
    }
    
    private func setTitheAmount(amount: String) {
        if previousOption.amount > (Int(amount) ?? 0) {
            titheAmount = addAmounts(amounts: totalAmount, amount)
        } else {
            titheAmount = subtractAmounts(amounts: totalAmount, amount)
        }
    }
    
    private func setMissionsAmount(amount: String) {
        if previousOption.amount > (Int(amount) ?? 0) {
            missionsAmount = addAmounts(amounts: totalAmount, amount)
        } else {
            missionsAmount = subtractAmounts(amounts: totalAmount, amount)
        }
    }
    
    private func setSpeakersAmount(amount: String) {
        if previousOption.amount > (Int(amount) ?? 0) {
            speakersAmount = addAmounts(amounts: totalAmount, amount)
        } else {
            speakersAmount = subtractAmounts(amounts: totalAmount, amount)
        }
    }
    
    
    private func resetPrevious() {
        switch previousOption.type {
        case .offering:
            offeringAmount = "0"
        case .tithe:
            titheAmount = "0"
        case .missions:
            missionsAmount = "0"
        case .specialSpeaker:
            speakersAmount = "0"
        case .other:
            otherAmount = "0"
        }
    }
    
    private func resetAmounts() {
        offeringAmount = "0"
        titheAmount = "0"
        missionsAmount = "0"
        speakersAmount = "0"
        otherAmount = "0"
    }
}


