package com.example.financetracker


import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FinanceTracker : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper
    @Inject lateinit var useCasesWrapperSetupAccount: UseCasesWrapperSetupAccount

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        Log.d("WorkManager", "Enqueuing Workers")

        applicationScope.launch {
            predefinedCategoriesUseCaseWrapper.insertPredefinedCategories()
            useCasesWrapperSetupAccount.insertCountryLocally()
        }
    }

    // Correctly override getWorkManagerConfiguration
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
