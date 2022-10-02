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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.mwaibanda.momentum.android.presentation.components.LockScreenOrientation


data class PlayedSermon(
    val id: String,
    val lastPlayedTime: Long
)


@Composable
fun PlayerScreen(
    sermonViewModel: SermonViewModel,
    videoURL: String
) {
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
            playWhenReady = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        val currentSermon = sermonViewModel.watchedSermons.firstOrNull {
            it.id == (sermonViewModel.currentSermon?.id ?: "")
        }
        currentSermon?.let {
            exoPlayer.apply {
                seekTo(it.lastPlayedTime)
                playWhenReady = true
            }

        }
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        Log.d("Player", "STATE READY")

                    }

                    Player.STATE_ENDED -> {}

                    Player.STATE_BUFFERING, Player.STATE_IDLE -> {}
                }
            }
        })
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
        onDispose {
            sermonViewModel.watchedSermons = buildList {
                addAll(sermonViewModel.watchedSermons)
                removeIf { it.id == (sermonViewModel.currentSermon?.id ?: "") }
                add(
                    PlayedSermon(
                        id = sermonViewModel.currentSermon?.id ?: "",
                        lastPlayedTime = exoPlayer.currentPosition
                    )
                )
            }
            exoPlayer.release()
        }
    }


}



