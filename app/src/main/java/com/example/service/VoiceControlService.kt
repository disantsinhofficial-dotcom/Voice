package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.media.MusicControllerManager
import java.util.Locale

class VoiceControlService : Service() {
    private var speechRecognizer: SpeechRecognizer? = null
    private lateinit var musicController: MusicControllerManager
    private var isListening = false
    private var configuredWakeWord = "hey buddy" // or "alexa"

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val CHANNEL_ID = "VoiceControlChannel"
        const val NOTIF_ID = 1001
    }

    override fun onCreate() {
        super.onCreate()
        musicController = MusicControllerManager(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                startForeground(NOTIF_ID, createNotification("Listening for wake word..."))
                startListeningCycle()
            }
            ACTION_STOP -> {
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Voice Control Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Silent background listening for voice commands"
                setSound(null, null)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(text: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Voice Controller")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun startListeningCycle() {
        if (isListening) return
        try {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: android.os.Bundle?) { isListening = true }
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}
                    override fun onError(error: Int) {
                        Log.d("VoiceService", "Recognition error: $error, restarting cycle")
                        restartListening()
                    }
                    override fun onResults(results: android.os.Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        matches?.forEach { phrase ->
                            val lower = phrase.lowercase(Locale.getDefault())
                            Log.d("VoiceService", "Heard: $lower")
                            if (lower.contains(configuredWakeWord) || lower.contains("alexa")) {
                                // Extract command after wake word
                                val command = lower.replace(configuredWakeWord, "").replace("alexa", "").trim()
                                executeCommand(command)
                            }
                        }
                        restartListening()
                    }
                    override fun onPartialResults(partialResults: android.os.Bundle?) {}
                    override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
                })
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "hi-IN")
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
            }
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            Log.e("VoiceService", "Failed to start listening: ${e.message}")
        }
    }

    private fun executeCommand(command: String) {
        Log.d("VoiceService", "Executing verified command: $command")
        when {
            command.contains("play") || command.contains("resume") -> musicController.dispatchPlay()
            command.contains("pause") || command.contains("stop") -> musicController.dispatchPause()
            command.contains("next") || command.contains("aage") -> musicController.dispatchNext()
            command.contains("previous") || command.contains("peechhe") -> musicController.dispatchPrevious()
            command.contains("mute") -> musicController.setMute(true)
            command.contains("unmute") -> musicController.setMute(false)
            command.contains("volume") -> {
                val digits = command.filter { it.isDigit() }
                if (digits.isNotEmpty()) {
                    musicController.setVolume(digits.toInt())
                } else {
                    musicController.adjustVolume(1)
                }
            }
            command.contains("rock") -> Log.d("VoiceService", "Switching to Rock preset")
            command.contains("bass boost") -> Log.d("VoiceService", "Toggling Bass Boost")
            command.contains("equalizer") -> Log.d("VoiceService", "Toggling Equalizer")
        }
    }

    private fun restartListening() {
        try {
            speechRecognizer?.stopListening()
            speechRecognizer?.destroy()
            speechRecognizer = null
            isListening = false
            android.os.Handler(mainLooper).postDelayed({
                startListeningCycle()
            }, 1000)
        } catch (e: Exception) {
            Log.e("VoiceService", "Error restarting listener: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
        isListening = false
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
