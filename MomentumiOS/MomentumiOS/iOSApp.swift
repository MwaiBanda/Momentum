import SwiftUI
import MomentumSDK

@main
struct iOSApp: App {
    init() {
        DependencyRegistryKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
