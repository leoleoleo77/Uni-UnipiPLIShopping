package com.leo.unipiplishopping.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.leo.unipiplishopping.AppConstants
import com.leo.unipiplishopping.R
import com.leo.unipiplishopping.authentication.AuthUtils
import com.leo.unipiplishopping.components.DivaCloseButton
import com.leo.unipiplishopping.components.DivaTextField
import com.leo.unipiplishopping.getAppPreferences
import com.leo.unipiplishopping.toggleDarkMode
import java.util.Locale

@Composable
fun ProfileDetails(
    homeState: MutableState<String>,
    authAgent: AuthUtils,
    toggleDarkMode: () -> Unit,
    updateAppLocale: (Locale) -> Unit
) {
    val thisContext = LocalContext.current

    var userName by remember { mutableStateOf(authAgent.getUser()?.displayName) }
    var email by remember { mutableStateOf(authAgent.getUser()?.email) }
    var pass by remember { mutableStateOf("") }

    val sharedPreferences = getAppPreferences(LocalContext.current)
    var isRadioSelected by remember { mutableStateOf(sharedPreferences.first) }
    var languageSelected by remember { mutableStateOf(sharedPreferences.second) }
    val expanded = remember { mutableStateOf(false) }
    val options = listOf(
        stringResource(R.string.english_label),
        stringResource(R.string.greek_label),
        stringResource(R.string.french_label))
    val selectedOption = remember { mutableStateOf(options[0]) }

    @Composable
    fun ProfileDetails() {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.profile_details),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        DivaTextField(
            placeholderResource = R.string.user_name_label,
            value = userName ?: "",
            onValueChange = { userName = it }
        )
        DivaTextField(
            placeholderResource = R.string.email_label,
            value = email ?: "",
            onValueChange = { email = it }
        )
        DivaTextField(
            placeholderResource = R.string.password_label,
            value = pass,
            onValueChange = { pass = it },
        )

        // Save Button
        Button(
            onClick = {  },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Text(stringResource(id = R.string.save_label))
        }
    }

    @Composable
    fun Settings() {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(id = R.string.settings_label),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.dark_mode_label),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(end = 8.dp)
            )
            RadioButton(
                selected = isRadioSelected,
                onClick = {
                    isRadioSelected = !isRadioSelected
                    toggleDarkMode.invoke()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White,
                    unselectedColor = Color.Gray
                )
            )
            Text(
                text = if (isRadioSelected) "On" else "Off",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = stringResource(id = R.string.language_label),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
            )

            Column {
                OutlinedButton(
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Text(text = selectedOption.value)
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    containerColor = MaterialTheme.colorScheme.secondary,
                ) {
                    options.forEachIndexed { i, language ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption.value = language
                                expanded.value = false
                                updateAppLocale.invoke(indexToLocale(i))
                            },
                            text = { Text(language) },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.primary,
                            ),
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header(homeState)

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box (Modifier.height(36.dp))
            ProfileDetails()
            Box (Modifier.height(36.dp))
            Settings()
        }
    }
}

@Composable
private fun Header(
    homeState: MutableState<String>,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End,
    ) { DivaCloseButton(homeState = homeState) }
}

private fun indexToLocale(index: Int): Locale {
    return when(index) {
        0 -> Locale(AppConstants.ENGLISH)
        1 -> Locale(AppConstants.GREEK)
        else -> Locale(AppConstants.FRENCH)
    }
}