package com.mwaibanda.momentum.android.core.utils

interface PlayerControl {
    fun closePlayer()
    fun onPlayPause(isPlayingContent: Boolean)
    fun onSeekBackwards()
    fun onSeekForward()
    fun onSeekChanged(seekTo: Float)
}
