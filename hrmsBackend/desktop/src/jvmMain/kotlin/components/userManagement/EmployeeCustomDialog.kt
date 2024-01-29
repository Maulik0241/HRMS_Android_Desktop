package components.userManagement

import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun EmployeeDialogBox(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    employeeName: String,
    employeeEmail: String,
    role: String,
    contactNumber: String,
    onConfirm: (String, String, String, String) -> Unit
) {
    val dialogBoxState = rememberDialogState(position = WindowPosition(x= 250.dp, y = 200.dp), size = DpSize(800.dp, 400.dp))
    if (showDialog) {

        Dialog(
            onCloseRequest = { onDismiss() },
            state = dialogBoxState,
            resizable = true,
            title = "Edit Employee"
        ) {
            var editedName by remember { mutableStateOf(employeeName) }
            var editedEmail by remember { mutableStateOf(employeeEmail) }
            var editedRole by remember { mutableStateOf(role) }
            var editedContactNumber by remember { mutableStateOf(contactNumber) }
            val scrollable = rememberScrollState()

            Column(
                modifier = Modifier.padding(16.dp)
                    .verticalScroll(state = scrollable, enabled = true),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = dialogTitle, style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text(text = "Name") },
                        readOnly = true
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    OutlinedTextField(
                        value = editedEmail,
                        onValueChange = { editedEmail = it },
                        label = { Text(text = "Email") },
                        readOnly = true
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    OutlinedTextField(
                        value = editedContactNumber,
                        onValueChange = { editedContactNumber = it },
                        label = { Text(text = "Contact number") },
                        readOnly = true
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    OutlinedTextField(
                        value = editedRole,
                        onValueChange = { editedRole = it },
                        label = { Text(text = "Role") },
                        maxLines = 1
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onConfirm(editedName, editedEmail, editedRole, editedContactNumber)
                            onDismiss()
                        }
                    ) {
                        Text(text = if (dialogTitle == "Add Employee") "Submit" else "Update")
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