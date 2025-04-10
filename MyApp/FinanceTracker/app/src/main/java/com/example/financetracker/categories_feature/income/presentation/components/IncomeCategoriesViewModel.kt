package com.example.financetracker.categories_feature.income.presentation.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.categories_feature.expense.presentation.ExpenseCategoriesEvents
import com.example.financetracker.categories_feature.expense.presentation.ExpenseCategoriesStates
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
class IncomeCategoriesViewModel @Inject constructor(
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper,
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper
): ViewModel() {

    private val _incomeCategoriesState = MutableStateFlow(ExpenseCategoriesStates())
    val incomeCategoriesState : StateFlow<ExpenseCategoriesStates> = _incomeCategoriesState.asStateFlow()

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
                _incomeCategoriesState.value = incomeCategoriesState.value.copy(
                    categoryName = expenseCategoriesEvents.name
                )
            }
            is ExpenseCategoriesEvents.SaveCategory -> {
                _categoryState.value = _categoryState.value!!.copy(
                    name = _incomeCategoriesState.value.categoryName
                )

                viewModelScope.launch {
                    predefinedCategoriesUseCaseWrapper.insertCustomCategories(_categoryState.value!!)
                }
            }
            is ExpenseCategoriesEvents.ChangeCategoryAlertBoxState -> {
                _incomeCategoriesState.value = incomeCategoriesState.value.copy(
                    categoryAlertBoxState = expenseCategoriesEvents.state
                )
            }

            is ExpenseCategoriesEvents.ChangeSelectedCategory -> {
                _categoryState.value = expenseCategoriesEvents.category
            }
            is ExpenseCategoriesEvents.DeleteCategory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    predefinedCategoriesUseCaseWrapper.deleteCustomCategories(_categoryState.value?.categoryId!!)
                }
            }
        }
    }

    private fun loadPredefinedCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                predefinedCategoriesUseCaseWrapper.getPredefinedCategories("Income".lowercase(),uid).collect{ predefinedCategories ->
                    _incomeCategoriesState.value = incomeCategoriesState.value.copy(
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
                predefinedCategoriesUseCaseWrapper.getCustomCategories("Income".lowercase(),uid).collect{ customCategories ->
                    _incomeCategoriesState.value = incomeCategoriesState.value.copy(
                        customCategories = customCategories
                    )
                }

            }catch (e:Exception){
                Log.d("ExpenseCategoriesViewModel","Error Message: ${e.localizedMessage}")
            }
        }
    }

}