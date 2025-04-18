package narek.dallakyan.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LightColorScheme = lightColorScheme(
    primary = Colors.BackgroundTurquoise,
    secondary = Colors.BackgroundTurquoise,
    tertiary = Colors.BackgroundTurquoise,
    background = Colors.BackgroundTurquoise
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun AppThemeTransparentStatusBars(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent)

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun AppThemeHiddenSystemBars(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    systemUiController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    systemUiController.isSystemBarsVisible = false

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
