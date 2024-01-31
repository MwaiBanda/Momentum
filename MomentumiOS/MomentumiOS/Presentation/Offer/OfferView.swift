//
//  OfferVIew.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI

struct OfferView: View {
    @EnvironmentObject var session: Session
    @StateObject private var offerViewModel = OfferViewModel()
    @State private var showAuthSheet = false

    var body: some View {
        VStack {
            NavBar(showMenu: .constant(false))
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
                        if offerViewModel.displayText.last == character {
                            Text(character)
                                .font(Font.system(size: 75, design: .default))
                                .frame(alignment: .center)
                                .scaleEffect(offerViewModel.scale, anchor: .center)
                                .animation(.easeInOut(duration: 0.1), value: offerViewModel.popIn)
                        } else {
                            Text(character)
                                .font(Font.system(size: 75, design: .default))
                                .frame(alignment: .center)
                        }
                        
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
                                Text(String(button))
                                    .font(Font.system(size: 30, design: .default))
                                    .padding()
                            }
                        }.disabled(offerViewModel.isKeypadDisabled && !offerViewModel.controlKeys.contains(button))
                    }
                    Spacer()
                    
                }
            }
            Spacer()
            NavigationLink {
                    PaymentSummaryView(offerViewModel: offerViewModel)
                
            } label: {
                Text("Offer")
                    .fontWeight(.heavy)
                    .frame(width: screenBounds.width - 30, height: 55)

         
            }
            .disabled(session.currentUser?.isGuest ?? false)
            .buttonStyle(FilledButtonStyle())
            .simultaneousGesture(TapGesture().onEnded{
                if (session.currentUser?.isGuest ?? false)  {
                    showAuthSheet.toggle()
                }
            })
            .padding(.bottom, 10)
        }
        .foregroundColor(.white)
        .onAppear {
            AppReviewRequest.RequestReviewWhenNeeeded()
        }
        .sheet(isPresented: $showAuthSheet) {
            MomentumBlurredBackground {
                AuthControllerView()
            }
        }
        
    }
    
}

struct OfferVIew_Previews: PreviewProvider {
    static var previews: some View {
            OfferView()
        
    }
}


