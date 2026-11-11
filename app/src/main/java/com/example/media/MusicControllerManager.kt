package com.example.media

import android.content.ComponentName
import android.content.Context
import android.media.AudioManager
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.util.Log
import com.example.service.MusicNotificationListenerService

class MusicControllerManager(private val context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val mediaSessionManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

    fun getActiveControllers(): List<MediaController> {
        return try {
            val componentName = ComponentName(context, MusicNotificationListenerService::class.java)
            mediaSessionManager.getActiveSessions(componentName)
        } catch (e: Exception) {
            Log.e("MusicControllerManager", "Error getting active media sessions: ${e.message}")
            emptyList()
        }
    }

    fun dispatchPlay() {
        val controllers = getActiveControllers()
        if (controllers.isNotEmpty()) {
            controllers.first().transportControls.play()
        } else {
            Log.d("MusicControllerManager", "No active session for play")
        }
    }

    fun dispatchPause() {
        getActiveControllers().firstOrNull()?.transportControls?.pause()
    }

    fun dispatchNext() {
        getActiveControllers().firstOrNull()?.transportControls?.skipToNext()
    }

    fun dispatchPrevious() {
        getActiveControllers().firstOrNull()?.transportControls?.skipToPrevious()
    }

    fun setVolume(percent: Int) {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val targetVolume = (maxVolume * (percent.coerceIn(0, 100) / 100.0)).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume, 0)
    }

    fun adjustVolume(delta: Int) {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, delta, AudioManager.FLAG_SHOW_UI)
    }

    fun setMute(mute: Boolean) {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            if (mute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE,
            AudioManager.FLAG_SHOW_UI
        )
    }
}
