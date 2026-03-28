package com.example.financetracker.presentation.features.auth_feature.components

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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.features.auth_feature.auth_utils.AccountManager
import com.example.financetracker.presentation.features.auth_feature.auth_utils.ResetPasswordWithEmailResult
import com.example.financetracker.presentation.features.auth_feature.events.ForgotPasswordEvents
import com.example.financetracker.presentation.features.auth_feature.viewmodels.ForgotPasswordViewModel
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.auth_feature.states.ForgotPasswordStates
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch


@Composable
fun ForgotPasswordRoot(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navController: NavController,
){
    val state by viewModel.forgotPasswordState.collectAsStateWithLifecycle()
    val forgotPasswordResults = viewModel.forgotPasswordValidationEvent

    ForgotPasswordPage(
        navController = navController,
        state = state,
        onEvent = viewModel::onEvent,
        forgotPasswordResult = forgotPasswordResults
    )

}
@Composable
fun ForgotPasswordPage(
    navController: NavController,
    state: ForgotPasswordStates,
    onEvent: (ForgotPasswordEvents) -> Unit,
    forgotPasswordResult: Flow<ForgotPasswordViewModel.ForgotPasswordValidationEvent>
) {


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
        forgotPasswordResult.collect{events->
            when(events){
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.Success -> {
                    Toast.makeText(context,
                        "Email Sent Successfully",
                        Toast.LENGTH_SHORT).show()
                    navController.navigate(route = Screens.LogInScreen)
                }
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.Failure -> {
                    Toast.makeText(context,
                        events.error,
                        Toast.LENGTH_SHORT).show()
                }
                is ForgotPasswordViewModel.ForgotPasswordValidationEvent.TriggerSendResetEmail -> {
                    coroutineScope.launch {
                        accountManager?.let { manager ->
                            val result = manager.resetPasswordWithEmail(state.email)
                            handleForgotPassword(result,onEvent)
                        }
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
                .fillMaxWidth(),
            text = state.email,
            onValueChange = {
                onEvent(ForgotPasswordEvents.ChangeEmail(it))
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = state.emailError != null,
            errorMessage = state.emailError ?: "",
            label = "Email"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onEvent(ForgotPasswordEvents.Submit)
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


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ForgotPassword(){
    FinanceTrackerTheme {
        ForgotPasswordPage(
            navController = rememberNavController(),
            state = ForgotPasswordStates(
                email = "shahvivek138@gmail.com",
                emailError = "email Error"
            ),
            onEvent = {

            },
            forgotPasswordResult = emptyFlow(),
        )
    }
}