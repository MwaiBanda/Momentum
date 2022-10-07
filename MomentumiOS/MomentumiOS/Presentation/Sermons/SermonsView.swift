//
//  SermonsView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import AVKit
import MomentumSDK


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
                    if sermonViewmodel.sermons.isEmpty {
                        ForEach(0..<12, id: \.self
                        ) { _ in
                            SermonCard(
                                isRedacted: true,
                                sermon: Sermon(
                                    id: "\(Int.random(in: 1000..<9999))",
                                    series: "14:7 Refresh - Part 1",
                                    title: "Pre-Decide: Better Choices, Better Life",
                                    preacher: "Charlie Arms",
                                    videoThumbnail: "thumbnail",
                                    videoURL: "",
                                    date: "Oct 2, 2022"
                                ),
                                playedSermons: sermonViewmodel.watchedSermons
                            ) {
                                self.sermon = $0
                            }
                        }
                    } else {
                        ForEach(sermonViewmodel.sermons, id: \.id) { sermon in
                            SermonCard(sermon: sermon, playedSermons: sermonViewmodel.watchedSermons) {
                                self.sermon = $0
                            }
                        }
                    }
                }
                .padding(.horizontal, 10)
                .padding(.bottom, sermonViewmodel.canLoadMoreSermons ? 25 :  50)
                if !sermonViewmodel.sermons.isEmpty {
                    if sermonViewmodel.canLoadMoreSermons {
                        Button { sermonViewmodel.loadMoreSermons() } label: {
                            Text("Load More")
                                .padding(.horizontal, 22)
                                .padding(.vertical, 10)
                        }
                        .buttonStyle(FilledButtonStyle())
                        .padding(.bottom, 25)
                    }
                    Divider()
                        .padding(.bottom, 10)
                }
            }
            .redacted(reason: sermonViewmodel.sermons.isEmpty ? .placeholder : [])
        }
        .background(Color.clear)
        .navigationTitle("Sermons")
        .onAppear {
            DispatchQueue.main.async {
                sermonViewmodel.fetchSermons()
                sermonViewmodel.getWatchedSermons()
            }
        }
        .fullScreenCover(item: $sermon) { sermon in
            PlayerView(
                player: sermonViewmodel.player,
                playbackURL: sermon.videoURL,
                lastPlayedTime: {
                    let lastPlayedSermon = sermonViewmodel.watchedSermons.first(where: { $0.id == sermon.id })
                    let myTime = CMTime(seconds: lastPlayedSermon?.last_played_time ?? 0, preferredTimescale: 60000)
                    return myTime
                }
            )
            .ignoresSafeArea(.all)
            .onDisappear {
                UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
                AppDelegate.orientationLock = .portrait
                let currentTimeInSeconds: Double = sermonViewmodel.player.currentItem?.currentTime().seconds ?? 0
                let currentTimeInSecondsRounded = round(currentTimeInSeconds * 100) / 100
                sermonViewmodel.addSermon(
                    sermon: MomentumSermon(
                        id: sermonViewmodel.currentSermon?.id ?? "",
                        last_played_time: currentTimeInSecondsRounded,
                        last_played_percentage: Int32((currentTimeInSecondsRounded / (round((sermonViewmodel.player.currentItem?.duration.seconds ?? 0) * 100) / 100)) * 100)
                    )
                ) 
                sermonViewmodel.resetNowPlaying()
            }
            .onAppear {
                AppDelegate.orientationLock = .all
                sermonViewmodel.updateNowPlaying(sermon: sermon)
                sermonViewmodel.currentSermon = sermon
                
            }
        }
    }
}


struct SermonsView_Previews: PreviewProvider {
    static var previews: some View {
        SermonsView()
    }
}



