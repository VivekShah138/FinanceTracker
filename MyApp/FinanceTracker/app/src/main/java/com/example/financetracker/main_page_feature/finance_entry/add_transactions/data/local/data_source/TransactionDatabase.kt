package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionsEntity::class],
    version = 2
)
abstract class TransactionDatabase(): RoomDatabase() {

    abstract val transactionDao: TransactionDao

    companion object{
        const val DATABASE_NAME = "transaction_db"
    }

}