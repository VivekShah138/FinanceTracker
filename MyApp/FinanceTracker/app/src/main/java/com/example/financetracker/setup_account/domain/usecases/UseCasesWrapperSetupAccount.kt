package com.example.financetracker.setup_account.domain.usecases

<<<<<<< Updated upstream
import com.example.financetracker.core.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.domain.usecases.GetUserUIDUseCase
=======
import com.example.financetracker.core.cloud.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
>>>>>>> Stashed changes

data class UseCasesWrapperSetupAccount(
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getCountryDetailsUseCase: GetCountryDetailsUseCase,
    val validateName: ValidateName,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateCountry: ValidateCountry,
    val updateUserProfile: UpdateUserProfile,
<<<<<<< Updated upstream
    val getUserProfileUseCase: GetUserProfileUseCase
=======
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getCountryLocally: GetCountryLocally,
    val insertCountryLocally: InsertCountryLocally,
    val insertUserProfileToLocalDb: InsertUserProfileToLocalDb,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb
>>>>>>> Stashed changes
)