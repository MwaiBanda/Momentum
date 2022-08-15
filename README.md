# Momentum
Multiplatform payments apps(Android & iOS) for Momentum Church in Indiana. Built in Kotlin Multiplatform Mobile(KMM) w/ SwiftUI & Jetpack Compose, the app allows users to make payments to the church, edit & update account information. Persists data locally w/ SQLDelight and Remote w/ Firebase. The apps are architected to emphasize code sharing between Android & iOS, so all core business logic written in the SDK, Android & iOS app only contain presentation logic and viewmodels.

### Download Via AppStore or PlayStore
The app's are available for download on the app store & playstore for Android & iPhone
<table>
  <tr>
    <th colspan="2"> <b>Momentum Church: Indiana - Payments(Android & iOS)</b> </th>
    
  </tr>
  <tr>   
     <td> <a href="https://apps.apple.com/us/app/momentum-church-indiana/id1637040037"  target="_blank" rel="noopener noreferrer"><img src="https://user-images.githubusercontent.com/49708426/137259580-5fbacaac-7fd3-4946-9412-7f1447e19075.png" width=140 height=45></a></td>
    <td><a href="https://play.google.com/store/apps/details?id=com.mwaibanda.momentum.android"  target="_blank" rel="noopener noreferrer"><img src="https://user-images.githubusercontent.com/49708426/152633576-d28488c9-68e1-4d5e-9922-b502e74d5c00.png"  width=140 height=70></a></td>
  </tr>
</table>

### Architecture
The SDK is architected w/ `MVC` it contains all core business objects/logic.The apps are architected using `MVVM w/ Clean Architecture`. Each ViewModel depends on a controller(Interface), and with the Dependency Injection upon creation of a ViewModel a concrete implementation is passed to ViewModel that conforms to the corresponding controller.
![momentum_architecture](https://user-images.githubusercontent.com/49708426/184673790-630a501f-9372-41bf-bf7a-a9ee99086c68.png)


