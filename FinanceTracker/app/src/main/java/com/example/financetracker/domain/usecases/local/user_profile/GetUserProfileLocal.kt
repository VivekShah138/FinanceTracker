package com.example.financetracker.domain.usecases.local.user_profile

import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.local.HomePageRepository
import javax.inject.Inject

class GetUserProfileLocal @Inject constructor(
    private val homePageRepository: HomePageRepository
){
    suspend operator fun invoke(): UserProfile?{
        return homePageRepository.getUserProfile()
    }
}