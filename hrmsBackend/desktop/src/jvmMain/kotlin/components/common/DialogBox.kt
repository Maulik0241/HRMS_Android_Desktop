package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    announcementText: String,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            var editedText by remember { mutableStateOf(announcementText) }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = dialogTitle, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    label = { Text(text = "Announcement") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onConfirm(editedText)
                            onDismiss()
                        }
                    ) {
                        Text(text = if (dialogTitle == "Add Announcement") "Submit" else "Update")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}
