package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import client.addAnnouncement
import kotlinx.coroutines.runBlocking
import navigation.NavController
import presentation.Colors

@Composable
fun AnnouncementCard(navController:NavController,text: String, icon: ImageVector, modifier: Modifier = Modifier, content: @Composable () -> Unit) {

    val showDialog = remember { mutableStateOf(false) }
    val announcementText by remember { mutableStateOf("") }

    Card(
        modifier = modifier.fillMaxHeight().padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFFffffff),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF093a83),
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF093a83),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(30.dp))
                IconButton(
                    onClick = { showDialog.value = true }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF093a83),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.TopStart
            ) {
                content()
            }
        }
    }
    CustomDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = false },
        dialogTitle = "Add Announcement",
        announcementText = announcementText,
        onConfirm = { editedText ->
            runBlocking {
                val result = addAnnouncement(editedText)
                println(result)
            }
            showDialog.value = false
        }
    )
}
