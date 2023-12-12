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
import Algorithms

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
                            HStack {
                            Text(passage.verse ?? "")
                                .bold()
                                Spacer()
                            }
                            if let message = passage.message  {
                                if #available(iOS 15, *) {
                                    Text(getMesssageAttritubuted(message))
                                } else {
                                    Group {
                                        getMessageTextArray(message: message).prefix(450).reduce(Text("")) { x, y in
                                            x + y
                                        }
                                    }
                                }
                            }
                        }.frame(maxWidth: .infinity).padding(10)
                    } else {
                        HStack {
                            Text(passage.header ?? "")
                            Spacer()
                        }.padding(10)
                    }
                    Divider()
                       
                }
            }
        }
        .navigationBarTitleDisplayMode(.inline)
    }
    
    func getMessageTextArray(message: String)  -> [Text] {
        message.map({
            if Character(extendedGraphemeClusterLiteral: $0).isNumber {
                Text(String($0))
                    .foregroundColor(.gray)
                    .bold()
                    .font(.caption2)
            } else {
                Text(String($0))
            }
        })
    }
    
    @available(iOS 15, *)
    func getMesssageAttritubuted(_ s: String) -> AttributedString {
        s.map({
            if Character(extendedGraphemeClusterLiteral: $0).isNumber {
                var res = AttributedString(String($0))
                res.font = .caption.bold()
                res.foregroundColor = .gray
                return res
            } else {
                return AttributedString(String($0))
            }
        }).reduce(AttributedString(""), { x, y in x + y })
    }
}

