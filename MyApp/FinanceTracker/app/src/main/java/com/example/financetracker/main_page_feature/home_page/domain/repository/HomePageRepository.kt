package com.example.financetracker.main_page_feature.home_page.domain.repository

import com.example.financetracker.core.local.domain.room.model.UserProfile

interface HomePageRepository {
    suspend fun getUserProfile(): UserProfile?
}