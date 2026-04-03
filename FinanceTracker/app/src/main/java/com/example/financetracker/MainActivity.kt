package com.example.financetracker

import com.example.financetracker.presentation.core_components.BottomNavigationBar
import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.financetracker.navigation.core.BottomNavItemsList
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import com.example.financetracker.navigation.core.FinanceTrackerNavHost
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Logger.d(Logger.Tag.MAIN_ACTIVITY, "Notification permission granted")
            } else {
                Logger.d(Logger.Tag.MAIN_ACTIVITY, "Notification permission denied")
            }
        }



    @Inject
    lateinit var settingsUseCaseWrapper: SettingsUseCaseWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {

                    Logger.d(Logger.Tag.MAIN_ACTIVITY, "Notification permission already granted")
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Optionally explain why the app needs this permission, then request again
                    // For simplicity, directly requesting here:
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                else -> {
                    // Directly request for permission
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }


        enableEdgeToEdge()

        setContent {

            val settingsViewModel: SettingsViewModel = hiltViewModel()

            // Collect the dark mode state reactively
            val settingsState by settingsViewModel.settingStates.collectAsStateWithLifecycle()
            Logger.d(Logger.Tag.MAIN_ACTIVITY,"settingsState.budgetExist ${settingsState.budgetExist}")
            Logger.d(Logger.Tag.MAIN_ACTIVITY,"settingsState.userId ${settingsState.userId}")

            LaunchedEffect(settingsState.userId) {
                settingsState.userId.let {
                    settingsViewModel.loadUserProfileIfReady()
                }
            }

            Logger.d(Logger.Tag.MAIN_ACTIVITY,"UserId state after launched Effect ${settingsState.userId}")

            val darkMode = settingsState.darkMode || settingsUseCaseWrapper.getDarkModeLocalUseCase() || isSystemInDarkTheme()
            Logger.d(Logger.Tag.MAIN_ACTIVITY,"Dark Mode $darkMode")
            Logger.d(Logger.Tag.MAIN_ACTIVITY,"System Dark Mode ${isSystemInDarkTheme()}")
            Logger.d(Logger.Tag.MAIN_ACTIVITY,"Dark Mode Settings State ${settingsState.darkMode}")

            FinanceTrackerTheme (dynamicColor = false, darkTheme = darkMode) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val currentBackStackEntry by navController.currentBackStackEntryAsState()

                    val currentDestination = currentBackStackEntry?.destination

                    val bottomNavLabels = BottomNavItemsList.map { it.label }

                    val showBottomBar = bottomNavLabels.any { label ->
                        Logger.d(Logger.Tag.MAIN_ACTIVITY,"label: $label")
                        Logger.d(Logger.Tag.MAIN_ACTIVITY,"currentDestination?.route?: ${currentDestination?.route}")
                        currentDestination?.route?.contains(label, ignoreCase = true) == true

                    }

                    Logger.d(Logger.Tag.MAIN_ACTIVITY,"show bottom bar: $showBottomBar")

                    Scaffold(
                        bottomBar = {
                            if(showBottomBar){
                                BottomNavigationBar(
                                    navController = navController,
                                )
                            }
                        }
                    ) { innerPadding ->
                        FinanceTrackerNavHost(
                            navController = navController,
                            settingsViewModel = settingsViewModel,
                            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding).fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
