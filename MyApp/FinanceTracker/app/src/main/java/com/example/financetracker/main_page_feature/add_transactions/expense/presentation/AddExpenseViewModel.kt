package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import androidx.lifecycle.ViewModel
import com.example.financetracker.core.domain.model.PredefinedCategories
import com.example.financetracker.setup_account.presentation.ProfileSetUpStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(

): ViewModel() {

    private val _addExpenseStates = MutableStateFlow(AddExpenseStates())
    val addExpenseStates : StateFlow<AddExpenseStates> = _addExpenseStates.asStateFlow()

    fun onEvent(addExpenseEvents: AddExpenseEvents){
        when(addExpenseEvents){
            is AddExpenseEvents.SelectCategory -> {
                _addExpenseStates.value = addExpenseStates.value.copy(
                    category = addExpenseEvents.categoryName,
                    bottomSheetState = addExpenseEvents.bottomSheetState
                )
            }
            is AddExpenseEvents.LoadCategory -> {

                // Right Now just for UI testing loading manually later will load it in JSON file and update ROOM

                _addExpenseStates.value = addExpenseStates.value.copy(
                    categoryList = listOf(
                        PredefinedCategories(
                            name = "Food",
                            type = "Expense"
                        ),
                        PredefinedCategories(
                            name = "Travel",
                            type = "Expense"
                        ),
                        PredefinedCategories(
                            name = "Rent",
                            type = "Expense"
                        ),
                        PredefinedCategories(
                            name = "Bills",
                            type = "Expense"
                        ),
                        PredefinedCategories(
                            name = "Salary",
                            type = "Income"
                        ),
                        PredefinedCategories(
                            name = "Freelance",
                            type = "Income"
                        ),
                        PredefinedCategories(
                            name = "Rent",
                            type = "Income"
                        ),
                    ).filter { name  ->
                        name.type == "Expense"
                    }
                )
            }
        }
    }

}