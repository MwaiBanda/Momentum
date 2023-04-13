package com.mwaibanda.momentum.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.github.mwaibanda.authentication.di.Authentication
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

object Authentication {
    val auth = Authentication
}