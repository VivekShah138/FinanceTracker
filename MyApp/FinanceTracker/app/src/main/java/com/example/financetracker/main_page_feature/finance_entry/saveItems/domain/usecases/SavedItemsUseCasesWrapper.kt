package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases

import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllSavedItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveItemLocalUseCase
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal

data class SavedItemsUseCasesWrapper (
    val saveItemLocalUseCase: SaveItemLocalUseCase,
    val getAllSavedItemLocalUseCase: GetAllSavedItemLocalUseCase,
    val getUserProfileLocal: GetUserProfileLocal,
    val getUIDLocally: GetUIDLocally,
    val savedItemsValidationUseCase: SavedItemsValidationUseCase
)