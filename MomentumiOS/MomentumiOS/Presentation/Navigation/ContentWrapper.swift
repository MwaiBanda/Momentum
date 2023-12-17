//
//  ContentWrapper.swift
//  Momentum (iOS)
//
//  Created by Mwai Banda on 9/9/21.
//

import SwiftUI
import AVFoundation
import SwiftUI
import StoreKit

struct ContentWrapper<Content: View>: View {
    @State var showMenu = false
    var hasBlurredBackground: Bool
    var navConfiguration: NavConfiguration
    var content: () -> (Content)
    
    init(
        navConfiguration: NavConfiguration = .defaultConfig,
        hasBlurredBackground: Bool = true,
        @ViewBuilder content: @escaping () -> (Content)
    ) {
        self.content = content
        self.navConfiguration = navConfiguration
        self.hasBlurredBackground = hasBlurredBackground
    }
    
    
    var body: some View {
        let drag = DragGesture().onEnded {
            if $0.translation.width < -100 {
                withAnimation(Animation.easeInOut(duration: 0.5)) {
                    self.showMenu = false
                    
                }
            }
        }
        VStack {
            ZStack{
                withAnimation {
                    ZStack {
                        if hasBlurredBackground {
                            BackgroundView().ignoresSafeArea(.all)
                        } else {
                            Color.white.ignoresSafeArea(.all)
                        }
                        GeometryReader { geometry in
                            VStack(spacing: 0){
                                if navConfiguration == .defaultConfig {
                                    NavBar(showMenu: $showMenu)
                                }
                                ScrollView(.vertical, showsIndicators: false){
                                    VStack {
                                        if navConfiguration == .defaultConfig {
                                            Spacer()
                                        }
                                        content()
                                    }
                                        .frame(maxHeight: .infinity)
                                }
                              /*   Divider()
                               .overlay(Color(.clear))
                               .padding(.bottom) */
                            }
                            .frame(width: geometry.size.width, height: geometry.size.height)
                            .offset(x: self.showMenu ? geometry.size.width/1.45 : 0)
                            .disabled(self.showMenu ? true : false)
                            if self.showMenu {
                                MenuView()
                                    .animation(.easeInOut(duration: 0.5))
                                    .frame(width: geometry.size.width/1.45)
                                    .transition(.move(edge: .leading))
                            }
                        }
                    }
                    .gesture(drag)
                    .onTapGesture {
                        withAnimation(Animation.easeInOut(duration: 0.5)) {
                            self.showMenu = false
                            
                            let haptic = UIImpactFeedbackGenerator(style: .light)
                            haptic.impactOccurred()
                        }
                    }
                }
                
            }
            .navigationBarHidden(navConfiguration == .defaultConfig)
        }
    }
}


