package com.leo.unipiplishopping.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    background = backgroundBlack
)

private val LightColorScheme = lightColorScheme(
    primary = secondaryDark,
    secondary = primaryDark,
    tertiary = backgroundBlack,
    background = tertiaryDark
)

@Composable
fun DivaTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DivaTypography,
        content = content
    )
}
