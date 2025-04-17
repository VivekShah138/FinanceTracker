package com.example.financetracker.categories_feature.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreCategoriesViewModel @Inject constructor(
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper
): ViewModel() {
    private val _coreCategoriesState = MutableStateFlow(CategoriesStates())
    val coreCategoriesState : StateFlow<CategoriesStates> = _coreCategoriesState.asStateFlow()


    fun onEvent(coreCategoriesEvents: CoreCategoriesEvents){
        when(coreCategoriesEvents){
            CoreCategoriesEvents.AddCategory -> {
                insertCustomCategory()
            }
            is CoreCategoriesEvents.ChangeCategoryAlertBoxState -> {
                _coreCategoriesState.value = coreCategoriesState.value.copy(
                    addCategoryAlertBoxState = coreCategoriesEvents.state
                )
            }
            is CoreCategoriesEvents.ChangeCategoryName -> {
                _coreCategoriesState.value = coreCategoriesState.value.copy(
                    categoryName = coreCategoriesEvents.name
                )
            }
            is CoreCategoriesEvents.SelectCategoryType -> {
                _coreCategoriesState.value = coreCategoriesState.value.copy(
                    categoryType = coreCategoriesEvents.type
                )
            }
        }
    }

    private fun insertCustomCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"
            val category = Category(
                uid = uid,
                name = _coreCategoriesState.value.categoryName,
                type = _coreCategoriesState.value.categoryType.lowercase(),
                icon = "ic_custom",
                isCustom = true
            )
            try{
                addTransactionUseCasesWrapper.insertCustomCategory(category)
                _coreCategoriesState.value = coreCategoriesState.value.copy(
                    categoryName = ""
                )
            }catch (e: Exception){
                Log.d("AddTransactionViewModel", "error: ${e.localizedMessage}")
            }
        }
    }

}