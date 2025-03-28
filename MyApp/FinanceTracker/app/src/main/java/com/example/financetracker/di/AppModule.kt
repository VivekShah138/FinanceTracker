package com.example.financetracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.auth_feature.domain.usecases.UseCasesWrapper
import com.example.financetracker.auth_feature.domain.usecases.ValidateConfirmPassword
import com.example.financetracker.auth_feature.domain.usecases.ValidateEmail
import com.example.financetracker.auth_feature.domain.usecases.ValidatePassword
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.cloud.data.repository.FirebaseRepositoryImpl
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.example.financetracker.core.local.domain.shared_preferences.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.domain.usecase.LogoutUseCase
import com.example.financetracker.core.cloud.domain.usecases.SaveUserProfileUseCase
import com.example.financetracker.core.domain.usecase.UseCasesWrapperCore
import com.example.financetracker.core.local.data.room.data_source.CategoryDao
import com.example.financetracker.core.local.data.room.data_source.CategoryDatabase
import com.example.financetracker.core.local.data.room.repository.CategoryRepositoryImpl
import com.example.financetracker.core.local.data.shared_preferences.repository.SharedPreferencesRepositoryImpl
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.usecases.GetPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.InsertPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
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
        firestore: FirebaseFirestore
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firestore = firestore)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(
        userPreferences: UserPreferences
    ): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(
            userPreferences = userPreferences
        )
    }


    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(
        firebaseRepository: FirebaseRepository,
        sharedPreferencesRepository: SharedPreferencesRepository): UseCasesWrapperCore {
        return UseCasesWrapperCore(
            logoutUseCase = LogoutUseCase(
                firebaseRepository = firebaseRepository,
                sharedPreferencesRepository = sharedPreferencesRepository
            ),
            checkIsLoggedInUseCase = CheckIsLoggedInUseCase(sharedPreferencesRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository),
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository),
            getUserProfileUseCase = GetUserProfileUseCase(firebaseRepository),
            saveUserProfileUseCase = SaveUserProfileUseCase(firebaseRepository)
        )
    }

    // Auth UseCases
    @Provides
    @Singleton
    fun provideAuthUseCases(sharedPreferencesRepository: SharedPreferencesRepository): UseCasesWrapper {
        return UseCasesWrapper(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateConfirmPassword = ValidateConfirmPassword(),
            keepUserLoggedIn = KeepUserLoggedIn(sharedPreferencesRepository)
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

    // CategoryDatabase
    @Provides
    @Singleton
    fun provideCategoryDatabase(app: Application): CategoryDatabase{
        return Room.databaseBuilder(
            context = app,
            klass = CategoryDatabase::class.java,
            name = CategoryDatabase.DATABASE_NAME
        ).build()
    }

    // Category Dao
    @Provides
    @Singleton
    fun provideCategoryRepository(db: CategoryDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }

    // CategoryUseCases
    @Provides
    @Singleton
    fun provideCategoryUseCase(categoryRepository: CategoryRepository): PredefinedCategoriesUseCaseWrapper {
        return PredefinedCategoriesUseCaseWrapper(
            getPredefinedCategories = GetPredefinedCategories(categoryRepository),
            insertPredefinedCategories = InsertPredefinedCategories(categoryRepository)
        )
    }
}


