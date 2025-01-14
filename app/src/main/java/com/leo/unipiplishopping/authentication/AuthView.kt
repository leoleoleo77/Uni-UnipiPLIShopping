package com.leo.unipiplishopping.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.leo.unipiplishopping.R

@Composable
fun AuthenticationView(navController: NavHostController) {
    // State to determine whether to show login or registration
    var isLoginScreen by remember { mutableStateOf(true) }
    val authAgent = AuthUtils()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, // Optional, defaults to black if using dark mode
        contentWindowInsets = WindowInsets(0) // Ignore all system insets

    ) { padding ->
        Column {
            HalfScreenHeightBox()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color = Color.Black),
                contentAlignment = Alignment.TopCenter
                //contentAlignment = BiasAlignment(0f, 0.2f)
            ) {
                // Dynamic box content
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f), // Width relative to screen width
                    //.padding()
                    //.background(Color.White, shape = MaterialTheme.shapes.medium),
                    //.padding(24.dp), // Padding inside the box
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isLoginScreen) {
                        LoginView(authAgent, navController)
                    } else {
                        RegisterView(authAgent, navController)
                    }
                    TextButton(
                        onClick = { isLoginScreen = !isLoginScreen } // Toggle login/register
                    ) {
                        val changeAuthText =
                            stringResource(id =
                                if (isLoginScreen) R.string.create_account_label
                                else  R.string.login_label)
                        Text(changeAuthText)
                    }
                }
            }
        }
    }
}

@Composable
private fun HalfScreenHeightBox() {
    Box(modifier = Modifier.fillMaxHeight(0.5f))
}
