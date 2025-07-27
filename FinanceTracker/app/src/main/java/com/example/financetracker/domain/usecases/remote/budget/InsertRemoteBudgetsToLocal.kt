package com.example.financetracker.domain.usecases.remote.budget

import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository

class InsertRemoteBudgetsToLocal(
    private val budgetRemoteRepository: BudgetRemoteRepository
) {

    suspend operator fun invoke(){
        return budgetRemoteRepository.insertRemoteBudgetToLocal()
    }
}