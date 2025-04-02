package com.example.financetracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import com.example.financetracker.auth_feature.domain.usecases.InsertUIDLocally
import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.auth_feature.domain.usecases.UseCasesWrapper
import com.example.financetracker.auth_feature.domain.usecases.ValidateConfirmPassword
import com.example.financetracker.auth_feature.domain.usecases.ValidateEmail
import com.example.financetracker.auth_feature.domain.usecases.ValidatePassword
import com.example.financetracker.setup_account.data.remote.repository.CountryRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCountryLocally
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.cloud.data.repository.FirebaseRepositoryImpl
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.example.financetracker.core.local.domain.shared_preferences.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.core.cloud.domain.usecases.SaveUserProfileUseCase
import com.example.financetracker.core.core_domain.usecase.GetUIDLocally
import com.example.financetracker.core.core_domain.usecase.UseCasesWrapperCore
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDao
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDatabase
import com.example.financetracker.core.local.data.room.data_source.category.migration.CATEGORY_MIGRATION_1_2
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDatabase
import com.example.financetracker.core.local.data.room.data_source.userprofile.migration.USER_PROFILE_MIGRATION_1_2
import com.example.financetracker.core.local.data.room.repository.CategoryRepositoryImpl
import com.example.financetracker.core.local.data.room.repository.UserProfileRepositoryImpl
import com.example.financetracker.core.local.data.shared_preferences.repository.SharedPreferencesRepositoryImpl
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import com.example.financetracker.core.local.domain.room.usecases.GetPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.room.usecases.InsertPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated
import com.example.financetracker.main_page_feature.home_page.data.repository.HomePageRepositoryImpl
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDao
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDatabase
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDao
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDatabase
import com.example.financetracker.setup_account.data.local.repository.CountryLocalRepositoryImpl
import com.example.financetracker.setup_account.data.local.repository.CurrencyRatesLocalRepositoryImpl
import com.example.financetracker.setup_account.data.remote.ApiClient
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.data.remote.CurrencyRatesApi
import com.example.financetracker.setup_account.data.remote.repository.CurrencyRatesRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CurrencyRatesRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryDetailsUseCase
import com.example.financetracker.setup_account.domain.usecases.InsertCurrencyRatesLocalOneTime
import com.example.financetracker.setup_account.domain.usecases.InsertCurrencyRatesLocalPeriodically
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
import dagger.hilt.android.qualifiers.ApplicationContext
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

    // CategoryDatabase
    @Provides
    @Singleton
    fun provideCategoryDatabase(app: Application): CategoryDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = CategoryDatabase::class.java,
            name = CategoryDatabase.DATABASE_NAME
        ).addMigrations(CATEGORY_MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db:CategoryDatabase): CategoryDao{
        return db.categoryDao
    }

    //CategoryWorkManager
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }


    // Category Repository
    @Provides
    @Singleton
    fun provideCategoryRepository(db: CategoryDatabase, workManager: WorkManager): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao = db.categoryDao, workManager = workManager)
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



    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(firebaseRepository: FirebaseRepository,
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
            keepUserLoggedIn = KeepUserLoggedIn(sharedPreferencesRepository = sharedPreferencesRepository),
            insertUIDLocally = InsertUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository)
        )
    }

    // UserProfile Database
    @Provides
    @Singleton
    fun provideUserProfileDatabase(app: Application): UserProfileDatabase{
        return Room.databaseBuilder(
            context = app,
            klass = UserProfileDatabase::class.java,
            name = UserProfileDatabase.DATABASE_NAME
        ).addMigrations(USER_PROFILE_MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(db: UserProfileDatabase): UserProfileDao{
        return db.userProfileDao
    }

    // UserProfile Local Repository
    @Provides
    @Singleton
    fun provideUserProfileRepository(db: UserProfileDatabase): UserProfileRepository {
        return UserProfileRepositoryImpl(userProfileDao = db.userProfileDao)
    }

    // Country Database
    @Provides
    @Singleton
    fun provideCountryDatabase(app: Application): CountryDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = CountryDatabase::class.java,
            name = CountryDatabase.DATABASE_NAME
        ).build()
    }

    // Country Dao
    @Provides
    @Singleton
    fun provideCountryDao(db: CountryDatabase): CountryDao {
        return db.countryDao
    }

    // Country Local Repository
    @Provides
    @Singleton
    fun provideLocalCountryRepository(db: CountryDatabase, workManager: WorkManager): CountryLocalRepository {
        return CountryLocalRepositoryImpl(countryDao = db.countryDao,workManager)
    }

    // Country API
    @Provides
    @Singleton
    fun provideCounrtyApi(): CountryApi = ApiClient.instance

    // Country Remote Repository
    @Provides
    @Singleton
    fun provideCountryRepository(api: CountryApi): CountryRemoteRepository = CountryRemoteRepositoryImpl(api)

    // CurrencyRates Database
    @Provides
    @Singleton
    fun provideCurrencyRatesDatabase(app : Application) : CurrencyRatesDatabase{
        return Room.databaseBuilder(
            app,
            CurrencyRatesDatabase::class.java,
            CurrencyRatesDatabase.DATABASE_NAME
        ).build()
    }

    // CurrencyRates Dao
    @Provides
    @Singleton
    fun provideCurrencyRatesDao(db: CurrencyRatesDatabase): CurrencyRatesDao {
        return db.currencyRatesDao
    }

    // CurrencyRates API
    @Provides
    @Singleton
    fun provideCurrencyRatesApi(): CurrencyRatesApi = ApiClient.instance2

    // Currency Rates Remote Repository
    @Provides
    @Singleton
    fun provideCurrencyRatesRemoteRepository(api: CurrencyRatesApi): CurrencyRatesRemoteRepository{
        return CurrencyRatesRemoteRepositoryImpl(api = api)
    }

    // Currency Rates Local Repository
    @Provides
    @Singleton
    fun provideCurrencyRatesLocalRepository(currencyRatesDao: CurrencyRatesDao,workManager: WorkManager,userPreferences: UserPreferences): CurrencyRatesLocalRepository{
        return CurrencyRatesLocalRepositoryImpl(
            currencyRatesDao =  currencyRatesDao,
            workManager = workManager,
            userPreferences = userPreferences
        )
    }


    // SetUpPage UseCases
    @Provides
    @Singleton
    fun provideSetupAccountUseCases(firebaseRepository: FirebaseRepository,
                                    countryRepository: CountryRemoteRepository,
                                    countryLocalRepository: CountryLocalRepository,
                                    userProfileRepository: UserProfileRepository,
                                    sharedPreferencesRepository: SharedPreferencesRepository,
                                    currencyRatesLocalRepository: CurrencyRatesLocalRepository
    ): UseCasesWrapperSetupAccount {
        return UseCasesWrapperSetupAccount(
            getUserEmailUserCase = GetUserEmailUserCase(firebaseRepository),
            getUserUIDUseCase = GetUserUIDUseCase(firebaseRepository),
            getCountryDetailsUseCase = GetCountryDetailsUseCase(countryRepository),
            validateName = ValidateName(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateCountry = ValidateCountry(),
            updateUserProfile = UpdateUserProfile(firebaseRepository),
            getUserProfileUseCase = GetUserProfileUseCase(firebaseRepository),
            getCountryLocally = GetCountryLocally(countryLocalRepository),
            insertCountryLocally = InsertCountryLocally(countryLocalRepository),
            insertUserProfileToLocalDb = InsertUserProfileToLocalDb(userProfileRepository),
            getUserProfileFromLocalDb = GetUserProfileFromLocalDb(userProfileRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository),
            insertCurrencyRatesLocalOneTime = InsertCurrencyRatesLocalOneTime(currencyRatesLocalRepository),
            insertCurrencyRatesLocalPeriodically = InsertCurrencyRatesLocalPeriodically(currencyRatesLocalRepository),
            getCurrencyRatesUpdated = GetCurrencyRatesUpdated(sharedPreferencesRepository),
            setCurrencyRatesUpdated = SetCurrencyRatesUpdated(sharedPreferencesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideHomePageRepository(userPreferences: UserPreferences,userProfileRepository: UserProfileRepository): HomePageRepository{
        return HomePageRepositoryImpl(userPreferences = userPreferences,userProfileRepository = userProfileRepository)
    }

    // HomePageUseCases
    @Provides
    @Singleton
    fun provideHomePageUseCases(
        sharedPreferencesRepository: SharedPreferencesRepository,
        firebaseRepository: FirebaseRepository,
        homePageRepository: HomePageRepository
    ): HomePageUseCaseWrapper {
        return HomePageUseCaseWrapper(
            logoutUseCase = LogoutUseCase(
                sharedPreferencesRepository = sharedPreferencesRepository
                , firebaseRepository = firebaseRepository
            ),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository),
            setCurrencyRatesUpdated = SetCurrencyRatesUpdated(sharedPreferencesRepository),
            getCurrencyRatesUpdated = GetCurrencyRatesUpdated(sharedPreferencesRepository)
        )
    }
}


