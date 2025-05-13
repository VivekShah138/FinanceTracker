package com.example.financetracker.main_page_feature.settings.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.settings.domain.use_cases.SettingsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingsUseCaseWrapper: SettingsUseCaseWrapper
): ViewModel() {

    private val _settingStates = MutableStateFlow(SettingStates())
    val settingStates: StateFlow<SettingStates> = _settingStates.asStateFlow()



    init {
        // Load cloud sync state when the ViewModel is initialized
        val cloudSyncState = settingsUseCaseWrapper.getCloudSyncLocally()
        _settingStates.value = _settingStates.value.copy(cloudSync = cloudSyncState)

        val darkMode = settingsUseCaseWrapper.getDarkModeLocally()
        _settingStates.value = _settingStates.value.copy(darkMode = darkMode)

//        val userName = settingsUseCaseWrapper.getUserNameLocally()
//        _settingStates.value = settingStates.value.copy(name = userName ?: "")
    }

    fun onEvent(settingEvents: SettingEvents) {
        when (settingEvents) {
            is SettingEvents.ChangeCloudSync -> {
                _settingStates.value = settingStates.value.copy(
                    cloudSync = settingEvents.isChecked
                )

                // Setting CloudSync in shared preferences
                settingsUseCaseWrapper.setCloudSyncLocally(settingEvents.isChecked)
                Log.d(
                    "SettingsViewModel",
                    "CloudSync: ${settingsUseCaseWrapper.getCloudSyncLocally()}"
                )

                if (settingEvents.isChecked) {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsUseCaseWrapper.saveMultipleTransactionsCloud()
                        settingsUseCaseWrapper.saveMultipleSavedItemCloud()
                    }
                }
            }

            is SettingEvents.ChangeDarkMode -> {
                _settingStates.value = settingStates.value.copy(
                    darkMode = settingEvents.isDarkMode
                )

                settingsUseCaseWrapper.setDarkModeLocally(settingEvents.isDarkMode)
                Log.d(
                    "SettingsViewModel",
                    "Dark Mode: ${settingsUseCaseWrapper.getDarkModeLocally}"
                )
            }

            is SettingEvents.LogOut -> {
                viewModelScope.launch(Dispatchers.IO) {
                    settingsUseCaseWrapper.logoutUseCase()
                }
            }
        }
    }


    private fun getUserDetails(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            val userId = settingsUseCaseWrapper.getUIDLocally() ?: "Unknown"

            Log.d("SettingViewModel", "UserId inside Func $uid")

            val userProfile = settingsUseCaseWrapper.getUserProfileFromLocalDb(uid)
            Log.d("SettingViewModel", "User Profile $userProfile")

            val name = (userProfile?.firstName + " " + userProfile?.lastName)
            Log.d("SettingViewModel", "User Name $name")

            _settingStates.value = settingStates.value.copy(
                name = name
            )
        }
    }

    fun loadUserProfileIfReady() {
        val uid = settingsUseCaseWrapper.getUIDLocally()
        if (!uid.isNullOrEmpty()) {
            getUserDetails(uid)
        } else {
            Log.d("SettingsViewModel", "UID still null, cannot load profile.")
        }
    }

}