package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomCommandDao {
    @Query("SELECT * FROM custom_commands")
    fun getAllCommands(): Flow<List<CustomCommandEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommand(command: CustomCommandEntity)

    @Update
    suspend fun updateCommand(command: CustomCommandEntity)

    @Delete
    suspend fun deleteCommand(command: CustomCommandEntity)
}

@Dao
interface DeviceProfileDao {
    @Query("SELECT * FROM device_profiles")
    fun getAllProfiles(): Flow<List<DeviceProfileEntity>>

    @Query("SELECT * FROM device_profiles WHERE deviceName = :name LIMIT 1")
    suspend fun getProfileByName(name: String): DeviceProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: DeviceProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: DeviceProfileEntity)
}

@Dao
interface AudioPresetDao {
    @Query("SELECT * FROM audio_presets")
    fun getAllPresets(): Flow<List<AudioPresetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(preset: AudioPresetEntity)

    @Update
    suspend fun updatePreset(preset: AudioPresetEntity)

    @Delete
    suspend fun deletePreset(preset: AudioPresetEntity)
}
