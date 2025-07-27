package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.DoesBudgetExits
import com.example.financetracker.domain.usecases.local.budget.GetAllUnSyncedBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.GetRemoteBudgetsList
import com.example.financetracker.domain.usecases.local.budget.InsertBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertRemoteBudgetsToLocal
import com.example.financetracker.domain.usecases.remote.budget.SaveBudgetToCloudUseCase
import com.example.financetracker.domain.usecases.remote.budget.SaveMultipleBudgetsToCloudUseCase
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