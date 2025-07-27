package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.domain.usecases.local.shared_pref.KeepUserLoggedIn
import com.example.financetracker.domain.usecases.remote.transaction.GetRemoteTransactionsList
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalDb
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileToLocalDb
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdated
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeInstalled
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeLogin
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdated
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeInstalled
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeLogin
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