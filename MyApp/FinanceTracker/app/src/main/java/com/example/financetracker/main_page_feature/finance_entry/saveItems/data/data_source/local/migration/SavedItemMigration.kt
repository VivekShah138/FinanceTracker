package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val SAVED_ITEM_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create a new table with autoGenerate for itemId and copy the data

        // Drop the old table
        db.execSQL("DROP TABLE `SavedItemsEntity`")


        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `SavedItemsEntity` (
                `itemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `itemName` TEXT NOT NULL, 
                `itemCurrency` TEXT NOT NULL, 
                `itemPrice` REAL, 
                `itemDescription` TEXT, 
                `itemShopName` TEXT, 
                `userUID` TEXT NOT NULL
            )
        """)
    }
}
