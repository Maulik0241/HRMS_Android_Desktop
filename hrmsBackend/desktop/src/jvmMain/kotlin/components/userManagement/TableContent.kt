package components.userManagement

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import client.*
import components.AskingDialog
import database.model.EmployeeItem
import kotlinx.coroutines.runBlocking

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(36.dp))
        Text(
            text = "No",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.width(26.dp))
        Text(
            text = "Name",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(26.dp))
        Text(
            text = "Email",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1.1f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Contact",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Destination",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1.1f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Joining Date",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Action",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1.1f)
        )
    }
}

@Composable
fun EmployeeRow(employee: EmployeeItem) {
    var deleteDialogShown by remember { mutableStateOf(false) }
    val selectedItemId = remember { mutableStateOf<String?>(null) }
    val selectedItem = remember { mutableStateOf<EmployeeItem?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var employeeList by remember { mutableStateOf<List<EmployeeItem>>(emptyList()) }


    suspend fun fetchHolidaysAndUpdateState() {
        isLoading = true // Set loading indicator to true
        employeeList = emptyList() // Clear previous holidays
        val fetchedEmployee = getAllUsers()
        employeeList = fetchedEmployee
        isLoading = false // Set loading indicator to false after data is fetched
        println(employeeList)
    }
    LaunchedEffect(Unit) {
        fetchHolidaysAndUpdateState()
    }

    SelectionContainer {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (employee.email.isNotEmpty()) {
                Spacer(modifier = Modifier.width(26.dp))
                Text(
                    text = employee.number.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.weight(0.3f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = employee.name,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(26.dp))
                Text(
                    text = employee.email,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.2f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = employee.contactNumber,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = employee.role,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = employee.signUpDate,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Row(modifier = Modifier.weight(1f)) {
                    Box(Modifier.padding(horizontal = 4.dp)) {
                        IconButton(
                            onClick = { selectedItem.value = employee },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF11009E)
                            )
                        }
                    }

                    Box(Modifier.padding(start = 4.dp)) {
                        IconButton(
                            onClick = {
                                selectedItemId.value = employee.id
                                deleteDialogShown = true
                                println(employee.id)
                            },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }

            }
        }
    }

    selectedItem.value?.let { selectedEmployee ->
        EmployeeDialogBox(
            showDialog = selectedItem.value != null,
            onDismiss = { selectedItem.value = null },
            dialogTitle = "Edit Employee",
            employeeName = selectedEmployee.name,
            employeeEmail = selectedEmployee.email,
            contactNumber = selectedEmployee.contactNumber,
            role = selectedEmployee.role,
            onConfirm = { editedName, editedEmail, editedContact, editedRole ->
                runBlocking {
                    isLoading = true // Set loading indicator to true
                    updateEmployee(selectedEmployee.id, editedName, editedEmail,editedRole, editedContact)
                    employeeList = getAllUsers() // Refetch employees after update
                    isLoading = false // Set loading indicator to false
                }
                selectedItem.value = null
            }
        )
    }

    AskingDialog(
        showDialog = deleteDialogShown,
        onDismiss = { deleteDialogShown = false },
        dialogTitle = "Delete Employee",
        announcementText = "Are you sure you want to delete this employee?",
        onConfirm = {
            runBlocking {
                selectedItemId.value?.let { deleteUserById(it) }
                employeeList = getAllUsers() // Refetch employees after delete
            }
            selectedItemId.value = null
        }
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}