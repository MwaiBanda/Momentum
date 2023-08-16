//
//  NavBar.swift
//  
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Momentum. All rights reserved.

import SwiftUI
import MomentumSDK


struct NavBar: View {
    @Environment(\.colorScheme) var colorScheme
    @Binding var showMenu: Bool
    @Environment(\.presentationMode) var presentationMode
    @State private var showTransactionSheet = false
    @StateObject private var profileViewModel = ProfileViewModel()

    var body: some View {
        HStack(alignment: .center)  {
            if false {
                Button(action: { withAnimation(Animation.easeInOut(duration: 0.5)) {
                    self.showMenu.toggle()
                    presentationMode.wrappedValue.dismiss()
                    let haptic = UIImpactFeedbackGenerator(style: .light)
                    haptic.impactOccurred()
                }}) {
                    
                    Image(systemName: showMenu ? "xmark" : "line.horizontal.3").imageScale(.large)
                        .font(.system(size: 25, weight: .medium))
                        .foregroundColor( Color.white)
                        .unredacted()
                    
                    
                }
                
            }
            Button(action: { withAnimation(Animation.easeInOut(duration: 0.5)) {
                showTransactionSheet.toggle()
                let haptic = UIImpactFeedbackGenerator(style: .light)
                haptic.impactOccurred()
            }}) {
                Image(systemName: "clock").imageScale(.large)
                    .font(.system(size: 20, weight: .medium))
                    .foregroundColor(Color.white)
            }
            
            Spacer()
            Image("momentum")
                .resizable()
                .aspectRatio( contentMode: .fit)
                .frame(width: 60, height: 60)
            
            
            
            Spacer()
            
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
        .padding(.horizontal)
        .frame(minHeight: 50, maxHeight: 50)
        .sheet(isPresented: $showTransactionSheet) {
            TransactionView()
        }
    }
}

struct NavBar_Previews: PreviewProvider {
    static var previews: some View {
        NavBar(showMenu: .constant(false))
    }
}

