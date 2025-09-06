package com.example.financetracker

import com.example.financetracker.presentation.core_components.BottomNavigationBar
import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.financetracker.navigation.BottomNavItemsList
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import com.example.financetracker.navigation.FinanceTrackerNavHost
import com.example.financetracker.presentation.features.settings_feature.SettingsViewModel
import com.example.financetracker.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "Notification permission granted")
            } else {
                Log.d("MainActivity", "Notification permission denied")
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
                    // Permission already granted
                    Log.d("MainActivity", "Notification permission already granted")
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
            Log.d("MainActivitySetting","budget state ${settingsState.budgetExist}")
            Log.d("MainActivitySetting","UserId state ${settingsState.userId}")

            LaunchedEffect(settingsState.userId) {
                settingsState.userId.let {
                    settingsViewModel.loadUserProfileIfReady()
                }
            }

            Log.d("MainActivitySetting","UserId state after launched Effect ${settingsState.userId}")

            val darkMode = settingsState.darkMode || settingsUseCaseWrapper.getDarkModeLocally() || isSystemInDarkTheme()
            Log.d("MainActivitySetting","Dark Mode $darkMode")
            Log.d("MainActivitySettings","Dark Mode State ${settingsState.darkMode}")

            AppTheme (dynamicColor = false, darkTheme = darkMode) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentRoute = currentBackStackEntry?.destination
//
//                    val showBottomBar = BottomNavItemsList.any {
//                        currentRoute?.route?.startsWith(it.screen.routes) == true
//                    }
                    val showBottomBar = true



                    Scaffold(
                        bottomBar = {
                            if(showBottomBar){
                                BottomNavigationBar(
                                    navController = navController,
//                                    showBadge = !settingsState.budgetExist,
//                                    currentRoute = currentRoute
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
