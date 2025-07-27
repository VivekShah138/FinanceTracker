package com.example.financetracker.di

import com.example.financetracker.domain.usecases.local.shared_pref.InsertUIDLocally
import com.example.financetracker.domain.usecases.local.shared_pref.KeepUserLoggedIn
import com.example.financetracker.domain.usecases.usecase_wrapper.AuthFeatureUseCasesWrapper
import com.example.financetracker.domain.usecases.local.validation.ValidateConfirmPassword
import com.example.financetracker.domain.usecases.local.validation.ValidateEmail
import com.example.financetracker.domain.usecases.local.validation.ValidatePassword
import com.example.financetracker.domain.repository.local.BudgetLocalRepository
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import com.example.financetracker.domain.usecases.local.budget.DoesBudgetExits
import com.example.financetracker.domain.usecases.local.budget.GetAllUnSyncedBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.GetRemoteBudgetsList
import com.example.financetracker.domain.usecases.local.budget.InsertBudgetLocalUseCase
import com.example.financetracker.domain.usecases.remote.budget.InsertRemoteBudgetsToLocal
import com.example.financetracker.domain.usecases.remote.budget.SaveBudgetToCloudUseCase
import com.example.financetracker.domain.usecases.remote.budget.SaveMultipleBudgetsToCloudUseCase
import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationUseCase
import com.example.financetracker.domain.repository.local.CountryLocalRepository
import com.example.financetracker.domain.repository.remote.CountryRemoteRepository
import com.example.financetracker.setup_account.domain.usecases.GetCountryLocally
import com.example.financetracker.setup_account.domain.usecases.InsertCountryLocallyWorkManager
import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.usecases.remote.transaction.DeleteTransactionCloud
import com.example.financetracker.domain.usecases.remote.transaction.GetRemoteTransactionsList
import com.example.financetracker.domain.usecases.local.shared_pref.CheckIsLoggedInUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.remote.transaction.SaveMultipleTransactionsCloud
import com.example.financetracker.domain.usecases.remote.transaction.SaveSingleTransactionCloud
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.SaveUserProfileUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.usecase_wrapper.CoreUseCasesWrapper
import com.example.financetracker.domain.repository.local.CategoryRepository
import com.example.financetracker.domain.repository.local.UserProfileRepository
import com.example.financetracker.domain.usecases.local.category.DeleteCustomCategories
import com.example.financetracker.domain.usecases.local.category.GetCustomCategories
import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategories
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalDb
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategories
import com.example.financetracker.domain.usecases.local.category.InsertPredefinedCategories
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileToLocalDb
import com.example.financetracker.domain.usecases.usecase_wrapper.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.domain.repository.local.SharedPreferencesRepository
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdated
import com.example.financetracker.domain.usecases.local.shared_pref.GetDarkModeLocally
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeInstalled
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeLogin
import com.example.financetracker.domain.usecases.local.shared_pref.GetUserNameLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdated
import com.example.financetracker.domain.usecases.local.shared_pref.SetDarkModeLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeInstalled
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeLogin
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocally
import com.example.financetracker.main_page_feature.charts.domain.usecases.ChartsUseCaseWrapper
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository
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
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository
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
import com.example.financetracker.domain.repository.local.HomePageRepository
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
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository
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