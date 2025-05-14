package com.example.financetracker.startup_page_feature.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.R
import com.example.financetracker.ui.theme.AppTheme

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun StartUpPagePreviewScreen(){
    AppTheme(dynamicColor = false, darkTheme = false) {


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(text = "Welcome!!",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.login_page_img),
                    contentDescription = "Startup page image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp), // match your vector's height if needed
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Track Today,\n" +
                            "Save Tomorrow !! ",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Difficulty in tracking daily expenses?\n" +
                            "No Worries, I’m here to help!",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                )




                Spacer(modifier = Modifier.height(100.dp))


                SegmentedLoginButton(
                    selectedType = "Register",
                    onLoginSelected = { },
                    onRegisterSelected = { },
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = MaterialTheme.colorScheme.onPrimary
                )

            }

        }
    }
}