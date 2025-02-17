package com.leo.unipiplishopping

import android.content.Context
import android.content.SharedPreferences

fun getAppPreferences(context: Context): Pair<Boolean, String> {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    val isDarkMode = sharedPreferences
        .getBoolean(AppConstants.SP_IS_DARK_MODE, true)

    val language = sharedPreferences
        .getString(AppConstants.SP_LANGUAGE, AppConstants.ENGLISH) ?: AppConstants.ENGLISH

    return Pair(isDarkMode, language)
}

fun saveSelectedColorMode(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    val isDarkMode = sharedPreferences
        .getBoolean(AppConstants.SP_IS_DARK_MODE, true)

    sharedPreferences.edit()
        .putBoolean(AppConstants.SP_IS_DARK_MODE, !isDarkMode)
        .apply()
}


fun saveSelectedLanguage(context: Context, language: String) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    sharedPreferences.edit()
        .putString(AppConstants.SP_LANGUAGE, language)
        .apply()
}