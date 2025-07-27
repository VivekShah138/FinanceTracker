package com.example.financetracker.mapper


import com.example.financetracker.data.data_source.local.room.modules.saved_items.DeletedSavedItemsEntity
import com.example.financetracker.domain.model.DeletedSavedItems

object DeletedSavedItemsMapper {

    fun toEntity(deletedItem: DeletedSavedItems): DeletedSavedItemsEntity {
        return DeletedSavedItemsEntity(
            itemId = deletedItem.itemId ?: 0,
            userUID = deletedItem.userUID
        )
    }

    fun toDomain(entity: DeletedSavedItemsEntity): DeletedSavedItems {
        return DeletedSavedItems(
            itemId = entity.itemId,
            userUID = entity.userUID
        )
    }
}
