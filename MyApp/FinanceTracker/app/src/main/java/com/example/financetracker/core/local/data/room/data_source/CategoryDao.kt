package com.example.financetracker.core.local.data.room.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM CategoryEntity WHERE type = :type")
    fun getCategories(type: String): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insertCategories(categories: List<CategoryEntity>)

}