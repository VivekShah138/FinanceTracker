package com.example.financetracker.di


import TRANSACTIONS_MIGRATION_1_2
import TRANSACTIONS_MIGRATION_2_3
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import com.example.financetracker.auth_feature.domain.usecases.InsertUIDLocally
import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.auth_feature.domain.usecases.AuthFeatureUseCasesWrapper
import com.example.financetracker.auth_feature.domain.usecases.ValidateConfirmPassword
import com.example.financetracker.auth_feature.domain.usecases.ValidateEmail
import com.example.financetracker.auth_feature.domain.usecases.ValidatePassword
import com.example.financetracker.setup_account.data.remote.repository.CountryRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCountryLocallyWorkManager
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.cloud.data.repository.RemoteRepositoryImpl
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.core.cloud.domain.usecase.DeleteTransactionCloud
import com.example.financetracker.core.local.domain.shared_preferences.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.cloud.domain.usecase.InternetConnectionAvailability
import com.example.financetracker.core.cloud.domain.usecase.SaveMultipleTransactionsCloud
import com.example.financetracker.core.cloud.domain.usecase.SaveSingleTransactionCloud
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.core.cloud.domain.usecase.SaveUserProfileUseCase
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.core.core_domain.usecase.CoreUseCasesWrapper
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDao
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDatabase
import com.example.financetracker.core.local.data.room.data_source.category.migration.CATEGORY_MIGRATION_1_2
import com.example.financetracker.core.local.data.room.data_source.category.migration.CATEGORY_MIGRATION_2_3
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDatabase
import com.example.financetracker.core.local.data.room.data_source.userprofile.migration.USER_PROFILE_MIGRATION_1_2
import com.example.financetracker.core.local.data.room.repository.CategoryRepositoryImpl
import com.example.financetracker.core.local.data.room.repository.UserProfileRepositoryImpl
import com.example.financetracker.core.local.data.shared_preferences.repository.SharedPreferencesRepositoryImpl
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import com.example.financetracker.core.local.domain.room.usecases.DeleteCustomCategories
import com.example.financetracker.core.local.domain.room.usecases.GetCustomCategories
import com.example.financetracker.core.local.domain.room.usecases.GetAllCategories
import com.example.financetracker.core.local.domain.room.usecases.GetPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.room.usecases.InsertCustomCategories
import com.example.financetracker.core.local.domain.room.usecases.InsertPredefinedCategories
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDatabase
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.repository.TransactionsLocalRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.repository.TransactionsRemoteRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertTransactionsLocally
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllLocalTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertCustomCategory
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertNewTransactionsReturnId
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateTransactionCategory
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateEmptyField
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateTransactionPrice
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.SavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.SavedItemsDatabase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.migration.SAVED_ITEM_MIGRATION_1_2
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.local.SavedItemsLocalRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllSavedItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsValidationUseCase
import com.example.financetracker.main_page_feature.home_page.data.repository.HomePageRepositoryImpl
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.main_page_feature.settings.domain.use_cases.SettingsUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteSelectedSavedItemsByIdsLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteSelectedTransactionsByIdsLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.InsertDeletedTransactionsLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDao
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDatabase
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDao
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDatabase
import com.example.financetracker.setup_account.data.local.repository.CountryLocalRepositoryImpl
import com.example.financetracker.setup_account.data.local.repository.CurrencyRatesLocalRepositoryImpl
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.data.remote.CurrencyRatesApi
import com.example.financetracker.setup_account.data.remote.repository.CurrencyRatesRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CurrencyRatesRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryDetailsUseCase
import com.example.financetracker.setup_account.domain.usecases.GetCurrencyRatesLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCountryLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCurrencyRatesLocalOneTime
import com.example.financetracker.setup_account.domain.usecases.InsertCurrencyRatesLocalPeriodically
import com.example.financetracker.setup_account.domain.usecases.UpdateUserProfile
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
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


    //CategoryWorkManager
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        @ApplicationContext context: Context,
        workManager: WorkManager
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firestore = firestore,
            context = context,
            workManager = workManager
            )
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
        ).addMigrations(
            CATEGORY_MIGRATION_1_2,
            CATEGORY_MIGRATION_2_3
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db:CategoryDatabase): CategoryDao{
        return db.categoryDao
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
            getAllCategories = GetAllCategories(categoryRepository),
            insertPredefinedCategories = InsertPredefinedCategories(categoryRepository),
            getCustomCategories = GetCustomCategories(categoryRepository),
            getPredefinedCategories = GetPredefinedCategories(categoryRepository),
            insertCustomCategories = InsertCustomCategories(categoryRepository),
            deleteCustomCategories = DeleteCustomCategories(categoryRepository)
        )
    }



    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(remoteRepository: RemoteRepository,
                            sharedPreferencesRepository: SharedPreferencesRepository): CoreUseCasesWrapper {
        return CoreUseCasesWrapper(
            logoutUseCase = LogoutUseCase(
                remoteRepository = remoteRepository,
                sharedPreferencesRepository = sharedPreferencesRepository
            ),
            checkIsLoggedInUseCase = CheckIsLoggedInUseCase(sharedPreferencesRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository),
            getUserEmailUserCase = GetUserEmailUserCase(remoteRepository),
            getUserProfileUseCase = GetUserProfileUseCase(remoteRepository),
            saveUserProfileUseCase = SaveUserProfileUseCase(remoteRepository)
        )
    }

    // Auth UseCases
    @Provides
    @Singleton
    fun provideAuthUseCases(sharedPreferencesRepository: SharedPreferencesRepository): AuthFeatureUseCasesWrapper {
        return AuthFeatureUseCasesWrapper(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateConfirmPassword = ValidateConfirmPassword(),
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

    //TransactionDatabase
    @Provides
    @Singleton
    fun provideTransactionDatabase(app : Application) : TransactionDatabase {
        return Room.databaseBuilder(
            app,
            TransactionDatabase::class.java,
            TransactionDatabase.DATABASE_NAME
        ).addMigrations(TRANSACTIONS_MIGRATION_1_2,TRANSACTIONS_MIGRATION_2_3).build()
    }

    // Transaction Dao
    @Provides
    @Singleton
    fun provideTransactionDao(db: TransactionDatabase): TransactionDao {
        return db.transactionDao
    }

    // Deleted Transaction Dao
    @Provides
    @Singleton
    fun provideDeletedTransactionDao(db: TransactionDatabase): DeletedTransactionDao {
        return db.deletedTransactionDao
    }

    // Transaction Local Repository
    @Provides
    @Singleton
    fun provideTransactionLocalRepository(transactionDao: TransactionDao): TransactionLocalRepository {
        return TransactionsLocalRepositoryImpl(transactionDao = transactionDao)
    }

    // Transaction Remote Repository
    @Provides
    @Singleton
    fun provideTransactionRemoteRepository(deletedTransactionDao: DeletedTransactionDao): TransactionRemoteRepository {
        return TransactionsRemoteRepositoryImpl(deletedTransactionDao = deletedTransactionDao)
    }

    // SavedItem Database
    @Provides
    @Singleton
    fun provideSavedItemDatabase(app : Application) : SavedItemsDatabase {
        return Room.databaseBuilder(
            app,
            SavedItemsDatabase::class.java,
            SavedItemsDatabase.DATABASE_NAME
        )
            .addMigrations(SAVED_ITEM_MIGRATION_1_2)
            .build()
    }

    // SavedItemDao
    @Provides
    @Singleton
    fun provideSavedItemDao(db: SavedItemsDatabase): SavedItemsDao {
        return db.savedItemsDao
    }

    // Saved Item Repository
    @Provides
    @Singleton
    fun provideSavedItemLocalRepository(savedItemsDao: SavedItemsDao): SavedItemsLocalRepository {
        return SavedItemsLocalRepositoryImpl(savedItemsDao)
    }

    // Saved Item UseCaseWrapper
    @Provides
    @Singleton
    fun provideSavedItemUseCases(
        savedItemsLocalRepository: SavedItemsLocalRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        homePageRepository: HomePageRepository
    ): SavedItemsUseCasesWrapper {
        return SavedItemsUseCasesWrapper(
            saveItemLocalUseCase = SaveItemLocalUseCase(savedItemsLocalRepository),
            getAllSavedItemLocalUseCase = GetAllSavedItemLocalUseCase(savedItemsLocalRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository),
            savedItemsValidationUseCase = SavedItemsValidationUseCase()

        )
    }


    // SetUpPage UseCases
    @Provides
    @Singleton
    fun provideSetupAccountUseCases(remoteRepository: RemoteRepository,
                                    countryRepository: CountryRemoteRepository,
                                    countryLocalRepository: CountryLocalRepository,
                                    userProfileRepository: UserProfileRepository,
                                    sharedPreferencesRepository: SharedPreferencesRepository,
                                    currencyRatesLocalRepository: CurrencyRatesLocalRepository
    ): SetupAccountUseCasesWrapper {
        return SetupAccountUseCasesWrapper(
            getUserEmailUserCase = GetUserEmailUserCase(remoteRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository),
            getCountryDetailsUseCase = GetCountryDetailsUseCase(countryRepository),
            validateName = ValidateName(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateCountry = ValidateCountry(),
            updateUserProfile = UpdateUserProfile(remoteRepository),
            getUserProfileUseCase = GetUserProfileUseCase(remoteRepository),
            getCountryLocally = GetCountryLocally(countryLocalRepository),
            insertCountryLocallyWorkManager = InsertCountryLocallyWorkManager(countryLocalRepository),
            insertUserProfileToLocalDb = InsertUserProfileToLocalDb(userProfileRepository),
            getUserProfileFromLocalDb = GetUserProfileFromLocalDb(userProfileRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository),
            insertCurrencyRatesLocalOneTime = InsertCurrencyRatesLocalOneTime(currencyRatesLocalRepository),
            insertCurrencyRatesLocalPeriodically = InsertCurrencyRatesLocalPeriodically(currencyRatesLocalRepository),
            getCurrencyRatesUpdated = GetCurrencyRatesUpdated(sharedPreferencesRepository),
            setCurrencyRatesUpdated = SetCurrencyRatesUpdated(sharedPreferencesRepository),
            getCurrencyRatesLocally = GetCurrencyRatesLocally(currencyRatesLocalRepository),
            insertCountryLocally = InsertCountryLocally(countryLocalRepository),
            keepUserLoggedIn = KeepUserLoggedIn(sharedPreferencesRepository)
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
        remoteRepository: RemoteRepository,
        homePageRepository: HomePageRepository
    ): HomePageUseCaseWrapper {
        return HomePageUseCaseWrapper(
            logoutUseCase = LogoutUseCase(
                sharedPreferencesRepository = sharedPreferencesRepository
                , remoteRepository = remoteRepository
            ),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository),
            setCurrencyRatesUpdated = SetCurrencyRatesUpdated(sharedPreferencesRepository),
            getCurrencyRatesUpdated = GetCurrencyRatesUpdated(sharedPreferencesRepository)
        )
    }

    // AddExpensePageUseCases
    @Provides
    @Singleton
    fun provideAddExpenseUseCases(
        currencyRatesLocalRepository: CurrencyRatesLocalRepository,
        categoryRepository: CategoryRepository,
        transactionLocalRepository: TransactionLocalRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository
    ): AddTransactionUseCasesWrapper {
        return AddTransactionUseCasesWrapper(
            getCurrencyRatesLocally = GetCurrencyRatesLocally(currencyRatesLocalRepository),
            insertCustomCategory = InsertCustomCategory(categoryRepository),
            validateTransactionPrice = ValidateTransactionPrice(),
            validateEmptyField = ValidateEmptyField(),
            validateTransactionCategory = ValidateTransactionCategory(),
            insertTransactionsLocally = InsertTransactionsLocally(transactionLocalRepository),
            insertNewTransactionsReturnId = InsertNewTransactionsReturnId(transactionLocalRepository = transactionLocalRepository),
            getCloudSyncLocally = GetCloudSyncLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            saveSingleTransactionCloud = SaveSingleTransactionCloud(remoteRepository = remoteRepository,transactionLocalRepository = transactionLocalRepository),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository = remoteRepository),
            getAllLocalTransactions = GetAllLocalTransactions(transactionLocalRepository = transactionLocalRepository)
        )
    }

    // ViewTransactionsUseCases
    @Provides
    @Singleton
    fun provideViewTransactionsUsesCases(
        transactionLocalRepository: TransactionLocalRepository,
        transactionRemoteRepository: TransactionRemoteRepository,
        savedItemsLocalRepository: SavedItemsLocalRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository
    ): ViewRecordsUseCaseWrapper {
        return ViewRecordsUseCaseWrapper(
            getAllTransactions = GetAllTransactions(transactionLocalRepository = transactionLocalRepository),
            getAllSavedItemLocalUseCase = GetAllSavedItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            deleteSelectedTransactionsByIdsLocally = DeleteSelectedTransactionsByIdsLocally(transactionLocalRepository = transactionLocalRepository),
            deleteSelectedSavedItemsByIdsLocally = DeleteSelectedSavedItemsByIdsLocally(savedItemsLocalRepository = savedItemsLocalRepository),
            getCloudSyncLocally = GetCloudSyncLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository = remoteRepository),
            insertDeletedTransactionsLocally = InsertDeletedTransactionsLocally(transactionRemoteRepository = transactionRemoteRepository),
            deleteTransactionCloud = DeleteTransactionCloud(remoteRepository = remoteRepository)
        )
    }

    // Setting UseCases
    @Provides
    @Singleton
    fun provideSettingsUsesCases(
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository
    ): SettingsUseCaseWrapper {
        return SettingsUseCaseWrapper(
            getCloudSyncLocally = GetCloudSyncLocally(sharedPreferencesRepository),
            setCloudSyncLocally = SetCloudSyncLocally(sharedPreferencesRepository),
            saveMultipleTransactionsCloud = SaveMultipleTransactionsCloud(remoteRepository)
        )
    }
}


