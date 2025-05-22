package com.example.financetracker.setup_account.presentation

import org.junit.Assert.*

import org.junit.Before

import app.cash.turbine.test
import com.example.financetracker.auth_feature.domain.usecases.ValidationResult
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.After

import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProfileSetUpViewModelTest {

 private lateinit var viewModel: ProfileSetUpViewModel
 private val setupAccountUseCasesWrapper = mockk<SetupAccountUseCasesWrapper>(relaxed = true)

 private val testDispatcher = StandardTestDispatcher()

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)
  viewModel = ProfileSetUpViewModel(setupAccountUseCasesWrapper)
 }

 @Test
 fun `validateFields failure sends failure event`() = runTest {
  // Arrange: mock validations to fail
  coEvery { setupAccountUseCasesWrapper.validateName(any()) } returns ValidationResult(false, "Invalid Name")
  coEvery { setupAccountUseCasesWrapper.validatePhoneNumber(any()) } returns ValidationResult(true)
  coEvery { setupAccountUseCasesWrapper.validateCountry(any()) } returns ValidationResult(true)

  // Set profile state with dummy data
  viewModel.onEvent(ProfileSetUpEvents.ChangeFirstName(""))
  viewModel.onEvent(ProfileSetUpEvents.ChangeLastName("Smith"))
  viewModel.onEvent(ProfileSetUpEvents.ChangePhoneNumber("1234567890"))
  viewModel.onEvent(ProfileSetUpEvents.SelectCountry(country = "USA", callingCode = "+1", expanded = false))
  viewModel.onEvent(ProfileSetUpEvents.SelectBaseCurrency(currency = "Dollar", expanded = false, currencyCode = "USD", currencySymbol = "$"))


  // Act & Assert: collect events and trigger validation
  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.Submit)
   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Failure)
   assertEquals("Invalid Name", (event as ProfileSetUpViewModel.ProfileUpdateEvent.Failure).errorMessage)
   cancelAndIgnoreRemainingEvents()
  }
 }


 @Test
 fun `validateNames event emits failure when name is invalid`() = runTest {
  coEvery { setupAccountUseCasesWrapper.validateName(any()) } returns ValidationResult(false, "Invalid Name")

  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.ValidateNames)

   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Failure)
   assertEquals("Invalid Name", (event as ProfileSetUpViewModel.ProfileUpdateEvent.Failure).errorMessage)

   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `validatePhoneNumber event emits success when phone number is valid`() = runTest {
  coEvery { setupAccountUseCasesWrapper.validatePhoneNumber(any()) } returns ValidationResult(true)

  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.ValidatePhoneNumber)

   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Success)

   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `validateCountry event emits success when country is valid`() = runTest {
  coEvery { setupAccountUseCasesWrapper.validateCountry(any()) } returns ValidationResult(true)

  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.ValidateCountry)

   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Success)

   cancelAndIgnoreRemainingEvents()
  }
 }



 @Test
 fun `validateFields success sends success event`() = runTest {
  // Arrange: mock all validations successful
  coEvery { setupAccountUseCasesWrapper.validateName(any()) } returns ValidationResult(true)
  coEvery { setupAccountUseCasesWrapper.validatePhoneNumber(any()) } returns ValidationResult(true)
  coEvery { setupAccountUseCasesWrapper.validateCountry(any()) } returns ValidationResult(true)

  // Mock other use case calls
  coEvery { setupAccountUseCasesWrapper.getUserUIDUseCase() } returns "user123"
  coEvery { setupAccountUseCasesWrapper.insertUserProfileToLocalDb(any(), any()) } just Runs
  coEvery { setupAccountUseCasesWrapper.updateUserProfile(any(), any(), any(), any(), any(), any(), any(),any()) } just Runs
  coEvery { setupAccountUseCasesWrapper.keepUserLoggedIn(true) } just Runs


  viewModel.onEvent(ProfileSetUpEvents.ChangeFirstName("John"))
  viewModel.onEvent(ProfileSetUpEvents.ChangeLastName("Doe"))
  viewModel.onEvent(ProfileSetUpEvents.ChangePhoneNumber("1234567890"))
  viewModel.onEvent(ProfileSetUpEvents.SelectCountry("Country", "+1", expanded = false))
  viewModel.onEvent(ProfileSetUpEvents.SelectBaseCurrency(
   currency = "US Dollar",
   expanded = false,
   currencyCode = "USD",
   currencySymbol = "$"
  ))

  // Act & Assert: collect events and trigger validation
  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.Submit)
   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Success)
   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `validateFields throws exception sends failure event`() = runTest {
  // Arrange: all validations successful
  coEvery { setupAccountUseCasesWrapper.validateName(any()) } returns ValidationResult(true)
  coEvery { setupAccountUseCasesWrapper.validatePhoneNumber(any()) } returns ValidationResult(true)
  coEvery { setupAccountUseCasesWrapper.validateCountry(any()) } returns ValidationResult(true)

  // Mock getUserUIDUseCase to return user id
  coEvery { setupAccountUseCasesWrapper.getUserUIDUseCase() } returns "user123"
  // Mock insertUserProfileToLocalDb to throw exception
  coEvery { setupAccountUseCasesWrapper.insertUserProfileToLocalDb(any(), any()) } throws Exception("DB error")

  // Set profile state with valid data
  viewModel.onEvent(ProfileSetUpEvents.ChangeFirstName("John"))
  viewModel.onEvent(ProfileSetUpEvents.ChangeLastName("Doe"))
  viewModel.onEvent(ProfileSetUpEvents.ChangePhoneNumber("1234567890"))
  viewModel.onEvent(ProfileSetUpEvents.SelectCountry("USA", "+1", expanded = false))
  viewModel.onEvent(ProfileSetUpEvents.SelectBaseCurrency(
   currency = "US Dollar",
   expanded = false,
   currencyCode = "USD",
   currencySymbol = "$"
  ))
  viewModel.onEvent(ProfileSetUpEvents.ChangeSearchCurrency("US Dollar"))
  viewModel.onEvent(ProfileSetUpEvents.ChangeSearchCountry("USA"))

  // Act & Assert: collect events and trigger validation
  viewModel.profileSetUpValidationEvents.test {
   viewModel.onEvent(ProfileSetUpEvents.Submit)
   val event = awaitItem()
   assert(event is ProfileSetUpViewModel.ProfileUpdateEvent.Failure)
   assertEquals("DB error", (event as ProfileSetUpViewModel.ProfileUpdateEvent.Failure).errorMessage)
   cancelAndIgnoreRemainingEvents()
  }
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }
}
