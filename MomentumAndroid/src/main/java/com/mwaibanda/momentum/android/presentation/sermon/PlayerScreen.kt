package com.mwaibanda.momentum.android.presentation.sermon

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.DefaultMediaItemConverter
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.mediarouter.app.MediaRouteButton
import androidx.navigation.NavController
import com.google.android.gms.cast.*
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.common.images.WebImage
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.exts.formatMinSec
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.PlayerControl
import com.mwaibanda.momentum.android.presentation.components.LockScreenOrientation
import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.domain.models.Sermon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun PlayerScreen(
    castContext: CastContext,
    navController: NavController,
    sermonViewModel: SermonViewModel,
    showControls: Boolean,
    sermon: Sermon,
    onShowControls: (Boolean) -> Unit,
    onBounds: (Rect) -> Unit
) {
    val context = LocalContext.current
    val isLandscape by sermonViewModel.isLandscape.collectAsState()
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val media = MediaItem.Builder()
                .setUri(sermon.videoURL)
                .build()
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(media)

            setMediaSource(source)
            prepare()
            playWhenReady = false
        }
    }


    val castPlayer = remember(context) {
        CastPlayer(castContext).apply {
            val mediaItem = MediaItem.Builder()
                .setUri("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd")
                .setMediaMetadata(
                    androidx.media3.common.MediaMetadata.Builder().setTitle("Clear DASH: Tears")
                        .build()
                )
                .setMimeType(MimeTypes.APPLICATION_MPD)
                .build()
            addMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }
    var showCast by remember {
        mutableStateOf(false)
    }
    var currentPlayer: Player? by remember {
        mutableStateOf(null)
    }


    castPlayer.setSessionAvailabilityListener(object : SessionAvailabilityListener {
        override fun onCastSessionAvailable() {
            showCast = true
            currentPlayer = castPlayer
            exoPlayer.release()
            val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
            movieMetadata.putString(MediaMetadata.KEY_TITLE, sermon.title)
            movieMetadata.putString(MediaMetadata.KEY_ALBUM_ARTIST, sermon.preacher)
            movieMetadata.putString(MediaMetadata.KEY_SERIES_TITLE, sermon.series)
            movieMetadata.addImage(WebImage(Uri.parse(sermon.videoThumbnail)))
            val mediaInfo: MediaInfo = MediaInfo.Builder(sermon.videoURL)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType(MimeTypes.VIDEO_UNKNOWN)
                .setMetadata(movieMetadata).build()
            val mediaQueueItem = MediaQueueItem.Builder(mediaInfo).build()
            castContext.sessionManager.currentCastSession?.remoteMediaClient?.load(
                MediaLoadRequestData.fromJson(mediaQueueItem.toJson())
            )
        }

        override fun onCastSessionUnavailable() {
            currentPlayer = exoPlayer
        }
    })


    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
    LaunchedEffect(key1 = Unit) {
        val currentSermon =
            sermonViewModel.getCurrentSermon(id = sermonViewModel.currentSermon?.id ?: "")
        currentSermon?.let {
            exoPlayer.apply {
                seekTo(it.last_played_time.toLong())
                playWhenReady = true // Turn back to true
            }
        }

    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            sermonViewModel.addSermon(
                MomentumSermon(
                    id = sermonViewModel.currentSermon?.id ?: "",
                    last_played_time = exoPlayer.currentPosition.toDouble(),
                    last_played_percentage = ((exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()) * 100.0).toInt()
                )
            ) {
                sermonViewModel.getWatchSermons()
            }
            Log.d(
                "PlayPercentage",
                "Played ${(((exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()) * 100.0).toInt())}% of the video"
            )
            exoPlayer.release()
        }
    }

    Player(
        Modifier.clickable { onShowControls(true) },
        exoPlayer = currentPlayer ?: exoPlayer,
        showControls = showControls,
        isLandscape = isLandscape,
        onShowControls = onShowControls,
        onBounds = onBounds
    ) {
        navController.popBackStack()
    }
}


@Composable
fun Player(
    modifier: Modifier = Modifier,
    exoPlayer: Player,
    showControls: Boolean,
    isLandscape: Boolean,
    onShowControls: (Boolean) -> Unit,
    onBounds: (Rect) -> Unit,
    onClosePlayer: () -> Unit
) {
    val context = LocalContext.current
    var totalDuration by remember { mutableStateOf(0L) }
    var currentTime by remember { mutableStateOf(0L) }
    var bufferedPercentage by remember { mutableStateOf(0) }
    var addDelay by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(true) }
    var count by remember { mutableStateOf(0L) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = showControls) {
        launch {
            delay(4500)
            if (addDelay) {
                delay(4000)
                addDelay = false
            }
            onShowControls(false)
        }
        launch {
            if (isPlaying) {
                count++
            }
        }

    }
    LaunchedEffect(key1 = count) {
        if (isPlaying) {
            delay(1000)
            currentTime = exoPlayer.currentPosition.coerceAtLeast(0L)
            count++
            if (count > 10L) {
                count = 0L
            }
        }
    }


    val playerView = remember(context) {
        PlayerView(context).apply {
            player = exoPlayer
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setShowSubtitleButton(true)
            useController = false
        }
    }




    Box(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                onBounds(
                    it
                        .boundsInWindow()
                        .toAndroidRect()
                )
            }
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        DisposableEffect(key1 = Unit) {
            val lifecycleListener = object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)
                    Log.d("Activity", "Create")
                }

                override fun onStart(owner: LifecycleOwner) {
                    super.onStart(owner)
//                    exoPlayer.playWhenReady = true TODO("If not cast play when ready")
                }

                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    playerView.onResume()
                }


                override fun onStop(owner: LifecycleOwner) {
                    super.onStop(owner)
                    playerView.onPause()
                    exoPlayer.playWhenReady = false
                }
            }
            val listener = object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    currentTime = player.currentPosition.coerceAtLeast(0L)
                    totalDuration = player.duration.coerceAtLeast(0L)
                    bufferedPercentage = player.bufferedPercentage
                }
            }
            exoPlayer.addListener(listener)
            lifecycle.addObserver(lifecycleListener)

            onDispose {
                lifecycle.removeObserver(lifecycleListener)
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }
        if (exoPlayer == CastPlayer::class.java) {
            Text(text = "Casting")
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { playerView }
            )
        }

        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PlayerControls(
                isPlaying = isPlaying,
                isLandscape = isLandscape,
                totalDuration = totalDuration,
                currentTime = currentTime,
                bufferedPercentage = bufferedPercentage,
                control = object : PlayerControl {
                    override fun onPlayPause(isPlayingContent: Boolean) {
                        if (isPlayingContent) {
                            exoPlayer.pause()
                        } else {
                            exoPlayer.play()

                        }
                        addDelay = true
                        isPlaying = isPlaying.not()
                    }

                    override fun onSeekBackwards() {
                        exoPlayer.seekTo((exoPlayer.currentPosition) - 30000L)
                        addDelay = true
                    }

                    override fun onSeekForward() {
                        exoPlayer.seekTo((exoPlayer.currentPosition) + 30000L)
                        addDelay = true
                    }

                    override fun onSeekChanged(seekTo: Float) {
                        exoPlayer.seekTo(seekTo.toLong())
                    }

                    override fun closePlayer() {
                        onClosePlayer()
                    }
                }
            )
        }
    }
}

@Composable
fun PlayerControls(
    isPlaying: Boolean,
    isLandscape: Boolean,
    totalDuration: Long,
    currentTime: Long,
    bufferedPercentage: Int,
    control: PlayerControl
) {
    Box(modifier = Modifier.background(Color.Black.copy(0.6f))) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            TopControls(
                isLandscape = isLandscape,
                onClosePlayer = control::closePlayer
            )
            CenterControls(
                isPlaying = isPlaying,
                onPlayPause = control::onPlayPause,
                onSeekBackwards = control::onSeekBackwards,
                onSeekForward = control::onSeekForward
            )

            BottomControls(
                isLandscape = isLandscape,
                totalDuration = totalDuration,
                currentTime = currentTime,
                bufferedPercentage = bufferedPercentage,
                onSeekChanged = control::onSeekChanged
            )

        }
    }
}

@Composable
fun TopControls(
    isLandscape: Boolean,
    onClosePlayer: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = if (isLandscape) 55.dp else 10.dp)
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = {
            onClosePlayer()
        }) {
            Icon(
                painter = painterResource(id = com.mwaibanda.momentum.android.R.drawable.ic_baseline_close_24),
                contentDescription = "Share button",
                tint = Color.White
            )
        }
        AndroidView(factory = { context ->
            MediaRouteButton(context).apply {
                CastButtonFactory.setUpMediaRouteButton(context, this)
                setBackgroundColor(Color.White.toArgb())
                dialogFactory.apply {

                }
            }
        })
    }
}

@Composable
fun CenterControls(
    isPlaying: Boolean,
    onPlayPause: (Boolean) -> Unit,
    onSeekBackwards: () -> Unit,
    onSeekForward: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onSeekBackwards) {
            Icon(
                painter = painterResource(id = com.mwaibanda.momentum.android.R.drawable.ic_baseline_replay_30_50),
                contentDescription = "rewind button",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            onPlayPause(isPlaying)
        }) {
            if (isPlaying) {
                Icon(
                    painter = painterResource(id = com.mwaibanda.momentum.android.R.drawable.ic_baseline_pause_100),
                    contentDescription = "pause button",
                    tint = Color.White
                )
            } else {
                Icon(
                    painter = painterResource(id = com.mwaibanda.momentum.android.R.drawable.ic_baseline_play_arrow_100),
                    contentDescription = "play button",
                    tint = Color.White
                )
            }
        }
        IconButton(onClick = onSeekForward) {
            Icon(
                painter = painterResource(id = com.mwaibanda.momentum.android.R.drawable.ic_baseline_forward_30_50),
                contentDescription = "fast forward button",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BottomControls(
    isLandscape: Boolean,
    totalDuration: Long,
    currentTime: Long,
    bufferedPercentage: Int,
    onSeekChanged: (seekTo: Float) -> Unit
) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = if (isLandscape) 55.dp else 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentTime.formatMinSec(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Row(Modifier.fillMaxWidth(0.85f)) {
                Box {
                    Slider(
                        value = bufferedPercentage.toFloat(),
                        enabled = false,
                        onValueChange = { },
                        valueRange = 0f..100f,
                        colors =
                        SliderDefaults.colors(
                            disabledThumbColor = Color.Transparent,
                            disabledActiveTrackColor = Color.Gray
                        )
                    )
                    Slider(
                        value = currentTime.toFloat(),
                        valueRange = 0f..totalDuration.toFloat(),
                        onValueChange = onSeekChanged,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color(C.MOMENTUM_ORANGE),
                            inactiveTrackColor = Color.White.copy(0.5f)
                        )
                    )
                }
            }
            Text(
                text = totalDuration.formatMinSec(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



