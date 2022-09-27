//
//  SermonsView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import SDWebImageSwiftUI
import MomentumSDK
import AVFoundation
import AVKit

struct SermonsView: View {
    @StateObject private var sermonViewmodel = SermonsViewModel()
    @State private var sermon: Sermon? = nil
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    var body: some View {
        
        VStack {
            Divider()
            HStack {
                Text("TAP TO WATCH RECENT SERMONS")
                    .font(.caption)
                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    .padding(.leading)
                Spacer()
            }
            ScrollView {
                LazyVGrid(columns: columns, spacing: 10) {
                    
                    ForEach(sermonViewmodel.sermons, id: \.id) { sermon in
                        
                        VStack(alignment: .leading) {
                            
                            WebImage(url: URL(string: sermon.videoThumbnail))
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                                .frame(width: screenBounds.width * 0.45)
                                .cornerRadius(8, corners: [.topLeft, .topRight])
                            
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
                            }.padding(5).padding(.bottom, 5)
                            
                        }
                        .background(Color(.systemGray6))
                        .cornerRadius(8)
                        .shadow(color: Color.black.opacity(0.15), radius: 20, x: 0, y: 10)
                        .onTapGesture {
                            self.sermon = sermon
                        }
                    }
                }
                .padding(.horizontal, 10)
                .padding(.bottom, sermonViewmodel.canLoadMoreSermons ? 25 :  50)
                if sermonViewmodel.canLoadMoreSermons && !sermonViewmodel.sermons.isEmpty {
                    Button { sermonViewmodel.loadMoreSermons() } label: {
                        Text("Load More")
                            .padding(.horizontal, 22)
                            .padding(.vertical, 10)
                    }
                    .buttonStyle(FilledButtonStyle())
                    .padding(.bottom, 25)
                    
                    Divider()
                        .padding(.bottom, 10)
                }
            }
        }
        .background(Color.clear)
        .navigationTitle("Sermons")
        .onAppear {
            sermonViewmodel.fetchSermons()
        }
        .fullScreenCover(item: $sermon) { sermon in
            PlayerView(player: sermonViewmodel.player, playbackURL: sermon.videoURL)
                .ignoresSafeArea(.all)
                .onDisappear {
                    UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
                    AppDelegate.orientationLock = .portrait
                    sermonViewmodel.currentSermon = nil
                }
                .onAppear {
                    AppDelegate.orientationLock = .all
                    sermonViewmodel.currentSermon = sermon
                    sermonViewmodel.updateNowPlaying(sermon: sermon)
                }
        }
    }
}


struct SermonsView_Previews: PreviewProvider {
    static var previews: some View {
        SermonsView()
    }
}



