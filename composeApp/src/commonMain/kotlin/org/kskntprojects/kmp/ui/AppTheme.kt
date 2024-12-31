package org.kskntprojects.kmp.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme (
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6)
        ),
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
    ) {
        content()
    }
}

@Composable
fun getColorsTheme(): DarkModeColors {
    val isDarkMode = false

    val backgroundColor = if (isDarkMode) Color(0xFF4e2c49) else Color(0xFFFFFFFF)

    val itemBackgroundColor = if (isDarkMode) Color(0xFFFAC46A) else Color(0xFFFAC46A)
    val itemTextColor = if (isDarkMode) Color(0xFF4e2c49) else Color(0xFF4e2c49)
    val itemImageBackgroundColor = if (isDarkMode) Color(0xFFD8D7D5) else Color(0xFFD8D7D5)
    val itemImageTintColor = if (isDarkMode) Color(0xFFFFFFFF) else Color(0xFF291643)

    val cardBackgroundColor = if (isDarkMode) Color(0xFFFAC56B) else Color(0xFF4e2c49)
    val cardTextColor = if (isDarkMode) Color(0xFFD3D2D0) else Color(0xFFD3D2D0)
    val appBarBackgroundColor = if (isDarkMode) Color(0xFFFAC56B) else Color(0xFF4e2c49)
    val appBarTextColor = if (isDarkMode) Color(0xFFD3D2D0) else Color(0xFFD3D2D0)

    return DarkModeColors(
        itemBackgroundColor = itemBackgroundColor,
        itemTextColor = itemTextColor,
        backgroundColor = backgroundColor,
        itemImageBackgroundColor = itemImageBackgroundColor,
        itemImageTintColor = itemImageTintColor,
        cardBackgroundColor = cardBackgroundColor,
        cardTextColor = cardTextColor,
        appBarBackgroundColor = appBarBackgroundColor,
        appBarTextColor = appBarTextColor
    )
}

data class DarkModeColors(
    val itemBackgroundColor: Color,
    val itemTextColor: Color,
    val itemImageBackgroundColor: Color,
    val backgroundColor: Color,
    val itemImageTintColor: Color,
    val cardBackgroundColor: Color,
    val cardTextColor: Color,
    val appBarBackgroundColor: Color,
    val appBarTextColor: Color
)