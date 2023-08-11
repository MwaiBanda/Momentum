//
//  SermonCard.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 10/4/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK
import SDWebImageSwiftUI


struct SermonCard: View {
    var isRedacted = false
    var sermon: Sermon
    var playedSermons: [MomentumSermon]
    var favourite: Bool = false
    var onFavouriteClick: (Bool) -> Void = {_ in }
    var onSermonClick: (Sermon) -> Void = {_ in }

    @State private var isFavourite = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if isRedacted {
                Image(sermon.videoThumbnail)
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(maxHeight: 100)
                    .cornerRadius(8, corners: [.topLeft, .topRight])
            } else {
                ZStack(alignment: .topTrailing){
                    WebImage(url: URL(string: sermon.videoThumbnail))
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(maxHeight: 100)
                        
                
                    Button {
                        isFavourite.toggle()
                        onFavouriteClick(isFavourite)
                    } label: {
                            ZStack(alignment: .center) {
                                Color(.white).opacity(0.75)
                                Image(systemName: "suit.heart.fill")
                                    .imageScale(.small)
                                    .foregroundColor(isFavourite ?  Color(.red) : Color(.darkGray))
                                    .frame(width: 5, height: 5)
                            }
                            .frame(width: 26, height: 26)
                            .clipShape(Circle())
                        }
                        .padding(5)
                    
                }
                .frame(maxWidth: screenBounds.width * 0.45)
                .cornerRadius(8, corners: [.topLeft, .topRight])
            }
            VStack {
                if let playedsermon = playedSermons.first(where: { $0.id == sermon.id }) {
                    ProgressView(value: Double(playedsermon.last_played_percentage), total: 100)
                        .frame(width: screenBounds.width * 0.46)
                } else {
                    ProgressView(value: 0)
                        .frame(width: screenBounds.width * 0.46)
                }
            }
            .frame(width: screenBounds.width * 0.45)
            .clipped()

            VStack(alignment: .leading) {
                
                Text(sermon.series)
                    .font(.caption)
                    .lineLimit(1)
                    .multilineTextAlignment(.leading)
                
                
                Text(sermon.title)
                    .font(.subheadline)
                    .bold()
                    .lineLimit(1)
                    .multilineTextAlignment(.leading)
                
                
                Text(sermon.preacher)
                    .foregroundColor(.gray)
                    .font(.caption)
                    .lineLimit(1)
                    .multilineTextAlignment(.leading)
                
                
                Text(sermon.date)
                    .font(.caption2)
                    .multilineTextAlignment(.leading)
            }
            .padding(5)
            .padding(.bottom, 5)
            
        }
        .frame(width: screenBounds.width * 0.45)
        .background(Color(.systemGray6))
        .cornerRadius(8)
        .shadow(color: Color.black.opacity(0.15), radius: 20, x: 0, y: 10)
        .onAppear {
            isFavourite = favourite
        }
        .onTapGesture {
           onSermonClick(sermon)
        }
    }
}


