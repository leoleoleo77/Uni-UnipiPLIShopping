package com.leo.unipiplishopping

class AppConstants {
    companion object {
        /* Database Constants */
        const val ARTWORK_COLLECTION = "artwork"
        const val PURCHASES_COLLECTION = "purchases"

        /* Navigation Constants */
        const val HOME = "home"
        const val LOGIN = "login"

        /* HomeView Constants */
        const val NAVIGATION_HOME = "navigation_home"
        const val NAVIGATION_ARTWORK_DETAILS = "artwork_detail"
        const val NAVIGATION_PROFILE_DETAILS = "profile_detail"

        /* Notification Constants */
        const val CHANNEL_ID = "id0"
        const val CHANNEL_NAME = "channel0"

        /* Deeplink Constants */
        const val DEEPLINK_KEY = "ARTWORK_ID"

        /* Purchases Entry Constants*/
        const val USER_NAME = "userName"
        const val ARTWORK_ID = "artworkId"
        const val TIMESTAMP = "timestamp"

        /*Shared Preferences*/
        const val APP_PREFERENCES = "app_preferences"
        const val SP_IS_DARK_MODE = "is_dark_mode"
        const val SP_LANGUAGE = "language"
        const val ENGLISH = "en"
        const val GREEK = "gr"
        const val FRENCH = "fr"
    }
}