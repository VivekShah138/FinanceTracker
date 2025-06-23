package com.example.financetracker.startup_page_feature.components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.R
import com.example.financetracker.auth_feature.presentation.AccountManager
import com.example.financetracker.auth_feature.presentation.login.LoginPageEvents
import com.example.financetracker.auth_feature.presentation.login.LoginPageViewModel
import com.example.financetracker.startup_page_feature.StartPageViewModel
import com.example.financetracker.core.core_presentation.utils.Screens
import kotlinx.coroutines.launch


@Composable
fun StartUpPageScreen(
    startUpPageViewModel: StartPageViewModel,
    loginPageViewModel: LoginPageViewModel,
    navController: NavController
){

    val startUpPageStates by startUpPageViewModel.startUpPageStates.collectAsStateWithLifecycle()
    val loginPageStates by loginPageViewModel.loginState.collectAsStateWithLifecycle()

    val loginEvents = loginPageViewModel.loginEvents

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

                    if(!loginPageStates.userProfile.profileSetUpCompleted){
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
                else -> {

                }
            }
        }
    }

    LaunchedEffect(startUpPageStates.isLoggedIn) {
        if(startUpPageStates.isLoggedIn){
            navController.navigate(route = Screens.HomePageScreen.routes) {
                popUpTo(route = Screens.LogInScreen.routes) {
                    inclusive = true
                }
            }
        }
    }


    LaunchedEffect(startUpPageStates.selectedButton) {
        if (startUpPageStates.previousSelectedButton != null && startUpPageStates.previousSelectedButton != startUpPageStates.selectedButton) {
            when (startUpPageStates.selectedButton) {
                "Login" -> navController.navigate(Screens.LogInScreen.routes)
                "Register" -> navController.navigate(Screens.RegistrationScreen.routes)
            }
        }
        startUpPageViewModel.onEvent(StartUpPageEvents.ChangePreviousSelectedButton(startUpPageStates.selectedButton))
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (loginPageStates.isLoading || loginPageStates.isDataSyncing) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f) else MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
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
                    .height(250.dp),
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


//            SegmentedLoginButton(
//                selectedType = states.selectedButton,
//                onLoginSelected = {
//                    viewModel.onEvent(StartUpPageEvents.ChangeSelectedButton("Login"))
////                    navController.navigate(route = Screens.LogInScreen.routes)
//                },
//                onRegisterSelected = {
//                    viewModel.onEvent(StartUpPageEvents.ChangeSelectedButton("Register"))
//
////                    navController.navigate(route = Screens.RegistrationScreen.routes)
//                },
//                selectedColor = MaterialTheme.colorScheme.primary,
//                unSelectedColor = MaterialTheme.colorScheme.onPrimary
//            )

            GoogleSignInButton(
                onClick = {
                    coroutineScope.launch {
                        loginPageViewModel.onEvent(LoginPageEvents.SetLoadingTrue(true))
                        val result = accountManager.signInWithGoogle()
                        loginPageViewModel.onEvent(LoginPageEvents.ClickLoginWithGoogle(result))
                    }
                },
                googleIcon = painterResource(R.drawable.google_icon),
                googleText = "Sign in with Google",
//                textStyle = MaterialTheme.typography.bodyMedium.copy(
//                    color = MaterialTheme.colorScheme.onBackground,
//                    fontSize = 28.sp,
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    shadow = Shadow(
//                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
//                        blurRadius = 4f,
//                        offset = Offset(2f, 2f)
//                    )
//                ),
            )


        }
        if(loginPageStates.isLoading){
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
        if(loginPageStates.isDataSyncing){
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