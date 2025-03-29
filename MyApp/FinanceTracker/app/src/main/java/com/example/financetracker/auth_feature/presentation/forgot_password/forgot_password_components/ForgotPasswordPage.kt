package com.example.financetracker.auth_feature.presentation.forgot_password.forgot_password_components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import com.example.financetracker.auth_feature.presentation.forgot_password.ForgotPasswordEvents
import com.example.financetracker.auth_feature.presentation.forgot_password.ForgotPasswordViewModel
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithEmailResult
import com.example.financetracker.core.core_presentation.utils.Screens
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordPage(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {

    val state by viewModel.forgotPasswordState.collectAsStateWithLifecycle()
    val forgotPasswordEvents = viewModel.forgotPasswordValidationEvent

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }


    LaunchedEffect(key1 = context) {
        forgotPasswordEvents.collect(){events->
            when(events){
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.Success -> {
                    Toast.makeText(context,
                        "Email Sent Successfully",
                        Toast.LENGTH_SHORT).show()
                    navController.navigate(route = Screens.LogInScreen.routes)
                }
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.Failure -> {
                    Toast.makeText(context,
                        events.error,
                        Toast.LENGTH_SHORT).show()
                }
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.TriggerSendResetEmail -> {
                    coroutineScope.launch {
                        val result = accountManager.resetPasswordWithEmail(state.email)
                        handleForgotPassword(result,viewModel::onEvent)
                    }
                }
            }
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        CustomText(
            text = "Forgot Password",
            size = 20.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

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
                viewModel.onEvent(ForgotPasswordEvents.ChangeEmail(it))
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = state.emailError != null
        )
        if(state.emailError != null){
            Text(
                text = state.emailError ?: "Unknown",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(ForgotPasswordEvents.Submit)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Submit"
            )
        }
    }
}

private fun handleForgotPassword(
    result: ResetPasswordWithEmailResult,
    onEvent: (ForgotPasswordEvents) -> Unit
){
    when(result){
        is ResetPasswordWithEmailResult.EmailSuccess -> {
            onEvent(ForgotPasswordEvents.EmailSendSuccess)
        }
        is ResetPasswordWithEmailResult.AuthFailure -> {
            onEvent(ForgotPasswordEvents.EmailSendFailure("Authentication Failure"))
        }
        is ResetPasswordWithEmailResult.Cancelled -> {
            onEvent(ForgotPasswordEvents.EmailSendFailure("Cancelled"))
        }
        is ResetPasswordWithEmailResult.UnknownFailure -> {
            onEvent(ForgotPasswordEvents.EmailSendFailure("Unknown Failure"))
        }
    }
}
