package com.example.financetracker

import timber.log.Timber

object Logger {


    object Tag {
        const val LOGIN_VIEWMODEL = "LoginViewModel"
        const val REGISTER_VIEWMODEL = "RegisterViewModel"
        const val STARTUP_VIEWMODEL = "StartUpViewModel"
        const val STARTUP_SCREEN = "StartUpScreen"
    }

    fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Timber.tag(tag).e(throwable, message)
    }
}