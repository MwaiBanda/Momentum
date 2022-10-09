package com.mwaibanda.momentum.android.presentation.sermon

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PercentageRating
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.mwaibanda.momentum.android.presentation.components.LockScreenOrientation
import com.mwaibanda.momentum.data.db.MomentumSermon

@Composable
fun PlayerScreen(
    sermonViewModel: SermonViewModel,
    videoURL: String
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
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
            playWhenReady = true
        }
    }
    val playerView = remember(context) {
        StyledPlayerView(context).apply {
            player = exoPlayer
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            setOnClickListener {
                Log.d("Player", "Clicked!")
            }
        }
    }

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
    LaunchedEffect(key1 = Unit) {
        val currentSermon = sermonViewModel.watchedSermons.firstOrNull {
            it.id == (sermonViewModel.currentSermon?.id ?: "")
        }
        currentSermon?.let {
            exoPlayer.apply {
                seekTo(it.last_played_time.toLong())
                playWhenReady = true
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        val playerListener = object: Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        Log.d("Player", "STATE READY")
                    }

                    Player.STATE_ENDED -> {}

                    Player.STATE_BUFFERING, Player.STATE_IDLE -> {}

                }
            }
        }

        val lifecycleListener = object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Log.d("Activity", "Create")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                playerView.onResume()
                exoPlayer.playWhenReady = true
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                playerView.onPause()
                exoPlayer.playWhenReady = false
            }
        }
        lifecycle.addObserver(lifecycleListener)
        exoPlayer.addListener(playerListener)
        onDispose {
            sermonViewModel.addSermon(MomentumSermon(
                id = sermonViewModel.currentSermon?.id ?: "",
                last_played_time = exoPlayer.currentPosition.toDouble(),
                last_played_percentage = ((exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()) * 100.0).toInt()
            )) {
                sermonViewModel.getWatchSermons()
            }
            Log.d("PlayPercentage", "Played ${(((exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()) * 100.0).toInt())}% of the video")
            lifecycle.removeObserver(lifecycleListener)
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 35.dp else 0.dp),
        factory = { playerView }
    )
}



