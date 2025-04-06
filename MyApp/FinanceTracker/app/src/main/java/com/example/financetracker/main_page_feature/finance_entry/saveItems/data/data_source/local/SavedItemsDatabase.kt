package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SavedItemsEntity::class],
    version = 1
)
abstract class SavedItemsDatabase: RoomDatabase() {

    abstract val savedItemsDao: SavedItemsDao

    companion object{
        const val DATABASE_NAME = "save_items_db"
    }
}