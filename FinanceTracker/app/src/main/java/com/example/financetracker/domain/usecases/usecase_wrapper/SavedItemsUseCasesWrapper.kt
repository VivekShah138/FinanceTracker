package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.validation.SavedItemsValidationUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DoesSavedItemExistsUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllUnSyncedSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertSavedItemAndReturnIdLocalUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.GetRemoteSavedItemList
import com.example.financetracker.domain.usecases.remote.saved_items.InsertRemoteSavedItemToLocal
import com.example.financetracker.domain.usecases.remote.saved_items.SaveSingleSavedItemCloud
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileLocalUseCase

data class SavedItemsUseCasesWrapper (
    val insertSavedItemLocalUseCase: InsertSavedItemLocalUseCase,
    val insertSavedItemAndReturnIdLocalUseCase: InsertSavedItemAndReturnIdLocalUseCase,
    val saveSingleSavedItemCloud: SaveSingleSavedItemCloud,
    val getAllSavedItemLocalUseCase: GetAllSavedItemLocalUseCase,
    val getAllUnSyncedSavedItemLocalUseCase: GetAllUnSyncedSavedItemLocalUseCase,
    val getUserProfileLocalUseCase: GetUserProfileLocalUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val savedItemsValidationUseCase: SavedItemsValidationUseCase,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val getCloudSyncLocalUseCase: GetCloudSyncLocalUseCase,
    val getRemoteSavedItemList: GetRemoteSavedItemList,
    val doesSavedItemExistsUseCase: DoesSavedItemExistsUseCase,
    val getUserUIDRemoteUseCase: GetUserUIDRemoteUseCase,
    val insertRemoteSavedItemToLocal: InsertRemoteSavedItemToLocal
)