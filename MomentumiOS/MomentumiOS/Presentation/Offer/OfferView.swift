//
//  OfferVIew.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct OfferView: View {
    @StateObject private var offerViewModel = OfferViewModel()
    var proxy: GeometryProxy
    var body: some View {
        
        
        VStack {
            Spacer()
            if offerViewModel.displayText.isEmpty {
                Text("$0")
                    .font(Font.system(size: 75, design: .default))
                    .frame(alignment: .center)
                    .scaleEffect(offerViewModel.scale, anchor: .center)
                    .animation(.easeInOut(duration: 0.15), value: offerViewModel.scale)
            } else {
            HStack(spacing: 0) {
            ForEach(offerViewModel.displayText, id: \.self) { character in
                Text(character)
                    .font(Font.system(size: 75, design: .default))
                    .frame(alignment: .center)
                    .scaleEffect(offerViewModel.scale, anchor: .center)
                    .animation(.easeInOut(duration: 0.15), value: offerViewModel.scale)
            }
            }
            }
            Spacer()
            ForEach(offerViewModel.offerKeypad, id: \.self) { row in
                HStack {
                    ForEach(row, id: \.self) { button in
                        Spacer()
                        Button{
                            offerViewModel.processInput(button: button)
                            
                        } label: {
                            ZStack {
                                Circle()
                                    .stroke(.clear)
                                    .frame(width: 80, height: 80)
                                Text(String(button))
                                    .font(Font.system(size: 30, design: .default))
                                    .padding()
                            }
                        }.disabled(offerViewModel.isKeypadDisabled && !offerViewModel.controlKeys.contains(button))
                    }
                    Spacer()
                    
                }
            }
            Button {} label: {
                Text("Offer")
                    .fontWeight(.heavy)
                    .frame(width: screenBounds.width - 30, height: 55)
                    .background(Color(hex: Constants.momentumOrange))
                    .cornerRadius(10)
            }
        }
        .foregroundColor(.white)
        .frame(minHeight: proxy.size.height - 90)
        
    }
    
}

struct OfferVIew_Previews: PreviewProvider {
    static var previews: some View {
        GeometryReader { proxy in
            OfferView(proxy: proxy)
        }
    }
}


