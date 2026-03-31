package com.example.financetracker.mapper

import com.example.financetracker.domain.model.Budget
import com.example.financetracker.data.data_source.local.room.modules.budget.BudgetEntity

object BudgetMapper {
    fun toEntity(budget: Budget): BudgetEntity {
        return BudgetEntity(
            id = budget.id,
            userId = budget.userId,
            amount = budget.amount,
            month = budget.month,
            year = budget.year,
            updatedAt = budget.updatedAt,
            cloudSync = budget.cloudSync,
            receiveAlerts = budget.receiveAlerts,
            thresholdAmount = budget.thresholdAmount
        )
    }

    fun toDomain(entity: BudgetEntity): Budget {
        return Budget(
            id = entity.id,
            userId = entity.userId,
            amount = entity.amount,
            month = entity.month,
            year = entity.year,
            updatedAt = entity.updatedAt,
            cloudSync = entity.cloudSync,
            receiveAlerts = entity.receiveAlerts,
            thresholdAmount = entity.thresholdAmount
        )
    }
}