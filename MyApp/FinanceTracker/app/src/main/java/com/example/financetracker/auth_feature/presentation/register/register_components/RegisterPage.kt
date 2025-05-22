package com.example.financetracker.auth_feature.presentation.register.register_components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.auth_feature.presentation.AccountManager
import com.example.financetracker.auth_feature.presentation.components.CustomPasswordField
import com.example.financetracker.auth_feature.presentation.components.CustomText
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields2
import com.example.financetracker.auth_feature.presentation.register.RegisterPageEvents
import com.example.financetracker.auth_feature.presentation.register.RegisterPageViewModel
import com.example.financetracker.auth_feature.presentation.register.RegisterResult
import com.example.financetracker.core.core_presentation.utils.Screens
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(
    navController: NavController,
    viewModel: RegisterPageViewModel
) {

    val state by viewModel.registerState.collectAsStateWithLifecycle()
    val registrationEvents = viewModel.registerEvents

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    LaunchedEffect(key1 = context) {
        registrationEvents.collect{event->
            when(event){
                is RegisterPageViewModel.RegisterEvent.Success -> {
                    Toast.makeText(context,
                        "Registration Successful " + event.username,
                        Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.LogInScreen.routes)
                }
                is RegisterPageViewModel.RegisterEvent.Error -> {
                    Toast.makeText(context,
                        event.errorMessage,
                        Toast.LENGTH_SHORT).show()
                }
                is RegisterPageViewModel.RegisterEvent.TriggerFirebaseRegistration->{
                    coroutineScope.launch {
                        val result = accountManager.registerUser(
                            username = state.email,
                            password = state.password)
                        handleRegistrationResult(result,viewModel::onEvent)
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomText(
                text = "Register",
                size = 30.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                CustomTextFields2(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.email,
                    onValueChange = {
                        viewModel.onEvent(RegisterPageEvents.ChangeEmail(it))
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    inputType = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
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
                        viewModel.onEvent(RegisterPageEvents.ChangePassword(it))
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    inputType = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError ?: "",
                    label = "Password"
                )
            if(state.passwordError == null){
                Spacer(modifier = Modifier.height(10.dp))
            }


                CustomPasswordField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.confirmPassword,
                    onValueChange = {
                        viewModel.onEvent(RegisterPageEvents.ChangeConfirmPassword(it))
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = true,
                    inputType = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    isError = state.confirmPasswordError != null,
                    errorMessage = state.confirmPasswordError ?: "",
                    label = "Confirm Password"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.onEvent(RegisterPageEvents.SubmitRegister)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Already have an account?",color = MaterialTheme.colorScheme.onBackground)
                TextButton(
                    onClick = {
                        navController.navigate(route = Screens.LogInScreen.routes)
                    }
                ) {
                    Text("Login")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}

fun handleRegistrationResult(
    result: RegisterResult,
    onEvent: (RegisterPageEvents) -> Unit
) {
    when(result){
        is RegisterResult.Success -> {
            onEvent(RegisterPageEvents.RegistrationSuccess)
        }
        is RegisterResult.CredentialCancelled -> {
            onEvent(RegisterPageEvents.RegistrationSuccess)
        }
        is RegisterResult.CredentialFailure -> {
            onEvent(RegisterPageEvents.RegistrationSuccess)
        }
        is RegisterResult.FirebaseAuthUserCollisionException -> {
            onEvent(RegisterPageEvents.RegistrationFailure(error = "User Already Registered"))
        }
        is RegisterResult.RegistrationFailure -> {
            onEvent(RegisterPageEvents.RegistrationFailure(error = "Registration Failed"))
        }
        is RegisterResult.UnknownFailure -> {
            onEvent(RegisterPageEvents.RegistrationFailure(error = "Registration Failed Unknown Error"))
        }

    }
}
