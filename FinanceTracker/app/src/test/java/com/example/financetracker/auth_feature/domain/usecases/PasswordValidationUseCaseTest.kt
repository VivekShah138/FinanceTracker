package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.domain.usecases.local.validation.PasswordValidationUseCase
import org.junit.Assert.*

import org.junit.Before
import kotlinx.coroutines.test.runTest
import org.junit.Test

 class PasswordValidationUseCaseTest {

  private lateinit var passwordValidationUseCase: PasswordValidationUseCase

  @Before
  fun setUp() {
   passwordValidationUseCase = PasswordValidationUseCase()
  }

  @Test
  fun `password shorter than 8 characters returns error`() = runTest {
   val result = passwordValidationUseCase("Ab@12")
   assertFalse(result.isSuccessful)
   assertEquals("Password Length Should be at least 8 Digits", result.errorMessage)
  }

  @Test
  fun `password without special character returns error`() = runTest {
   val result = passwordValidationUseCase("Abcdefg1")
   assertFalse(result.isSuccessful)
   assertEquals("Password Must Contain at least One Special Character", result.errorMessage)
  }

  @Test
  fun `password without uppercase or digit returns error`() = runTest {
   val result = passwordValidationUseCase("abcdefg@")
   assertFalse(result.isSuccessful)
   assertEquals("Password Must Contain at least One Uppercase Letter Or One Digit", result.errorMessage)
  }

  @Test
  fun `valid password returns success`() = runTest {
   val result = passwordValidationUseCase("Test@123")
   assertTrue(result.isSuccessful)
   assertNull(result.errorMessage)
  }

}