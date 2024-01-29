package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AskingDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    announcementText: String,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),

            ) {
                Text(text = dialogTitle, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = announcementText)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text(text = "No")
                    }
                }
            }
        }
    }
}
