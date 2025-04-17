package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.home_page.presentation.components.HomePageScreen
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageViewModel
import com.example.financetracker.startup_page_feature.components.StartUpPageScreen
import com.example.financetracker.startup_page_feature.StartPageViewModel
import com.example.financetracker.auth_feature.presentation.login.LoginPageViewModel
import com.example.financetracker.auth_feature.presentation.register.RegisterPageViewModel
import com.example.financetracker.auth_feature.presentation.register.register_components.RegisterPage
import com.example.financetracker.auth_feature.presentation.login.login_components.LogInPage
import com.example.financetracker.auth_feature.presentation.forgot_password.ForgotPasswordViewModel
import com.example.financetracker.auth_feature.presentation.forgot_password.forgot_password_components.ForgotPasswordPage
import com.example.financetracker.categories_feature.core.presentation.CoreCategoriesViewModel
import com.example.financetracker.categories_feature.expense.presentation.ExpenseCategoriesViewModel
import com.example.financetracker.categories_feature.income.presentation.IncomeCategoriesViewModel
import com.example.financetracker.categories_feature.core.presentation.components.CategoriesScreen
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.FinanceEntryPage
import com.example.financetracker.main_page_feature.charts.presentation.components.ChartsPage
import com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation.SavedItemViewModel
import com.example.financetracker.main_page_feature.settings.presentation.SettingViewModel
import com.example.financetracker.main_page_feature.settings.presentation.components.SettingsPage
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel
import com.example.financetracker.main_page_feature.view_records.presentation.components.RecordsPage
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel
import com.example.financetracker.setup_account.presentation.ProfileSetUpViewModel
import com.example.financetracker.setup_account.presentation.components.NewUserProfileOnBoardingScreens
import com.example.financetracker.setup_account.presentation.components.ProfileSetUp
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceTrackerTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.StartUpPageScreen.routes
                    ) {

                        composable(
                            route = Screens.StartUpPageScreen.routes
                        ) {
                            val viewModel: StartPageViewModel = hiltViewModel()
                            StartUpPageScreen(viewModel, navController)
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
                            HomePageScreen(viewModel, navController)
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

                            val viewModel: SettingViewModel = hiltViewModel()

                            SettingsPage(navController = navController,viewModel = viewModel)
                        }

                        composable(
                            route = Screens.GraphicalVisualizationScreen.routes
                        ) {
                            ChartsPage(navController)
                        }

                        composable(
                            route = Screens.ViewRecordsScreen.routes
                        ) {
                            val viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel()
                            val viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel()
                            RecordsPage(
                                navController = navController,
                                viewSavedItemsViewModel = viewSavedItemsViewModel,
                                viewTransactionsViewModel = viewTransactionsViewModel
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

                    }
                }
            }
        }
    }
}
