package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation

import app.cash.turbine.test
import com.example.financetracker.auth_feature.domain.usecases.ValidationResult
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class SavedItemsViewModelTest {

 private val setupAccountUseCasesWrapper = mockk<SetupAccountUseCasesWrapper>(relaxed = true)
 private val savedItemsUseCasesWrapper = mockk<SavedItemsUseCasesWrapper>(relaxed = true)

 private lateinit var savedItemViewModel: SavedItemViewModel
 private val userUID = "testUserId"
 private val testDispatcher = StandardTestDispatcher()
 private val testScope = TestScope(testDispatcher)

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)


  savedItemViewModel = SavedItemViewModel(
   savedItemsUseCasesWrapper = savedItemsUseCasesWrapper,
   setupAccountUseCasesWrapper = setupAccountUseCasesWrapper

  )
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
  clearAllMocks()
 }

 @Test
 fun `submit item with valid inputs should emit Success`() = testScope.runTest {
  // Given
  val testItemName = "Milk"
  val testItemPrice = "3.5"
  val testCurrencyCode = "INR"
  val testCurrencyName = "Indian Rupee"
  val testCurrencySymbol = "₹"


  every { savedItemsUseCasesWrapper.getCloudSyncLocally() } returns true
  coEvery { savedItemsUseCasesWrapper.internetConnectionAvailability() } returns true
  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Name",state = testItemName) } returns ValidationResult(true)
  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Price", state =  testItemPrice) } returns ValidationResult(true)
  coEvery { savedItemsUseCasesWrapper.saveItemLocalUseCase(any()) } just Runs


  // When
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemName(testItemName))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemPrice(testItemPrice))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemCurrency(testCurrencyName, testCurrencyCode, testCurrencySymbol,false))

  savedItemViewModel.savedItemsValidationEvents.test {
   savedItemViewModel.onEvent(SavedItemsEvent.Submit)

   testDispatcher.scheduler.advanceUntilIdle()

   val event = awaitItem()
   kotlin.test.assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Success)
   cancelAndIgnoreRemainingEvents()
  }

 }

 @Test
 fun `submit item with invalid name should emit Failure`() = testScope.runTest {
  val testItemName = ""
  val testItemPrice = "5.0"

  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Name", state = testItemName) } returns ValidationResult(false,"Item name required")
  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Price", state = testItemPrice) } returns ValidationResult(true)


  // When
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemName(testItemName))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemPrice(testItemPrice))


  savedItemViewModel.savedItemsValidationEvents.test {
   savedItemViewModel.onEvent(SavedItemsEvent.Submit)

   testDispatcher.scheduler.advanceUntilIdle()

   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Failure)
   assertEquals(
    "Item name required",
    (event as AddTransactionViewModel.AddTransactionValidationEvent.Failure).errorMessage
   )


   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `submit item with invalid price should emit Failure`() = testScope.runTest {
  val testItemName = "Bread"
  val testItemPrice = ""

  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Name", state = testItemName) } returns ValidationResult(true)
  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Price",state = testItemPrice) } returns ValidationResult(false,"Item price required")


  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemName(testItemName))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemPrice(testItemPrice))

  savedItemViewModel.savedItemsValidationEvents.test {
   savedItemViewModel.onEvent(SavedItemsEvent.Submit)

   testDispatcher.scheduler.advanceUntilIdle()

//   assertEquals(
//    AddTransactionViewModel.AddTransactionValidationEvent.Failure("Item Price required"),
//    awaitItem()
//   )

   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Failure)
   assertEquals(
    "Item price required",
    (event as AddTransactionViewModel.AddTransactionValidationEvent.Failure).errorMessage
   )
   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `submit item with cloud sync error should emit Failure`() = testScope.runTest {
  val testItemName = "Butter"
  val testItemPrice = "5.0"
  val testCurrencyCode = "INR"
  val testCurrencyName = "Rupee"
  val testCurrencySymbol = "₹"

  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Name", state = testItemName) } returns ValidationResult(true)
  coEvery { savedItemsUseCasesWrapper.savedItemsValidationUseCase(stateName = "Item Price", state = testItemPrice) } returns ValidationResult(true)
  coEvery { savedItemsUseCasesWrapper.saveItemLocalUseCase(any()) } throws RuntimeException("Local save failed")

  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemName(testItemName))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemPrice(testItemPrice))
  savedItemViewModel.onEvent(SavedItemsEvent.OnChangeItemCurrency(testCurrencyName, testCurrencyCode, testCurrencySymbol,false))

  savedItemViewModel.savedItemsValidationEvents.test {
   savedItemViewModel.onEvent(SavedItemsEvent.Submit)

   testDispatcher.scheduler.advanceUntilIdle()

   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Failure)
   assertEquals(
    "Local save failed",
    (event as AddTransactionViewModel.AddTransactionValidationEvent.Failure).errorMessage
   )
   cancelAndIgnoreRemainingEvents()
  }
 }
}