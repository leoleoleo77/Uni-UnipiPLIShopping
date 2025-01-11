package com.leo.unipiplishopping.authentication


import android.net.wifi.hotspot2.pps.Credential.UserCredential
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leo.unipiplishopping.R
import com.leo.unipiplishopping.ui.theme.DivaTextField

@Composable
fun LoginView(authAgent: AuthUtils) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var loginResult by remember {
        mutableStateOf(AuthResult.Unknown)
    }

    LoginTitle()
    DivaTextField(
        placeholderResource = R.string.email_label,
        value = email,
        onValueChange = { email = it }
    )
    DivaTextField(
        placeholderResource = R.string.password_label,
        value = pass,
        onValueChange = { pass = it }
    )
    LoginFailedMessage(loginResult)
    LoginButton {
        loginResult = authAgent.attemptLogin(email, pass)
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
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun LoginFailedMessage(authResult: AuthResult) {
    // Define a TextStyle for error messages

    val ErrorTextStyle = TextStyle(
        fontSize = 12.sp,         // Small font size // Optional: bold font weight
        color = Color.Red        // Red color for error messages
    )

    if (authResult == AuthResult.Fail || authResult == AuthResult.Error) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.login_failed),
            textAlign = TextAlign.Start,
            style = ErrorTextStyle
        )
    }
}

@Composable
private fun LoginButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // Background color
            contentColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Text(stringResource(id = R.string.continue_label))
    }
}
