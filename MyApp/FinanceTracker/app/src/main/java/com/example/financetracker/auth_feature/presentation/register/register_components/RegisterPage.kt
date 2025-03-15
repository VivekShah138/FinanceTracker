package com.example.financetracker.auth_feature.presentation.register.register_components

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.auth_feature.presentation.AccountManager
import com.example.financetracker.auth_feature.presentation.components.CustomText
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields
import com.example.financetracker.auth_feature.presentation.register.RegisterPageEvents
import com.example.financetracker.auth_feature.presentation.register.RegisterPageViewModel
import com.example.financetracker.auth_feature.presentation.register.RegisterResult
import com.example.financetracker.core.presentation.utils.Screens
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




    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CustomText(
            text = "Register",
            size = 30.sp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            CustomTextFields(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(
//                        width = 2.dp,
//                        color = Color.Black,
//                        shape = RoundedCornerShape(10.dp)
//                    ),
//                text = state.userName,
//                onValueChange = {
//                    viewModel.onEvent(RegisterPageEvents.ChangeUserName(it))
//                },
//                textStyle = MaterialTheme.typography.bodySmall,
//                singleLine = true,
//                inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
//                isError = state.userNameError != null
//            )
//
//            if(state.userNameError != null){
//                Text(
//                    text = state.userNameError ?: "Unknown Error",
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.align(Alignment.End)
//                )
//            }

            Text(text = "Email", modifier = Modifier.fillMaxWidth())
            CustomTextFields(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = state.email,
                onValueChange = {
                    viewModel.onEvent(RegisterPageEvents.ChangeEmail(it))
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.emailError != null
            )
            if(state.emailError!= null){
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
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = state.password,
                onValueChange = {
                    viewModel.onEvent(RegisterPageEvents.ChangePassword(it))
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = state.passwordError != null
            )
            if(state.passwordError != null){
                Text(
                    text = state.passwordError ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Text(text = "ConfirmPassword", modifier = Modifier.fillMaxWidth())
            CustomTextFields(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = state.confirmPassword,
                onValueChange = {
                    viewModel.onEvent(RegisterPageEvents.ChangeConfirmPassword(it))
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = state.confirmPasswordError != null
            )
            if(state.confirmPasswordError != null){
                Text(
                    text = state.confirmPasswordError ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Button(
            onClick = {
                viewModel.onEvent(RegisterPageEvents.SubmitRegister)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Already have an account?")
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
