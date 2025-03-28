package com.example.financetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.core.presentation.utils.Screens
import com.example.financetracker.main_page_feature.presentation.components.HomePageScreen
import com.example.financetracker.main_page_feature.presentation.HomePageViewModel
import com.example.financetracker.startup_page_feature.components.StartUpPageScreen
import com.example.financetracker.startup_page_feature.StartPageViewModel
import com.example.financetracker.auth_feature.presentation.login.LoginPageViewModel
import com.example.financetracker.auth_feature.presentation.register.RegisterPageViewModel
import com.example.financetracker.auth_feature.presentation.register.register_components.RegisterPage
import com.example.financetracker.auth_feature.presentation.login.login_components.LogInPage
import com.example.financetracker.auth_feature.presentation.forgot_password.ForgotPasswordViewModel
import com.example.financetracker.auth_feature.presentation.forgot_password.forgot_password_components.ForgotPasswordPage
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
                            route = Screens.ProfileSetUp.routes
                        ) {
                            val viewModel : ProfileSetUpViewModel = hiltViewModel()
                            ProfileSetUp(viewModel,navController)
                        }

                        composable(
                            route = Screens.NewUserProfileOnBoardingScreens.routes
                        ) {
                            val viewModel : ProfileSetUpViewModel = hiltViewModel()
                            NewUserProfileOnBoardingScreens(viewModel,navController)
                        }

                    }
                }
            }
        }
    }
}
