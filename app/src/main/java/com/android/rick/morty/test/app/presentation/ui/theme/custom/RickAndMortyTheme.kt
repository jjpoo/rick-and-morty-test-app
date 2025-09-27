package com.android.rick.morty.test.app.presentation.ui.theme.custom

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.rick.morty.test.app.presentation.ui.theme.Pink40
import com.android.rick.morty.test.app.presentation.ui.theme.Pink80
import com.android.rick.morty.test.app.presentation.ui.theme.Purple40
import com.android.rick.morty.test.app.presentation.ui.theme.Purple80
import com.android.rick.morty.test.app.presentation.ui.theme.PurpleGrey40
import com.android.rick.morty.test.app.presentation.ui.theme.PurpleGrey80

// Colors
val darkColors = CustomColors(
    primary = PurpleGrey40,
    onPrimary = PurpleGrey80,
    background = Color.Black,
    onBackground = Purple80,
    secondary = Pink40,
    onSecondary = Pink80
)

val lightColors = CustomColors(
    primary = PurpleGrey80,
    onPrimary = PurpleGrey40,
    background = Color.White,
    onBackground = Purple40,
    secondary = Pink80,
    onSecondary = Pink40
)

// Typography
val typography = CustomTypography(
    titleSmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
    bodySmall = TextStyle(fontSize = 8.sp),
    bodyNormal = TextStyle(fontSize = 12.sp),
    bodyLarge = TextStyle(fontSize = 16.sp)
)

// Shapes
val shapes = CustomShapes(
    inputForm = RoundedCornerShape(8.dp),
    button = RoundedCornerShape(20)
)

// Spacing
val sizes = CustomSpacing(
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
    extraLarge = 24.dp
)

@Composable
fun RinkAndMortyAppTheme(
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkModeEnabled) darkColors else lightColors
    CompositionLocalProvider(
        RickAndMortyColorScheme provides colorScheme,
        RickAndMortyTextStyles provides typography,
        RickAndMortyTextShapes provides shapes,
        RickAndMortySizes provides sizes,
        content = content
    )
}

object RickAndMortyTheme {
    val color: CustomColors
        @Composable get() = RickAndMortyColorScheme.current

    val txtStyle: CustomTypography
        @Composable get() = RickAndMortyTextStyles.current

    val space: CustomSpacing
        @Composable get() = RickAndMortySizes.current
}