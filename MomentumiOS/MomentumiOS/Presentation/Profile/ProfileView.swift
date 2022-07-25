//
//  AccountView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @State private var isContactInfoExpanded = false
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
                    Text("Mwai Banda")
                        .font(.title3)
                        .fontWeight(.heavy)
                    Text("Est. 07/24/2022")
                        .font(.caption)
                        .foregroundColor(.gray)
                    
                }
            }.padding(.vertical)
            ScrollView {
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
                BasePlainExpandableCard(
                    contentHeight: 300,
                    isExpanded: $isContactInfoExpanded,
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
                        Text("Inner Content")
                    }, onCoverClick: {
                        isContactInfoExpanded.toggle()
                    }
                )
            }
            Spacer()
            HStack {
                Spacer()
                Button { } label: {
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
            Text("MB")
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
