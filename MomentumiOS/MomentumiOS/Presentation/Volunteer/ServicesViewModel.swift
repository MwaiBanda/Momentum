//
//  ServicesViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 1/11/25.
//  Copyright © 2025 Momentum. All rights reserved.
//
import Foundation
import MomentumSDK
import TinyDi
import os

class ServicesViewModel: ObservableObject {
    @Inject private var servicesUseCases: ServicesUseCases
    @Published var services = [Tab]()
    @Published var service : Service? = nil
    func getServices() {
        servicesUseCases.read.execute().collect { [weak self] res in
            guard let status = res?.status else { fatalError("No Result Found") }
            switch(status) {
            case .loading:
                os_log("[getServices[Loading]]")
                break
            case .error:
                guard let message = res?.message  else { return }
                os_log("[getServices[Error]]: \(message)")
                break
            case .data:
                guard let services = res?.data as? [Tab] else { return }
                DispatchQueue.main.async {
                    self?.services = services
                }
                os_log("[getServices[Data]]")
                break
            default: break
            }
        }
    }
    
    func getServiceByType(tab: Tab, onCompletion: @escaping ([VolunteerService]) -> Void) {
        servicesUseCases.readByType.execute(type: tab.type).collect { [weak self] res in
            guard let status = res?.status else { fatalError("No Result Found") }
            switch(status) {
            case .loading:
                os_log("[getServiceByType[Loading]]")
                break
            case .error:
                guard let message = res?.message  else { return }
                os_log("[getServiceByType[Error]]: \(message)")
                break
            case .data:
                guard let service = res?.data else { return }
                DispatchQueue.main.async {
                    self?.service = service
                    onCompletion(service.services)
                }
                os_log("[getServiceByType[Data]]")
                break
            default: break
            }
        }
    }
    
    func updateVolunteerServiceDay(request: DayRequest, onCompletion: @escaping () -> Void) {
        servicesUseCases.updateDay.execute(request: request).collect { res in
            guard let status = res?.status else { fatalError("No Result Found") }
            switch(status) {
            case .loading:
                os_log("[updateVolunteerServiceDay[Loading]]")
                break
            case .error:
                guard let message = res?.message  else { return }
                os_log("[updateVolunteerServiceDay[Error]]: \(message)")
                break
            case .data:
                onCompletion()
                os_log("[updateVolunteerServiceDay[Data]]")
                break
            default: break
            }
        }
    }
    
    func postVolunteerService(request: VolunteerServiceRequest) {
        servicesUseCases.create.execute(request: request).collect { res in
            guard let status = res?.status else { fatalError("No Result Found") }
            switch(status) {
            case .loading:
                os_log("[postVolunteerService[Loading]]")
                break
            case .error:
                guard let message = res?.message  else { return }
                os_log("[postVolunteerService[Error]]: \(message)")
                break
            case .data:
                os_log("[postVolunteerService[Data]]")
                break
            default: break
            }
        }
    }
    
}
