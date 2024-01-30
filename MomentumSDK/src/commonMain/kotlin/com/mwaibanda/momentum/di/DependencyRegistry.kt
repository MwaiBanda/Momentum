package com.mwaibanda.momentum.di


import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            singletonModule,
            repositoryModule,
            useCasesModule,
            controllerModule
        )
    }

// IOS
fun initKoin() = initKoin {
    modules(
        singletonModule,
        repositoryModule,
        useCasesModule,
        controllerModule
    )
}

