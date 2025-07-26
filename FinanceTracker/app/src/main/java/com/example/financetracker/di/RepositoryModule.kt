package com.example.financetracker.di

import android.content.Context
import androidx.work.WorkManager
import com.example.financetracker.data.local.data_source.room.modules.budget.BudgetDao
import com.example.financetracker.data.local.repository.BudgetLocalRepositoryImpl
import com.example.financetracker.data.remote.repository.BudgetRemoteRepositoryImpl
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository
import com.example.financetracker.core.cloud.data.repository.RemoteRepositoryImpl
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.data.local.data_source.room.modules.category.CategoryDatabase
import com.example.financetracker.data.local.data_source.room.modules.userprofile.UserProfileDatabase
import com.example.financetracker.data.local.repository.CategoryRepositoryImpl
import com.example.financetracker.data.local.repository.UserProfileRepositoryImpl
import com.example.financetracker.data.local.data_source.shared_pref.UserPreferences
import com.example.financetracker.data.local.repository.SharedPreferencesRepositoryImpl
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import com.example.financetracker.data.local.data_source.room.modules.transactions.DeletedTransactionDao
import com.example.financetracker.data.local.data_source.room.modules.transactions.TransactionDao
import com.example.financetracker.data.local.repository.TransactionsLocalRepositoryImpl
import com.example.financetracker.data.remote.repository.TransactionsRemoteRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.data.local.data_source.room.modules.saved_items.DeletedSavedItemsDao
import com.example.financetracker.data.local.data_source.room.modules.saved_items.SavedItemsDao
import com.example.financetracker.data.local.repository.SavedItemsLocalRepositoryImpl
import com.example.financetracker.data.remote.repository.SavedItemsRemoteRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository
import com.example.financetracker.data.local.repository.HomePageRepositoryImpl
import com.example.financetracker.main_page_feature.home_page.domain.repository.HomePageRepository
import com.example.financetracker.data.local.data_source.room.modules.country.CountryDatabase
import com.example.financetracker.data.local.data_source.room.modules.currency_rates.CurrencyRatesDao
import com.example.financetracker.data.local.repository.CountryLocalRepositoryImpl
import com.example.financetracker.data.local.repository.CurrencyRatesLocalRepositoryImpl
import com.example.financetracker.data.remote.data_source.CountryApi
import com.example.financetracker.data.remote.data_source.CurrencyRatesApi
import com.example.financetracker.data.remote.repository.CountryRemoteRepositoryImpl
import com.example.financetracker.data.remote.repository.CurrencyRatesRemoteRepositoryImpl
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository
import com.example.financetracker.setup_account.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.setup_account.domain.repository.remote.CurrencyRatesRemoteRepository
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
object RepositoryModule {

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

    // Category Repository
    @Provides
    @Singleton
    fun provideCategoryRepository(db: CategoryDatabase, workManager: WorkManager): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao = db.categoryDao, workManager = workManager)
    }

    // UserProfile Local Repository
    @Provides
    @Singleton
    fun provideUserProfileRepository(db: UserProfileDatabase, workManager: WorkManager): UserProfileRepository {
        return UserProfileRepositoryImpl(userProfileDao = db.userProfileDao,workManager = workManager)
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

    // Currency Rates Remote Repository
    @Provides
    @Singleton
    fun provideCurrencyRatesRemoteRepository(api: CurrencyRatesApi): CurrencyRatesRemoteRepository {
        return CurrencyRatesRemoteRepositoryImpl(api = api)
    }

    // Currency Rates Local Repository
    @Provides
    @Singleton
    fun provideCurrencyRatesLocalRepository(currencyRatesDao: CurrencyRatesDao, workManager: WorkManager, userPreferences: UserPreferences): CurrencyRatesLocalRepository {
        return CurrencyRatesLocalRepositoryImpl(
            currencyRatesDao =  currencyRatesDao,
            workManager = workManager,
            userPreferences = userPreferences
        )
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

    // Budget Remote Repository
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

    // HomePageRepository
    @Provides
    @Singleton
    fun provideHomePageRepository(userPreferences: UserPreferences, userProfileRepository: UserProfileRepository): HomePageRepository {
        return HomePageRepositoryImpl(userPreferences = userPreferences,userProfileRepository = userProfileRepository)
    }



}