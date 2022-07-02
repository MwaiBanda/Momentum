//
//  OfferVIew.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct OfferView: View {
    var buttons = [
        ["1", "2", "3"],
        ["4", "5", "6"],
        ["7", "8", "9"],
        [".", "0", "<"]
    ]
    var proxy: GeometryProxy
    var body: some View {

      
            VStack {
                Spacer()
                Text("$0")
                    .font(Font.system(size: 130, weight: .medium, design: .default))
                Spacer()
                ForEach(buttons, id: \.self) { row in
                    HStack {
                        ForEach(row, id: \.self) { button in
                            Spacer()
                        Button{} label: {
                            ZStack {
                                   Circle()
                                    .stroke(.clear)
                            Text(button)
                                .font(Font.system(size: 35, weight: .medium, design: .default))
                                .padding()
                            }


                        }
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
            } .foregroundColor(.white)
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


