package com.snick55.smartlist.core

import android.content.SharedPreferences
import javax.inject.Inject

interface PreferenceSource<T> {

    fun putValue(value: T)

    fun getValue(): T




    class NamePreferenceSource @Inject constructor(
        private val sharedPreferences: SharedPreferences
    ): PreferenceSource<String>{


        override fun putValue(value: String) {
            sharedPreferences.edit().putString(NAME_KEY,value).apply()
        }

        override fun getValue(): String {
            return sharedPreferences.getString(NAME_KEY, "") ?: ""
        }
    }

    private companion object {
        private const val NAME_KEY = "NAME_KEY"

    }

}