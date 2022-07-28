//
//  AccountView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var session: Session
    @StateObject private var profileViewModel = ProfileViewModel()
    @State private var showAlert = false
    var body: some View {
        VStack(alignment: .leading) {
            Divider()
            Text("Edit Personal Information & Manage Account".uppercased())
                .font(.caption)
                .foregroundColor(Color(hex: Constants.momentumOrange))
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
                                Text("Contact Information")
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                TitledTextField(title: "fullname", text: $profileViewModel.fullname) {
                                    profileViewModel.updateFullname(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "phone", text: $profileViewModel.phone) {
                                    profileViewModel.updatePhone(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "email", text: $profileViewModel.email) {
                                    profileViewModel.updateEmail(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "password", text:  $profileViewModel.password) {
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
                                Text("Billing Information")
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            Group {
                                TitledTextField(title: "street address", text: $profileViewModel.streetAddress) {
                                    profileViewModel.updateStreetAddress(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "apt, suite or floor", text: $profileViewModel.apt) {
                                    profileViewModel.updateApt(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "city", text: $profileViewModel.city) {
                                    profileViewModel.updateCity(userId: session.currentUser?.id ?? "")
                                }
                                Divider()
                                TitledTextField(title: "zip code", text: $profileViewModel.zipCode) {
                                    profileViewModel.updateZipCode(userId: session.currentUser?.id ?? "")
                                }
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
                                Text("Manage Account")
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack(alignment: .leading) {
                                Text("Delete Account")
                                    .fontWeight(.heavy)
                                    .font(.title3)
                                Text("Deleting your account will result in removing all the data connected to your Momentum account from our services. This action cannot be undone.")
                                    .foregroundColor(.gray)
                                Button { showAlert.toggle() } label: {
                                    VStack {
                                        Text("Delete Account")
                                            .fontWeight(.medium)
                                            .foregroundColor(Color(hex: Constants.momentumOrange))
                                            .padding()
                                            .frame(width: screenBounds.width - 30, height: 55)
                                    } .overlay(
                                        RoundedRectangle(cornerRadius: 10)
                                            .stroke(Color(hex: Constants.momentumOrange), lineWidth: 2)
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
                            title: Text("Delete Account"),
                            message: Text("Are you sure you want to delete account?"),
                            primaryButton: .default(Text("Cancel"), action: {
                                showAlert.toggle()
                            }),
                            secondaryButton: .destructive(Text("Delete"), action: {
                                // Delete a/c
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
                                Text("Technical Support")
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                Text("Are you experiencing any problems? Do you need assistance navigating through the app? Let's get in touch")
                                    .multilineTextAlignment(.leading)
                                    .lineSpacing(5)
                                LinkLabel(title: "Technical Support", description: "Developer Phone") {
                                    
                                }.padding(.vertical, 10)
                                
                                LinkLabel(title: "Technical Support", description: "Developer Email") {
                                    
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
                                Text("Feedback")
                                    .font(.headline)
                                    .fontWeight(.heavy)
                                
                            }
                        }, coverIcon: { isExpanded in
                            Image(systemName: isExpanded ? "chevron.up":"chevron.down")
                                .foregroundColor(.gray)
                        }, innerContent: {
                            VStack {
                                Text("Are enjoying the app? Do you have any thoughts on anything we can improve? Let's get in touch")
                                    .multilineTextAlignment(.leading)
                                    .lineSpacing(5)
                                LinkLabel(title: "Feedback", description: "Developer") {
                                    
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
                                Text("Information")
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
                                    
                                    LinkLabel(title: "Momentum Phone", description: "Church Phone") {
                                        
                                    }.padding(.vertical, 10)
                                    
                                    LinkLabel(title: "Momentum Email", description: "Church Email") {
                                        
                                    }.padding(.bottom, 10)
                                    Text("Copyright **©** 2022 Momentum. All rights reserved.")
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
                        session.checkAndSignInAsGuest()
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
                        .fill(Color(hex: Constants.momentumOrange))
                    
                    Circle()
                        .strokeBorder(.white, lineWidth: 5)
                }
                .frame(width: 60, height: 60)
                Circle()
                    .strokeBorder(Color(hex: Constants.momentumOrange), lineWidth: 5)
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
    
}

struct AccountView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
