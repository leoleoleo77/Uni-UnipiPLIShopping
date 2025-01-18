package com.leo.unipiplishopping

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    val appPreferences = getAppPreferences(LocalContext.current)
    val locale = Locale(appPreferences.second)
    val isDarkMode = appPreferences.first

    updateLocale(LocalContext.current, Locale("gr"))
    DivaTheme(darkTheme = isDarkMode) {
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


