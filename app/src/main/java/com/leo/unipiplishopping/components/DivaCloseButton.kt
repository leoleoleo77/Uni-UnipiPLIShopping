package com.leo.unipiplishopping.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.leo.unipiplishopping.AppConstants

@Composable
fun DivaCloseButton(homeState: MutableState<String>) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .size(88.dp)
            .clickable {
                homeState.value = AppConstants.NAVIGATION_HOME
            }
            .padding(top = 32.dp),
    )
}