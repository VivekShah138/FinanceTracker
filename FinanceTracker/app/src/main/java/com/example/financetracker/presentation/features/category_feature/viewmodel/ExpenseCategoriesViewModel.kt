package com.example.financetracker.presentation.features.category_feature.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.presentation.features.category_feature.events.SharedCategoriesEvents
import com.example.financetracker.presentation.features.category_feature.states.CategoriesStates
import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.usecases.usecase_wrapper.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
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

    private val _expenseCategoriesState = MutableStateFlow(CategoriesStates())
    val expenseCategoriesState : StateFlow<CategoriesStates> = _expenseCategoriesState.asStateFlow()

    private val _categoryState = MutableStateFlow<Category?>(null)
    val categoryState : StateFlow<Category?> = _categoryState.asStateFlow()

    private val uid = setupAccountUseCasesWrapper.getUIDLocalUseCase() ?: "Unknown"

    init {
        loadPredefinedCategories()
        loadCustomCategories()
    }

    fun onEvent(sharedCategoriesEvents: SharedCategoriesEvents){
        when(sharedCategoriesEvents){
            is SharedCategoriesEvents.ChangeCategoryName -> {
                _categoryState.value = categoryState.value?.copy(
                    name = sharedCategoriesEvents.name
                )
            }
            is SharedCategoriesEvents.SaveCategory -> {
                val category = _categoryState.value ?: return
                val formattedName = category.name
                    .trim()
                    .replace("\\s+".toRegex(), " ")

                _categoryState.value = category.copy(
                    name = formattedName
                )

                Logger.d(Logger.Tag.EXPENSE_CATEGORY_VIEWMODEL,"SharedCategoriesEvents.SaveCategory: category: $category ")
                viewModelScope.launch(Dispatchers.IO) {
                    predefinedCategoriesUseCaseWrapper.insertCustomCategoriesLocalUseCase(category)
                }
            }
            is SharedCategoriesEvents.ChangeCategoryAlertBoxState -> {
                _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                    updateCategoryAlertBoxState = sharedCategoriesEvents.state
                )
            }
            is SharedCategoriesEvents.ChangeSelectedCategory -> {
                _categoryState.value = sharedCategoriesEvents.category
            }
            is SharedCategoriesEvents.DeleteCategory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    predefinedCategoriesUseCaseWrapper.deleteCustomCategoriesLocalUseCase(_categoryState.value?.categoryId!!)
                }
            }
        }
    }

    private fun loadPredefinedCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                predefinedCategoriesUseCaseWrapper.getPredefinedCategoriesLocalUseCase("Expense".lowercase(),uid).collect{ predefinedCategories ->
                    _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                        predefinedCategories = predefinedCategories
                    )
                }

            }catch (e:Exception){
                Logger.e(Logger.Tag.EXPENSE_CATEGORY_VIEWMODEL,"Load predefined Category: error: ${e.localizedMessage}",e)

            }
        }
    }

    private fun loadCustomCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                predefinedCategoriesUseCaseWrapper.getCustomCategoriesLocalUseCase("Expense".lowercase(),uid).collect{ customCategories ->
                    _expenseCategoriesState.value = expenseCategoriesState.value.copy(
                        customCategories = customCategories
                    )
                }
            }catch (e:Exception){
                Logger.e(Logger.Tag.EXPENSE_CATEGORY_VIEWMODEL,"Load Custom Category: error: ${e.localizedMessage}",e)
            }
        }
    }
}