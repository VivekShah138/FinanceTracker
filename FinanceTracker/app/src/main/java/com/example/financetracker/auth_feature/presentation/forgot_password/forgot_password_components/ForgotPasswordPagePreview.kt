package com.example.financetracker.auth_feature.presentation.forgot_password.forgot_password_components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.auth_feature.presentation.components.CustomText
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields
import com.example.financetracker.auth_feature.presentation.forgot_password.ForgotPasswordStates


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun LoginPagePreview() {
    MaterialTheme { // Wrap with MaterialTheme to apply colors properly

        Content2(
            ForgotPasswordStates(
                email = "shahvivek138@gmail.com",
                emailError = "Please Enter a Valid Email"
            )
        )
    }
}


@Composable
fun Content2(
    forgotPasswordStates: ForgotPasswordStates
){
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
            text = forgotPasswordStates.email,
            onValueChange = {
                // Add Function Change
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = forgotPasswordStates.emailError != null
        )
        if(forgotPasswordStates.emailError != null){
            Text(
                text = forgotPasswordStates.emailError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Submit"
            )
        }
    }
}