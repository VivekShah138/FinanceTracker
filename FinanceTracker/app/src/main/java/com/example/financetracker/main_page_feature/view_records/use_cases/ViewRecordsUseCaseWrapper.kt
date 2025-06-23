package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.core.cloud.domain.usecase.DeleteTransactionCloud
import com.example.financetracker.core.cloud.domain.usecase.InternetConnectionAvailability
import com.example.financetracker.core.local.domain.room.usecases.GetAllCategories
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.DeleteDeletedTransactionsByIdsFromLocal
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllDeletedTransactionByUserId
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllLocalTransactionsById
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllTransactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsValidationUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.DeleteDeletedSavedItemsById
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllDeletedSavedItemsByUserId
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllSavedItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetSavedItemById
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.DeleteMultipleSavedItemCloud
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveMultipleSavedItemCloud
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveSingleSavedItemCloud
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal

data class ViewRecordsUseCaseWrapper (
    val getAllTransactions: GetAllTransactions,
    val getAllSavedItemLocalUseCase: GetAllSavedItemLocalUseCase,
    val getUIDLocally: GetUIDLocally,
    val getUserProfileLocal: GetUserProfileLocal,
    val deleteSelectedTransactionsByIdsLocally: DeleteSelectedTransactionsByIdsLocally,
    val deleteSelectedSavedItemsByIdsLocally: DeleteSelectedSavedItemsByIdsLocally,
    val getCloudSyncLocally: GetCloudSyncLocally,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val insertDeletedTransactionsLocally: InsertDeletedTransactionsLocally,
    val deleteTransactionCloud: DeleteTransactionCloud,
    val getAllDeletedTransactionByUserId: GetAllDeletedTransactionByUserId,
    val deleteDeletedTransactionsByIdsFromLocal: DeleteDeletedTransactionsByIdsFromLocal,
    val deleteMultipleTransactionsFromCloud: DeleteMultipleTransactionsFromCloud,
    val getAllLocalTransactionsById: GetAllLocalTransactionsById,
    val deleteSavedItemCloud: DeleteSavedItemCloud,
    val insertDeletedSavedItemLocally: InsertDeletedSavedItemLocally,
    val getSavedItemById: GetSavedItemById,
    val getAllDeletedSavedItemsByUserId: GetAllDeletedSavedItemsByUserId,
    val deleteDeletedSavedItemsById: DeleteDeletedSavedItemsById,
    val deleteMultipleSavedItemCloud: DeleteMultipleSavedItemCloud,
    val savedItemsValidationUseCase: SavedItemsValidationUseCase,
    val saveSingleSavedItemCloud: SaveSingleSavedItemCloud,
    val saveItemLocalUseCase: SaveItemLocalUseCase,
    val getAllCategories: GetAllCategories,
    val getAllTransactionsFilters: GetAllTransactionsFilters,
)