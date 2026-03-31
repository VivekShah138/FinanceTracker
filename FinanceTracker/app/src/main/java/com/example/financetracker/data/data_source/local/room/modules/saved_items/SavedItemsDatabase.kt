package com.example.financetracker.data.data_source.local.room.modules.saved_items

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