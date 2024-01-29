//
//  MessageDetailView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/7/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import UIKit
import MomentumSDK
import SDWebImageSwiftUI
import Algorithms

struct MessageDetailView: View {
    let message: Message
    @State private var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
    @State private var range = NSRange()
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                WebImage(url: URL(string: message.thumbnail))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxHeight: 350)
                    .edgesIgnoringSafeArea(.top)
//                TextView(text: $text, range: $range)
//                    .frame(maxWidth: .infinity, minHeight: 100)

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
                                        .textSelection(.enabled)
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


struct TextView: UIViewRepresentable {
    @Binding var text: String
    @Binding var range: NSRange

    func makeCoordinator() -> Coordinator {
        Coordinator(self, range: $range)
    }

    func makeUIView(context: Context) -> UITextView {

        let myTextView = UITextView()
        myTextView.delegate = context.coordinator

        myTextView.font = UIFont(name: "HelveticaNeue", size: 15)
        myTextView.isScrollEnabled = true
        myTextView.isEditable = false
        myTextView.isUserInteractionEnabled = true
        myTextView.backgroundColor = UIColor(white: 0.0, alpha: 0.05)

        return myTextView
    }

    func updateUIView(_ uiView: UITextView, context: Context) {
        uiView.text = text
    }

    class Coordinator : NSObject, UITextViewDelegate {

        var parent: TextView
        @Binding var range: NSRange // <---

        init(_ uiTextView: TextView, range: Binding<NSRange>) {
            self.parent = uiTextView
            _range = range
        }

        func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
            return true
        }

        func textViewDidChange(_ textView: UITextView) {
            print("text now: \(String(describing: textView.text!))")
            self.parent.text = textView.text
        }
        func textViewDidChangeSelection(_ textView: UITextView) {
            range = textView.selectedRange
        }
    }
}



