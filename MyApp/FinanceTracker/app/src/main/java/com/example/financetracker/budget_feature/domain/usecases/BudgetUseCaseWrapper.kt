package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.core.cloud.domain.usecase.GetRemoteTransactionsList
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally

data class BudgetUseCaseWrapper(
    val getBudgetLocalUseCase: GetBudgetLocalUseCase,
    val insertBudgetLocalUseCase: InsertBudgetLocalUseCase,
    val getUIDLocally: GetUIDLocally,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb,
    val getAllUnSyncedBudgetLocalUseCase: GetAllUnSyncedBudgetLocalUseCase,
    val saveBudgetToCloudUseCase: SaveBudgetToCloudUseCase,
    val saveMultipleBudgetsToCloudUseCase: SaveMultipleBudgetsToCloudUseCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val doesBudgetExits: DoesBudgetExits,
    val getRemoteBudgetsList: GetRemoteBudgetsList,
    val insertRemoteBudgetsToLocal: InsertRemoteBudgetsToLocal
)