//
//  MessageDetailView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/7/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK
import SDWebImageSwiftUI

struct MessageDetailView: View {
    let message: Message
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                WebImage(url: URL(string: message.thumbnail))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxHeight: 350)
                    .edgesIgnoringSafeArea(.top)
                
                ForEach(message.passages) { passage in
                    if (passage.header ?? "").isEmpty {
                        VStack(alignment: .leading) {
                            Text(passage.verse ?? "")
                                .bold()
                            (passage.message ?? "").map({
                                if Character(extendedGraphemeClusterLiteral: $0).isNumber {
                                     Text(String($0))
                                        .foregroundColor(.gray)
                                        .bold()
                                        .font(.caption2)
                                } else {
                                     Text(String($0))
                                }
                            }).reduce(Text("")) { x, y in
                                x + y
                            }
                        }.frame(maxWidth: .infinity).padding(10)
                    } else {
                        Divider()
                        HStack {
                            Text(passage.header ?? "")
                            Spacer()
                        }.padding(10)
                        Divider()
                            .padding(.bottom, 10)
                        
                    }
                }
            }
        }
        .navigationBarTitleDisplayMode(.inline)
    }
}

