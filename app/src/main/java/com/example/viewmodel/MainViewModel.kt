package com.example.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.AudioEffectsManager
import com.example.data.AppDatabase
import com.example.data.CustomCommandEntity
import com.example.data.DeviceProfileEntity
import com.example.data.AudioPresetEntity
import com.example.media.MusicControllerManager
import com.example.service.VoiceControlService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val musicController = MusicControllerManager(application)
    val audioEffects = AudioEffectsManager()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _currentPreset = MutableStateFlow("Normal")
    val currentPreset: StateFlow<String> = _currentPreset.asStateFlow()

    private val _bassBoostStrength = MutableStateFlow(0)
    val bassBoostStrength: StateFlow<Int> = _bassBoostStrength.asStateFlow()

    private val _virtualizerStrength = MutableStateFlow(0)
    val virtualizerStrength: StateFlow<Int> = _virtualizerStrength.asStateFlow()

    private val _reverbPreset = MutableStateFlow(0)
    val reverbPreset: StateFlow<Int> = _reverbPreset.asStateFlow()

    private val _loudnessGain = MutableStateFlow(0)
    val loudnessGain: StateFlow<Int> = _loudnessGain.asStateFlow()

    val customCommands = db.customCommandDao().getAllCommands()
    val deviceProfiles = db.deviceProfileDao().getAllProfiles()
    val audioPresets = db.audioPresetDao().getAllPresets()

    fun toggleVoiceService(enabled: Boolean) {
        _isListening.value = enabled
        val context = getApplication<Application>()
        val intent = Intent(context, VoiceControlService::class.java).apply {
            action = if (enabled) VoiceControlService.ACTION_START else VoiceControlService.ACTION_STOP
        }
        if (enabled) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        } else {
            context.stopService(intent)
        }
    }

    fun updateBassBoost(strength: Int) {
        _bassBoostStrength.value = strength
        audioEffects.setBassBoost(strength.toShort(), strength > 0)
    }

    fun updateVirtualizer(strength: Int) {
        _virtualizerStrength.value = strength
        audioEffects.setVirtualizer(strength.toShort(), strength > 0)
    }

    fun updateReverb(preset: Int) {
        _reverbPreset.value = preset
        audioEffects.setReverbPreset(preset.toShort(), preset > 0)
    }

    fun updateLoudness(gain: Int) {
        _loudnessGain.value = gain
        audioEffects.setLoudnessEnhancer(gain, gain > 0)
    }

    fun applyPreset(name: String) {
        _currentPreset.value = name
        when (name) {
            "Deep Bass" -> {
                updateBassBoost(800)
                updateVirtualizer(400)
            }
            "Rock" -> {
                updateBassBoost(500)
                updateLoudness(500)
            }
            "Vocal Boost" -> {
                updateLoudness(700)
            }
            else -> {
                updateBassBoost(0)
                updateVirtualizer(0)
                updateLoudness(0)
            }
        }
    }

    fun addCustomCommand(wakeWord: String, actionKey: String, phrase: String) {
        viewModelScope.launch {
            db.customCommandDao().insertCommand(
                CustomCommandEntity(wakeWord = wakeWord, actionKey = actionKey, customPhrase = phrase)
            )
        }
    }

    fun deleteCustomCommand(command: CustomCommandEntity) {
        viewModelScope.launch {
            db.customCommandDao().deleteCommand(command)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioEffects.release()
    }
}
