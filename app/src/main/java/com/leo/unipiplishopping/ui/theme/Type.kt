package com.leo.unipiplishopping.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DivaTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 44.sp,
        letterSpacing = 4.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 32.sp,
        letterSpacing = 2.sp
    ),
    titleSmall = TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    ),
    bodyMedium = TextStyle(
        color = Color.White,
        fontSize = 12.sp,
        letterSpacing = 2.sp
    ),
    labelMedium = TextStyle(
        color = Color.White,
        fontSize = 12.sp,
        letterSpacing = 2.sp,
        fontWeight = FontWeight.Bold
    )

)