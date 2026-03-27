package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.transactions.DeleteTransactionRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.local.category.GetAllCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.DeleteDeletedTransactionsByIdRemoteUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllDeletedTransactionByUIDUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetTransactionByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsByUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.validation.SavedItemsValidationUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DeleteDeletedSavedItemByIdUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllDeletedSavedItemsByUserIdUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetSavedItemByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.DeleteMultipleSavedItemCloud
import com.example.financetracker.domain.usecases.remote.saved_items.SaveSingleSavedItemCloud
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.DeleteTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.DeleteSavedItemCloud
import com.example.financetracker.domain.usecases.local.saved_items.DeleteSavedItemByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.DeleteTransactionByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFiltersUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertDeletedSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertDeletedTransactionsLocalUseCase

data class ViewRecordsUseCaseWrapper (
    val getAllTransactionsByUIDLocalUseCase: GetAllTransactionsByUIDLocalUseCase,
    val getAllSavedItemLocalUseCase: GetAllSavedItemLocalUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val getUserProfileLocalUseCase: GetUserProfileLocalUseCase,
    val deleteTransactionByIdLocalUseCase: DeleteTransactionByIdLocalUseCase,
    val deleteSavedItemByIdLocalUseCase: DeleteSavedItemByIdLocalUseCase,
    val getCloudSyncLocalUseCase: GetCloudSyncLocalUseCase,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val insertDeletedTransactionsLocalUseCase: InsertDeletedTransactionsLocalUseCase,
    val deleteTransactionRemoteUseCase: DeleteTransactionRemoteUseCase,
    val getAllDeletedTransactionByUIDUseCase: GetAllDeletedTransactionByUIDUseCase,
    val deleteDeletedTransactionsByIdRemoteUseCase: DeleteDeletedTransactionsByIdRemoteUseCase,
    val deleteTransactionsRemoteUseCase: DeleteTransactionsRemoteUseCase,
    val getTransactionByIdLocalUseCase: GetTransactionByIdLocalUseCase,
    val deleteSavedItemCloud: DeleteSavedItemCloud,
    val insertDeletedSavedItemLocalUseCase: InsertDeletedSavedItemLocalUseCase,
    val getSavedItemByIdLocalUseCase: GetSavedItemByIdLocalUseCase,
    val getAllDeletedSavedItemsByUserIdUseCase: GetAllDeletedSavedItemsByUserIdUseCase,
    val deleteDeletedSavedItemByIdUseCase: DeleteDeletedSavedItemByIdUseCase,
    val deleteMultipleSavedItemCloud: DeleteMultipleSavedItemCloud,
    val savedItemsValidationUseCase: SavedItemsValidationUseCase,
    val saveSingleSavedItemCloud: SaveSingleSavedItemCloud,
    val insertSavedItemLocalUseCase: InsertSavedItemLocalUseCase,
    val getAllCategoriesLocalUseCase: GetAllCategoriesLocalUseCase,
    val getAllTransactionsFilters: GetAllTransactionsFiltersUseCase,
)