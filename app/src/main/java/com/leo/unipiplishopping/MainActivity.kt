package com.leo.unipiplishopping

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
        setContent {
            val locale = Locale("en")
            updateLocale(locale)
            DivaTheme {
                MyApp(deepLinkArtworkId)
            }
        }
    }


    private fun updateLocale(locale: Locale) {
        val config = resources.configuration
        val updatedConfig = Configuration(config)
        updatedConfig.setLocale(locale)

        // Apply the new configuration to the context
        createConfigurationContext(updatedConfig)
        resources.updateConfiguration(updatedConfig, resources.displayMetrics)
    }
}

@Composable
fun MyApp(deepLinkArtworkId: String?) {
    val authAgent = AuthUtils()

    MaterialTheme {
        val navController = rememberNavController()
        val showLogin = deepLinkArtworkId == null && authAgent.getUser() == null
        NavHost(
            navController = navController,
            startDestination = if (showLogin) AppConstants.LOGIN else AppConstants.HOME
        ) {
            composable(AppConstants.HOME) { HomeView(authAgent, deepLinkArtworkId) }
            composable(AppConstants.LOGIN) { AuthenticationView(authAgent, navController) }
        }
    }
}

