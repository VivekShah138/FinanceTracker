package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SavedItemsEntity::class, DeletedSavedItemsEntity::class],
    version = 3
)
abstract class SavedItemsDatabase: RoomDatabase() {

    abstract val savedItemsDao: SavedItemsDao
    abstract val deletedSavedItemsDao: DeletedSavedItemsDao

    companion object{
        const val DATABASE_NAME = "save_items_db"
    }
}