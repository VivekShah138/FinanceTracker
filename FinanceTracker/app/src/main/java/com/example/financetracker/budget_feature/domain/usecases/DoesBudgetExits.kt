package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.domain.repository.local.BudgetLocalRepository

class DoesBudgetExits(
    private val budgetLocalRepository: BudgetLocalRepository
){
    suspend operator fun invoke(userId: String,id: String): Boolean{
        return budgetLocalRepository.doesBudgetExist(userId = userId,id = id)
    }
}