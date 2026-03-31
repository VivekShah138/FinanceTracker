package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.UserProfile

interface HomePageRepository {
    suspend fun getUserProfile(): UserProfile?
}