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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.financetracker.presentation.features.home_feature.components.HomePageScreen
import com.example.financetracker.presentation.features.home_feature.HomePageViewModel
import com.example.financetracker.presentation.features.budget_feature.BudgetViewModel
import com.example.financetracker.presentation.features.budget_feature.components.BudgetScreen
import com.example.financetracker.presentation.features.category_feature.viewmodel.CoreCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.ExpenseCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.IncomeCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.component.CategoriesScreen
import com.example.financetracker.navigation.BottomNavItemsList
import com.example.financetracker.navigation.Screens
import com.example.financetracker.presentation.features.charts_feature.ChartsViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.components.FinanceEntryPage
import com.example.financetracker.presentation.features.charts_feature.components.ChartsPage
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.SavedItemViewModel
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import com.example.financetracker.presentation.features.settings_feature.SettingViewModel
import com.example.financetracker.presentation.features.settings_feature.components.SettingsPage
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel
import com.example.financetracker.presentation.features.view_records_feature.components.RecordsPage
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.presentation.features.view_records_feature.components.SingleSavedItemScreen
import com.example.financetracker.presentation.features.view_records_feature.components.SingleTransactionScreen
import com.example.financetracker.presentation.features.auth_feature.components.ForgotPasswordPage
import com.example.financetracker.presentation.features.auth_feature.components.LogInPage
import com.example.financetracker.presentation.features.auth_feature.components.RegisterPage
import com.example.financetracker.presentation.features.auth_feature.viewmodels.ForgotPasswordViewModel
import com.example.financetracker.presentation.features.auth_feature.viewmodels.LoginPageViewModel
import com.example.financetracker.presentation.features.auth_feature.viewmodels.RegisterPageViewModel
import com.example.financetracker.presentation.features.setup_account_feature.ProfileSetUpViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.NewUserProfileOnBoardingScreens
import com.example.financetracker.presentation.features.setup_account_feature.components.ProfileSetUp
import com.example.financetracker.presentation.features.splash_screen_feature.components.StartUpPageRoot
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

            val settingsViewModel: SettingViewModel = hiltViewModel()

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
                    val currentRoute = currentBackStackEntry?.destination

                    val showBottomBar = BottomNavItemsList.any {
                        currentRoute?.route?.startsWith(it.screen.routes) == true
                    }


                    Scaffold(
                        bottomBar = {
                            if(showBottomBar){
                                BottomNavigationBar(
                                    navController = navController,
                                    showBadge = !settingsState.budgetExist,
                                    currentRoute = currentRoute
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screens.StartUpPageScreen.routes,
                            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding).fillMaxSize()
                        ) {
                            composable(
                                route = Screens.StartUpPageScreen.routes
                            ) {
                                StartUpPageRoot(navController = navController)
                            }

                            composable(
                                route = Screens.LogInScreen.routes
                            ) {
                                val viewModel: LoginPageViewModel = hiltViewModel()
                                LogInPage(navController, viewModel)
                            }

                            composable(
                                route = Screens.RegistrationScreen.routes
                            ) {
                                val viewModel: RegisterPageViewModel = hiltViewModel()
                                RegisterPage(navController, viewModel)
                            }

                            composable(
                                route = Screens.ForgotPasswordScreen.routes
                            ) {
                                val viewModel: ForgotPasswordViewModel = hiltViewModel()
                                ForgotPasswordPage(navController, viewModel)
                            }

                            composable(
                                route = Screens.HomePageScreen.routes
                            ) {
                                val viewModel: HomePageViewModel = hiltViewModel()
                                HomePageScreen(viewModel, navController,settingViewModel = settingsViewModel)
                            }

                            composable(
                                route = Screens.ProfileSetUpScreen.routes
                            ) {
                                val viewModel : ProfileSetUpViewModel = hiltViewModel()
                                ProfileSetUp(viewModel,navController)
                            }

                            composable(
                                route = Screens.NewUserProfileOnBoardingScreen.routes
                            ) {
                                val viewModel : ProfileSetUpViewModel = hiltViewModel()
                                NewUserProfileOnBoardingScreens(viewModel,navController)
                            }

                            composable(
                                route = Screens.SettingScreen.routes
                            ) {
                                SettingsPage(navController = navController,viewModel = settingsViewModel)
                            }

                            composable(
                                route = Screens.GraphicalVisualizationScreen.routes
                            ) {

                                val viewModel: ChartsViewModel = hiltViewModel()

                                ChartsPage(navController = navController,viewModel = viewModel)
                            }


                            composable(
                                route = "${Screens.ViewRecordsScreen.routes}/{tabIndex}",
                                arguments = listOf(navArgument("tabIndex") { type = NavType.IntType; defaultValue = 0 })
                            ) { backStackEntry ->
                                val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
                                val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
                                val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()

                                Log.d("RecordsPage", "pagerTabIndex $tabIndex")
                                RecordsPage(
                                    navController = navController,
                                    viewSavedItemsViewModel = viewSavedItemsViewModel,
                                    viewTransactionsViewModel = viewTransactionsViewModel,
                                    defaultTabIndex = tabIndex
                                )
                            }

                            composable(
                                route = Screens.AddTransactionsScreen.routes
                            ) {
                                val addTransactionViewModel: AddTransactionViewModel = hiltViewModel()
                                val savedItemViewModel: SavedItemViewModel = hiltViewModel()
                                FinanceEntryPage(
                                    navController = navController,
                                    addTransactionViewModel = addTransactionViewModel,
                                    savedItemViewModel = savedItemViewModel)
                            }

                            composable(
                                route = Screens.CategoriesScreen.routes
                            ) {

                                val expenseCategoriesViewModel: ExpenseCategoriesViewModel = hiltViewModel()
                                val incomeCategoriesViewModel: IncomeCategoriesViewModel = hiltViewModel()
                                val coreCategoriesViewModel: CoreCategoriesViewModel = hiltViewModel()

                                CategoriesScreen(
                                    navController = navController,
                                    expenseCategoriesViewModel = expenseCategoriesViewModel,
                                    incomeCategoriesViewModel = incomeCategoriesViewModel,
                                    coreCategoriesViewModel = coreCategoriesViewModel

                                )
                            }

                            composable(
                                route = Screens.BudgetScreen.routes
                            ) {

                                val budgetViewModel: BudgetViewModel = hiltViewModel()

                                BudgetScreen(
                                    navController = navController,
                                    budgetViewModel = budgetViewModel
                                )
                            }

                            composable(
                                route = Screens.SingleTransactionScreen.routes + "/{transactionId}",
                                arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val transactionId = backStackEntry.arguments?.getInt("transactionId")
                                SingleTransactionScreen(
                                    navController = navController,
                                    viewTransactionsViewModel = hiltViewModel(),
                                    transactionId = transactionId ?: -1  // fallback if null
                                )
                            }

                            composable(
                                route = Screens.SingleSavedItemScreen.routes + "/{savedItemId}",
                                arguments = listOf(navArgument("savedItemId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val savedItemId = backStackEntry.arguments?.getInt("savedItemId")
                                SingleSavedItemScreen(
                                    navController = navController,
                                    viewSavedItemsViewModel = hiltViewModel(),
                                    savedItemId = savedItemId ?: -1  // fallback if null
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
