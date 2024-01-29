package components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import navigation.NavController
import navigation.Screen
import navigation.rememberNavController
import presentation.Colors
import java.util.prefs.Preferences

@Composable
fun Container(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize().background(Colors.bgColor)
    ) {
        content()
    }
}


