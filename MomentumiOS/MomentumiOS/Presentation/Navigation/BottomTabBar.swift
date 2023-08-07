//
//  Tabbar.swift
//  PeaceWork (iOS)
//
//  Created by Mwai Banda on 12/16/21.
//

import SwiftUI

struct BottomTabBar: View {
    let itemAppearance = UITabBarItemAppearance()
    let appearance = UITabBarAppearance()
    
    
    var body: some View {
        TabView {
            NavigationView {
                ContentWrapper(navConfiguration: .detailConfig, hasBlurredBackground: false) {
                    MealsView()
                        .onAppear {
                            itemAppearance.normal.iconColor = UIColor(Color(.black))
                            itemAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor(Color(.black))]
                            appearance.shadowColor = UIColor(Color.black)
                            appearance.backgroundColor = UIColor(Color(.clear))
                            appearance.stackedLayoutAppearance = itemAppearance
                            appearance.inlineLayoutAppearance = itemAppearance
                            appearance.compactInlineLayoutAppearance = itemAppearance
                            
                            UITabBar.appearance().standardAppearance = appearance
                        }
                }
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .tag(0)
            .tabItem {
                Image(systemName: "takeoutbag.and.cup.and.straw")
                
            }
            NavigationView {
                ContentWrapper {
                    OfferView()
                        .onAppear {
                            itemAppearance.normal.iconColor = UIColor(Color(.white))
                            itemAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor(Color(.white))]
                            appearance.shadowColor = UIColor(Color.black)
                            appearance.backgroundColor = UIColor(Color(.clear))
                            appearance.stackedLayoutAppearance = itemAppearance
                            appearance.inlineLayoutAppearance = itemAppearance
                            appearance.compactInlineLayoutAppearance = itemAppearance
                            
                            UITabBar.appearance().standardAppearance = appearance
                        }
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
                        .onAppear {
                            itemAppearance.normal.iconColor = UIColor(Color(.black))
                            itemAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor(Color(.black))]
                            appearance.shadowColor = UIColor(Color.black)
                            appearance.backgroundColor = UIColor(Color(.clear))
                            appearance.stackedLayoutAppearance = itemAppearance
                            appearance.inlineLayoutAppearance = itemAppearance
                            appearance.compactInlineLayoutAppearance = itemAppearance
                            
                            UITabBar.appearance().standardAppearance = appearance
                        }
                }
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .tag(0)
            .tabItem {
                Image(systemName: "ticket")
                
            }
        }
        .accentColor(Color(hex: Constants.MOMENTUM_ORANGE))
        
    }
}

struct BottomTabBar_Previews: PreviewProvider {
    static var previews: some View {
        BottomTabBar()
    }
}
