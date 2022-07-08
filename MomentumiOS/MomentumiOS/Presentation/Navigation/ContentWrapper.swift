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
    var isDetail: Bool
    var navTitle: String
    var navConfig: NavConfig
    var content: (GeometryProxy) -> (Content)
    
    init(
        navConfig: NavConfig = .defaultConfig,
        navTitle: String = "",
        isDetail: Bool = false,
        @ViewBuilder content: @escaping (GeometryProxy) -> (Content)
    ) {
        self.navTitle = navTitle
        self.content = content
        self.navConfig = navConfig
        self.isDetail = isDetail
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
                        if isDetail {
                            Color.white.ignoresSafeArea(.all)
                        } else {
                        BackgroundView().ignoresSafeArea(.all)
                        }
                        GeometryReader { geometry in
                            VStack(spacing: 0){
                                if !isDetail {
                                NavBar(showMenu: $showMenu, navTitle: navTitle, navConfig: navConfig)
                                    .frame(minHeight: 50)
                                    
                                }
                                ScrollView(.vertical, showsIndicators: false){
                                content(geometry)
                                }.padding(.vertical)
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
            .navigationBarHidden(isDetail ? false : true)
        }
        .onAppear(perform: {
            
        })
    }
}


//struct MenuWrapper_Previews: PreviewProvider {
//    static var previews: some View {
//        MenuWrapper {
//            Text("Home")
//        }
//    }
//}
