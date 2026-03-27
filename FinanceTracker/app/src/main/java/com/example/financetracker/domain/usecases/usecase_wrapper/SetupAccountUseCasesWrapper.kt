package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.shared_pref.KeepUserLoggedInLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailRemoteUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalUseCase
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeInstalledLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetFirstTimeLoginLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeInstalledLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetFirstTimeLoginUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.UpdateUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.local.transaction.DoesTransactionExitsLocalUseCase
import com.example.financetracker.domain.usecases.local.saved_items.DoesSavedItemExistsUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.GetRemoteSavedItemList
import com.example.financetracker.domain.usecases.remote.country.GetCountryDetailsRemoteUseCase
import com.example.financetracker.domain.usecases.local.country.GetCountryLocalUseCase
import com.example.financetracker.domain.usecases.local.currency_rates.GetCurrencyRatesLocal
import com.example.financetracker.domain.usecases.local.country.InsertCountryLocalUseCase
import com.example.financetracker.domain.usecases.local.country.SeedCountryLocalUseCase
import com.example.financetracker.domain.usecases.local.currency_rates.SeedCurrencyRatesLocalOneTime
import com.example.financetracker.domain.usecases.local.currency_rates.SeedCurrencyRatesLocalPeriodical
import com.example.financetracker.domain.usecases.local.validation.CountryValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.NameValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.PhoneNumberValidationUseCase


data class SetupAccountUseCasesWrapper(
    val getUserEmailRemoteUserCase: GetUserEmailRemoteUserCase,
    val getUserUIDRemoteUseCase: GetUserUIDRemoteUseCase,
    val getCountryDetailsRemoteUseCase: GetCountryDetailsRemoteUseCase,
    val nameValidationUseCase: NameValidationUseCase,
    val phoneNumberValidationUseCase: PhoneNumberValidationUseCase,
    val countryValidationUseCase: CountryValidationUseCase,
    val updateUserProfileRemoteUseCase: UpdateUserProfileRemoteUseCase,
    val getUserProfileRemoteUseCase: GetUserProfileRemoteUseCase,
    val getCountryLocalUseCase: GetCountryLocalUseCase,
    val seedCountryLocalUseCase: SeedCountryLocalUseCase,
    val insertUserProfileLocalUseCase: InsertUserProfileLocalUseCase,
    val getUserProfileFromLocalUseCase: GetUserProfileFromLocalUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val seedCurrencyRatesLocalOneTime: SeedCurrencyRatesLocalOneTime,
    val seedCurrencyRatesLocalPeriodical: SeedCurrencyRatesLocalPeriodical,
    val getCurrencyRatesUpdatedLocalUseCase: GetCurrencyRatesUpdatedLocalUseCase,
    val setCurrencyRatesUpdatedLocalUseCase: SetCurrencyRatesUpdatedLocalUseCase,
    val getCurrencyRatesLocal: GetCurrencyRatesLocal,
    val insertCountryLocalUseCase: InsertCountryLocalUseCase,
    val keepUserLoggedInLocalUseCase: KeepUserLoggedInLocalUseCase,
    val getAllTransactionsRemoteUseCase: GetAllTransactionsRemoteUseCase,
    val getRemoteSavedItemList: GetRemoteSavedItemList,
    val doesSavedItemExistsUseCase: DoesSavedItemExistsUseCase,
    val doesTransactionExitsLocalUseCase: DoesTransactionExitsLocalUseCase,
    val getFirstTimeInstalledLocalUseCase: GetFirstTimeInstalledLocalUseCase,
    val setFirstTimeInstalledLocalUseCase: SetFirstTimeInstalledLocalUseCase,
    val getFirstTimeLoggedIn: GetFirstTimeLoginLocalUseCase,
    val setFirstTimeLoginUseCase: SetFirstTimeLoginUseCase
)