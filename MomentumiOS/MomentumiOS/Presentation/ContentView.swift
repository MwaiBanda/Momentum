import SwiftUI
import MomentumSDK

struct ContentView: View {
    @StateObject private var session = Session()
	var body: some View {
		BottomTabBar()
            .environmentObject(session)
            .onAppear  {
                session.checkAndSignInAsGuest()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
