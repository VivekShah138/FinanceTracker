package com.example.financetracker.budget_feature.presentation

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel



import app.cash.turbine.test
import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.setup_account.domain.model.Currency
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class BudgetViewModelTest {

 private lateinit var viewModel: BudgetViewModel
 private val budgetUseCaseWrapper = mockk<BudgetUseCaseWrapper>(relaxed = true)
 private val testDispatcher = StandardTestDispatcher()

 // These values must match what your ViewModel internally sets (mocked uid, updatedAt)
 private val testUserId = "user123"
 private val testUpdatedAt = 123456789L

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)

  every { budgetUseCaseWrapper.getUIDLocally() } returns testUserId



  viewModel = spyk(BudgetViewModel(budgetUseCaseWrapper), recordPrivateCalls = true)
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
 fun `ChangeBudget updates budget state`() = runTest {
  val inputBudget = "2000"

  viewModel.onEvent(BudgetEvents.ChangeBudget(inputBudget))

  assertEquals(inputBudget, viewModel.budgetStates.value.budget)
 }

 @Test
 fun `SaveBudget emits failure when budget is empty`() = runTest {
  viewModel.onEvent(BudgetEvents.ChangeBudget(""))

  viewModel.budgetValidationEvents.test {
   viewModel.onEvent(BudgetEvents.SaveBudget)


   advanceUntilIdle()

   val event = awaitItem()

   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Failure)
   assertEquals("Field can not be empty", (event as AddTransactionViewModel.AddTransactionValidationEvent.Failure).errorMessage)

   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `Changing alert threshold updates budget state`() = runTest {
  viewModel.onEvent(BudgetEvents.ChangeAlertThresholdAmount(65f))

  val state = viewModel.budgetStates.value
  assertEquals(65f, state.alertThresholdPercent)
 }

 @Test
 fun `ChangeReceiveBudgetAlerts updates state`() = runTest {
  viewModel.onEvent(BudgetEvents.ChangeReceiveBudgetAlerts(true))
  val state = viewModel.budgetStates.value
  assertEquals(true, state.receiveAlerts)
 }

 @Test
 fun `ChangeCreateBudgetState updates create budget flag`() = runTest {
  viewModel.onEvent(BudgetEvents.ChangeCreateBudgetState(true))
  val state = viewModel.budgetStates.value
  assertEquals(true,state.createBudgetState)
 }

 @Test
 fun `ChangeBudget updates budget value in state`() = runTest {
  viewModel.onEvent(BudgetEvents.ChangeBudget("999.99"))
  advanceUntilIdle()
  val state = viewModel.budgetStates.value
  assertEquals("999.99", state.budget)
 }



 @Test
 fun `SaveBudget inserts budget and emits success when budget is valid`() = runTest {
  val budgetAmount = "1500"

  viewModel.onEvent(BudgetEvents.ChangeBudget(budgetAmount))

  // Mock getBudgetLocalUseCase to simulate no existing budget
  coEvery {
   budgetUseCaseWrapper.getBudgetLocalUseCase(
    userId = testUserId,
    month = any(),
    year = any()
   )
  } returns null

  // Mock insertBudgetLocalUseCase to just run
  coEvery { budgetUseCaseWrapper.insertBudgetLocalUseCase(any()) } just Runs

  viewModel.budgetValidationEvents.test {
   viewModel.onEvent(BudgetEvents.SaveBudget)

   val event = awaitItem()
   assertTrue(event is AddTransactionViewModel.AddTransactionValidationEvent.Success)

   coVerify {
    budgetUseCaseWrapper.insertBudgetLocalUseCase(
     withArg { budget ->
      assertEquals(testUserId, budget.userId)
      assertEquals(budgetAmount.toDouble(), budget.amount)
      assertTrue(budget.updatedAt > 0) // can't exactly test timestamp, but ensure > 0
     }
    )
   }

   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun `MonthSelected updates state and loads budget`() = runTest {
  val month = 4
  val year = 2025

  val dummyBudget = Budget(
   id = UUID.randomUUID().toString(),
   userId = testUserId,
   amount = 1000.0,
   month = month,
   year = year,
   updatedAt = testUpdatedAt,
   cloudSync = false,
   receiveAlerts = true,
   thresholdAmount = 20f
  )




  val dummyCurrency = mockk<Currency>()
  every { dummyCurrency.symbol } returns "£"
  every { dummyCurrency.name } returns "British Pound"

  val dummyUserProfile = mockk<UserProfile>()
  every { dummyUserProfile.baseCurrency } returns mapOf("GPB" to dummyCurrency)


  coEvery {
   budgetUseCaseWrapper.getBudgetLocalUseCase(testUserId, month, year)
  } returns dummyBudget

  coEvery {
   budgetUseCaseWrapper.getUserProfileFromLocalDb(testUserId)
  } returns dummyUserProfile

  viewModel.onEvent(BudgetEvents.MonthSelected(year, month))

  // Allow coroutines to execute
  advanceUntilIdle()

  // Verify state updated
  assertEquals(month, viewModel.budgetStates.value.selectedMonth)
  assertEquals(year, viewModel.budgetStates.value.selectedYear)
  assertEquals(dummyBudget.amount.toString(), viewModel.budgetStates.value.budget)
  assertEquals("£", viewModel.budgetStates.value.budgetCurrencySymbol)
  assertEquals(false, viewModel.budgetStates.value.createBudgetState)
  assertEquals(dummyBudget.receiveAlerts, viewModel.budgetStates.value.receiveAlerts)
  assertEquals(dummyBudget.thresholdAmount, viewModel.budgetStates.value.alertThresholdPercent)
 }
}
