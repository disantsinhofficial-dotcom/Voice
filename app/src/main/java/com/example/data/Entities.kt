package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_commands")
data class CustomCommandEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val wakeWord: String,
    val actionKey: String, // e.g. "PLAY_NEXT", "VOLUME_UP", "EQUALIZER_ON", "BASS_BOOST_ON", "ROCK_MODE"
    val customPhrase: String,
    val isEnabled: Boolean = true
)

@Entity(tableName = "device_profiles")
data class DeviceProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val deviceName: String,
    val eqSettings: String, // comma separated 10 band levels (-1500 to 1500)
    val bassBoostStrength: Int, // 0 to 1000
    val virtualizerStrength: Int, // 0 to 1000
    val reverbPreset: Int, // 0 to 6
    val loudnessGain: Int // 0 to 1000
)

@Entity(tableName = "audio_presets")
data class AudioPresetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val eqBands: String, // comma separated 10 band levels
    val bassBoostStrength: Int,
    val virtualizerStrength: Int,
    val reverbPreset: Int,
    val loudnessGain: Int,
    val isCustom: Boolean = false
)
