package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomProjectDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    projectName: String = "",
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            var editedProjectName by remember { mutableStateOf(projectName) }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = dialogTitle, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = editedProjectName,
                    onValueChange = { editedProjectName = it },
                    label = { Text(text = "Project Name") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onConfirm(editedProjectName)
                            onDismiss()
                        }
                    ) {
                        Text(text = if (dialogTitle == "Add Project") "Add" else "Update")
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
