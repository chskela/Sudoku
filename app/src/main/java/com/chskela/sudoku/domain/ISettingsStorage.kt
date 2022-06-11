package com.chskela.sudoku.domain

interface ISettingsStorage {
    suspend fun updateSettings(settings: Settings): SettingsStorageResult
    suspend fun getSettings(): SettingsStorageResult
}

sealed class SettingsStorageResult {
    data class OnSuccess(val settings: Settings) : SettingsStorageResult()
    data class OnError(val exception: Exception) : SettingsStorageResult()
}