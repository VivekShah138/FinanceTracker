package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.DateTypeConverters

@Database(
    entities = [TransactionsEntity::class, DeletedTransactionsEntity::class],
    version = 3
)
@TypeConverters(DateTypeConverters::class)
abstract class TransactionDatabase: RoomDatabase() {

    abstract val transactionDao: TransactionDao
    abstract val deletedTransactionDao: DeletedTransactionDao

    companion object{
        const val DATABASE_NAME = "transaction_db"
    }
}