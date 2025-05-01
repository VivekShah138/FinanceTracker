package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally

data class BudgetUseCaseWrapper(
    val getBudgetLocalUseCase: GetBudgetLocalUseCase,
    val insertBudgetLocalUseCase: InsertBudgetLocalUseCase,
    val getUIDLocally: GetUIDLocally,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb
)