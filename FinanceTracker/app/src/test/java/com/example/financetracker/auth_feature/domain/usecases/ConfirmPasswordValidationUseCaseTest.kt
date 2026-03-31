package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.domain.usecases.local.validation.ConfirmPasswordValidationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ConfirmPasswordValidationUseCaseTest {

  private lateinit var confirmPasswordValidationUseCase: ConfirmPasswordValidationUseCase

  @Before
  fun setUp() {
   confirmPasswordValidationUseCase = ConfirmPasswordValidationUseCase()
  }

  @Test
  fun `confirm password shorter than 8 characters returns error`() = runTest {
   val result = confirmPasswordValidationUseCase("Test@123", "T@1")
   assertFalse(result.isSuccessful)
   assertEquals("Password Length Should be at least 8 Digits", result.errorMessage)
  }

  @Test
  fun `confirm password not matching returns error`() = runTest {
   val result = confirmPasswordValidationUseCase("Test@123", "Test@124")
   assertFalse(result.isSuccessful)
   assertEquals("Confirm Password Should Be Same as Password", result.errorMessage)
  }

  @Test
  fun `valid confirm password returns success`() = runTest {
   val result = confirmPasswordValidationUseCase("Test@123", "Test@123")
   assertTrue(result.isSuccessful)
   assertNull(result.errorMessage)
  }
}