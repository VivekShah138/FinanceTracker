package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.DoesBudgetExitsLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetAllUnSyncedBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.GetBudgetsRemoteUseCase
import com.example.financetracker.domain.usecases.local.budget.InsertBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetsRemoteToLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetRemoteUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase

data class BudgetUseCaseWrapper(
    val getBudgetLocalUseCase: GetBudgetLocalUseCase,
    val insertBudgetLocalUseCase: InsertBudgetLocalUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val getUserProfileFromLocalUseCase: GetUserProfileFromLocalUseCase,
    val getAllUnSyncedBudgetLocalUseCase: GetAllUnSyncedBudgetLocalUseCase,
    val insertBudgetRemoteUseCase: InsertBudgetRemoteUseCase,
    val insertBudgetsRemoteUseCase: InsertBudgetsRemoteUseCase,
    val getUserUIDRemoteUseCase: GetUserUIDRemoteUseCase,
    val doesBudgetExitsLocalUseCase: DoesBudgetExitsLocalUseCase,
    val getBudgetsRemoteUseCase: GetBudgetsRemoteUseCase,
    val insertBudgetsRemoteToLocalUseCase: InsertBudgetsRemoteToLocalUseCase
)