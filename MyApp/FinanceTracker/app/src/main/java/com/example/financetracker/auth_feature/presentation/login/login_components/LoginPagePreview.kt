package com.example.financetracker.auth_feature.presentation.login.login_components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.auth_feature.presentation.components.CustomText
import com.example.financetracker.auth_feature.presentation.components.CustomTextFields
import com.example.financetracker.auth_feature.presentation.login.LoginPageStates


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun LoginPagePreview() {
    MaterialTheme { // Wrap with MaterialTheme to apply colors properly

        Content2(
            LoginPageStates(
            email = "shah@138gmail.com",
            password = "VivekShah",
            emailError = "emailError",
            passwordError = "passwordError"
        )
        )
    }
}


@Composable
fun Content2(state: LoginPageStates) {

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
                    // Add Function Change
                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = state.emailError != null,
            )
            if(state.emailError != null){
                Text(
                    text = state.emailError,
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

                },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = true,
                inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.passwordError != null
            )
            if(state.passwordError != null){
                Text(
                    text = state.passwordError,
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
                checked = false,
                onCheckedChange = {
                    // Add Function
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

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Google")
        }

        TextButton(
            onClick = {

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

                }
            ) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

}