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

@Composable
fun ProfileDetails(
    homeState: MutableState<String>,
    authAgent: AuthUtils,
) {
    // State variables for text fields and slider
    var userName by remember { mutableStateOf(authAgent.getUser()?.displayName) }
    var email by remember { mutableStateOf(authAgent.getUser()?.email) }
    var pass by remember { mutableStateOf("") }
    val sharedPreferences = getAppPreferences(LocalContext.current)
    val darkMode = sharedPreferences.first
    var isRadioSelected by remember { mutableStateOf(darkMode) }
    val language = sharedPreferences.second
    var languageSelected by remember { mutableStateOf(language) }
    // Screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Header(homeState)
        QuarterScreenHeightBox()
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "darkmode",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                RadioButton(
                    selected = isRadioSelected,
                    onClick = { isRadioSelected = !isRadioSelected },
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

            DropdownButtonExample()

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
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.profile_details),
            style = MaterialTheme.typography.titleMedium
        )
        DivaCloseButton(homeState = homeState)
    }
}

@Composable
fun DropdownButtonExample() {
    val expanded = remember { androidx.compose.runtime.mutableStateOf(false) }
    val options = listOf("Option 1", "Option 2", "Option 3")
    val selectedOption = remember { androidx.compose.runtime.mutableStateOf(options[0]) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(onClick = { expanded.value = !expanded.value }) {
            Text(text = "Dropdown")
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                    selectedOption.value = option
                    expanded.value = false
                },
                    text = { Text("waddup") }
                )
            }
        }

        Text(text = "Selected: ${selectedOption.value}", modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}

@Composable
private fun QuarterScreenHeightBox() {
    Box(modifier = Modifier.fillMaxHeight(0.25f))
}
