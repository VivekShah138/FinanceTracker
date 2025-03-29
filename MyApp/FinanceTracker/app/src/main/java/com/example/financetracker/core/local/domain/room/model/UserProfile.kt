package com.example.financetracker.core.local.domain.room.model

import com.example.financetracker.core.local.data.room.data_source.CategoryEntity
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileEntity
import com.example.financetracker.setup_account.domain.model.Currency

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val baseCurrency: Map<String,Currency> = emptyMap(),
    val country: String = "",
    val callingCode: String = "",
    val phoneNumber: String = "",
    val profileSetUpCompleted: Boolean = false
)

//fun UserProfileEntity.toDomain(): UserProfile {
//    return UserProfile( firstName =
//    )
//}
//
//fun Category.toEntity(): CategoryEntity {
//    return CategoryEntity(name = name,
//        type = type,
//        icon = icon
//    )
//}
