package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.migration

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

val SAVED_ITEM_MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // 1. Rename the existing table
        db.execSQL("ALTER TABLE `SavedItemsEntity` RENAME TO `SavedItemsEntity_old`")

        // 2. Create new table with `cloudSync` column
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `SavedItemsEntity` (
                `itemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `itemName` TEXT NOT NULL,
                `itemCurrency` TEXT NOT NULL,
                `itemPrice` REAL,
                `itemDescription` TEXT,
                `itemShopName` TEXT,
                `userUID` TEXT NOT NULL,
                `cloudSync` INTEGER NOT NULL DEFAULT 0
            )
            """
        )

        // 3. Copy the data from old table to new table, set `cloudSync = 0`
        db.execSQL(
            """
            INSERT INTO `SavedItemsEntity` (
                itemId,
                itemName,
                itemCurrency,
                itemPrice,
                itemDescription,
                itemShopName,
                userUID,
                cloudSync
            )
            SELECT 
                itemId,
                itemName,
                itemCurrency,
                itemPrice,
                itemDescription,
                itemShopName,
                userUID,
                0 AS cloudSync
            FROM `SavedItemsEntity_old`
            """
        )

        // 4. Drop the old table
        db.execSQL("DROP TABLE `SavedItemsEntity_old`")




        // Create the new table for deleted transactions
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `DeletedSavedItemsEntity` (
                `itemId` INTEGER PRIMARY KEY NOT NULL,
                `userUID` TEXT NOT NULL
            )
        """.trimIndent())
    }
}
