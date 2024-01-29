package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navigation.Screen
import org.w3c.dom.Text
import java.time.format.TextStyle


@Composable
fun HeaderTitle(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 42.dp, top = 10.dp),
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF093a83)
        )
    }
}
