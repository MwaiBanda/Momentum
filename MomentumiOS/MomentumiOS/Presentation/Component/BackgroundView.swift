//
//  BackgroundView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BackgroundView: View {
    var body: some View {
        ZStack {
            LinearGradient(colors: [ Color(hex: Constants.momentumOrange), Color(.lightGray)
                                   ], startPoint: .top, endPoint: .bottom)
            .ignoresSafeArea()
            Color(.black)
                .opacity(0.2)
                .blur(radius: 200)
                .ignoresSafeArea(.all)
           
                Circle()
                    .fill(Color(hex: Constants.momentumOrange))
                    .frame(width: screenBounds.width * 0.85, height: screenBounds.height * 0.85)
                    .blur(radius: 60)
                    .offset(x: -screenBounds.width / 1.8, y: -((screenBounds.height) / 4))
            Circle()
                .fill(Color(.lightGray))
                .frame(width: screenBounds.width * 0.75, height: screenBounds.height * 0.75)
                .blur(radius: 75)
                .offset(x: screenBounds.width / 1.8, y: -((screenBounds.height) / 2))
            
            Circle()
                .fill(Color(.lightGray))
                .frame(width: screenBounds.width * 0.75, height: screenBounds.height * 0.75)
                .blur(radius: 60)
                .offset(x: screenBounds.width / 1.8, y: ((screenBounds.height) / 2))
            Circle()
                .fill(Color(hex: Constants.momentumOrange))
                .frame(width: screenBounds.width * 0.75, height: screenBounds.height * 0.75)
                .blur(radius: 110)
                .offset(x: screenBounds.width / 1.8, y: ((screenBounds.height) / 2))
            Circle()
                .fill(Color(hex: Constants.momentumOrange))
                .frame(width: screenBounds.width * 0.75, height: screenBounds.height * 0.75)
                .blur(radius: 110)
                .offset(x: -screenBounds.width / 1.8, y: ((screenBounds.height) / 2))
            Rectangle()
                .fill(.white)
                .opacity(0.1)
                .background(
                    Color.white
                        .opacity(0.08)
                        .blur(radius: 10)
                )
                .ignoresSafeArea()
           
         
        }
    }
}

struct MyView_Previews: PreviewProvider {
    static var previews: some View {
        BackgroundView()
    }
}
