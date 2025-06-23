package com.example.financetracker.auth_feature.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidateConfirmPasswordTest {

  private lateinit var validateConfirmPassword: ValidateConfirmPassword

  @Before
  fun setUp() {
   validateConfirmPassword = ValidateConfirmPassword()
  }

  @Test
  fun `confirm password shorter than 8 characters returns error`() = runTest {
   val result = validateConfirmPassword("Test@123", "T@1")
   assertFalse(result.isSuccessful)
   assertEquals("Password Length Should be at least 8 Digits", result.errorMessage)
  }

  @Test
  fun `confirm password not matching returns error`() = runTest {
   val result = validateConfirmPassword("Test@123", "Test@124")
   assertFalse(result.isSuccessful)
   assertEquals("Confirm Password Should Be Same as Password", result.errorMessage)
  }

  @Test
  fun `valid confirm password returns success`() = runTest {
   val result = validateConfirmPassword("Test@123", "Test@123")
   assertTrue(result.isSuccessful)
   assertNull(result.errorMessage)
  }
}