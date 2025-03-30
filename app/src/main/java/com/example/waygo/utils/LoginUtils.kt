package com.example.waygo.utils

import android.util.Patterns

class LoginUtils {
    fun isValidEmailAddress(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
