package com.mwaibanda.momentum.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MomentumEntry {
                NavHost(
                    navController = navController,
                    modifier = Modifier.padding(it),
                    startDestination = "offer"
                ) {
                    composable("offer") {
                        OfferScreen()
                    }
                }
            }
        }
    }
}
