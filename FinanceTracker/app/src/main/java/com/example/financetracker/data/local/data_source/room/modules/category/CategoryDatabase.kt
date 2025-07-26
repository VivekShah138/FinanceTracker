package com.example.financetracker.data.local.data_source.room.modules.category

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CategoryEntity::class],
    version = 3
)
abstract class CategoryDatabase: RoomDatabase() {
    abstract val categoryDao: CategoryDao
    companion object{
        const val DATABASE_NAME = "category_db"
    }
}