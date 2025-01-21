package com.leo.unipiplishopping

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leo.unipiplishopping.authentication.AuthUtils
import com.leo.unipiplishopping.authentication.AuthenticationView
import com.leo.unipiplishopping.home.HomeView
import com.leo.unipiplishopping.ui.theme.DivaTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val deepLinkArtworkId = intent.getStringExtra(AppConstants.DEEPLINK_KEY)

        enableEdgeToEdge()
        setContent { MyApp(deepLinkArtworkId) }
    }

}

@Composable
fun MyApp(deepLinkArtworkId: String?) {
    val authAgent = AuthUtils()
    val context = LocalContext.current
    val appPreferences = getAppPreferences(context)

    // Manage dark mode state
    val isDarkMode = remember { mutableStateOf(appPreferences.first) }

    // Manage language preference
    //val currentLocale = remember { mutableStateOf(Locale(appPreferences.second)) }

    val toggleDarkMode: () -> Unit = {
        val currentDarkMode = isDarkMode.value
        toggleDarkMode(context) // Update SharedPreferences
        isDarkMode.value = !currentDarkMode // Update state
    }

    val updateAppLocale: (Locale) -> Unit = { newLocale ->
        val config = context.resources.configuration
        Locale.setDefault(newLocale)
        config.setLocale(newLocale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        updateLanguage(context, newLocale.toLanguageTag())
    }

    updateAppLocale.invoke(Locale(appPreferences.second))

    DivaTheme(darkTheme = isDarkMode.value) {
        val navController = rememberNavController()
        val showLogin = deepLinkArtworkId == null && authAgent.getUser() == null

        NavHost(
            navController = navController,
            startDestination = if (showLogin) AppConstants.LOGIN else AppConstants.HOME
        ) {
            composable(AppConstants.HOME) {
                HomeView(authAgent, deepLinkArtworkId, toggleDarkMode, updateAppLocale)
            }
            composable(AppConstants.LOGIN) {
                AuthenticationView(authAgent, navController)
            }
        }
    }
}



