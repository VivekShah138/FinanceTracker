package com.example.financetracker


import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.ViewRecordsUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FinanceTracker : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper
    @Inject lateinit var setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper
    @Inject lateinit var viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
    @Inject lateinit var settingsUseCaseWrapper: SettingsUseCaseWrapper
    @Inject lateinit var budgetUseCaseWrapper: BudgetUseCaseWrapper


    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        createNotificationChannel()

        applicationScope.launch {

            val firstInstall = setupAccountUseCasesWrapper.getFirstTimeInstalledLocalUseCase()
            Logger.d(Logger.Tag.APP_ENTRY,"FirstTimeInstalled -> $firstInstall")
            if(firstInstall){
                predefinedCategoriesUseCaseWrapper.seedPredefinedCategoriesLocalUseCase()
                setupAccountUseCasesWrapper.seedCountryLocalUseCase()
                setupAccountUseCasesWrapper.setFirstTimeInstalledLocalUseCase()
                Logger.d(Logger.Tag.APP_ENTRY,"FirstTimeInstalled set to false")
            }
            viewRecordsUseCaseWrapper.deleteTransactionsRemoteUseCase()
            viewRecordsUseCaseWrapper.deleteMultipleSavedItemCloud()
            settingsUseCaseWrapper.insertTransactionsRemoteUseCase()
            settingsUseCaseWrapper.saveMultipleSavedItemCloud()
            budgetUseCaseWrapper.insertBudgetsRemoteUseCase()
        }
    }

    // Correctly override getWorkManagerConfiguration
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "budget_alert_channel", // must match id in notification builder
                "Budget Alerts",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies when budget threshold is exceeded"
            }

            val notificationManager: android.app.NotificationManager =
                getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
