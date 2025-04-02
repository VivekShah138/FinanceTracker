package com.example.financetracker.main_page_feature.home_page.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homePageUseCaseWrapper: HomePageUseCaseWrapper,
    private val userCasesWrapperSetupAccount: UseCasesWrapperSetupAccount
): ViewModel(){

    init {
        getUserProfile()
//        if(!homePageUseCaseWrapper.getCurrencyRatesUpdated()){
            updateCurrencyRatesOneTime()
//        }
        updateCurrencyRatesPeriodically()

    }

    fun onEvent(homePageEvents: HomePageEvents){
        when(homePageEvents){
            is HomePageEvents.Logout -> {
                viewModelScope.launch {
                    homePageUseCaseWrapper.logoutUseCase()
                }
            }
        }
    }

    fun getUserProfile(){
        viewModelScope.launch {
            val userProfile = homePageUseCaseWrapper.getUserProfileLocal()
            Log.d("HomePageViewModel","userProfile $userProfile")
        }
    }

    fun updateCurrencyRatesOneTime(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("WorkManagerCurrencies","one time function called inside homepage")

            userCasesWrapperSetupAccount.insertCurrencyRatesLocalOneTime()
        }
    }

    fun updateCurrencyRatesPeriodically(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("WorkManagerCurrencies","periodically time function called inside homepage")
            userCasesWrapperSetupAccount.insertCurrencyRatesLocalPeriodically()
        }
    }


}