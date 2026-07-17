package com.example.study.service

import android.content.Context
import androidx.core.content.edit

class UserPrefs (context : Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

    /// fun save login state
    fun saveLoginState(isLoggedIn: Boolean, username: String) {
        prefs.edit {
            putBoolean("is_logged_in", isLoggedIn).putString("username", username)
        }
    }

    fun isLoggedIn()  : Boolean{
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getUsername() : String {
        return prefs.getString("username", "").orEmpty()
    }
}