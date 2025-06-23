package com.example.financetracker.budget_feature.data.data_source

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val BUDGET_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE monthly_budgets ADD COLUMN cloudSync INTEGER NOT NULL DEFAULT 0")
    }
}

val BUDGET_MIGRATION_2_3 = object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE monthly_budgets ADD COLUMN receiveAlerts INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE monthly_budgets ADD COLUMN thresholdAmount REAL NOT NULL DEFAULT 0.0")
    }
}
