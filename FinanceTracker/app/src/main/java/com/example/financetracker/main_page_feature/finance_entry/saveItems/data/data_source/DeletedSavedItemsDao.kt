package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeletedSavedItemsDao {

    @Upsert
    suspend fun insertDeletedSavedItems(deletedSavedItemsEntity: DeletedSavedItemsEntity)

    @Query("SELECT * FROM DeletedSavedItemsEntity WHERE userUid = :uid")
    fun getAllDeletedSavedItems(uid: String): Flow<List<DeletedSavedItemsEntity>>

    @Query("DELETE FROM DeletedSavedItemsEntity WHERE itemId =:itemId")
    suspend fun deleteDeletedSavedItemsByIds(itemId: Int)

}