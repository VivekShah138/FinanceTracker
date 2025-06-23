package com.example.financetracker.budget_feature.data.repository

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.financetracker.R
import com.example.financetracker.budget_feature.data.data_source.BudgetDao
import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.model.toDomain
import com.example.financetracker.budget_feature.domain.model.toEntity
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetLocalRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val context: Context
): BudgetLocalRepository {
    override suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): Budget? {
        return budgetDao.getBudgetForMonth(userId = userId,month = month,year = year)?.toDomain()
    }

    override suspend fun getAllUnSyncedBudget(userId: String): Flow<List<Budget>> {
        return budgetDao.getAllUnSyncedBudget(userId).map { budgets ->
            budgets.map { budget ->
                budget.toDomain()
            }
        }
    }

    override suspend fun insertBudget(budget: Budget) {
        return budgetDao.insertBudget(budget = budget.toEntity())
    }

    override suspend fun sendBudgetNotifications(title: String, message: String) {
        val builder = NotificationCompat.Builder(context, "budget_alert_channel")
            .setSmallIcon(R.drawable.warning) // ✅ use your icon resource
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, builder.build())
    }

    override suspend fun doesBudgetExist(userId: String, id: String): Boolean {
        return budgetDao.doesBudgetExist(userId = userId, id = id)
    }
}