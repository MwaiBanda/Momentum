//
//  NavBar.swift
//  
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Momentum. All rights reserved.

import SwiftUI
import MomentumSDK

enum NavConfig {
    case defaultConfig
    case detailConfig
}
struct NavBar: View {
    @Environment(\.colorScheme) var colorScheme
    @Binding var showMenu: Bool
    @Environment(\.presentationMode) var presentationMode
    var navTitle: String?
    var navConfig: NavConfig
    var Title: String {
        return navTitle ?? ""
    }
    @State private var showTransactionSheet = false
    
    var body: some View {
        ZStack {
            if navConfig == .defaultConfig {
                ZStack {
                    Color(.clear ).ignoresSafeArea(.all)
                    
                    HStack(alignment:.center){
                        
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

                            
                            Text((Title.isEmpty ? "" : navTitle) ?? "")
                                .fontWeight(.heavy)
                                .font(.title2)
                                .foregroundColor(colorScheme == .dark ? Color.white : Color.white)
                            Spacer()
                            if Title.isEmpty {
                                
                               
                                Button(action: { withAnimation(Animation.easeInOut(duration: 0.5)) {
                                    presentationMode.wrappedValue.dismiss()
                                    let haptic = UIImpactFeedbackGenerator(style: .light)
                                    haptic.impactOccurred()
                                }}) {
                                    Image(systemName: "person.crop.circle").imageScale(.large)
                                        .font(.system(size: 20, weight: .medium))
                                        .foregroundColor(Color.white)
                                }
                            }
                            
                        }
                        
                        
                    }
                    .padding(.horizontal)
                }
                .frame(minHeight: 50, maxHeight: 50)
                
            }
            if navConfig == .detailConfig {
            }
        }.sheet(isPresented: $showTransactionSheet) {
            TransactionView()
        }
    }
}

struct NavBar_Previews: PreviewProvider {
    static var previews: some View {
        NavBar(showMenu: .constant(false), navConfig: .defaultConfig)
    }
}

