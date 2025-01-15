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
import com.leo.unipiplishopping.ui.theme.DivaTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterView(
    authAgent: AuthUtils,
    navController: NavHostController
) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    var registerResult by remember {
        mutableStateOf<AuthResult>(AuthResult.Unknown) }

    RegisterTitle()
    DivaTextField(
        placeholderResource = R.string.user_name_label,
        value = userName,
        onValueChange = { userName = it }
    )
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
    RegisterFailedMessage(registerResult)
    RegisterButton {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val result = authAgent.attemptRegister(userName, email, pass)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                if (result is AuthResult.Success) {
                    navController.navigate(AppConstants.HOME)
                } else {
                    registerResult = AuthResult.Fail
                }
            }
        }
    }
}

@Composable
private fun RegisterTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        textAlign = TextAlign.Start,
        text = stringResource(id = R.string.register),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun RegisterButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
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

@Composable
private fun RegisterFailedMessage(authResult: AuthResult) {
    if (authResult != AuthResult.Fail) return

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = R.string.register_failed),
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontSize = 12.sp,
            color = Color.Red
        )
    )

}