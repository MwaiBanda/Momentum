//
//  AccountView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MomentumSDK
import KeyboardToolbar

struct ProfileView: View {
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var session: Session
    @StateObject private var profileViewModel = ProfileViewModel()
    @State private var showAlert = false
    var body: some View {
        VStack(alignment: .leading) {
            Divider()
            Text(MultiplatformConstants.shared.PROFILE_SUBHEADING.uppercased())
                .font(.caption)
                .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                .padding(.leading)
                .padding(.leading, 5)
            HStack {
                circleIcon
                    .padding(.leading)
                
                VStack(alignment: .leading) {
                    Text(profileViewModel.fullname.isEmpty ? "Guest" : profileViewModel.fullname)
                        .font(.title3)
                        .fontWeight(.heavy)
                    Text(profileViewModel.createdOn.isEmpty ? "Create an Account or Sign In" : "Est. \(profileViewModel.createdOn)")
                        .font(.caption)
                        .foregroundColor(.gray)
                    
                }
            }.padding(.vertical)
            ScrollViewReader { proxy in
                ScrollView(showsIndicators: false) {
                    BasePlainExpandableCard(
                        contentHeight: 300,
                        isExpanded: $profileViewModel.isContactInfoExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "person.fill.viewfinder")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.CONTACT_INFORMATION)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                TitledTextField(
                                    title: MultiplatformConstants.shared.FULLNAME,
                                    text: $profileViewModel.fullname
                                ) {
                                    profileViewModel.updateFullname(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.PHONE,
                                    text: $profileViewModel.phone
                                ) {
                                    profileViewModel.updatePhone(userId: session.currentUser?.id ?? "")
                                }
                                .textContentType(.oneTimeCode)
                                .keyboardType(.numberPad)
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.EMAIL,
                                    text: $profileViewModel.email
                                ) {
                                    profileViewModel.updateEmail(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.PASSWORD,
                                    text:  $profileViewModel.password
                                ) {
                                    profileViewModel.updatePassword(userId: session.currentUser?.id ?? "")
                                }
                            }
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .contactInfo)
                            profileViewModel.closeCards(
                                cards: .billingInfo, .manageAcc, .techSupport, .feedback, .information
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.contactInfo, anchor: .bottom)
                                }
                            }
                        }
                    ).id(ProfileCard.contactInfo)
                    
                    BasePlainExpandableCard(
                        contentHeight: 300,
                        isExpanded: $profileViewModel.isBillingInfoExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "person.text.rectangle")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.BILLING_INFORMATION)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            Group {
                                TitledTextField(
                                    title: MultiplatformConstants.shared.STREET_ADDRESS,
                                    text: $profileViewModel.streetAddress
                                ) {
                                    profileViewModel.updateStreetAddress(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.APT,
                                    text: $profileViewModel.apt
                                ) {
                                    profileViewModel.updateApt(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.CITY,
                                    text: $profileViewModel.city
                                ) {
                                    profileViewModel.updateCity(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(
                                    title: MultiplatformConstants.shared.ZIP_CODE,
                                    text: $profileViewModel.zipCode
                                ) {
                                    profileViewModel.updateZipCode(userId: session.currentUser?.id ?? "")
                                }
                                .textContentType(.oneTimeCode)
                                .keyboardType(.numberPad)
                            }
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .billingInfo)
                            profileViewModel.closeCards(
                                cards: .contactInfo, .manageAcc, .techSupport, .feedback, .information
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.billingInfo, anchor: .bottom)
                                }
                            }
                        }
                    ).id(ProfileCard.billingInfo)
                    
                    BasePlainExpandableCard(
                        contentHeight: 250,
                        isExpanded: $profileViewModel.isManageAccExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "trash")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.MANAGE_ACCOUNT)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack(alignment: .leading) {
                                Text(MultiplatformConstants.shared.DELETE_ACCOUNT)
                                    .fontWeight(.heavy)
                                    .font(.title3)
                                Text(MultiplatformConstants.shared.DELETION_WARNING)
                                    .foregroundColor(.gray)
                                Button { showAlert.toggle() } label: {
                                    VStack {
                                        Text(MultiplatformConstants.shared.DELETE_ACCOUNT)
                                            .fontWeight(.medium)
                                            .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                                            .padding()
                                            .frame(width: screenBounds.width - 30, height: 55)
                                    } .overlay(
                                        RoundedRectangle(cornerRadius: 10)
                                            .stroke(Color(hex: Constants.MOMENTUM_ORANGE), lineWidth: 2)
                                    )
                                }
                                .padding(.vertical)
                            }
                            .padding()
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .manageAcc)
                            profileViewModel.closeCards(
                                cards: .contactInfo, .billingInfo, .techSupport, .feedback, .information
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.manageAcc, anchor: .bottom)
                                }
                            }
                        }
                    )
                    .id(ProfileCard.manageAcc)
                    .alert(isPresented: $showAlert) {
                        Alert(
                            title: Text(MultiplatformConstants.shared.DELETE_ACCOUNT),
                            message: Text("Are you sure you want to delete account?"),
                            primaryButton: .default(Text("Cancel"), action: {
                                showAlert.toggle()
                            }),
                            secondaryButton: .destructive(Text("Delete"), action: {
                                profileViewModel.deleteUser(userId: session.currentUser?.id ?? "") {
                                    session.deleteCurrentUser {
                                        popNavigation {
                                            session.signInAsGuest()
                                        }
                                    }
                                }
                            })
                        )
                    }
                    
                    BasePlainExpandableCard(
                        contentHeight: 300,
                        isExpanded: $profileViewModel.isTechSupportExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "person.2.wave.2")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.TECHNICAL_SUPPORT)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                Text(MultiplatformConstants.shared.TECHNICAL_SUPPORT_PROMPT)
                                    .multilineTextAlignment(.leading)
                                    .lineSpacing(5)
                                LinkLabel(
                                    title: MultiplatformConstants.shared.TECHNICAL_SUPPORT,
                                    description: MultiplatformConstants.shared.DEVELOPER_PHONE_TITLE
                                ) {
                                    guard let url =  URL(string: "tel:\(MultiplatformConstants.shared.DEVELOPER_PHONE)") else { return }
                                    UIApplication.shared.open(url)
                                }.padding(.vertical, 10)
                                
                                LinkLabel(
                                    title:MultiplatformConstants.shared.TECHNICAL_SUPPORT,
                                    description: MultiplatformConstants.shared.DEVELOPER_EMAIL_TITLE
                                ) {
                                    let mailSubject = "?subject=Technical Support".replacingOccurrences(of: " ", with: "%20")
                                    guard let url =  URL(string: "mailto:\(MultiplatformConstants.shared.DEVELOPER_EMAIL)" + mailSubject) else { return }
                                    UIApplication.shared.open(url)
                                }.padding(.bottom, 10)
                            }
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .techSupport)
                            profileViewModel.closeCards(
                                cards: .contactInfo, .billingInfo, .manageAcc, .feedback, .information
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.techSupport, anchor: .bottom)
                                }
                            }
                        }
                    ).id(ProfileCard.techSupport)
                    
                    BasePlainExpandableCard(
                        contentHeight: 200,
                        isExpanded: $profileViewModel.isFeedbackExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "bubble.left.and.exclamationmark.bubble.right")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.FEEDBACK)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                Text(MultiplatformConstants.shared.FEEDBACK_PROMPT)
                                    .multilineTextAlignment(.leading)
                                    .lineSpacing(5)
                                LinkLabel(
                                    title: MultiplatformConstants.shared.FEEDBACK,
                                    description: MultiplatformConstants.shared.DEVELOPER
                                ) {
                                    let mailSubject = "?subject=Developer Feedback".replacingOccurrences(of: " ", with: "%20")
                                    guard let url =  URL(string: "mailto:\(MultiplatformConstants.shared.DEVELOPER_EMAIL)" + mailSubject) else { return }
                                    UIApplication.shared.open(url)
                                }.padding(.vertical, 10)
                            }
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .feedback)
                            profileViewModel.closeCards(
                                cards: .contactInfo, .billingInfo, .manageAcc, .techSupport, .information
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.feedback, anchor: .bottom)
                                }
                            }
                        }
                    ).id(ProfileCard.feedback)
                    
                    BasePlainExpandableCard(
                        contentHeight: 300,
                        isExpanded: $profileViewModel.isInformationExpanded,
                        coverContent: {
                            HStack {
                                Image(systemName: "info.circle")
                                    .foregroundColor(.gray)
                                    .frame(width: 35)
                                Text(MultiplatformConstants.shared.INFORMATION)
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack(alignment: .leading) {
                                VStack(alignment: .leading) {
                                    
                                    Text("**Momentum Church** exists to help people move forward in faith and life by discovering and fulfilling their purpose. The word “**Momentum**” is synonymous with movement. Sometimes people get stuck and need help and support to move forward from their past, their sin, their struggles, their fears, and their worries and into an empowering relationship with **Christ**.")
                                        .multilineTextAlignment(.leading)
                                        .lineSpacing(5)
                                    
                                    LinkLabel(
                                        title: MultiplatformConstants.shared.MOMENTUM_PHONE,
                                        description: MultiplatformConstants.shared.CHURCH_PHONE_TITLE
                                    ) {
                                        guard let url =  URL(string: "tel:\(MultiplatformConstants.shared.CHURCH_PHONE)") else { return }
                                        UIApplication.shared.open(url)
                                    }.padding(.vertical, 10)
                                    LinkLabel(
                                        title: MultiplatformConstants.shared.MOMENTUM_PHONE,
                                        description: MultiplatformConstants.shared.CHURCH_EMERGENCY_PHONE_TITLE
                                    ) {
                                        guard let url =  URL(string: "tel:\(MultiplatformConstants.shared.CHURCH_EMERGENCY_PHONE)") else { return }
                                        UIApplication.shared.open(url)
                                    }.padding(.bottom, 10)
                                    LinkLabel(
                                        title: MultiplatformConstants.shared.MOMENTUM_EMAIL,
                                        description: MultiplatformConstants.shared.CHURCH_EMAIL_TITLE
                                    ) {
                                        let mailSubject = "?subject=Developer Feedback".replacingOccurrences(of: " ", with: "%20")
                                        guard let url =  URL(string: "mailto:\(MultiplatformConstants.shared.CHURCH_EMAIL)" + mailSubject) else { return }
                                        UIApplication.shared.open(url)
                                    }.padding(.bottom, 10)
                                    Text(MultiplatformConstants.shared.COPYRIGHT)
                                        .font(.caption)
                                }.padding()
                                Divider()
                            }
                        }, onCoverClick: {
                            profileViewModel.cardToggle(card: .information)
                            profileViewModel.closeCards(
                                cards: .contactInfo, .billingInfo, .manageAcc, .techSupport, .feedback
                            )
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
                                withAnimation(.easeIn) {
                                    proxy.scrollTo(ProfileCard.information, anchor: .bottom)
                                }
                            }
                        }
                    ).id(ProfileCard.information)
                }
            }
            Spacer()
            HStack {
                Spacer()
                Button {
                    session.signOut {
                        presentationMode.wrappedValue.dismiss()
                        session.signInAsGuest()
                    }
                } label: {
                    Text("Sign Out")
                        .fontWeight(.heavy)
                        .frame(width: screenBounds.width - 30, height: 55)
                }.buttonStyle(FilledButtonStyle())
                Spacer()
            }
            Divider()
        }
        .navigationBarHidden(false)
        .navigationTitle("Profile")
        .navigationBarTitleDisplayMode(.large)
        .onAppear {
            profileViewModel.getContactInformation(userId: session.currentUser?.id ?? "") {
                profileViewModel.getBillingInformation(userId: session.currentUser?.id ?? "")
            }
        }
    }
    var circleIcon: some View {
        ZStack(alignment: .center) {
            ZStack {
                ZStack {
                    Circle()
                        .fill(Color(hex: Constants.MOMENTUM_ORANGE))
                    
                    Circle()
                        .strokeBorder(.white, lineWidth: 5)
                }
                .frame(width: 60, height: 60)
                Circle()
                    .strokeBorder(Color(hex: Constants.MOMENTUM_ORANGE), lineWidth: 5)
                    .frame(width: 70, height: 70)
                
            }
            Text(profileViewModel.fullname.isEmpty ? "G" : profileViewModel.fullname
                .split(separator: " ")
                .map({ String($0.first?.uppercased() ?? "")})
                .reduce("", { x, y in x + y})
            )
            .font(.title2)
            .fontWeight(.heavy)
            .foregroundColor(.white)
        }
        
    }
    func popNavigation(onCompletion: @escaping () -> Void) {
        presentationMode.wrappedValue.dismiss()
        onCompletion()
    }
}

struct AccountView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
