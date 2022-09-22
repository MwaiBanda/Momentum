package com.mwaibanda.momentum.android.presentation.sermon

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioAttributes
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.*
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.components.LockScreenOrientation


@Composable
fun PlayerScreen(videoURL: String) {
    val context = LocalContext.current
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val media = MediaItem.Builder()
                .setUri(videoURL)
                .build()
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(media)

            setMediaSource(source)
            prepare()
        }
    }
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 30.dp else 0.dp),
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                    setOnClickListener {


                    }
                }
            })
    ) {
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.release()
        }

    }


}



