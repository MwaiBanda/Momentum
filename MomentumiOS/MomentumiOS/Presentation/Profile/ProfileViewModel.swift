//
//  ProfileViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MomentumSDK

class ProfileViewModel: ObservableObject {
    @Inject private var userController: UserController
    @Inject private var billingAddressController: BillingAddressController
    
    /* Contact Information */
    @Published var fullname = ""
    @Published var phone = ""
    @Published var email = ""
    @Published var password = "Ngosa1978"
    @Published var createdOn = ""
    
    /* Billing Information */
    @Published var streetAddress = ""
    @Published var apt = ""
    @Published var city = ""
    @Published var zipCode = ""

    /* Card State */
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
    
    func addUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String
    ) {
        userController.addMomentumUser(
            fullname: fullname,
            phone: phone,
            password: password,
            email: email,
            createdOn: createdOn,
            userId: userId
        ) { [unowned self] in
            self.userController.postUser(
                user: User(
                    fullname: fullname,
                    email: email,
                    phone: phone,
                    userId: userId,
                    createdOn: createdOn
                )
            )
            self.billingAddressController.addBillingAddress(
                streetAddress: "",
                apt: "",
                city: "",
                zipCode: "",
                userId: userId
            )
        }
    }
    
    func getContactInformation(userId: String, onCompletion: @escaping () -> Void) {
        userController.getMomentumUserById(userId: userId) { [unowned self] user in
            if let user = user {
                fullname = user.fullname
                phone = user.phone
                email = user.email
                password = user.password
                createdOn = user.created_on
                onCompletion()
            }
        }
    }
    
    func getBillingInformation(userId: String) {
        billingAddressController.getBillingAddressByUserId(userId: userId) { [unowned self] address in
            if let address = address {
                streetAddress = address.street_address
                apt = address.apt ?? ""
                city = address.city
                zipCode = address.zip_code
            }
        }
    }
    
    func updateFullname(userId: String){
        userController.updateUserFullname(userID: userId, fullname: fullname)
    }
    
    
}
