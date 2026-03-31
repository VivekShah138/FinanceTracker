package com.example.financetracker.data.repository.local

import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.local.UserProfileRepository
import com.example.financetracker.domain.repository.local.HomePageRepository
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