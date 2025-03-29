package com.example.financetracker.auth_feature.presentation.login.login_components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.auth_feature.presentation.AccountManager
import com.example.financetracker.auth_feature.presentation.components.CustomText
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithCredentialResult
import com.example.financetracker.auth_feature.presentation.login.LogInResult
import com.example.financetracker.auth_feature.presentation.login.LoginPageEvents
import com.example.financetracker.auth_feature.presentation.login.LoginPageViewModel
import com.example.financetracker.core.presentation.utils.Screens

import kotlinx.coroutines.launch

@Composable
fun LogInPage(
    navController: NavController,
    viewModel: LoginPageViewModel
){

    val state by viewModel.loginState.collectAsStateWithLifecycle()
    val loginEvents = viewModel.loginEvents

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }


    LaunchedEffect(key1 = context) {
        loginEvents.collect{event->
            when(event){
                is LoginPageViewModel.LoginEvent.Success->{
                    Toast.makeText(context,
                        "Login Successful " + event.username,
                        Toast.LENGTH_SHORT).show()

                    if(!state.userProfile.profileSetUpCompleted){
                        navController.navigate(Screens.NewUserProfileOnBoardingScreen.routes)
                    }
                    else{
                        navController.navigate(Screens.HomePageScreen.routes)
                    }
                }
                is LoginPageViewModel.LoginEvent.Error->{
                    Toast.makeText(context,
                        event.errorMessage,
                        Toast.LENGTH_SHORT).show()
                }

                LoginPageViewModel.LoginEvent.TriggerFirebaseLogIn -> {
                    coroutineScope.launch {
                        val result = accountManager.loginInUser(state.email,state.password)
                        handleLoginResult(result,viewModel::onEvent)
                    }

                }
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // TEXT
        CustomText(
            text = "LogIn",
            size = 30.sp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Email", modifier = Modifier.fillMaxWidth())
            CustomTextFields(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(top = 10.dp, end = 20.dp, start = 20.dp, bottom = 10.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginPageEvents.ChangeEmail(it))
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = state.emailError != null
            )
            if(state.emailError != null){
                Text(
                    text = state.emailError ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Text(text = "Password", modifier = Modifier.fillMaxWidth())
            CustomTextFields(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(top = 10.dp, end = 20.dp, start = 20.dp, bottom = 10.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = state.password,
                onValueChange = {
                    // Add Function Change
                    viewModel.onEvent(LoginPageEvents.ChangePassword(it))

                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.passwordError != null
            )
            if(state.passwordError != null){
                Text(
                    text = state.passwordError ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Checkbox(
                checked = state.keepLoggedIn,
                onCheckedChange = {
                    // Add Function
                    viewModel.onEvent(LoginPageEvents.ChangeKeepLoggedIn(it))
                }
            )

            CustomText(
                text = "Keep LoggedIn",
                size = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = {
                //  Add Login Functionality
                viewModel.onEvent(LoginPageEvents.SubmitLogIn)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Button(
            onClick = {
                // Add Google Functionality
                coroutineScope.launch {
                    val result = accountManager.signInWithGoogle()
                    viewModel.onEvent(LoginPageEvents.ClickLoginWithGoogle(result))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Google")
        }

        TextButton(
            onClick = {
                // Navigate to Forget Password Page
                coroutineScope.launch {
                    val result = accountManager.resetPasswordWithCredential()
                    if(result is ResetPasswordWithCredentialResult.CredentialLoginSuccess){
                        viewModel.onEvent(LoginPageEvents.ClickForgotPassword(result))
                    }
                    else{
                        navController.navigate(route = Screens.ForgotPasswordScreen.routes)
                    }

                }
            }
        ) {
            Text("Forgot Password?")
        }


        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Don't have an account?")
            TextButton(
                onClick = {
                    navController.navigate(route = Screens.RegistrationScreen.routes)
                }
            ) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

private fun handleLoginResult(
    result: LogInResult,
    onEvent: (LoginPageEvents) -> Unit
    ){
    when(result){
        is LogInResult.Success -> {
            onEvent(LoginPageEvents.LoginSuccess(result.userName))
        }
        is LogInResult.Failure -> {
            onEvent(LoginPageEvents.LoginFailure("Log In Failed"))
        }
    }
}
