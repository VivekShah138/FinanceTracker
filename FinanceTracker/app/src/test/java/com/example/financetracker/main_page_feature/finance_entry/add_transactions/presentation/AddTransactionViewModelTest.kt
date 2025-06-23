package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

import org.junit.Assert.*
import app.cash.turbine.test
import com.example.financetracker.auth_feature.domain.usecases.ValidationResult
import com.example.financetracker.core.local.domain.room.usecases.PredefinedCategoriesUseCaseWrapper
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddTransactionViewModelTest {

 private val testDispatcher = StandardTestDispatcher()
 private lateinit var viewModel: AddTransactionViewModel

 private val predefinedCategoriesUseCaseWrapper = mockk<PredefinedCategoriesUseCaseWrapper>(relaxed = true)
 private val setupAccountUseCasesWrapper = mockk<SetupAccountUseCasesWrapper>(relaxed = true)
 private val addTransactionUseCasesWrapper = mockk<AddTransactionUseCasesWrapper>(relaxed = true)
 private val savedItemsUseCasesWrapper = mockk<SavedItemsUseCasesWrapper>(relaxed = true)


 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)

  viewModel = AddTransactionViewModel(
   predefinedCategoriesUseCaseWrapper = predefinedCategoriesUseCaseWrapper,
   setupAccountUseCasesWrapper = setupAccountUseCasesWrapper,
   addTransactionUseCasesWrapper = addTransactionUseCasesWrapper,
   savedItemUseCasesWrapper = savedItemsUseCasesWrapper
  )
 }


 @After
 fun tearDown() {
  Dispatchers.resetMain()
  clearAllMocks()
 }

 @Test
 fun `addTransactions sends success event when validation passes and local+cloud save succeeds`() = runTest {
  // Arrange: Validation passes
  coEvery { addTransactionUseCasesWrapper.validateEmptyField(any()) } returns ValidationResult(true, null)
  coEvery { addTransactionUseCasesWrapper.validateTransactionPrice(any()) } returns ValidationResult(true, null)
  coEvery { addTransactionUseCasesWrapper.validateTransactionCategory(any()) } returns ValidationResult(true, null)

  coEvery { addTransactionUseCasesWrapper.internetConnectionAvailability() } returns true

  coEvery { addTransactionUseCasesWrapper.insertNewTransactionsReturnId(any()) } returns 1L
  coEvery { addTransactionUseCasesWrapper.saveSingleTransactionCloud(any(), any()) } just Runs
  coEvery { addTransactionUseCasesWrapper.insertTransactionsLocally(any()) } just Runs

  viewModel.onEvent(AddTransactionEvents.ChangeTransactionName("Coffee"))
  viewModel.onEvent(AddTransactionEvents.ChangeTransactionPrice("3.5"))
  viewModel.onEvent(AddTransactionEvents.SelectCategory("Food", bottomSheetState = false, alertBoxState = false))
  viewModel.onEvent(AddTransactionEvents.ChangeTransactionCurrency(
   currencyName = "Dollar",
   currencySymbol = "$",
   currencyCode = "USD",
   currencyExpanded = false
  ))
  viewModel.onEvent(AddTransactionEvents.SelectTransactionType("Expense"))
  viewModel.onEvent(AddTransactionEvents.ChangeTransactionDescription("Morning coffee"))
  viewModel.onEvent(AddTransactionEvents.ChangeRecurringItemState(false))
  viewModel.onEvent(AddTransactionEvents.SetConvertedTransactionPrice(price = "", rate = "1.0"))


  // Act & Assert: Collect events from the channel with Turbine
  viewModel.addTransactionsValidationEvents.test {
   viewModel.onEvent(AddTransactionEvents.AddTransactionTransaction)

   // Advance coroutine execution
   testDispatcher.scheduler.advanceUntilIdle()

   // Expect success event emitted
   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Success)

   cancelAndConsumeRemainingEvents()
  }
 }

 @Test
 fun `addTransactions sends failure event when validation fails`() = runTest {
  // Arrange: validation fails on empty field
  coEvery { addTransactionUseCasesWrapper.validateEmptyField(any()) } returns ValidationResult(false, "Name empty")
  coEvery { addTransactionUseCasesWrapper.validateTransactionPrice(any()) } returns ValidationResult(true, null)
  coEvery { addTransactionUseCasesWrapper.validateTransactionCategory(any()) } returns ValidationResult(true, null)

  viewModel.onEvent(AddTransactionEvents.ChangeTransactionName("")) // Empty name
  viewModel.onEvent(AddTransactionEvents.ChangeTransactionPrice("3.5"))
  viewModel.onEvent(AddTransactionEvents.SelectCategory("Food", bottomSheetState = false, alertBoxState = false))


  viewModel.addTransactionsValidationEvents.test {
   viewModel.onEvent(AddTransactionEvents.AddTransactionTransaction)

   testDispatcher.scheduler.advanceUntilIdle()

   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Failure)
   assertEquals("Name empty", (event as AddTransactionViewModel.AddTransactionValidationEvent.Failure).errorMessage)

   cancelAndConsumeRemainingEvents()
  }
 }
}
