package com.example.financetracker.main_page_feature.settings.presentation


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

): ViewModel() {

    private val _settingStates = MutableStateFlow(SettingStates())
    val settingStates : StateFlow<SettingStates> = _settingStates.asStateFlow()

    fun onEvent(settingEvents: SettingEvents){
        when(settingEvents){
            is SettingEvents.ChangeCloudSync -> {
                _settingStates.value = settingStates.value.copy(
                    cloudSync = settingEvents.isChecked
                )
            }
        }
    }

}