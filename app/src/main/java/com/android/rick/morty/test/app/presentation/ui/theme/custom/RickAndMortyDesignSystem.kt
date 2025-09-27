package com.android.rick.morty.test.app.presentation.ui.theme.custom

import android.annotation.SuppressLint
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class CustomColors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
    val secondary: Color,
    val onSecondary: Color,
)

data class CustomTypography(
    val titleSmall: TextStyle,
    val titleLarge: TextStyle,
    val body: TextStyle
)

data class CustomShapes(
    val inputForm: Shape,
    val button: Shape
)

data class CustomSizes(
    val small: Dp,
    val medium: Dp,
    val large: Dp
)

@SuppressLint("CompositionLocalNaming")
val RickAndMortyColorScheme = staticCompositionLocalOf {
    CustomColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified
    )
}

@SuppressLint("CompositionLocalNaming")
val RickAndMortyTextStyles = staticCompositionLocalOf {
    CustomTypography(
        titleLarge = TextStyle.Default,
        titleSmall = TextStyle.Default,
        body = TextStyle.Default
    )
}

@SuppressLint("CompositionLocalNaming")
val RickAndMortyTextShapes = staticCompositionLocalOf {
    CustomShapes(
        inputForm = RectangleShape,
        button = RectangleShape
    )
}

@SuppressLint("CompositionLocalNaming")
val RickAndMortySizes = staticCompositionLocalOf {
    CustomSizes(
        small = Dp.Unspecified,
        medium = Dp.Unspecified,
        large = Dp.Unspecified
    )
}