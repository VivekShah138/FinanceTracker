package com.example.financetracker.presentation.features.auth_feature.components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.features.auth_feature.auth_utils.AccountManager
import com.example.financetracker.presentation.features.auth_feature.auth_utils.ResetPasswordWithCredentialResult
import com.example.financetracker.presentation.features.auth_feature.auth_utils.LogInResult
import com.example.financetracker.presentation.features.auth_feature.events.LoginPageEvents
import com.example.financetracker.presentation.features.auth_feature.viewmodels.LoginPageViewModel
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.auth_feature.states.LoginPageStates
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.launch


@Composable
fun LoginPageRoot(
    viewModel: LoginPageViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    LogInPage(
        navController = navController,
        onEvents = viewModel::onEvent,
        state = state,
        loginResult = viewModel.loginEvents
    )
}

@Composable
fun LogInPage(
    navController: NavController,
    onEvents : (LoginPageEvents) -> Unit,
    state: LoginPageStates,
    loginResult: Flow<LoginPageViewModel.LoginEvent>
){

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val accountManager = if (!LocalInspectionMode.current) {
        remember {
            AccountManager(context as ComponentActivity)
        }
    } else {
        null
    }


    LaunchedEffect(key1 = context) {
        loginResult.collect{event->
            when(event){
                is LoginPageViewModel.LoginEvent.Success->{
                    Toast.makeText(context,
                        "Login Successful " + event.username,
                        Toast.LENGTH_SHORT).show()

                    if(!state.userProfile.profileSetUpCompleted){
                        navController.navigate(Screens.NewUserProfileOnBoardingScreen)
                    }
                    else{
                        navController.navigate(Screens.HomePageScreen)
                    }
                }
                is LoginPageViewModel.LoginEvent.Error->{
                    Toast.makeText(context,
                        event.errorMessage,
                        Toast.LENGTH_SHORT).show()
                }

                LoginPageViewModel.LoginEvent.TriggerFirebaseLogIn -> {
                    coroutineScope.launch {
                        accountManager?.let { manager ->
                            val result = manager.loginInUser(state.email, state.password)
                            handleLoginResult(result, onEvents)
                        }
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (state.isLoading || state.isDataSyncing) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f) else MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomText(
                text = "LogIn",
                size = 30.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTextFields(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.email,
                    onValueChange = {
                        onEvents(LoginPageEvents.ChangeEmail(it))
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    inputType = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = state.emailError != null,
                    errorMessage = state.emailError ?: "",
                    label = "Email"
                )

            if(state.emailError == null){
                Spacer(modifier = Modifier.height(10.dp))
            }


                CustomPasswordField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.password,
                    onValueChange = {
                        onEvents(LoginPageEvents.ChangePassword(it))

                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    inputType = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError ?: "",
                    label = "Password"
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onEvents(LoginPageEvents.SubmitLogIn)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        onEvents(LoginPageEvents.SetLoadingTrue(true))
                        accountManager?.let { manager ->
                            val result =  manager.signInWithGoogle()
                            onEvents(LoginPageEvents.ClickLoginWithGoogle(result))
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Google")
            }

            Box(
                modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ){
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            accountManager?.let { manager ->
                                val result = manager.resetPasswordWithCredential()
                                if(result is ResetPasswordWithCredentialResult.CredentialLoginSuccess){
                                    onEvents(LoginPageEvents.ClickForgotPassword(result))
                                }
                                else{
                                    navController.navigate(route = Screens.ForgotPasswordScreen)
                                }
                            }
                        }
                    }
                ) {
                    Text("Forgot Password?")
                }
            }



            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Don't have an account?", color = MaterialTheme.colorScheme.onBackground)
                TextButton(
                    onClick = {
                        navController.navigate(route = Screens.RegistrationScreen)
                    }
                ) {
                    Text("Register")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
        if(state.isLoading){
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 8.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .background(Color.Transparent)
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.background)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        if(state.isDataSyncing){
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 8.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .background(Color.Transparent)
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.background)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Syncing Data...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
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

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoginPagePreview(){
    FinanceTrackerTheme {
        LogInPage(
            navController = rememberNavController(),
            state = LoginPageStates(
                email = "shah@138gmail.com",
                password = "VivekShah",
                emailError = "emailError",
                passwordError = "passwordError",
                isDataSyncing = true
            ),
            onEvents = {

            },
            loginResult = emptyFlow()
        )
    }
}