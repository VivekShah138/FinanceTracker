package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases

import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.DoesItemExistsUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllNotSyncedSavedItemUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllSavedItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveNewItemReturnId
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.GetRemoteSavedItemList
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.InsertRemoteSavedItemToLocal
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveSingleSavedItemCloud
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal

data class SavedItemsUseCasesWrapper (
    val saveItemLocalUseCase: SaveItemLocalUseCase,
    val saveNewItemReturnId: SaveNewItemReturnId,
    val saveSingleSavedItemCloud: SaveSingleSavedItemCloud,
    val getAllSavedItemLocalUseCase: GetAllSavedItemLocalUseCase,
    val getAllNotSyncedSavedItemUseCase: GetAllNotSyncedSavedItemUseCase,
    val getUserProfileLocal: GetUserProfileLocal,
    val getUIDLocally: GetUIDLocally,
    val savedItemsValidationUseCase: SavedItemsValidationUseCase,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val getCloudSyncLocally: GetCloudSyncLocally,
    val getRemoteSavedItemList: GetRemoteSavedItemList,
    val doesItemExistsUseCase: DoesItemExistsUseCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val insertRemoteSavedItemToLocal: InsertRemoteSavedItemToLocal
)