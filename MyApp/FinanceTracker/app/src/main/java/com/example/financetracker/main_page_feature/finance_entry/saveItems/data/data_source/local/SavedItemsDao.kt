package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedItemsDao {
    @Upsert
    suspend fun insertSavedItems(savedItemsEntity: SavedItemsEntity)

    @Query("SELECT * FROM SAVEDITEMSENTITY WHERE userUID = :userUID")
    fun getAllSavedItems(userUID: String): Flow<List<SavedItemsEntity>>
}