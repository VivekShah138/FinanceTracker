package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.KeepUserLoggedIn
import com.example.financetracker.budget_feature.domain.usecases.DoesBudgetExits
import com.example.financetracker.budget_feature.domain.usecases.GetBudgetLocalUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetRemoteTransactionsList
import com.example.financetracker.core.cloud.domain.usecase.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetFirstTimeInstalled
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetFirstTimeLogin
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetFirstTimeInstalled
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetFirstTimeLogin
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.DoesTransactionExits
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local.DoesItemExistsUseCase
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.GetRemoteSavedItemList


data class SetupAccountUseCasesWrapper(
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getCountryDetailsUseCase: GetCountryDetailsUseCase,
    val validateName: ValidateName,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateCountry: ValidateCountry,
    val updateUserProfile: UpdateUserProfile,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getCountryLocally: GetCountryLocally,
    val insertCountryLocallyWorkManager: InsertCountryLocallyWorkManager,
    val insertUserProfileToLocalDb: InsertUserProfileToLocalDb,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb,
    val getUIDLocally: GetUIDLocally,
    val insertCurrencyRatesLocalOneTime: InsertCurrencyRatesLocalOneTime,
    val insertCurrencyRatesLocalPeriodically: InsertCurrencyRatesLocalPeriodically,
    val getCurrencyRatesUpdated: GetCurrencyRatesUpdated,
    val setCurrencyRatesUpdated: SetCurrencyRatesUpdated,
    val getCurrencyRatesLocally: GetCurrencyRatesLocally,
    val insertCountryLocally: InsertCountryLocally,
    val keepUserLoggedIn: KeepUserLoggedIn,
    val getRemoteTransactionsList: GetRemoteTransactionsList,
    val getRemoteSavedItemList: GetRemoteSavedItemList,
    val doesItemExistsUseCase: DoesItemExistsUseCase,
    val doesTransactionExits: DoesTransactionExits,
    val getFirstTimeInstalled: GetFirstTimeInstalled,
    val setFirstTimeInstalled: SetFirstTimeInstalled,
    val getFirstTimeLoggedIn: GetFirstTimeLogin,
    val setFirstTimeLogin: SetFirstTimeLogin
)