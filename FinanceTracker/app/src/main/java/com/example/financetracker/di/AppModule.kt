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
import com.example.financetracker.budget_feature.data.data_source.BUDGET_MIGRATION_1_2
import com.example.financetracker.budget_feature.data.data_source.BUDGET_MIGRATION_2_3
import com.example.financetracker.budget_feature.data.data_source.BudgetDao
import com.example.financetracker.budget_feature.data.data_source.BudgetDatabase
import com.example.financetracker.budget_feature.data.repository.BudgetLocalRepositoryImpl
import com.example.financetracker.budget_feature.data.repository.BudgetRemoteRepositoryImpl
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.budget_feature.domain.usecases.DoesBudgetExits
import com.example.financetracker.budget_feature.domain.usecases.GetAllUnSyncedBudgetLocalUseCase
import com.example.financetracker.budget_feature.domain.usecases.GetBudgetLocalUseCase
import com.example.financetracker.budget_feature.domain.usecases.GetRemoteBudgetsList
import com.example.financetracker.budget_feature.domain.usecases.InsertBudgetLocalUseCase
import com.example.financetracker.budget_feature.domain.usecases.InsertRemoteBudgetsToLocal
import com.example.financetracker.budget_feature.domain.usecases.SaveBudgetToCloudUseCase
import com.example.financetracker.budget_feature.domain.usecases.SaveMultipleBudgetsToCloudUseCase
import com.example.financetracker.budget_feature.domain.usecases.SendBudgetNotificationUseCase
import com.example.financetracker.setup_account.data.remote.repository.CountryRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCountryLocallyWorkManager
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.cloud.data.repository.RemoteRepositoryImpl
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.core.cloud.domain.usecase.DeleteTransactionCloud
import com.example.financetracker.core.cloud.domain.usecase.GetRemoteTransactionsList
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
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetDarkModeLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetFirstTimeInstalled
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetFirstTimeLogin
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUserNameLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCloudSyncLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetDarkModeLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetFirstTimeInstalled
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetFirstTimeLogin
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetUserNameLocally
import com.example.financetracker.main_page_feature.charts.domain.usecases.ChartsUseCaseWrapper
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
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.DeleteDeletedTransactionsByIdsFromLocal
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.DoesTransactionExits
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllDeletedTransactionByUserId
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllLocalTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllLocalTransactionsById
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertCustomCategory
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertNewTransactionsReturnId
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.InsertRemoteTransactionsToLocal
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateTransactionCategory
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateEmptyField
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.ValidateTransactionPrice
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.DeletedSavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsDatabase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.migration.SAVED_ITEM_MIGRATION_1_2
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.migration.SAVED_ITEM_MIGRATION_2_3
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.local.SavedItemsLocalRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.remote.SavedItemsRemoteRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllSavedItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveItemLocalUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsValidationUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.DeleteDeletedSavedItemsById
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.DoesItemExistsUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllDeletedSavedItemsByUserId
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetAllNotSyncedSavedItemUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.GetSavedItemById
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.SaveNewItemReturnId
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.DeleteMultipleSavedItemCloud
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.GetRemoteSavedItemList
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.InsertRemoteSavedItemToLocal
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveMultipleSavedItemCloud
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.SaveSingleSavedItemCloud
import com.example.financetracker.main_page_feature.home_page.data.repository.HomePageRepositoryImpl
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import com.example.financetracker.main_page_feature.home_page.domain.usecases.GetUserProfileLocal
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.main_page_feature.settings.domain.use_cases.SettingsUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteMultipleTransactionsFromCloud
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteSavedItemCloud
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteSelectedSavedItemsByIdsLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteSelectedTransactionsByIdsLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.GetAllTransactionsFilters
import com.example.financetracker.main_page_feature.view_records.use_cases.InsertDeletedSavedItemLocally
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
            deleteCustomCategories = DeleteCustomCategories(categoryRepository),

        )
    }



    // Core UseCases
    @Provides
    @Singleton
    fun provideCoreUseCases(
        remoteRepository: RemoteRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        userProfileRepository: UserProfileRepository
    ): CoreUseCasesWrapper {
        return CoreUseCasesWrapper(
            logoutUseCase = LogoutUseCase(
                remoteRepository = remoteRepository,
                sharedPreferencesRepository = sharedPreferencesRepository
            ),
            checkIsLoggedInUseCase = CheckIsLoggedInUseCase(sharedPreferencesRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository),
            getUserEmailUserCase = GetUserEmailUserCase(remoteRepository),
            getUserProfileUseCase = GetUserProfileUseCase(remoteRepository),
            saveUserProfileUseCase = SaveUserProfileUseCase(remoteRepository),
            setUserNameLocally = SetUserNameLocally(sharedPreferencesRepository),
            insertUserProfileToLocalDb = InsertUserProfileToLocalDb(userProfileRepository)
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
            insertUIDLocally = InsertUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository)
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
    fun provideUserProfileRepository(db: UserProfileDatabase,workManager: WorkManager): UserProfileRepository {
        return UserProfileRepositoryImpl(userProfileDao = db.userProfileDao,workManager = workManager)
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
    fun provideTransactionRemoteRepository(
        deletedTransactionDao: DeletedTransactionDao,
        @ApplicationContext context: Context
    ): TransactionRemoteRepository {
        return TransactionsRemoteRepositoryImpl(
            deletedTransactionDao = deletedTransactionDao,
            context = context
        )
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
            .addMigrations(SAVED_ITEM_MIGRATION_1_2, SAVED_ITEM_MIGRATION_2_3)
            .build()
    }

    // SavedItemDao
    @Provides
    @Singleton
    fun provideSavedItemDao(db: SavedItemsDatabase): SavedItemsDao {
        return db.savedItemsDao
    }

    // DeletedSavedItemDao
    @Provides
    @Singleton
    fun provideDeletedSavedItemDao(db: SavedItemsDatabase): DeletedSavedItemsDao {
        return db.deletedSavedItemsDao
    }

    // Saved Item Local Repository
    @Provides
    @Singleton
    fun provideSavedItemLocalRepository(
        savedItemsDao: SavedItemsDao,
        deletedSavedItemsDao: DeletedSavedItemsDao
    ): SavedItemsLocalRepository {
        return SavedItemsLocalRepositoryImpl(
            savedItemsDao = savedItemsDao,
            deletedSavedItemsDao = deletedSavedItemsDao
        )
    }

    // Saved Item Remote Repository
    @Provides
    @Singleton
    fun provideSavedItemRemoteRepository(
        firestore: FirebaseFirestore,
        deletedSavedItemsDao: DeletedSavedItemsDao,
        @ApplicationContext context: Context
    ): SavedItemsRemoteRepository {
        return SavedItemsRemoteRepositoryImpl(
            deletedSavedItemsDao = deletedSavedItemsDao,
            firestore = firestore,
            context = context
            
        )
    }

    // Saved Item UseCaseWrapper
    @Provides
    @Singleton
    fun provideSavedItemUseCases(
        savedItemsLocalRepository: SavedItemsLocalRepository,
        savedItemsRemoteRepository: SavedItemsRemoteRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        homePageRepository: HomePageRepository,
        remoteRepository: RemoteRepository
    ): SavedItemsUseCasesWrapper {
        return SavedItemsUseCasesWrapper(
            saveItemLocalUseCase = SaveItemLocalUseCase(savedItemsLocalRepository),
            getAllSavedItemLocalUseCase = GetAllSavedItemLocalUseCase(savedItemsLocalRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository),
            savedItemsValidationUseCase = SavedItemsValidationUseCase(),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository),
            saveSingleSavedItemCloud = SaveSingleSavedItemCloud(
                savedItemsRemoteRepository = savedItemsRemoteRepository,
                savedItemsLocalRepository = savedItemsLocalRepository
            ),
            saveNewItemReturnId = SaveNewItemReturnId(savedItemsLocalRepository = savedItemsLocalRepository),
            getCloudSyncLocally = GetCloudSyncLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getAllNotSyncedSavedItemUseCase = GetAllNotSyncedSavedItemUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getRemoteSavedItemList = GetRemoteSavedItemList(savedItemsRemoteRepository = savedItemsRemoteRepository),
            doesItemExistsUseCase = DoesItemExistsUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository = remoteRepository),
            insertRemoteSavedItemToLocal = InsertRemoteSavedItemToLocal(savedItemsRemoteRepository = savedItemsRemoteRepository)
        )
    }

    // Budget Database
    @Provides
    @Singleton
    fun provideBudgetDatabase(app : Application) : BudgetDatabase {
        return Room.databaseBuilder(
            app,
            BudgetDatabase::class.java,
            BudgetDatabase.DATABASE_NAME
        )
            .addMigrations(BUDGET_MIGRATION_1_2, BUDGET_MIGRATION_2_3)
            .build()
    }

    // BudgetDao
    @Provides
    @Singleton
    fun provideBudgetDao(db: BudgetDatabase): BudgetDao {
        return db.budgetDao
    }


    // Budget Local Repository
    @Provides
    @Singleton
    fun provideBudgetLocalRepository(
        budgetDao: BudgetDao,
        @ApplicationContext context: Context
    ): BudgetLocalRepository {
        return BudgetLocalRepositoryImpl(
            budgetDao = budgetDao,
            context = context
        )
    }

    // Budget Local Repository
    @Provides
    @Singleton
    fun provideBudgetRemoteRepository(
        firestore: FirebaseFirestore,
        @ApplicationContext context: Context
    ): BudgetRemoteRepository {
        return BudgetRemoteRepositoryImpl(
            firestore = firestore,
            context = context
        )
    }


    @Provides
    @Singleton
    fun provideBudgetUseCases(
        budgetLocalRepository: BudgetLocalRepository,
        budgetRemoteRepository: BudgetRemoteRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        userProfileRepository: UserProfileRepository,
        remoteRepository: RemoteRepository
    ): BudgetUseCaseWrapper{
        return BudgetUseCaseWrapper(
            getBudgetLocalUseCase = GetBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            insertBudgetLocalUseCase = InsertBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getUserProfileFromLocalDb = GetUserProfileFromLocalDb(userProfileRepository = userProfileRepository),
            getAllUnSyncedBudgetLocalUseCase = GetAllUnSyncedBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            saveBudgetToCloudUseCase = SaveBudgetToCloudUseCase(budgetRemoteRepository = budgetRemoteRepository),
            saveMultipleBudgetsToCloudUseCase = SaveMultipleBudgetsToCloudUseCase(budgetRemoteRepository = budgetRemoteRepository),
            doesBudgetExits = DoesBudgetExits(budgetLocalRepository = budgetLocalRepository),
            insertRemoteBudgetsToLocal = InsertRemoteBudgetsToLocal(budgetRemoteRepository = budgetRemoteRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository = remoteRepository),
            getRemoteBudgetsList = GetRemoteBudgetsList(budgetRemoteRepository = budgetRemoteRepository)
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
                                    currencyRatesLocalRepository: CurrencyRatesLocalRepository,
                                    savedItemsRemoteRepository: SavedItemsRemoteRepository,
                                    savedItemsLocalRepository: SavedItemsLocalRepository,
                                    transactionLocalRepository: TransactionLocalRepository
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
            keepUserLoggedIn = KeepUserLoggedIn(sharedPreferencesRepository),
            getRemoteTransactionsList = GetRemoteTransactionsList(remoteRepository = remoteRepository),
            getRemoteSavedItemList = GetRemoteSavedItemList(savedItemsRemoteRepository = savedItemsRemoteRepository),
            doesItemExistsUseCase = DoesItemExistsUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            doesTransactionExits = DoesTransactionExits(transactionLocalRepository = transactionLocalRepository),
            getFirstTimeInstalled = GetFirstTimeInstalled(sharedPreferencesRepository),
            setFirstTimeInstalled = SetFirstTimeInstalled(sharedPreferencesRepository),
            getFirstTimeLoggedIn = GetFirstTimeLogin(sharedPreferencesRepository),
            setFirstTimeLogin = SetFirstTimeLogin(sharedPreferencesRepository)
        )
    }

    // HomePageRepository
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
        homePageRepository: HomePageRepository,
        transactionLocalRepository: TransactionLocalRepository,
        categoryRepository: CategoryRepository,
        budgetLocalRepository: BudgetLocalRepository,
        savedItemsRemoteRepository: SavedItemsRemoteRepository
    ): HomePageUseCaseWrapper {
        return HomePageUseCaseWrapper(
            logoutUseCase = LogoutUseCase(
                sharedPreferencesRepository = sharedPreferencesRepository
                , remoteRepository = remoteRepository
            ),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository),
            setCurrencyRatesUpdated = SetCurrencyRatesUpdated(sharedPreferencesRepository),
            getCurrencyRatesUpdated = GetCurrencyRatesUpdated(sharedPreferencesRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository),
            getAllTransactions = GetAllTransactions(transactionLocalRepository),
            getAllCategories = GetAllCategories(categoryRepository = categoryRepository),
            getAllTransactionsFilters = GetAllTransactionsFilters(transactionLocalRepository = transactionLocalRepository),
            getBudgetLocalUseCase = GetBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            sendBudgetNotificationUseCase = SendBudgetNotificationUseCase(budgetLocalRepository = budgetLocalRepository),
            insertRemoteSavedItemToLocal = InsertRemoteSavedItemToLocal(savedItemsRemoteRepository = savedItemsRemoteRepository)
        )
    }

    // AddExpensePageUseCases
    @Provides
    @Singleton
    fun provideAddTransactionsUseCases(
        currencyRatesLocalRepository: CurrencyRatesLocalRepository,
        categoryRepository: CategoryRepository,
        transactionLocalRepository: TransactionLocalRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository,
        budgetLocalRepository: BudgetLocalRepository
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
            getAllLocalTransactions = GetAllLocalTransactions(transactionLocalRepository = transactionLocalRepository),
            sendBudgetNotificationUseCase = SendBudgetNotificationUseCase(budgetLocalRepository = budgetLocalRepository),
            getUserUIDUseCase = GetUserUIDUseCase(remoteRepository = remoteRepository),
            getRemoteTransactionsList = com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetRemoteTransactionsList(remoteRepository = remoteRepository),
            insertRemoteTransactionsToLocal = InsertRemoteTransactionsToLocal(remoteRepository = remoteRepository),
            doesTransactionExits = DoesTransactionExits(transactionLocalRepository = transactionLocalRepository)
        )
    }

    // ViewTransactionsUseCases
    @Provides
    @Singleton
    fun provideViewTransactionsUsesCases(
        transactionLocalRepository: TransactionLocalRepository,
        transactionRemoteRepository: TransactionRemoteRepository,
        savedItemsLocalRepository: SavedItemsLocalRepository,
        savedItemsRemoteRepository: SavedItemsRemoteRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository,
        categoryRepository: CategoryRepository,
        homePageRepository: HomePageRepository
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
            deleteTransactionCloud = DeleteTransactionCloud(remoteRepository = remoteRepository),
            getAllDeletedTransactionByUserId = GetAllDeletedTransactionByUserId(transactionRemoteRepository = transactionRemoteRepository),
            deleteDeletedTransactionsByIdsFromLocal = DeleteDeletedTransactionsByIdsFromLocal(transactionRemoteRepository = transactionRemoteRepository),
            deleteMultipleTransactionsFromCloud = DeleteMultipleTransactionsFromCloud(transactionRemoteRepository = transactionRemoteRepository),
            getAllLocalTransactionsById = GetAllLocalTransactionsById(transactionLocalRepository = transactionLocalRepository),
            deleteDeletedSavedItemsById = DeleteDeletedSavedItemsById(savedItemsLocalRepository = savedItemsLocalRepository),
            deleteSavedItemCloud = DeleteSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            getAllDeletedSavedItemsByUserId = GetAllDeletedSavedItemsByUserId(savedItemsLocalRepository = savedItemsLocalRepository),
            deleteMultipleSavedItemCloud = DeleteMultipleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            insertDeletedSavedItemLocally = InsertDeletedSavedItemLocally(savedItemsLocalRepository = savedItemsLocalRepository),
            getSavedItemById = GetSavedItemById(savedItemsLocalRepository = savedItemsLocalRepository),
            savedItemsValidationUseCase = SavedItemsValidationUseCase(),
            saveSingleSavedItemCloud = SaveSingleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository,savedItemsLocalRepository = savedItemsLocalRepository),
            saveItemLocalUseCase = SaveItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getAllCategories = GetAllCategories(categoryRepository = categoryRepository),
            getAllTransactionsFilters = GetAllTransactionsFilters(transactionLocalRepository = transactionLocalRepository),
            getUserProfileLocal = GetUserProfileLocal(homePageRepository = homePageRepository)

        )
    }

    // Setting UseCases
    @Provides
    @Singleton
    fun provideSettingsUsesCases(
        sharedPreferencesRepository: SharedPreferencesRepository,
        remoteRepository: RemoteRepository,
        savedItemsRemoteRepository: SavedItemsRemoteRepository,
        userProfileRepository: UserProfileRepository,
        budgetLocalRepository: BudgetLocalRepository
    ): SettingsUseCaseWrapper {
        return SettingsUseCaseWrapper(
            getCloudSyncLocally = GetCloudSyncLocally(sharedPreferencesRepository),
            setCloudSyncLocally = SetCloudSyncLocally(sharedPreferencesRepository),
            saveMultipleTransactionsCloud = SaveMultipleTransactionsCloud(remoteRepository),
            saveMultipleSavedItemCloud = SaveMultipleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            setDarkModeLocally = SetDarkModeLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getUserProfileFromLocalDb = GetUserProfileFromLocalDb(userProfileRepository),
            getDarkModeLocally = GetDarkModeLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            logoutUseCase = LogoutUseCase(remoteRepository = remoteRepository,sharedPreferencesRepository = sharedPreferencesRepository),
            getUserNameLocally = GetUserNameLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            setUserNameLocally = SetUserNameLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getRemoteTransactionsList = GetRemoteTransactionsList(remoteRepository = remoteRepository),
            getBudgetLocalUseCase = GetBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository)
        )
    }


    // Charts UseCases
    @Provides
    @Singleton
    fun provideChartsUseCases(
        transactionLocalRepository: TransactionLocalRepository,
        sharedPreferencesRepository: SharedPreferencesRepository,
        categoryRepository: CategoryRepository,
        userProfileRepository: UserProfileRepository
    ): ChartsUseCaseWrapper {
        return ChartsUseCaseWrapper(
            getAllCategories = GetAllCategories(categoryRepository = categoryRepository),
            getUIDLocally = GetUIDLocally(sharedPreferencesRepository = sharedPreferencesRepository),
            getAllTransactionsFilters = GetAllTransactionsFilters(transactionLocalRepository = transactionLocalRepository),
            getUserProfileFromLocalDb = GetUserProfileFromLocalDb(userProfileRepository = userProfileRepository)
        )
    }
}


