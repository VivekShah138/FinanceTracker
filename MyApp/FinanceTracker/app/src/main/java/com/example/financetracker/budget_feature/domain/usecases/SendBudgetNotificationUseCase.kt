package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository

class SendBudgetNotificationUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {

    suspend operator fun invoke(title: String, message: String){
        return budgetLocalRepository.sendBudgetNotifications(title = title,message = message)
    }

}