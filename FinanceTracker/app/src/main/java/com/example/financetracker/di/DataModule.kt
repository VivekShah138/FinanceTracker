package com.example.financetracker.di

import TRANSACTIONS_MIGRATION_1_2
import TRANSACTIONS_MIGRATION_2_3
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import com.example.financetracker.budget_feature.data.data_source.BUDGET_MIGRATION_1_2
import com.example.financetracker.budget_feature.data.data_source.BUDGET_MIGRATION_2_3
import com.example.financetracker.budget_feature.data.data_source.BudgetDao
import com.example.financetracker.budget_feature.data.data_source.BudgetDatabase
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDao
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDatabase
import com.example.financetracker.core.local.data.room.data_source.category.migration.CATEGORY_MIGRATION_1_2
import com.example.financetracker.core.local.data.room.data_source.category.migration.CATEGORY_MIGRATION_2_3
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDatabase
import com.example.financetracker.core.local.data.room.data_source.userprofile.migration.USER_PROFILE_MIGRATION_1_2
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDatabase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.DeletedSavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsDatabase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.migration.SAVED_ITEM_MIGRATION_1_2
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.migration.SAVED_ITEM_MIGRATION_2_3
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDao
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDatabase
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDao
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDatabase
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
object DataModule {

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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    //CategoryWorkManager
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
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
    fun provideCategoryDao(db: CategoryDatabase): CategoryDao {
        return db.categoryDao
    }

    // UserProfile Database
    @Provides
    @Singleton
    fun provideUserProfileDatabase(app: Application): UserProfileDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = UserProfileDatabase::class.java,
            name = UserProfileDatabase.DATABASE_NAME
        ).addMigrations(USER_PROFILE_MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(db: UserProfileDatabase): UserProfileDao {
        return db.userProfileDao
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

    // CurrencyRates Database
    @Provides
    @Singleton
    fun provideCurrencyRatesDatabase(app : Application) : CurrencyRatesDatabase {
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
}