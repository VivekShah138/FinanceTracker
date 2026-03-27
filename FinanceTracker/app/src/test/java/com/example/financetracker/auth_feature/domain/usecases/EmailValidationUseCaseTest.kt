package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.domain.usecases.local.validation.EmailValidationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

 class EmailValidationUseCaseTest{
  private lateinit var emailValidationUseCase: EmailValidationUseCase

  @Before
  fun setUp() {
   emailValidationUseCase = EmailValidationUseCase()
  }

  @Test
  fun `blank email returns error`() = runTest {
   val result = emailValidationUseCase("")
   assertFalse(result.isSuccessful)
   assertEquals("Please Enter The Email", result.errorMessage)
  }

  @Test
  fun `invalid email format returns error`() = runTest {
   val result = emailValidationUseCase("invalid-email")
   assertFalse(result.isSuccessful)
   assertEquals("Please Enter The Valid Email", result.errorMessage)
  }

  @Test
  fun `valid email returns success`() = runTest {
   val result = emailValidationUseCase("test@example.com")
   assertTrue(result.isSuccessful)
   assertNull(result.errorMessage)
  }
 }