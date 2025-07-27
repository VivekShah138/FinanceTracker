package com.example.financetracker.data.data_source.local.room.modules.category

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [com.example.financetracker.data.data_source.local.room.modules.category.CategoryEntity::class],
    version = 3
)
abstract class CategoryDatabase: RoomDatabase() {
    abstract val categoryDao: CategoryDao
    companion object{
        const val DATABASE_NAME = "category_db"
    }
}