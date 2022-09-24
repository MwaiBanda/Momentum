# Momentum Church: Indiana (SDK) 
Shared SDK for Momentum Church in Indiana. Built in Kotlin Multiplatform Mobile(KMM), the SDK allows users to make payments to the church, edit & update account information. Persists data locally w/ SQLDelight and Remote w/ Firebase. The SDK is architected to emphasize code sharing, so all core business logic is contained here, Android & iOS apps only contain presentation logic, viewmodels and tests.

## Dependencies
- SQLDelight
- Firebase Auth
- Firebase Storage
- Ktor Http Client
- Cache4K
- Koin
- Multiplaform Settings	
- Kotlin Serialization	
- Stately

## Architecture

The project is architected using Clean Architecture, the SDK is architected using MVC & Usecases it contains all core business objects/logic. The apps are architected using MVVM. Each ViewModel depends on a controller(Interface/Protocol), and with Dependency Injection upon creation of a ViewModel a concrete implementation is passed to ViewModel that conforms to the corresponding controller.
![momentumSDK (1)](https://user-images.githubusercontent.com/49708426/184941806-59b72cd4-1c26-412b-b7eb-1ae80fbc7dd7.png)
