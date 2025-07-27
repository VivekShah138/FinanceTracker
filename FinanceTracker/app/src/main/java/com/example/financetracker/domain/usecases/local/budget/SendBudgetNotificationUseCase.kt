package com.example.financetracker.domain.usecases.local.budget

import com.example.financetracker.domain.repository.local.BudgetLocalRepository

class SendBudgetNotificationUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {

    suspend operator fun invoke(title: String, message: String){
        return budgetLocalRepository.sendBudgetNotifications(title = title,message = message)
    }

}