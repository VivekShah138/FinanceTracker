package com.example.financetracker.data.data_source.local.room.modules.category

import androidx.room.Database
import androidx.room.Index
import androidx.room.RoomDatabase

@Database(
    entities = [
        CategoryEntity::class
    ],
    version = 4
)
abstract class CategoryDatabase: RoomDatabase() {
    abstract val categoryDao: CategoryDao
    companion object{
        const val DATABASE_NAME = "category_db"
    }
}