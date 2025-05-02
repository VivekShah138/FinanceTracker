package com.example.financetracker.budget_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BudgetEntity::class],
    version = 3
)
abstract class BudgetDatabase: RoomDatabase() {
    abstract val budgetDao: BudgetDao
    companion object{
        const val DATABASE_NAME = "budget_db"
    }
}