package com.example.financetracker


import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.core.local.data.room.data_source.category.PrepopulateCategoryDatabaseWorker
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FinanceTracker : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper

    override fun onCreate() {
        super.onCreate()

        Log.d("WorkManager", "Enqueuing PrepopulateCategoryDatabaseWorker task")
        CoroutineScope(Dispatchers.Main).launch {
            predefinedCategoriesUseCaseWrapper.insertPredefinedCategories()
        }

    }

    // Correctly override getWorkManagerConfiguration
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
