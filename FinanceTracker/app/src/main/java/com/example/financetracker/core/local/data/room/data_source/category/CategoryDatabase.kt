package com.example.financetracker.core.local.data.room.data_source.category

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