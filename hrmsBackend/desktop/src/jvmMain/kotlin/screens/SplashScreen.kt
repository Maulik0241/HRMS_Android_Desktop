package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashScreenFinished: () -> Unit, content: @Composable () -> Unit) {
    val splashScreenDuration = 2000L // Adjust as needed

    // Simulate some loading process
    LaunchedEffect(Unit) {
        delay(splashScreenDuration)
        onSplashScreenFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        // Show a loading indicator or your custom splash screen UI
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = Color.White
        )
    }
}