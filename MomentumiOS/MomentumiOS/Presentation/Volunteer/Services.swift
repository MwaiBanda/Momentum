//
//  Services.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 1/11/25.
//  Copyright © 2025 Momentum. All rights reserved.
//

import SwiftUI
import SDWebImageSwiftUI
import MomentumSDK

struct Services: View {
    @StateObject private var serviceViewModel = ServicesViewModel()
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Divider()
            HStack {
                Text(MultiplatformConstants.shared.VOLUNTEER_SUBHEADING)
                    .textCase(.uppercase)
                    .font(.caption2)
                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    .padding(.leading)
                    .padding(.leading, 5)
                
                Spacer()
            }
            .padding(.vertical, 5)

            if serviceViewModel.services.isEmpty {
                Spacer()
                HStack {
                    Spacer()
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                    Spacer()
                }
            } else {
                ScrollView {
                    LazyVGrid(
                        columns: [
                            GridItem(.flexible()),
                            GridItem(.flexible())
                        ],
                        spacing: 10
                    ) {
                        ForEach(serviceViewModel.services, id: \.id) { tab in
                            NavigationLink {
                                switch tab.type {
                                case .meals:
                                    MealsView()
                                default:
                                    VolunteerServiceView(tab: .constant(tab), servicesViewModel: serviceViewModel)
                                }
                            } label: {
                                ZStack {
                                    RoundedRectangle(cornerRadius: 10, style: .continuous)
                                        .fill(.white)
                                        .shadow(radius: 1)
                                    
                                    VStack {
                                        WebImage(url: URL(string: tab.image))
                                            .resizable()
                                            .scaleEffect(0.85)
                                        Text(tab.name)
                                            .font(.headline)
                                            .foregroundColor(Color.black)
                                    }.scaleEffect(0.85)
                                }
                                .frame(maxWidth: 185, maxHeight: 180)
                            }
                            
                        }
                    }
                    .redacted(reason: serviceViewModel.services.isEmpty ? .placeholder : [])
                    .padding([.horizontal, .vertical], 10)
                }
            }
            Spacer()
            Divider()
                .padding(.bottom, 10)
        }
        .navigationBarTitleDisplayMode(.inline)
        .toolbar(content: {
                ToolbarItemGroup(placement: .navigationBarLeading) {
                    Text("Volunteer")
                        .font(.title3)
                        .fontWeight(.heavy)
                }
            }
        )
        .onAppear {
            if serviceViewModel.services.isEmpty {
                serviceViewModel.getServices()
            }
        }
    }
}

#Preview {
    Services()
}
