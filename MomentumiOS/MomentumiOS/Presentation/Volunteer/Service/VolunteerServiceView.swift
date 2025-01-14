//
//  Meals.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/6/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MultiDatePicker
import MomentumSDK
import Lottie


struct VolunteerServiceView: View {
    @Binding var tab: MomentumSDK.Tab
    @ObservedObject var servicesViewModel: ServicesViewModel
    @StateObject private var profileViewModel = ProfileViewModel()
    @State private var showAddServiceSheet = false
    @State private var showAuthSheet = false
    @State private var services = [VolunteerService]()
    @EnvironmentObject var session: Session
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Divider()
            HStack {
                Text(MultiplatformConstants.shared.MEALS_SUBHEADING.uppercased())
                    .font(.caption2)
                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    .padding(.leading)
                    .padding(.leading, 5)
                
                Spacer()
            }
            .padding(.top, 5)
            if #available(iOS 15, *) {
                ServiceList(services: $services, profileViewModel: profileViewModel, servicesViewModel: servicesViewModel)
                    .refreshable {
                        services = []
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5, execute: {
                            servicesViewModel.getServiceByType(tab: tab) { remote in
                                DispatchQueue.main.async {
                                    services = remote
                                }
                            }
                        })
                    }
            } else {
                ServiceList(services: $services, profileViewModel: profileViewModel, servicesViewModel: servicesViewModel)
            }
            Divider()
                .padding(.bottom, 5)
        }
        .onAppear {
            DispatchQueue.global().async {
                if !(session.currentUser?.isGuest ?? true) {
                    profileViewModel.getContactInformation(userId: session.currentUser?.id ?? "") {
                        profileViewModel.getBillingInformation(userId: session.currentUser?.id ?? "")
                    }
                }
                servicesViewModel.getServiceByType(tab: tab) { remote in
                    DispatchQueue.main.async {
                        services = remote
                    }
                }
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle(tab.name)
        .toolbar(
            content: {
                ToolbarItemGroup(placement: .navigationBarTrailing) {
                    Button {
                        withAnimation(.easeInOut(duration: 0.35)) {
                            if (session.currentUser?.isGuest ?? true) {
                                showAuthSheet.toggle()
                            } else {
                                showAddServiceSheet.toggle()
                            }
                        }
                    } label: {
                        Image(systemName: "plus")
                    }
                }
            }
        )
        .sheet(isPresented: $showAuthSheet) {
            MomentumBlurredBackground {
                AuthControllerView()
            }
        }
        .sheet(isPresented: $showAddServiceSheet) {
            UploadServiceCardView(tab: $tab) {
                print("Dismiss")
                showAddServiceSheet.toggle()
                servicesViewModel.postVolunteerService(request: $0)
            }
        }
    }
}



struct ServiceList: View {
    @Binding var services: [VolunteerService]
    @ObservedObject var profileViewModel: ProfileViewModel
    @ObservedObject var servicesViewModel: ServicesViewModel
    var body: some View {
        ScrollView(showsIndicators: false) {
            if services.isEmpty {
                ForEach(0..<12, id: \.self) { _ in
                    DescriptionCard(title: "placeholder", description: "placeholder")
                }
            } else {
                ForEach(services, id: \.id) { service in
                    NavigationLink  {
                        VolunteerServiceDetailView(profileViewModel: profileViewModel, servicesViewModel: servicesViewModel, service: service)
                    } label: {
                        DescriptionCard(title: service.description_, description: service.organizer)
                            .padding(.bottom, 5)
                            .padding([.bottom, .horizontal], 5)
                    }
                }
            }
        }
        .redacted(reason: services.isEmpty ? .placeholder : [])
        .padding(.top, 15)
        .padding(.bottom, 10)
    }
}






