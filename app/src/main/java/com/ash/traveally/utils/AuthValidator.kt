package com.ash.traveally.utils

import android.util.Patterns

object AuthValidator {
    fun isValidName(name: String): Boolean = name.length >= 3 && name.all { it.isLetter() || it == ' ' }
    fun isValidEmail(email: String): Boolean = email.trim().length in (4..30) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String): Boolean = password.trim().length in (6..15)
    fun isValidUsername(username: String): Boolean = Regex("^[a-zA-Z0-9._-]{3,15}\$").matches(username)
    fun isValidPhoneNumber(phoneNumber: String): Boolean =  phoneNumber.trim().length in (10..14) && Patterns.PHONE.matcher(phoneNumber).matches()
    fun arePasswordAndConfirmPasswordSame(password: String, confirmPassword: String): Boolean = password.equals(confirmPassword)
}