//
//  LinkLabel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/25/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct LinkLabel: View {
    var title: String
    var description: String
    var onLabelClick: () -> Void
    
    var body: some View {
        Button { onLabelClick() } label: {
            HStack {
                VStack(alignment: .leading) {
                    
                    Text(title)
                        .foregroundColor(.gray)
                        .textCase(.uppercase)
                        .font(.subheadline)
                    
                    Text(description)
                        .font(.headline)
                        .fontWeight(.bold)
                    
                }.padding(.leading)
                Spacer()
                Image(systemName: "arrow.up.forward.app")
                           .imageScale(.large)
                           .offset(y: 10)
                           .padding(.trailing)
            }

        }
    }
}


struct LinkLabel_Previews: PreviewProvider {
    static var previews: some View {
        LinkLabel(title: "Phone", description: "Momentum Church Line") {
            
        }
    }
}
