import SwiftUI
import FirebaseMessaging
import AVKit
import MomentumSDK
import FirebaseCore
import TinyDi

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
   
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

extension DependencyRegistry {
    func inject() {
        TDi.inject { resolver in
            singletonModule()
            controllerModule(resolver: resolver)
            
        }
    }
}

class AppDelegate : NSObject, UIApplicationDelegate {
    static var orientationLock = UIInterfaceOrientationMask.portrait 
    
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        return AppDelegate.orientationLock
    }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        DependencyRegistryKt.doInitKoin()
        DependencyRegistry.shared.inject()
        
        Thread.sleep(forTimeInterval: 1.0)
        do {
            try AVAudioSession.sharedInstance().setCategory(AVAudioSession.Category.playback, mode: AVAudioSession.Mode.default)
            try AVAudioSession.sharedInstance().setActive(true)
        }
        catch {
            print("Setting category to AVAudioSessionCategoryPlayback failed.")
        }
        return true
    }
    
    
    // MARK: UISceneSession Lifecycle
    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }
    
    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
    
}

extension AppDelegate: UNUserNotificationCenterDelegate, MessagingDelegate {
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
       Messaging.messaging().apnsToken = deviceToken
       //FCM TOken
       Messaging.messaging().token { token, error in
           if let error = error {
               print("Error fetching FCM registration token: \(error)")
           } else if let token = token {
               print(token)
           }
       }
   }
    func application(_ application: UIApplication, willFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        UNUserNotificationCenter.current().delegate = self

        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(
          options: authOptions,
          completionHandler: { _, _ in }
        )

        application.registerForRemoteNotifications()
        Messaging.messaging().delegate = self
          
        return true
    }
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("Firebase registration token: \(String(describing: fcmToken ?? "" ))")
        Messaging.messaging().subscribe(toTopic: MultiplatformConstants.shared.ALL_USERS_TOPIC) { error in
            if let error {
                print(error.localizedDescription)
            } else {
                print("Subscribed to \(MultiplatformConstants.shared.ALL_USERS_TOPIC) topic")
                Messaging.messaging().subscribe(toTopic: MultiplatformConstants.shared.ALL_IOS_USERS_TOPIC) { error in
                    if let error {
                        print(error.localizedDescription)
                    } else {
                        print("Subscribed to \(MultiplatformConstants.shared.ALL_IOS_USERS_TOPIC) topic")
                    }
                    
                }
            }
        }
         let dataDict: [String: String] = ["token": fcmToken ?? ""]
         NotificationCenter.default.post(
           name: Notification.Name("FCMToken"),
           object: nil,
           userInfo: dataDict
         )
        
     
    }
    func application(
        _ application: UIApplication,
                     
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) async -> UIBackgroundFetchResult {

      print(userInfo)

      return UIBackgroundFetchResult.newData
    }
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void)
    {
        completionHandler(.banner)

    }

}
