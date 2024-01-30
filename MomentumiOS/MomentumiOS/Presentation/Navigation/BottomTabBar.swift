//
//  Tabbar.swift
//  PeaceWork (iOS)
//
//  Created by Mwai Banda on 12/16/21.
//

import SwiftUI
import MomentumSDK

struct BottomTabBar: View {
    @State private var selection = 2
    
    var body: some View {
        ZStack {
            Color.black.edgesIgnoringSafeArea(.all)
            TabView(selection: $selection) {
                NavigationView {
                    MealsView()
                }
                .navigationViewStyle(StackNavigationViewStyle())
                .tag(0)
                .tabItem {
                    Image(systemName: "takeoutbag.and.cup.and.straw")
                    
                }
                
                NavigationView {
                    SermonsView()
                }
                .navigationViewStyle(StackNavigationViewStyle())
                .tag(1)
                .tabItem {
                    Image(systemName: "ticket")
                    
                }
                NavigationView {
                    MomentumBlurredBackground {
                        OfferView()
                    }
                }
                .navigationViewStyle(StackNavigationViewStyle())
                .tag(2)
                .tabItem {
                    Image(systemName: "giftcard")
                }
                NavigationView {
                    MessageView()
                }
                .navigationViewStyle(StackNavigationViewStyle())
                .tag(3)
                .tabItem {
                    Image(systemName: "book")
                    
                }
                
                NavigationView {
                    EventView()
                }
                .navigationViewStyle(StackNavigationViewStyle())
                .tag(4)
                .tabItem {
                    Image(systemName: "calendar")
                    
                }
            }
            .accentColor(Color(hex: Constants.MOMENTUM_ORANGE))
           
        }
    }
}

struct BottomTabBar_Previews: PreviewProvider {
    static var previews: some View {
        BottomTabBar()
    }
}

struct MomentumBlurredBackground<Content: View>: View {
    var content: () -> Content
    var body: some View {
        ZStack {
            BackgroundView().ignoresSafeArea(.all)
            VStack {
                content()
            }
        }
    }
}
