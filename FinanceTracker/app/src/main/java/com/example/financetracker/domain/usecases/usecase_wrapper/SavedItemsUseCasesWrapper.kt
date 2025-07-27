package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.local.saved_items.SavedItemsValidationUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DoesItemExistsUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllNotSyncedSavedItemUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.SaveItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.SaveNewItemReturnId
import com.example.financetracker.domain.usecases.remote.saved_items.GetRemoteSavedItemList
import com.example.financetracker.domain.usecases.remote.saved_items.InsertRemoteSavedItemToLocal
import com.example.financetracker.domain.usecases.remote.saved_items.SaveSingleSavedItemCloud
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileLocal

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