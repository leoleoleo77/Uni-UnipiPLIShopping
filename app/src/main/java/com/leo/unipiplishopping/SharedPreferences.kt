package com.leo.unipiplishopping

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

fun getAppPreferences(context: Context): Pair<Boolean, String> {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    val isDarkMode = sharedPreferences
        .getBoolean(AppConstants.SP_IS_DARK_MODE, true)

    val language = sharedPreferences
        .getString(AppConstants.SP_LANGUAGE, AppConstants.ENGLISH) ?: AppConstants.ENGLISH

    return Pair(isDarkMode, language)
}

fun updateLocale(context: Context, locale: Locale) {
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
}

fun toggleDarkMode(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    val isDarkMode = sharedPreferences
        .getBoolean(AppConstants.SP_IS_DARK_MODE, true)

    sharedPreferences.edit()
        .putBoolean(AppConstants.SP_IS_DARK_MODE, !isDarkMode)
        .apply()
}


fun updateLanguage(context: Context, language: String) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    sharedPreferences.edit()
        .putString(AppConstants.SP_LANGUAGE, language)
        .apply()
}