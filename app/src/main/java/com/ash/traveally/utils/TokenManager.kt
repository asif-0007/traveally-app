package com.ash.traveally.utils

import android.content.Context
import android.content.SharedPreferences
import com.ash.traveally.utils.Constants.PREFS_TOKEN_FILE
import com.ash.traveally.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? = prefs.getString(USER_TOKEN, null)
}