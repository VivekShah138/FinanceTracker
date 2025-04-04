package com.example.financetracker.core.local.data.room.data_source.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM CategoryEntity WHERE type = :type")
    fun getCategories(type: String): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Upsert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT COUNT(*) FROM CategoryEntity")
    suspend fun getCategoryCount(): Int

    @Query("DELETE FROM CategoryEntity WHERE name = :categoryName AND uid = :categoryUid")
    suspend fun deleteCustomCategory(categoryName: String, categoryUid: Int)



}