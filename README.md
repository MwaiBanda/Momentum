# Momentum
Multiplatform payments apps(Android & iOS) for Momentum Church in Indiana. Built in Kotlin Multiplatform Mobile(KMM) with SwiftUI & Jetpack Compose, the apps allow users to make payments to the church, edit & update account information. Persists data locally with SQLDelight and Remote with Firebase. The apps are architected to emphasize code sharing between Android & iOS, so all core business logic written in the SDK, Android & iOS apps only contain presentation logic, viewmodels and tests.

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
    <td>Multiplaform Settings</td>
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
    <td>StateFlow</td>
     <td></td>
  </tr>
   <tr>
    <td>Koin</td>
     <td></td>
  </tr>
  </table>

### Architecture
The project is architected using `Clean Architecture`, the SDK is architected using `MVC & Usecases` it contains all core business objects/logic. The apps are architected using `MVVM`. Each ViewModel depends on a controller(Interface/Protocol), and with Dependency Injection upon creation of a ViewModel a concrete implementation is passed to ViewModel that conforms to the corresponding controller.
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

## iOS
<table>
  <tr>
     <td>Lanuch Screen</td>
     <td>Offer Screen</td>
     <td>SignUp Screen</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184936981-c2833f8c-406c-469a-be88-ad27b5535c2c.png" width=320 height=500></td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184937170-b124b3aa-4967-4857-9227-ade8179fd19d.png" width=320 height=500</td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184937772-1f37bcad-8835-4d8d-b8db-9ef8afa74252.png" width=320 height=500</td>
  </tr>
  <tr>
    <td>SignIn Screen</td>
    <td>Payment Summary Screen</td>
    <td>Stripe</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184938133-f1b1521c-8052-4122-b2d9-20ca710c4476.png" width=320 height=500></td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184938525-00b680b1-e6e9-4997-8f98-11a0c6d35d2f.png" width=320 height=500</td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184939932-e37f4bca-b38b-4528-a52e-3edda7f5c0dc.png" width=320 height=500</td>
  </tr>
   <tr>
    <td>Profile Screen</td>
    <td>Transaction Screen</td>
    <td>Payment Success</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/49708426/184938859-94c26627-276b-4e77-906d-6a0172377bc3.png" width=320 height=500></td>
    <td> <img src="https://user-images.githubusercontent.com/49708426/184939575-4d18a52c-d1be-4105-8715-cfa668caa3a8.png" width=320 height=500></td>
    <td><img src="https://user-images.githubusercontent.com/49708426/184939742-db25468b-5ffd-4459-ab15-4143d20a417d.png" width=320 height=500></td>
  </tr>
</table>
