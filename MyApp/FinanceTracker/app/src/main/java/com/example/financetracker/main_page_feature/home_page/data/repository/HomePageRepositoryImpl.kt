package com.example.financetracker.main_page_feature.home_page.data.repository

import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomePageRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val userProfileRepository: UserProfileRepository
): HomePageRepository {

    override suspend fun getUserProfile(): UserProfile? {
        return withContext(Dispatchers.IO) {
            userProfileRepository.getUserProfile(userPreferences.getUserIdLocally() ?: "Null")
        }
    }
}