package com.example.financetracker.presentation.features.finance_entry_feature.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddTransactionEvents
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddTransactionStates
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    states: AddTransactionStates,
    selectedItem: SavedItems?,
    addTransactionValidationEvents: Flow<AddTransactionValidationEvent>,
    onEvent : (AddTransactionEvents) -> Unit
){
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }



    LaunchedEffect(key1 = context) {
        addTransactionValidationEvents.collect { event ->
            when (event) {
                is AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Transaction Successfully Added",Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen)
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        if(states.searchBarFocusedState){
            SavedItemTransactionContent(
                states = states,
                focusRequester = focusRequester,
                selectedItem = selectedItem,
                onEvent = onEvent
            )
        }
        else{
            ManualTransactionContent(
                scrollState = scrollState,
                states = states,
                onEvent = onEvent
            )
        }

        if(states.isLoading){
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.Center),
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
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "With Loading"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreenPreview2(){
    FinanceTrackerTheme {
        AddTransactionScreen(
            navController = rememberNavController(),
            states = AddTransactionStates(
                isLoading = true
            ),
            selectedItem = null,
            addTransactionValidationEvents = emptyFlow(),
            onEvent = {

            }
        ) 
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreenPreview(){
    FinanceTrackerTheme {
        AddTransactionScreen(
            navController = rememberNavController(),
            states = AddTransactionStates(),
            selectedItem = null,
            addTransactionValidationEvents = emptyFlow(),
            onEvent = {

            }
        )
    }
}