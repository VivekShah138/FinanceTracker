package com.example.financetracker.presentation.features.settings_feature


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCaseWrapper: SettingsUseCaseWrapper
): ViewModel() {

    private val _settingStates = MutableStateFlow(SettingStates())
    val settingStates: StateFlow<SettingStates> = _settingStates.asStateFlow()


    init {
        val cloudSyncState = settingsUseCaseWrapper.getCloudSyncLocalUseCase()
        _settingStates.value = _settingStates.value.copy(cloudSync = cloudSyncState)

        val darkMode = settingsUseCaseWrapper.getDarkModeLocalUseCase()
        _settingStates.value = _settingStates.value.copy(darkMode = darkMode)
    }

    fun onEvent(settingEvents: SettingEvents) {
        when (settingEvents) {
            is SettingEvents.ChangeCloudSync -> {
                _settingStates.value = settingStates.value.copy(
                    cloudSync = settingEvents.isChecked
                )

                // Setting CloudSync in shared preferences
                settingsUseCaseWrapper.setCloudSyncLocalUseCase(settingEvents.isChecked)
                Logger.d(Logger.Tag.SETTING_VIEWMODEL,"ChangeCloudSync -> CloudSync: ${settingsUseCaseWrapper.getCloudSyncLocalUseCase()}")

                if (settingEvents.isChecked) {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsUseCaseWrapper.insertTransactionsRemoteUseCase()
                        settingsUseCaseWrapper.saveMultipleSavedItemCloud()
                    }
                }
            }

            is SettingEvents.ChangeDarkMode -> {
                _settingStates.value = settingStates.value.copy(
                    darkMode = settingEvents.isDarkMode
                )

                settingsUseCaseWrapper.setDarkModeLocalUseCase(settingEvents.isDarkMode)
                Logger.d(Logger.Tag.SETTING_VIEWMODEL,"ChangeCloudSync -> Dark Mode: ${settingsUseCaseWrapper.getDarkModeLocalUseCase}")

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

            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"getUserDetails -> UserId inside Func $uid")

            val userProfile = settingsUseCaseWrapper.getUserProfileFromLocalUseCase(uid)

            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"getUserDetails -> User Profile $userProfile")

            val name = (userProfile?.firstName + " " + userProfile?.lastName)

            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"getUserDetails -> User Name $name")


            val budget = settingsUseCaseWrapper.getBudgetLocalUseCase(
                userId = uid,
                month = Calendar.getInstance().get(Calendar.MONTH),
                year = Calendar.getInstance().get(Calendar.YEAR)
            )

            _settingStates.value = settingStates.value.copy(
                name = name,
                userId = uid,
                budgetExist = budget != null
            )
            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"getUserDetails -> budget: $budget")
            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"getUserDetails -> bugetExist State: ${settingStates.value.budgetExist}")
        }
    }

    fun loadUserProfileIfReady() {
        val uid = settingsUseCaseWrapper.getUIDLocalUseCase()
        if (!uid.isNullOrEmpty()) {
            getUserDetails(uid)
        } else {
            Logger.d(Logger.Tag.SETTING_VIEWMODEL,"loadUserProfileIfReady() -> UID still null, cannot load profile.")

        }
    }
}