package com.example.financetracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.auth_feature.domain.usecases.UseCasesWrapper
import com.example.financetracker.auth_feature.domain.usecases.ValidateConfirmPassword
import com.example.financetracker.auth_feature.domain.usecases.ValidateEmail
import com.example.financetracker.auth_feature.domain.usecases.ValidateName
import com.example.financetracker.auth_feature.domain.usecases.ValidatePassword
import com.example.financetracker.auth_feature.domain.usecases.ValidatePhoneNumber
import com.example.financetracker.core.data.local.data_source.UserPreferences
import com.example.financetracker.core.data.cloud.repository.FirebaseRepositoryImpl
import com.example.financetracker.core.domain.repository.FirebaseRepository
import com.example.financetracker.core.domain.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.domain.usecases.LogoutUseCase
import com.example.financetracker.core.domain.usecases.UseCasesWrapperCore
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(sharedPreferences: SharedPreferences): UserPreferences {
        return UserPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        userPreferences: UserPreferences
    ) : FirebaseRepository {
        return FirebaseRepositoryImpl(firebaseAuth,userPreferences)
    }


    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(firebaseRepository: FirebaseRepository): UseCasesWrapperCore {
        return UseCasesWrapperCore(
            logoutUseCase = LogoutUseCase(firebaseRepository),
            checkIsLoggedInUseCase = CheckIsLoggedInUseCase(firebaseRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository),
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository)
        )
    }

    // Auth UseCases
    @Provides
    @Singleton
    fun provideAuthUseCases(firebaseRepository: FirebaseRepository): UseCasesWrapper {
        return UseCasesWrapper(
            validateName = ValidateName(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateConfirmPassword = ValidateConfirmPassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            keepUserLoggedIn = KeepUserLoggedIn(firebaseRepository)
            )
    }

    // SetUpPage UseCases
    @Provides
    @Singleton
    fun provideSetupAccountUseCases(firebaseRepository: FirebaseRepository): UseCasesWrapperSetupAccount {
        return UseCasesWrapperSetupAccount(
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository)
        )
    }
}


