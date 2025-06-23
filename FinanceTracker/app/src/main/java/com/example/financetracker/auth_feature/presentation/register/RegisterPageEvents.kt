package com.example.financetracker.auth_feature.presentation.register

sealed class RegisterPageEvents {
    data class ChangeEmail(val email: String) : RegisterPageEvents()
//    data class ChangeUserName(val name: String) : RegisterPageEvents()
    data class ChangePassword(val password: String) : RegisterPageEvents()
    data class ChangeConfirmPassword(val confirmPassword: String) : RegisterPageEvents()
    data object SubmitRegister: RegisterPageEvents()
    data object RegistrationSuccess: RegisterPageEvents()
    data class RegistrationFailure(val error: String): RegisterPageEvents()
}
