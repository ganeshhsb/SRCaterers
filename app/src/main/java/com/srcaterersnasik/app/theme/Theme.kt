package com.srcaterersnasik.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryDarkColor,
    primaryVariant = primaryColor,
    onPrimary = primaryTextColor,

//    secondary = secondaryDarkColor,
//    secondaryVariant = secondaryColor,
//    onSecondary = secondaryTextColor,

    surface = Color.White,
    onSurface = Color.Black,

    background = Color.White,
    onBackground = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = primaryLightColor,
    primaryVariant = primaryColor,
    onPrimary = primaryTextColor,

//    secondary = secondaryLightColor,
//    secondaryVariant = secondaryColor,
//    onSecondary = secondaryTextColor,

    surface = Color.White,
    onSurface = Color.Black,

    background = Color.White,
    onBackground = Color.Black,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SRCaterersTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}