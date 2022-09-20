//
//  Tabbar.swift
//  PeaceWork (iOS)
//
//  Created by Mwai Banda on 12/16/21.
//

import SwiftUI

struct BottomTabBar: View {
    init() {
        
        let itemAppearance = UITabBarItemAppearance()
        itemAppearance.normal.iconColor = UIColor(Color(.white))
        itemAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor(Color(.white))]
        let appearance = UITabBarAppearance()
        appearance.shadowColor = UIColor(Color.black)
        appearance.backgroundColor = UIColor(Color(.clear))
        appearance.stackedLayoutAppearance = itemAppearance
        appearance.inlineLayoutAppearance = itemAppearance
        appearance.compactInlineLayoutAppearance = itemAppearance
    
        UITabBar.appearance().standardAppearance = appearance
        
        
    }
  
    var body: some View {
        TabView {
            NavigationView {
                ContentWrapper {
                    OfferView()
                }
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .tag(0)
            .tabItem {
                Image(systemName: "giftcard")
            }
            NavigationView {
                ContentWrapper(navConfiguration: .detailConfig, hasBlurredBackground: false) {
                    SermonsView()
                }
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .tag(0)
            .tabItem {
                Image(systemName: "ticket")
                
            }
        }
        .accentColor(Color(hex: Constants.MOMENTUM_ORANGE))
        .onAppear {
            UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
            AppDelegate.orientationLock = .portrait 
        }
    }
}

struct BottomTabBar_Previews: PreviewProvider {
    static var previews: some View {
        BottomTabBar()
    }
}
