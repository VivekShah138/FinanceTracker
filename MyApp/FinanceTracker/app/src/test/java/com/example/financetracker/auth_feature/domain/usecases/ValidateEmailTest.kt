package com.example.financetracker.auth_feature.domain.usecases

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

 class ValidateEmailTest{
  private lateinit var validateEmail: ValidateEmail

  @Before
  fun setUp() {
   validateEmail = ValidateEmail()
  }

  @Test
  fun `blank email returns error`() = runTest {
   val result = validateEmail("")
   assertFalse(result.isSuccessful)
   assertEquals("Please Enter The Email", result.errorMessage)
  }

  @Test
  fun `invalid email format returns error`() = runTest {
   val result = validateEmail("invalid-email")
   assertFalse(result.isSuccessful)
   assertEquals("Please Enter The Valid Email", result.errorMessage)
  }

  @Test
  fun `valid email returns success`() = runTest {
   val result = validateEmail("test@example.com")
   assertTrue(result.isSuccessful)
   assertNull(result.errorMessage)
  }
 }