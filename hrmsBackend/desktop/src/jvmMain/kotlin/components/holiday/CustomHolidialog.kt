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
fun HolidayCustomDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    holidayDate: String,
    holidayDescription: String,
    onConfirm: (String, String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            var editedDate by remember { mutableStateOf(holidayDate) }
            var editedDescription by remember { mutableStateOf(holidayDescription) }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = dialogTitle, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = editedDate,
                    onValueChange = { editedDate = it },
                    label = { Text(text = "Date") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    label = { Text(text = "Description") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onConfirm(editedDate, editedDescription)
                            onDismiss()
                        }
                    ) {
                        Text(text = if (dialogTitle == "Add Holiday") "Submit" else "Update")
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
