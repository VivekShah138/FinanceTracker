package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.transactions.DeleteTransactionCloud
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
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
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveSingleSavedItemCloud
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal
import com.example.financetracker.domain.usecases.remote.transactions.DeleteMultipleTransactionsFromCloud
import com.example.financetracker.domain.usecases.remote.saved_items.DeleteSavedItemCloud
import com.example.financetracker.domain.usecases.local.saved_items.DeleteSelectedSavedItemsByIdsLocally
import com.example.financetracker.domain.usecases.local.transaction.DeleteSelectedTransactionsByIdsLocally
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFilters
import com.example.financetracker.domain.usecases.local.saved_items.InsertDeletedSavedItemLocally
import com.example.financetracker.domain.usecases.local.transaction.InsertDeletedTransactionsLocally

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