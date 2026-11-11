package com.example.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.util.Log

class AudioEffectsManager(audioSessionId: Int = 0) {
    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null
    private var presetReverb: PresetReverb? = null
    private var loudnessEnhancer: LoudnessEnhancer? = null

    var isInitialized: Boolean = false
        private set

    init {
        try {
            // Audio session 0 applies to global output mix or main player session if permitted
            equalizer = Equalizer(0, audioSessionId).apply { enabled = true }
            bassBoost = BassBoost(0, audioSessionId).apply { enabled = false }
            virtualizer = Virtualizer(0, audioSessionId).apply { enabled = false }
            presetReverb = PresetReverb(0, audioSessionId).apply { enabled = false }
            loudnessEnhancer = LoudnessEnhancer(audioSessionId).apply { enabled = false }
            isInitialized = true
            Log.d("AudioEffectsManager", "Successfully initialized audio effects on session $audioSessionId")
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Failed to initialize audio effects: ${e.message}")
            isInitialized = false
        }
    }

    fun setEqualizerEnabled(enabled: Boolean) {
        try {
            equalizer?.enabled = enabled
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting equalizer enabled: ${e.message}")
        }
    }

    fun setEqualizerBandLevel(band: Short, level: Short) {
        try {
            equalizer?.setBandLevel(band, level)
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting EQ band level: ${e.message}")
        }
    }

    fun getNumberOfBands(): Short {
        return try {
            equalizer?.numberOfBands ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun setBassBoost(strength: Short, enabled: Boolean) {
        try {
            bassBoost?.let {
                if (it.strengthSupported) {
                    it.setStrength(strength)
                    it.enabled = enabled
                }
            }
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting bass boost: ${e.message}")
        }
    }

    fun setVirtualizer(strength: Short, enabled: Boolean) {
        try {
            virtualizer?.let {
                if (it.strengthSupported) {
                    it.setStrength(strength)
                    it.enabled = enabled
                }
            }
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting virtualizer: ${e.message}")
        }
    }

    fun setReverbPreset(preset: Short, enabled: Boolean) {
        try {
            presetReverb?.let {
                it.preset = preset
                it.enabled = enabled
            }
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting reverb preset: ${e.message}")
        }
    }

    fun setLoudnessEnhancer(targetGain: Int, enabled: Boolean) {
        try {
            loudnessEnhancer?.let {
                it.setTargetGain(targetGain)
                it.enabled = enabled
            }
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error setting loudness enhancer: ${e.message}")
        }
    }

    fun release() {
        try {
            equalizer?.release()
            bassBoost?.release()
            virtualizer?.release()
            presetReverb?.release()
            loudnessEnhancer?.release()
            isInitialized = false
        } catch (e: Exception) {
            Log.e("AudioEffectsManager", "Error releasing audio effects: ${e.message}")
        }
    }
}
