package com.example.financetracker.main_page_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): ViewModel(){

    fun onEvent(homePageEvents: HomePageEvents){
        when(homePageEvents){
            is HomePageEvents.Logout -> {
                viewModelScope.launch {
                    logoutUseCase()
                }

            }
        }
    }

}