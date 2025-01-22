package com.leo.unipiplishopping.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun DivaTextField(
    placeholderResource: Int?,
    placeHolderText: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = ({
            placeholderResource?.let {
                Text(text = stringResource(id = it))
            } ?: Text(text = placeHolderText ?: "")
        }),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary, // Background color when focused
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary, // Background color when unfocused
            focusedTextColor = MaterialTheme.colorScheme.primary,    // Text color when focused
            unfocusedTextColor = MaterialTheme.colorScheme.primary,  // Text color when unfocused
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,// Cursor color
        )
    )
}