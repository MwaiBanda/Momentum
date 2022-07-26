//
//  ProfileViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

enum ProfileCard {
    case contactInfo
    case billingInfo
    case manageAcc
    case techSupport
    case feedback
    case information
}
class ProfileViewModel: ObservableObject {
    @Published var isContactInfoExpanded = false
    @Published var isBillingInfoExpanded = false
    @Published var isManageAccExpanded = false
    @Published var isTechSupportExpanded = false
    @Published var isFeedbackExpanded = false
    @Published var isInformationExpanded = false
    
    func cardToggle(card: ProfileCard) {
        switch card {
        case .contactInfo:
            isContactInfoExpanded.toggle()
        case .billingInfo:
            isBillingInfoExpanded.toggle()
        case .manageAcc:
            isManageAccExpanded.toggle()
        case .techSupport:
            isTechSupportExpanded.toggle()
        case .feedback:
            isFeedbackExpanded.toggle()
        case .information:
            isInformationExpanded.toggle()
        }
    }

    func closeCards(cards: ProfileCard...) {
        cards.forEach { card in
            switch card {
            case .contactInfo:
                isContactInfoExpanded = false
            case .billingInfo:
                isBillingInfoExpanded = false
            case .manageAcc:
                isManageAccExpanded = false
            case .techSupport:
                isTechSupportExpanded = false
            case .feedback:
                isFeedbackExpanded = false
            case .information:
                isInformationExpanded = false
            }
        }
    }
}
