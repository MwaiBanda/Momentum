import SwiftUI
import MomentumSDK

struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
		BottomTabBar()
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
