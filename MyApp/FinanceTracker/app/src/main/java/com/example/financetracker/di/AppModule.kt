package com.example.financetracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.auth_feature.domain.usecases.UseCasesWrapper
import com.example.financetracker.auth_feature.domain.usecases.ValidateConfirmPassword
import com.example.financetracker.auth_feature.domain.usecases.ValidateEmail
import com.example.financetracker.auth_feature.domain.usecases.ValidatePassword
import com.example.financetracker.core.data.local.data_source.UserPreferences
import com.example.financetracker.core.data.cloud.repository.FirebaseRepositoryImpl
import com.example.financetracker.core.domain.repository.FirebaseRepository
import com.example.financetracker.core.domain.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.domain.usecases.LogoutUseCase
import com.example.financetracker.core.domain.usecases.SaveUserProfileUseCase
import com.example.financetracker.core.domain.usecases.UseCasesWrapperCore
import com.example.financetracker.setup_account.data.remote.ApiClient
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.data.repository.CountryRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.CountryRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryDetailsUseCase
import com.example.financetracker.setup_account.domain.usecases.UpdateUserProfile
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import com.example.financetracker.setup_account.domain.usecases.ValidateCountry
import com.example.financetracker.setup_account.domain.usecases.ValidateName
import com.example.financetracker.setup_account.domain.usecases.ValidatePhoneNumber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        userPreferences: UserPreferences,
        firestore: FirebaseFirestore
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            firebaseAuth = firebaseAuth,
            userPreferences = userPreferences,
            firestore = firestore)
    }


    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(firebaseRepository: FirebaseRepository): UseCasesWrapperCore {
        return UseCasesWrapperCore(
            logoutUseCase = LogoutUseCase(firebaseRepository),
            checkIsLoggedInUseCase = CheckIsLoggedInUseCase(firebaseRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository),
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository),
            getUserProfileUseCase = GetUserProfileUseCase(firebaseRepository),
            saveUserProfileUseCase = SaveUserProfileUseCase(firebaseRepository)
        )
    }

    // Auth UseCases
    @Provides
    @Singleton
    fun provideAuthUseCases(firebaseRepository: FirebaseRepository): UseCasesWrapper {
        return UseCasesWrapper(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateConfirmPassword = ValidateConfirmPassword(),
            keepUserLoggedIn = KeepUserLoggedIn(firebaseRepository)
        )
    }

    @Provides
    @Singleton
    fun provideApi(): CountryApi = ApiClient.instance

    @Provides
    @Singleton
    fun provideCountryRepository(api: CountryApi): CountryRepository = CountryRepositoryImpl(api)

    // SetUpPage UseCases
    @Provides
    @Singleton
    fun provideSetupAccountUseCases(firebaseRepository: FirebaseRepository,
                                    countryRepository: CountryRepository
    ): UseCasesWrapperSetupAccount {
        return UseCasesWrapperSetupAccount(
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository),
            getCountryDetailsUseCase = GetCountryDetailsUseCase(countryRepository),
            validateName = ValidateName(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateCountry = ValidateCountry(),
            updateUserProfile = UpdateUserProfile(firebaseRepository),
            getUserProfileUseCase = GetUserProfileUseCase(firebaseRepository)
        )
    }
}


