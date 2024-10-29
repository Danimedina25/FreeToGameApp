package com.danifitdev.freetogameapp.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.danifitdev.freetogameapp.R

val MontserratFont = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

val BodoniFont = FontFamily(
    Font(R.font.bodoni_flf_roman, FontWeight.Normal),
    Font(R.font.bodoni_flf_bold, FontWeight.Bold),
    Font(R.font.bodoni_flf_italic, FontWeight.Light),
    Font(R.font.bodoni_flf_bold_italic, FontWeight.ExtraBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    /* Other default text styles to override*/
    titleLarge = TextStyle(
        fontFamily = BodoniFont,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = BodoniFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)