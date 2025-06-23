package com.example.financetracker.core.local.data.room.data_source.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query(" SELECT * FROM CategoryEntity WHERE (type = :type AND uid = :uid) OR (type = :type AND isCustom = 0)")
    fun getCategories(type: String, uid: String): Flow<List<CategoryEntity>>

    @Query(" SELECT * FROM CategoryEntity WHERE (type = :type AND uid = :uid AND isCustom = 1)")
    fun getCustomCategories(type: String, uid: String): Flow<List<CategoryEntity>>

    @Query(" SELECT * FROM CategoryEntity WHERE (type = :type AND isCustom = 0)")
    fun getPredefinedCategories(type: String): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Upsert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT COUNT(*) FROM CategoryEntity")
    suspend fun getCategoryCount(): Int

    @Query("DELETE FROM CategoryEntity WHERE categoryId = :categoryId")
    suspend fun deleteCustomCategory(categoryId: Int)
}