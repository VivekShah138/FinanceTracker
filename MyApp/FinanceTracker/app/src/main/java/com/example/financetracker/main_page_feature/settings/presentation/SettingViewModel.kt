package com.example.financetracker.main_page_feature.settings.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.financetracker.main_page_feature.settings.domain.use_cases.SettingsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingsUseCaseWrapper: SettingsUseCaseWrapper
): ViewModel() {

    private val _settingStates = MutableStateFlow(SettingStates())
    val settingStates : StateFlow<SettingStates> = _settingStates.asStateFlow()

    init {
        // Load cloud sync state when the ViewModel is initialized
        val cloudSyncState = settingsUseCaseWrapper.getCloudSyncLocally()
        _settingStates.value = _settingStates.value.copy(cloudSync = cloudSyncState)
    }

    fun onEvent(settingEvents: SettingEvents){
        when(settingEvents){
            is SettingEvents.ChangeCloudSync -> {
                _settingStates.value = settingStates.value.copy(
                    cloudSync = settingEvents.isChecked
                )

                // Setting CloudSync in shared preferences
                settingsUseCaseWrapper.setCloudSyncLocally(settingEvents.isChecked)
                Log.d("SettingsViewModel","CloudSync: ${settingsUseCaseWrapper.getCloudSyncLocally()}")
            }
        }
    }
}