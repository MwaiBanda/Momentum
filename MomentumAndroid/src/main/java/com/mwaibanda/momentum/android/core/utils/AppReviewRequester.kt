package com.mwaibanda.momentum.android.core.utils

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.mwaibanda.momentum.android.BuildConfig
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController

class AppReviewRequester(private val localDefaultsController: LocalDefaultsController) {
    private var lastRun = 0
    private var version = ""
    init {
        localDefaultsController.getInt(key = LAST_RUN) {
            lastRun = it
        }
        localDefaultsController.getString(key = VERSION) {
            version = it
        }
    }

    fun request(context: ComponentActivity) {
        val manager = ReviewManagerFactory.create(context)
        val request = manager.requestReviewFlow()
        val currentVersion = BuildConfig.VERSION_NAME
        lastRun += 1
        if (version != currentVersion) {
            if (lastRun >= THRESHOLD) {
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // We got the ReviewInfo object
                        val reviewInfo = task.result
                        val flow = manager.launchReviewFlow(context, reviewInfo)
                        flow.addOnCompleteListener { _ ->
                            localDefaultsController.setInt(key = LAST_RUN, value = lastRun)
                            localDefaultsController.setString(key = VERSION, value = version)
                            Log.d("AppReview", "Requested $currentVersion")

                        }
                    } else {
                        // There was some problem, log or handle the error code.
                        @ReviewErrorCode val reviewErrorCode = (task.getException())
                        Log.d("ReviewError", reviewErrorCode.toString())
                    }
                }
            }
        }
    }

    companion object {
        const val THRESHOLD = 3
        const val LAST_RUN = "lastRun"
        const val VERSION = "version"

    }
}