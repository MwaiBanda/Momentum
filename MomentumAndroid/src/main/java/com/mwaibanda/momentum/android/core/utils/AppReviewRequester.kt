package com.mwaibanda.momentum.android.core.utils

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.mwaibanda.momentum.android.BuildConfig
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController

class AppReviewRequester(
    private val localDefaultsController: LocalDefaultsController
) {
    private var lastRun = 0
    private var version = ""
    private var hasReviewed = false
    init {
        localDefaultsController.getInt(key = LAST_RUN) {
            lastRun = it
        }
        localDefaultsController.getString(key = VERSION) {
            version = it
        }
        localDefaultsController.getBoolean(key = HAS_REVIEWED) {
            hasReviewed = it
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
                        val reviewInfo = task.result
                        val flow = manager.launchReviewFlow(context, reviewInfo)
                        flow.addOnCompleteListener { _ ->
                            lastRun = 0
                            localDefaultsController.setString(key = VERSION, value = currentVersion)
                            localDefaultsController.setBoolean(key = HAS_REVIEWED, value = true)
                            Log.d("APP-REVIEW", "Requested $currentVersion")
                        }
                    } else {
                        @ReviewErrorCode val reviewErrorCode = (task.exception as ReviewException).errorCode
                        Log.d("REVIEW-ERROR", reviewErrorCode.toString())
                    }
                }
            }
            Log.d("NEW-VERSION", "Version $currentVersion")
            localDefaultsController.setBoolean(key = HAS_REVIEWED, value = false)
        }
        localDefaultsController.setInt(key = LAST_RUN, value = if (hasReviewed) 0 else lastRun)
        Log.d("THRESHOLD", "Last Run: $lastRun")
    }

    companion object {
        const val THRESHOLD = 8
        const val LAST_RUN = "lastRun"
        const val VERSION = "version"
        const val HAS_REVIEWED = "hasReviewed"
    }
}