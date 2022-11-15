package com.mwaibanda.momentum.android.presentation.sermon

import android.content.pm.ActivityInfo
import android.graphics.Picture
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CastConnected
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.mediarouter.app.MediaRouteButton
import androidx.navigation.NavController
import com.google.android.exoplayer2.DeviceInfo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.ext.cast.SessionAvailabilityListener
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.*
import com.google.android.gms.cast.framework.*
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
fun PlayerScreen(
    castContext: CastContext,
    navController: NavController,
    sermonViewModel: SermonViewModel,
    showControls: Boolean,
    sermon: Sermon,
    onShowControls: (Boolean) -> Unit,
    canEnterPictureInPicture: (Boolean) -> Unit,
    onBounds: (Rect) -> Unit
) {
    val context = LocalContext.current
    val isLandscape by sermonViewModel.isLandscape.collectAsState()
    var isCasting by remember { mutableStateOf(false) }
    var currentPlayer: Player? by remember { mutableStateOf(null) }
    var lastPosition by remember { mutableStateOf(0L) }

    var exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val media = MediaItem.Builder()
                .setUri(sermon.videoURL)
                .setMimeType(MimeTypes.VIDEO_MP4)
                .build()
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(media)

            setMediaSource(source)
            prepare()
            playWhenReady = true
        }
    }

    var castPlayer = remember(context) {
        CastPlayer(castContext)
    }





    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
    LaunchedEffect(key1 = Unit) {
        canEnterPictureInPicture(true)
        val currentSermon =
            sermonViewModel.getCurrentSermon(id = sermonViewModel.currentSermon?.id ?: "")
        currentSermon?.let {
            exoPlayer.apply {
                seekTo(it.last_played_time.toLong())
                playWhenReady = true // Turn back to true
            }
        }
        currentPlayer = exoPlayer
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    DisposableEffect(key1 = Unit) {
        castPlayer.setSessionAvailabilityListener(object : SessionAvailabilityListener {
            override fun onCastSessionAvailable() {
                currentPlayer?.release()
                isCasting = true
                castPlayer =  CastPlayer(castContext)

                val sermonMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
                sermonMetadata.putString(MediaMetadata.KEY_TITLE, sermon.title)
                sermonMetadata.putString(MediaMetadata.KEY_ALBUM_ARTIST, sermon.preacher)
                sermonMetadata.putString(MediaMetadata.KEY_SERIES_TITLE, sermon.series)
                sermonMetadata.addImage(WebImage(Uri.parse(sermon.videoThumbnail)))
                val mediaInfo: MediaInfo = MediaInfo.Builder(sermon.videoURL)
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType(MimeTypes.APPLICATION_MP4)
                    .setMetadata(sermonMetadata).build()
                val mediaQueueItem = MediaQueueItem.Builder(mediaInfo).build()
                castContext.sessionManager.currentCastSession?.remoteMediaClient?.apply {
                    load(MediaLoadRequestData.fromJson(mediaQueueItem.toJson()))
                }
                currentPlayer = castPlayer
                lastPosition = exoPlayer.currentPosition
                exoPlayer.release()
            }

            override fun onCastSessionUnavailable() {
                isCasting = false
                exoPlayer = ExoPlayer.Builder(context).build().apply {
                    val dataSourceFactory = DefaultHttpDataSource.Factory()
                    val media = MediaItem.Builder()
                        .setUri(sermon.videoURL)
                        .setMimeType(MimeTypes.APPLICATION_MP4)
                        .build()
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(media)

                    setMediaSource(source)
                    seekTo(castPlayer.currentPosition)
                    currentPlayer?.release()
                    prepare()
                    playWhenReady = true
                }
                currentPlayer = exoPlayer
            }
        })
        castPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_READY -> {
                        Log.e("Player", "STATE READY")
                        castPlayer.seekTo(lastPosition)
                    }

                    Player.STATE_ENDED -> {}

                    Player.STATE_BUFFERING, Player.STATE_IDLE -> {}

                }
            }
        })
        castContext.sessionManager.addSessionManagerListener(object: SessionManagerListener<Session> {
            override fun onSessionEnded(p0: Session, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onSessionEnding(p0: Session) {
                TODO("Not yet implemented")
            }

            override fun onSessionResumeFailed(p0: Session, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onSessionResumed(p0: Session, p1: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onSessionResuming(p0: Session, p1: String) {
                TODO("Not yet implemented")
            }

            override fun onSessionStartFailed(p0: Session, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onSessionStarted(p0: Session, p1: String) {
                TODO("Not yet implemented")
            }

            override fun onSessionStarting(p0: Session) {
                TODO("Not yet implemented")
            }

            override fun onSessionSuspended(p0: Session, p1: Int) {
                TODO("Not yet implemented")
            }

        })
        onDispose {
            canEnterPictureInPicture(false)
            sermonViewModel.addSermon(
                MomentumSermon(
                    id = sermonViewModel.currentSermon?.id ?: "",
                    last_played_time = (currentPlayer?.currentPosition?.toDouble() ?: 0.0),
                    last_played_percentage = (((currentPlayer?.currentPosition?.toFloat() ?: 0f) / (currentPlayer?.duration?.toFloat() ?: 0f)) * 100.0).toInt()
                )
            ) {
                sermonViewModel.getWatchSermons()
            }
            Log.d(
                "PlayPercentage",
                "Played ${(((exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()) * 100.0).toInt())}% of the video"
            )
            castContext.sessionManager.endCurrentSession(true)
            exoPlayer.release()
            castPlayer.release()
            currentPlayer?.release()
        }
    }
    Box(
        modifier = Modifier
            .background(Color.Black)
            .systemBarsPadding()
    ) {
        Player(
            Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onShowControls(true) },
            isCasting = isCasting,
            player = currentPlayer ?: exoPlayer,
            showControls = showControls,
            isLandscape = isLandscape,
            onShowControls = onShowControls,
            onBounds = onBounds
        ) {
            navController.popBackStack()
            canEnterPictureInPicture(false)
        }
    }
}


@Composable
fun Player(
    modifier: Modifier = Modifier,
    player: Player,
    isCasting: Boolean,
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
            currentTime = player.currentPosition.coerceAtLeast(0L)
            count++
            if (count > 10L) {
                count = 0L
            }
        }
    }


    val playerView = remember(context) {
        StyledPlayerView(context).apply {
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
                    player.playWhenReady = true
                }

                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    playerView.onResume()
                }


                override fun onStop(owner: LifecycleOwner) {
                    super.onStop(owner)
                    playerView.onPause()
                    player.playWhenReady = false
                }
            }
            val listener = object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    currentTime = player.currentPosition.coerceAtLeast(0L)
                    totalDuration = player.duration.coerceAtLeast(0L)
                    bufferedPercentage = player.bufferedPercentage
                }
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            Log.e("Player", "STATE READY")
                        }

                        Player.STATE_ENDED -> {}

                        Player.STATE_BUFFERING, Player.STATE_IDLE -> {}

                    }
                }
            }
            player.addListener(listener)
            lifecycle.addObserver(lifecycleListener)

            onDispose {
                lifecycle.removeObserver(lifecycleListener)
                player.removeListener(listener)
                player.release()
            }
        }
        if (isCasting) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.CastConnected,
                    contentDescription = "Cast Icon",
                    tint = Color.Gray,
                    modifier = Modifier.fillMaxSize(0.5f)
                )
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { playerView }
            )
        }
        LaunchedEffect(key1 = player, block = {
            playerView.player = player

        })


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
                            player.pause()
                        } else {
                            player.play()

                        }
                        addDelay = true
                        isPlaying = isPlaying.not()
                    }

                    override fun onSeekBackwards() {
                        player.seekTo((player.currentPosition) - 30000L)
                        addDelay = true
                    }

                    override fun onSeekForward() {
                        player.seekTo((player.currentPosition) + 30000L)
                        addDelay = true
                    }

                    override fun onSeekChanged(seekTo: Float) {
                        player.seekTo(seekTo.toLong())
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
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = "Share button",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        AndroidView(
            modifier = Modifier.offset(x = -(10).dp),
            factory = { context ->
                MediaRouteButton(context).apply {
                    CastButtonFactory.setUpMediaRouteButton(context, this)
                }
            }
        )
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
            .padding(bottom = 20.dp)
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



