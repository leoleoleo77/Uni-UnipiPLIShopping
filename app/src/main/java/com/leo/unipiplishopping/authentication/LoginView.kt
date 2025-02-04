package com.leo.unipiplishopping.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R
import com.leo.unipiplishopping.components.DivaTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginView(
    authAgent: AuthUtils,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var loginResult by remember {
        mutableStateOf<AuthResult>(AuthResult.Unknown) }

    LoginTitle()
    DivaTextField(
        placeholderResource = R.string.email_label,
        value = email,
        onValueChange = { email = it }
    )
    DivaTextField(
        placeholderResource = R.string.password_label,
        value = pass,
        onValueChange = { pass = it },
        isPassword = true
    )
    RegisterFailedMessage(loginResult)
    LoginButton {
        // check the credential entered by the user asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            val result = authAgent.attemptLogin(email, pass)

            withContext(Dispatchers.Main) {
                loginResult = result // Update login result dynamically
                if (result is AuthResult.Success) {
                    navController.navigate(AppConstants.HOME)
                }
            }
        }
    }
}

@Composable
private fun LoginTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        textAlign = TextAlign.Start,
        text = stringResource(id = R.string.login),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun RegisterFailedMessage(authResult: AuthResult) {
    if (authResult != AuthResult.Fail) return

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = R.string.login_failed),
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontSize = 12.sp,         // Small font size
            color = Color.Red        // Red color for error messages
        )
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Text(stringResource(id = R.string.continue_label))
    }
}
