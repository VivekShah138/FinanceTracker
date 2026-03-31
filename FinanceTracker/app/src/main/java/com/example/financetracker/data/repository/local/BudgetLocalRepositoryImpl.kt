package com.example.financetracker.data.repository.local

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.financetracker.R
import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.local.BudgetLocalRepository
import com.example.financetracker.data.data_source.local.room.modules.budget.BudgetDao
import com.example.financetracker.mapper.BudgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetLocalRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val context: Context
): BudgetLocalRepository {
    override suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): Budget? {
        val entity = budgetDao.getBudgetForMonth(userId = userId, month = month, year = year)
        return entity?.let { BudgetMapper.toDomain(it) }
    }

    override suspend fun getAllUnSyncedBudget(userId: String): Flow<List<Budget>> {
        return budgetDao.getAllUnSyncedBudget(userId).map { budgetEntities ->
            budgetEntities.map { budget ->
                BudgetMapper.toDomain(budget)
            }
        }
    }

    override suspend fun insertBudget(budget: Budget) {
        return budgetDao.insertBudget(budget = BudgetMapper.toEntity(budget))
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