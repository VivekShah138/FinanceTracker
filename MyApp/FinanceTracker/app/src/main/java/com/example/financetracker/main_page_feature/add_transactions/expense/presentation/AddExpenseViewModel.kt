package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper
): ViewModel() {

    private val _addExpenseStates = MutableStateFlow(AddExpenseStates())
    val addExpenseStates : StateFlow<AddExpenseStates> = _addExpenseStates.asStateFlow()

    init {
        saveFromJSON()
    }

    fun onEvent(addExpenseEvents: AddExpenseEvents){
        when(addExpenseEvents){
            is AddExpenseEvents.SelectCategory -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    category = addExpenseEvents.categoryName,
                    bottomSheetState = addExpenseEvents.bottomSheetState
                )
            }
            is AddExpenseEvents.LoadCategory -> {
                viewModelScope.launch {
                    predefinedCategoriesUseCaseWrapper.getPredefinedCategories(addExpenseEvents.type.lowercase())
                        .collect { categoryList -> // Collect the flow to get the list
                            _addExpenseStates.value = addExpenseStates.value.copy(
                                categoryList = categoryList
                            )
                        }
                    }


                // Right Now just for UI testing loading manually later will load it in JSON file and update ROOM

//                _addExpenseStates.value = addExpenseStates.value.copy(
//                    categoryList = listOf(
//                        Category(
//                            name = "Food",
//                            type = "Expense",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Travel",
//                            type = "Expense",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Rent",
//                            type = "Expense",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Bills",
//                            type = "Expense",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Salary",
//                            type = "Income",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Freelance",
//                            type = "Income",
//                            icon = ""
//                        ),
//                        Category(
//                            name = "Rent",
//                            type = "Income",
//                            icon = ""
//                        ),
//                    ).filter { name  ->
//                        name.type == "Expense"
//                    }
//                )
            }
        }
    }

    fun saveFromJSON(){
        viewModelScope.launch {
            predefinedCategoriesUseCaseWrapper.insertPredefinedCategories()
            Log.d("Reached","Reached ViewModel")
        }
    }

}