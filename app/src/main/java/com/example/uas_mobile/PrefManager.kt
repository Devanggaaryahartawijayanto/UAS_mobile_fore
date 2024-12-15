package com.example.uas_mobile

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context : Context){
    private val sharedPref : SharedPreferences

    companion object {
        private const val PREF_NAME = "myPref"
        private const val IS_LOGIN = "IS_LOGIN"
        private const val NAME = "NAME"
        private const val ROLE = "USER"
        private const val PASSWORD = "PASSWORD"
        private const val EMAIL = "EMAIL"

        @Volatile
        private var instance : PrefManager? = null
        fun getInstance(context: Context) : PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context).also {
                    instance = it
                }
            }
        }
    }

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPref.edit().clear().apply()
    }

    fun setLogin(isLogin : Boolean) {
        sharedPref.edit().putBoolean(IS_LOGIN, isLogin).apply()
    }

    fun getLogin() : Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

    fun setUsername(username : String) {
        sharedPref.edit().putString(NAME, username).apply()
    }

    fun getUsername() : String? {
        return sharedPref.getString(NAME, null)
    }

    fun setEmail(email: String) {
        sharedPref.edit().putString(EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return sharedPref.getString(EMAIL, null)
    }

    fun setPassword(password : String) {
        sharedPref.edit().putString(PASSWORD, password).apply()
    }

    fun getPassword() : String? {
        return sharedPref.getString(PASSWORD, null)
    }

    fun setRole(role : String) {
        sharedPref.edit().putString(ROLE, role).apply()
    }

    fun getRole() : String? {
        return sharedPref.getString(ROLE, null)
    }
}