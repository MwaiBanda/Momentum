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

## Dependencies, Targets & Stack 

<table>
    <tr>
    <td><b>Project Structure</b></td>
    <td><b>Targets</b></td>
  </tr>
  <tr>
    <td>Kotlin MultiPlatform Mobile</td>
    <td>Android & iOS</td>
  </tr>
    <tr>
    <td><b>Momentum SDK</b>(MultiPlatform)</td>
    <td> </td>
  </tr>
  <tr>
    <td>SQLDelight</td>
    <td> </td>
  </tr>
  <tr>
    <td>Ktor Http Client</td>
    <td> </td>
  </tr>
 <tr>
    <td>Koin</td>
    <td> </td>
  </tr>
  <tr>
    <td>Firebase Auth</td>
    <td> </td>
  </tr>
  <tr>
    <td>Firebase Firestore</td>
    <td> </td>
  </tr>
  <tr>
    <td>Kotlin Serialization </td>
    <td> </td>
  </tr>
   <tr>
    <td>Stately</td>
    <td> </td>
  </tr>
  <tr>
    <td><b>Android</b></td>
    <td><b>iOS</b></td>
  </tr>
   <tr>
    <td>Jetpack Compose</td>
     <td>SwiftUI</td>
  </tr>
    <tr>
    <td>Stripe</td>
     <td>Stripe</td>
  </tr>
  <tr>
    <td>Firebase</td>
     <td>Firebase</td>
  </tr>
   <tr>
    <td>LottieAnimations </td>
     <td>LottieAnimations</td>
  </tr>
    
   <tr>
    <td>Koin</td>
     <td></td>
  </tr>
  </table>

### Architecture
The project is architected w/ `Clean Architecture`, the SDK is architected w/ `MVC & Usecases` it contains all core business objects/logic. The apps are architected using `MVVM`. Each ViewModel depends on a controller(Interface/Protocol), and with the Dependency Injection upon creation of a ViewModel a concrete implementation is passed to ViewModel that conforms to the corresponding controller.
![momentum_architecture](https://user-images.githubusercontent.com/49708426/184673790-630a501f-9372-41bf-bf7a-a9ee99086c68.png)


## Android

<table>
  <tr>
     <td>Lanuch Screen</td>
     <td>Offer Screen</td>
     <td>SignUp Screen</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184928071-8b19f8dc-50fb-4fd1-a73a-85c4524ac48d.png" width=320 height=500></td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184928269-a852ba26-1935-4507-9535-d91305f093a9.png" width=320 height=500</td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184928835-50e451e0-f979-472a-938d-9d478999596d.png" width=320 height=500</td>
  </tr>
  <tr>
    <td>SignIn Screen</td>
    <td>Payment Summary Screen</td>
    <td>Stripe</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184929199-25d43aea-8a43-46e9-8551-4068868bc482.png" width=320 height=500></td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184930835-8795c25a-99ee-4220-aea8-35af0c1536f3.png" width=320 height=500</td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184931160-73b50904-bb03-4f13-9149-58295bd38b07.png" width=320 height=500</td>
  </tr>
   <tr>
    <td>Profile Screen</td>
    <td>Transaction Screen</td>
    <td></td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184931476-4b6388a7-59cc-4c4c-b241-8f310eb60dcf.png" width=320 height=500></td>
    <td> <img src="https://user-images.githubusercontent.com/49708426/184932926-3a666cc4-76d5-4674-a3ff-67bbd2d7d152.png" width=320 height=500></td>
    <td></td>

  </tr>
</table>



