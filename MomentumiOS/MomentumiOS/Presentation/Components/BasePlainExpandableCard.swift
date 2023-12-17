//
//  BasePlainExpandableCard.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/24/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct BasePlainExpandableCard<
    CoverContent: View,
    CoverIcon: View,
    InnerContent: View
>: View {
    var contentHeight: CGFloat
    var isExpanded: Binding<Bool>
    var coverContent: () -> (CoverContent)
    var coverIcon: (Bool) -> (CoverIcon)
    var innerContent: () -> (InnerContent)
    var onCoverClick: () -> Void
    var body: some View {
        ZStack(alignment: .top) {
            ZStack {
                if isExpanded.wrappedValue {
                    VStack {
                        ScrollView {
                            innerContent()
                                .padding(.top)
                                .padding(.top, 5)
                        }
                    }
                    .padding(.top, isExpanded.wrappedValue ? 55 : 0)
                    .opacity(isExpanded.wrappedValue ? 1 : 0)
                }
            }
            .frame(maxWidth: .infinity,  minHeight:isExpanded.wrappedValue ? contentHeight : 0,alignment: .bottom)
            ZStack {
                VStack {
                    Divider()
                    Spacer()
                    HStack(alignment: .center) {
                        coverContent()
                        Spacer()
                        HStack(spacing: 0) {
                            Button {
                                withAnimation(.easeInOut) {
                                onCoverClick()
                                }
                            } label: {
                                coverIcon(isExpanded.wrappedValue)
                            }
                        }
                    }
                    .padding()
                    Divider()
                    
                }.background(Color.white)
            }
            .frame(maxWidth: .infinity, maxHeight: 55, alignment: .top)
            .onTapGesture {
                withAnimation(.easeInOut) {
                    onCoverClick()
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .top)
    }
}
