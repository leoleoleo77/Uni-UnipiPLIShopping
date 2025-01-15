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
import com.leo.unipiplishopping.authentication.AuthenticationView
import com.leo.unipiplishopping.home.HomeView
import com.leo.unipiplishopping.ui.theme.DivaTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val locale = Locale("en")
            updateLocale(locale)
            DivaTheme {
                MyApp()
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
fun MyApp() {
    // Material theme applied
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppConstants.LOGIN
        ) {
            composable(AppConstants.HOME) { HomeView(navController) }
            composable(AppConstants.LOGIN) { AuthenticationView(navController) }
        }
    }
}

