package com.example.financetracker.presentation.features.finance_entry_feature.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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

    Log.d("AddTransactionPage","searchBarFocus ${states.searchBarFocusedState}")
    Log.d("AddTransactionPage","saveItemState ${states.saveItemState}")
    Log.d("AddTransactionPage","searchableList ${states.transactionSearchList}")


    Box(
        modifier = Modifier.fillMaxSize()
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