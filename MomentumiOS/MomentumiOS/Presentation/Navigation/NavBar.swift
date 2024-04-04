//
//  NavBar.swift
//  
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Momentum. All rights reserved.

import SwiftUI
import MomentumSDK


struct MomentumNavBar: ViewModifier {
    @Environment(\.colorScheme) var colorScheme
    @Environment(\.presentationMode) var presentationMode
    @State private var showTransactionSheet = false
    @StateObject private var profileViewModel = ProfileViewModel()
    
    func body(content: Content) -> some View {
        content
            .toolbar(content: {
                ToolbarItemGroup(placement: .navigationBarLeading) {
                    Button(action: { withAnimation(Animation.easeInOut(duration: 0.5)) {
                        showTransactionSheet.toggle()
                        let haptic = UIImpactFeedbackGenerator(style: .light)
                        haptic.impactOccurred()
                    }}) {
                        Image(systemName: "clock").imageScale(.large)
                            .font(.system(size: 20, weight: .medium))
                            .foregroundColor(Color.white)
                    }
                }
                ToolbarItemGroup(placement: .principal) {
                    Image("momentum")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 47, height: 47)
                    
                }
                
                ToolbarItemGroup(placement: .navigationBarTrailing) {
                    NavigationLink { ProfileView(profileViewModel: profileViewModel) } label: {
                        Image(systemName: "person.crop.circle").imageScale(.large)
                            .font(.system(size: 20, weight: .medium))
                            .foregroundColor(Color.white)
                    }.simultaneousGesture(TapGesture().onEnded{
                        presentationMode.wrappedValue.dismiss()
                        let haptic = UIImpactFeedbackGenerator(style: .light)
                        haptic.impactOccurred()
                    })
                }
            })   
            .sheet(isPresented: $showTransactionSheet) {
                TransactionView()
            }
    }
}

extension View {
    func momentumNavigation() -> some View {
        modifier(MomentumNavBar())
    }
}


