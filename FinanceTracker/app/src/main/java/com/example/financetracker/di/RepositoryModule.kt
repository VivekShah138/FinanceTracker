package com.example.financetracker.di

import android.content.Context
import androidx.work.WorkManager
import com.example.financetracker.data.data_source.local.room.modules.budget.BudgetDao
import com.example.financetracker.data.data_source.local.room.modules.category.CategoryDatabase
import com.example.financetracker.data.data_source.local.room.modules.country.CountryDatabase
import com.example.financetracker.data.data_source.local.room.modules.currency_rates.CurrencyRatesDao
import com.example.financetracker.data.data_source.local.room.modules.saved_items.DeletedSavedItemsDao
import com.example.financetracker.data.data_source.local.room.modules.saved_items.SavedItemsDao
import com.example.financetracker.data.data_source.local.room.modules.transactions.DeletedTransactionDao
import com.example.financetracker.data.data_source.local.room.modules.transactions.TransactionDao
import com.example.financetracker.data.data_source.local.room.modules.userprofile.UserProfileDatabase
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.data.data_source.remote.CountryApi
import com.example.financetracker.data.data_source.remote.CurrencyRatesApi
import com.example.financetracker.data.repository.local.CategoryRepositoryImpl
import com.example.financetracker.data.repository.local.CountryLocalRepositoryImpl
import com.example.financetracker.data.repository.local.CurrencyRatesLocalRepositoryImpl
import com.example.financetracker.data.repository.local.SavedItemsLocalRepositoryImpl
import com.example.financetracker.data.repository.local.SharedPreferencesRepositoryImpl
import com.example.financetracker.data.repository.local.TransactionsLocalRepositoryImpl
import com.example.financetracker.data.repository.local.UserProfileRepositoryImpl
import com.example.financetracker.data.repository.remote.BudgetRemoteRepositoryImpl
import com.example.financetracker.data.repository.remote.CountryRemoteRepositoryImpl
import com.example.financetracker.data.repository.remote.CurrencyRatesRemoteRepositoryImpl
import com.example.financetracker.domain.repository.local.BudgetLocalRepository
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository
import com.example.financetracker.data.repository.remote.RemoteRepositoryImpl
import com.example.financetracker.data.repository.remote.SavedItemsRemoteRepositoryImpl
import com.example.financetracker.data.repository.remote.TransactionsRemoteRepositoryImpl
import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.repository.local.CategoryRepository
import com.example.financetracker.domain.repository.local.UserProfileRepository
import com.example.financetracker.domain.repository.local.SharedPreferencesRepository
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository
import com.example.financetracker.domain.repository.local.HomePageRepository
import com.example.financetracker.domain.repository.local.CountryLocalRepository
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository
import com.example.financetracker.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.domain.repository.remote.CurrencyRatesRemoteRepository
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
        return com.example.financetracker.data.repository.local.BudgetLocalRepositoryImpl(
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
        return com.example.financetracker.data.repository.local.HomePageRepositoryImpl(
            userPreferences = userPreferences,
            userProfileRepository = userProfileRepository
        )
    }



}