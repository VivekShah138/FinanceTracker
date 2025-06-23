package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model

import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.DeletedSavedItemsEntity

data class DeletedSavedItems(
    val itemId: Int? = null,
    val userUID: String
)

fun DeletedSavedItems.toEntity(): DeletedSavedItemsEntity {
    return DeletedSavedItemsEntity(
        itemId = itemId ?: 0,
        userUID = this.userUID
    )
}

fun DeletedSavedItemsEntity.toDomain(): DeletedSavedItems {
    return DeletedSavedItems(
        userUID = this.userUID,
        itemId = this.itemId
    )
}