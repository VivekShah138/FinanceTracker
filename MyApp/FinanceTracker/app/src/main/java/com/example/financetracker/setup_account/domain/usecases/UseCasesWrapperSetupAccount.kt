package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.core.cloud.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.core_domain.usecase.GetUIDLocally
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated


data class UseCasesWrapperSetupAccount(
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getCountryDetailsUseCase: GetCountryDetailsUseCase,
    val validateName: ValidateName,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateCountry: ValidateCountry,
    val updateUserProfile: UpdateUserProfile,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getCountryLocally: GetCountryLocally,
    val insertCountryLocally: InsertCountryLocally,
    val insertUserProfileToLocalDb: InsertUserProfileToLocalDb,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb,
    val getUIDLocally: GetUIDLocally,
    val insertCurrencyRatesLocalOneTime: InsertCurrencyRatesLocalOneTime,
    val insertCurrencyRatesLocalPeriodically: InsertCurrencyRatesLocalPeriodically,
    val getCurrencyRatesUpdated: GetCurrencyRatesUpdated,
    val setCurrencyRatesUpdated: SetCurrencyRatesUpdated,
    val getCurrencyRatesLocally: GetCurrencyRatesLocally
)