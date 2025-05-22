package com.example.financetracker


import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.main_page_feature.settings.domain.use_cases.SettingsUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        createNotificationChannel()

        Log.d("AppEntry", "Enqueuing Workers")

        applicationScope.launch {

            val firstInstall = setupAccountUseCasesWrapper.getFirstTimeInstalled()
            Log.d("AppEntry","FirstTimeInstalled -> $firstInstall")
            if(firstInstall){
                predefinedCategoriesUseCaseWrapper.insertPredefinedCategories()
                setupAccountUseCasesWrapper.insertCountryLocallyWorkManager()
                setupAccountUseCasesWrapper.setFirstTimeInstalled()
                Log.d("AppEntry","FirstTimeInstalled set to false")
            }



            viewRecordsUseCaseWrapper.deleteMultipleTransactionsFromCloud()
            viewRecordsUseCaseWrapper.deleteMultipleSavedItemCloud()
            settingsUseCaseWrapper.saveMultipleTransactionsCloud()
            settingsUseCaseWrapper.saveMultipleSavedItemCloud()
            budgetUseCaseWrapper.saveMultipleBudgetsToCloudUseCase()
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
