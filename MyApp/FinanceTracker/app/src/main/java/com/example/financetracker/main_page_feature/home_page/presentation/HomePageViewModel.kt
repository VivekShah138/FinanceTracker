package com.example.financetracker.main_page_feature.home_page.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.main_page_feature.home_page.usecases.HomePageUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homePageUseCaseWrapper: HomePageUseCaseWrapper
): ViewModel(){

    fun onEvent(homePageEvents: HomePageEvents){
        when(homePageEvents){
            is HomePageEvents.Logout -> {
                viewModelScope.launch {
                    homePageUseCaseWrapper.logoutUseCase()
                }
            }
        }
    }
}