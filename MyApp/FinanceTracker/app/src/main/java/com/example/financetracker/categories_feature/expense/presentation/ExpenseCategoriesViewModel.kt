package com.example.financetracker.categories_feature.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseCategoriesViewModel @Inject constructor(
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper,
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper
): ViewModel() {

    private val _expenseCategoriesState = MutableStateFlow(ExpenseCategoriesStates())
    val expenseCategoriesState : StateFlow<ExpenseCategoriesStates> = _expenseCategoriesState.asStateFlow()

    private val _categoryState = MutableStateFlow<Category?>(null)
    val categoryState : StateFlow<Category?> = _categoryState.asStateFlow()

    private val uid = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"

    init {
        loadPredefinedCategories()
        loadCustomCategories()
    }

    fun onEvent(expenseCategoriesEvents: ExpenseCategoriesEvents){
        when(expenseCategoriesEvents){
            is ExpenseCategoriesEvents.ChangeCategoryName -> {
                _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                    categoryName = expenseCategoriesEvents.name
                )
            }
            is ExpenseCategoriesEvents.SaveCategory -> {

            }
        }
    }

    private fun loadPredefinedCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                predefinedCategoriesUseCaseWrapper.getPredefinedCategories("Expense".lowercase(),uid).collect{ predefinedCategories ->
                    _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                        predefinedCategories = predefinedCategories
                    )
                }

            }catch (e:Exception){
                Log.d("ExpenseCategoriesViewModel","Error Message: ${e.localizedMessage}")
            }
        }
    }

    private fun loadCustomCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                predefinedCategoriesUseCaseWrapper.getCustomCategories("Expense".lowercase(),uid).collect{ customCategories ->
                    _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                        customCategories = customCategories
                    )
                }

            }catch (e:Exception){
                Log.d("ExpenseCategoriesViewModel","Error Message: ${e.localizedMessage}")
            }
        }
    }

}