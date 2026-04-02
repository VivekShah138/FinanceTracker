package com.example.financetracker.di

import com.example.financetracker.domain.usecases.local.shared_pref.InsertUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.KeepUserLoggedInLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.AuthFeatureUseCasesWrapper
import com.example.financetracker.domain.usecases.local.validation.ConfirmPasswordValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.EmailValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.PasswordValidationUseCase
import com.example.financetracker.domain.repository.local.BudgetLocalRepository
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import com.example.financetracker.domain.usecases.local.budget.DoesBudgetExitsLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetAllUnSyncedBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.GetBudgetsRemoteUseCase
import com.example.financetracker.domain.usecases.local.budget.InsertBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetsRemoteToLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetRemoteUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertBudgetsRemoteUseCase
import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationLocalUseCase
import com.example.financetracker.domain.repository.local.CountryLocalRepository
import com.example.financetracker.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.domain.usecases.local.country.GetCountryLocalUseCase
import com.example.financetracker.domain.usecases.local.country.SeedCountryLocalUseCase
import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.usecases.remote.transactions.DeleteTransactionRemoteUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.CheckIsLoggedInLocalUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailRemoteUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.remote.transactions.InsertTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.transactions.InsertSingleTransactionRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.SaveUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.CoreUseCasesWrapper
import com.example.financetracker.domain.repository.local.CategoryRepository
import com.example.financetracker.domain.repository.local.UserProfileRepository
import com.example.financetracker.domain.usecases.local.category.DeleteCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetAllCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategoriesByTypeLocalUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalUseCase
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.SeedPredefinedCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.domain.repository.local.SharedPreferencesRepository
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetDarkModeLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeInstalledLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeLoginLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUserNameLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetDarkModeLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeInstalledLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeLoginUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.ChartsUseCaseWrapper
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsByUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertTransactionsLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.AddTransactionUseCasesWrapper
import com.example.financetracker.domain.usecases.remote.transactions.DeleteDeletedTransactionsByIdRemoteUseCase
import com.example.financetracker.domain.usecases.local.transaction.DoesTransactionExitsLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllDeletedTransactionByUIDUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllUnsyncedTransactionsLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetTransactionByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategoryLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertTransactionsAndReturnIdLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.SyncTransactionsRemoteToLocalUseCase
import com.example.financetracker.domain.usecases.local.validation.TransactionCategoryValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.EmptyFieldValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.TransactionPriceValidationUseCase
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository
import com.example.financetracker.domain.usecases.local.saved_items.GetAllSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.SavedItemsUseCasesWrapper
import com.example.financetracker.domain.usecases.local.validation.SavedItemsValidationUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DeleteDeletedSavedItemByIdUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DoesSavedItemExistsUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllDeletedSavedItemsByUserIdUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetAllUnSyncedSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.GetSavedItemByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertSavedItemAndReturnIdLocalUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.DeleteMultipleSavedItemCloud
import com.example.financetracker.domain.usecases.remote.saved_items.GetRemoteSavedItemList
import com.example.financetracker.domain.usecases.remote.saved_items.InsertRemoteSavedItemToLocal
import com.example.financetracker.domain.usecases.remote.saved_items.SaveMultipleSavedItemCloud
import com.example.financetracker.domain.usecases.remote.saved_items.SaveSingleSavedItemCloud
import com.example.financetracker.domain.repository.local.HomePageRepository
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.HomePageUseCaseWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SettingsUseCaseWrapper
import com.example.financetracker.domain.usecases.remote.transactions.DeleteTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.DeleteSavedItemCloud
import com.example.financetracker.domain.usecases.local.saved_items.DeleteSavedItemByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.DeleteTransactionByIdLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFiltersUseCase
import com.example.financetracker.domain.usecases.local.saved_items.InsertDeletedSavedItemLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertDeletedTransactionsLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.ViewRecordsUseCaseWrapper
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.remote.country.GetCountryDetailsRemoteUseCase
import com.example.financetracker.domain.usecases.local.currency_rates.GetCurrencyRatesLocal
import com.example.financetracker.domain.usecases.local.country.InsertCountryLocalUseCase
import com.example.financetracker.domain.usecases.local.currency_rates.SeedCurrencyRatesLocalOneTime
import com.example.financetracker.domain.usecases.local.currency_rates.SeedCurrencyRatesLocalPeriodical
import com.example.financetracker.domain.usecases.remote.user_profile.UpdateUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
import com.example.financetracker.domain.usecases.local.validation.CountryValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.NameValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.PhoneNumberValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    // CategoryUseCases
    @Provides
    @Singleton
    fun provideCategoryUseCase(categoryRepository: CategoryRepository): PredefinedCategoriesUseCaseWrapper {
        return PredefinedCategoriesUseCaseWrapper(
            getAllCategoriesLocalUseCase = GetAllCategoriesLocalUseCase(categoryRepository),
            seedPredefinedCategoriesLocalUseCase = SeedPredefinedCategoriesLocalUseCase(categoryRepository),
            getCustomCategoriesLocalUseCase = GetCustomCategoriesLocalUseCase(categoryRepository),
            getPredefinedCategoriesByTypeLocalUseCase = GetPredefinedCategoriesByTypeLocalUseCase(categoryRepository),
            insertCustomCategoriesLocalUseCase = InsertCustomCategoriesLocalUseCase(categoryRepository),
            deleteCustomCategoriesLocalUseCase = DeleteCustomCategoriesLocalUseCase(categoryRepository),
            getPredefinedCategoriesLocalUseCase = GetPredefinedCategoriesLocalUseCase(categoryRepository),
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
            checkIsLoggedInLocalUseCase = CheckIsLoggedInLocalUseCase(sharedPreferencesRepository),
            getUserUIDRemoteUseCase = GetUserUIDRemoteUseCase(remoteRepository),
            getUserEmailRemoteUserCase = GetUserEmailRemoteUserCase(remoteRepository),
            getUserProfileRemoteUseCase = GetUserProfileRemoteUseCase(remoteRepository),
            saveUserProfileRemoteUseCase = SaveUserProfileRemoteUseCase(remoteRepository),
            setUserNameLocalUseCase = SetUserNameLocalUseCase(sharedPreferencesRepository),
            insertUserProfileLocalUseCase = InsertUserProfileLocalUseCase(userProfileRepository)
        )
    }

    // Auth UseCases
    @Provides
    @Singleton
    fun provideAuthUseCases(sharedPreferencesRepository: SharedPreferencesRepository): AuthFeatureUseCasesWrapper {
        return AuthFeatureUseCasesWrapper(
            emailValidationUseCase = EmailValidationUseCase(),
            passwordValidationUseCase = PasswordValidationUseCase(),
            confirmPasswordValidationUseCase = ConfirmPasswordValidationUseCase(),
            insertUIDLocalUseCase = InsertUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository)
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
            insertSavedItemLocalUseCase = InsertSavedItemLocalUseCase(savedItemsLocalRepository),
            getAllSavedItemLocalUseCase = GetAllSavedItemLocalUseCase(savedItemsLocalRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository),
            getUserProfileLocalUseCase = GetUserProfileLocalUseCase(homePageRepository),
            savedItemsValidationUseCase = SavedItemsValidationUseCase(),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository),
            saveSingleSavedItemCloud = SaveSingleSavedItemCloud(
                savedItemsRemoteRepository = savedItemsRemoteRepository,
                savedItemsLocalRepository = savedItemsLocalRepository
            ),
            insertSavedItemAndReturnIdLocalUseCase = InsertSavedItemAndReturnIdLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getCloudSyncLocalUseCase = GetCloudSyncLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getAllUnSyncedSavedItemLocalUseCase = GetAllUnSyncedSavedItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getRemoteSavedItemList = GetRemoteSavedItemList(savedItemsRemoteRepository = savedItemsRemoteRepository),
            doesSavedItemExistsUseCase = DoesSavedItemExistsUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getUserUIDRemoteUseCase = GetUserUIDRemoteUseCase(remoteRepository = remoteRepository),
            insertRemoteSavedItemToLocal = InsertRemoteSavedItemToLocal(savedItemsRemoteRepository = savedItemsRemoteRepository)
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
    ): BudgetUseCaseWrapper {
        return BudgetUseCaseWrapper(
            getBudgetLocalUseCase = GetBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            insertBudgetLocalUseCase = InsertBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getUserProfileFromLocalUseCase = GetUserProfileFromLocalUseCase(userProfileRepository = userProfileRepository),
            getAllUnSyncedBudgetLocalUseCase = GetAllUnSyncedBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            insertBudgetRemoteUseCase = InsertBudgetRemoteUseCase(budgetRemoteRepository = budgetRemoteRepository),
            insertBudgetsRemoteUseCase = InsertBudgetsRemoteUseCase(budgetRemoteRepository = budgetRemoteRepository),
            doesBudgetExitsLocalUseCase = DoesBudgetExitsLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            insertBudgetsRemoteToLocalUseCase = InsertBudgetsRemoteToLocalUseCase(budgetRemoteRepository = budgetRemoteRepository),
            getUserUIDRemoteUseCase = GetUserUIDRemoteUseCase(remoteRepository = remoteRepository),
            getBudgetsRemoteUseCase = GetBudgetsRemoteUseCase(budgetRemoteRepository = budgetRemoteRepository)
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
            getUserEmailRemoteUserCase = GetUserEmailRemoteUserCase(remoteRepository),
            getUserUIDRemoteUseCase = GetUserUIDRemoteUseCase(remoteRepository),
            getCountryDetailsRemoteUseCase = GetCountryDetailsRemoteUseCase(countryRepository),
            nameValidationUseCase = NameValidationUseCase(),
            phoneNumberValidationUseCase = PhoneNumberValidationUseCase(),
            countryValidationUseCase = CountryValidationUseCase(),
            updateUserProfileRemoteUseCase = UpdateUserProfileRemoteUseCase(remoteRepository),
            getUserProfileRemoteUseCase = GetUserProfileRemoteUseCase(remoteRepository),
            getCountryLocalUseCase = GetCountryLocalUseCase(countryLocalRepository),
            seedCountryLocalUseCase = SeedCountryLocalUseCase(countryLocalRepository),
            insertUserProfileLocalUseCase = InsertUserProfileLocalUseCase(userProfileRepository),
            getUserProfileFromLocalUseCase = GetUserProfileFromLocalUseCase(userProfileRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository),
            seedCurrencyRatesLocalOneTime = SeedCurrencyRatesLocalOneTime(currencyRatesLocalRepository),
            seedCurrencyRatesLocalPeriodical = SeedCurrencyRatesLocalPeriodical(currencyRatesLocalRepository),
            getCurrencyRatesUpdatedLocalUseCase = GetCurrencyRatesUpdatedLocalUseCase(sharedPreferencesRepository),
            setCurrencyRatesUpdatedLocalUseCase = SetCurrencyRatesUpdatedLocalUseCase(sharedPreferencesRepository),
            getCurrencyRatesLocal = GetCurrencyRatesLocal(currencyRatesLocalRepository),
            insertCountryLocalUseCase = InsertCountryLocalUseCase(countryLocalRepository),
            keepUserLoggedInLocalUseCase = KeepUserLoggedInLocalUseCase(sharedPreferencesRepository),
            getAllTransactionsRemoteUseCase = GetAllTransactionsRemoteUseCase(remoteRepository = remoteRepository),
            getRemoteSavedItemList = GetRemoteSavedItemList(savedItemsRemoteRepository = savedItemsRemoteRepository),
            doesSavedItemExistsUseCase = DoesSavedItemExistsUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            doesTransactionExitsLocalUseCase = DoesTransactionExitsLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            getFirstTimeInstalledLocalUseCase = GetFirstTimeInstalledLocalUseCase(sharedPreferencesRepository),
            setFirstTimeInstalledLocalUseCase = SetFirstTimeInstalledLocalUseCase(sharedPreferencesRepository),
            getFirstTimeLoggedIn = GetFirstTimeLoginLocalUseCase(sharedPreferencesRepository),
            setFirstTimeLoginUseCase = SetFirstTimeLoginUseCase(sharedPreferencesRepository)
        )
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
            getUserProfileLocalUseCase = GetUserProfileLocalUseCase(homePageRepository),
            setCurrencyRatesUpdatedLocalUseCase = SetCurrencyRatesUpdatedLocalUseCase(sharedPreferencesRepository),
            getCurrencyRatesUpdatedLocalUseCase = GetCurrencyRatesUpdatedLocalUseCase(sharedPreferencesRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository),
            getAllTransactionsByUIDLocalUseCase = GetAllTransactionsByUIDLocalUseCase(transactionLocalRepository),
            getAllCategoriesLocalUseCase = GetAllCategoriesLocalUseCase(categoryRepository = categoryRepository),
            getAllTransactionsFilters = GetAllTransactionsFiltersUseCase(transactionLocalRepository = transactionLocalRepository),
            getBudgetLocalUseCase = GetBudgetLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            sendBudgetNotificationLocalUseCase = SendBudgetNotificationLocalUseCase(budgetLocalRepository = budgetLocalRepository),
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
            getCurrencyRatesLocal = GetCurrencyRatesLocal(currencyRatesLocalRepository),
            insertCustomCategoryLocalUseCase = InsertCustomCategoryLocalUseCase(categoryRepository),
            transactionPriceValidationUseCase = TransactionPriceValidationUseCase(),
            emptyFieldValidationUseCase = EmptyFieldValidationUseCase(),
            transactionCategoryValidationUseCase = TransactionCategoryValidationUseCase(),
            insertTransactionsLocalUseCase = InsertTransactionsLocalUseCase(transactionLocalRepository),
            insertTransactionsAndReturnIdLocalUseCase = InsertTransactionsAndReturnIdLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            getCloudSyncLocalUseCase = GetCloudSyncLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            insertSingleTransactionRemoteUseCase = InsertSingleTransactionRemoteUseCase(remoteRepository = remoteRepository,transactionLocalRepository = transactionLocalRepository),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository = remoteRepository),
            getAllUnsyncedTransactionsLocalUseCase = GetAllUnsyncedTransactionsLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            sendBudgetNotificationLocalUseCase = SendBudgetNotificationLocalUseCase(budgetLocalRepository = budgetLocalRepository),
            getUserUIDRemoteUseCase = GetUserUIDRemoteUseCase(remoteRepository = remoteRepository),
            getAllTransactionsRemoteUseCase = GetAllTransactionsRemoteUseCase(remoteRepository = remoteRepository),
            syncTransactionsRemoteToLocalUseCase = SyncTransactionsRemoteToLocalUseCase(remoteRepository = remoteRepository),
            doesTransactionExitsLocalUseCase = DoesTransactionExitsLocalUseCase(transactionLocalRepository = transactionLocalRepository)
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
            getAllTransactionsByUIDLocalUseCase = GetAllTransactionsByUIDLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            getAllSavedItemLocalUseCase = GetAllSavedItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            deleteTransactionByIdLocalUseCase = DeleteTransactionByIdLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            deleteSavedItemByIdLocalUseCase = DeleteSavedItemByIdLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getCloudSyncLocalUseCase = GetCloudSyncLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            internetConnectionAvailability = InternetConnectionAvailability(remoteRepository = remoteRepository),
            insertDeletedTransactionsLocalUseCase = InsertDeletedTransactionsLocalUseCase(transactionRemoteRepository = transactionRemoteRepository),
            deleteTransactionRemoteUseCase = DeleteTransactionRemoteUseCase(remoteRepository = remoteRepository),
            getAllDeletedTransactionByUIDUseCase = GetAllDeletedTransactionByUIDUseCase(transactionRemoteRepository = transactionRemoteRepository),
            deleteDeletedTransactionsByIdRemoteUseCase = DeleteDeletedTransactionsByIdRemoteUseCase(transactionRemoteRepository = transactionRemoteRepository),
            deleteTransactionsRemoteUseCase = DeleteTransactionsRemoteUseCase(transactionRemoteRepository = transactionRemoteRepository),
            getTransactionByIdLocalUseCase = GetTransactionByIdLocalUseCase(transactionLocalRepository = transactionLocalRepository),
            deleteDeletedSavedItemByIdUseCase = DeleteDeletedSavedItemByIdUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            deleteSavedItemCloud = DeleteSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            getAllDeletedSavedItemsByUserIdUseCase = GetAllDeletedSavedItemsByUserIdUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            deleteMultipleSavedItemCloud = DeleteMultipleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            insertDeletedSavedItemLocalUseCase = InsertDeletedSavedItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getSavedItemByIdLocalUseCase = GetSavedItemByIdLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            savedItemsValidationUseCase = SavedItemsValidationUseCase(),
            saveSingleSavedItemCloud = SaveSingleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository,savedItemsLocalRepository = savedItemsLocalRepository),
            insertSavedItemLocalUseCase = InsertSavedItemLocalUseCase(savedItemsLocalRepository = savedItemsLocalRepository),
            getAllCategoriesLocalUseCase = GetAllCategoriesLocalUseCase(categoryRepository = categoryRepository),
            getAllTransactionsFilters = GetAllTransactionsFiltersUseCase(transactionLocalRepository = transactionLocalRepository),
            getUserProfileLocalUseCase = GetUserProfileLocalUseCase(homePageRepository = homePageRepository)
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
            getCloudSyncLocalUseCase = GetCloudSyncLocalUseCase(sharedPreferencesRepository),
            setCloudSyncLocalUseCase = SetCloudSyncLocalUseCase(sharedPreferencesRepository),
            insertTransactionsRemoteUseCase = InsertTransactionsRemoteUseCase(remoteRepository),
            saveMultipleSavedItemCloud = SaveMultipleSavedItemCloud(savedItemsRemoteRepository = savedItemsRemoteRepository),
            setDarkModeLocalUseCase = SetDarkModeLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getUserProfileFromLocalUseCase = GetUserProfileFromLocalUseCase(userProfileRepository),
            getDarkModeLocalUseCase = GetDarkModeLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            logoutUseCase = LogoutUseCase(remoteRepository = remoteRepository,sharedPreferencesRepository = sharedPreferencesRepository),
            getUserNameLocalUseCase = GetUserNameLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            setUserNameLocalUseCase = SetUserNameLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getAllTransactionsRemoteUseCase = GetAllTransactionsRemoteUseCase(remoteRepository = remoteRepository),
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
            getAllCategoriesLocalUseCase = GetAllCategoriesLocalUseCase(categoryRepository = categoryRepository),
            getUIDLocalUseCase = GetUIDLocalUseCase(sharedPreferencesRepository = sharedPreferencesRepository),
            getAllTransactionsFilters = GetAllTransactionsFiltersUseCase(transactionLocalRepository = transactionLocalRepository),
            getUserProfileFromLocalUseCase = GetUserProfileFromLocalUseCase(userProfileRepository = userProfileRepository)
        )
    }
}