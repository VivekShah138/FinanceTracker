package com.example.financetracker.main_page_feature.home_page.domain.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import javax.inject.Inject

class GetUserProfileLocal @Inject constructor(
    private val homePageRepository: HomePageRepository
){
    suspend operator fun invoke(): UserProfile?{
        return homePageRepository.getUserProfile()
    }
}