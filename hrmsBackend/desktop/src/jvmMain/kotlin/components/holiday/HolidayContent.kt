package components.holiday


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import client.deleteHoliday
import client.fetchHolidays
import client.updateHoliday
import components.AskingDialog
import components.HolidayCustomDialog
import database.model.HolidayItem
import kotlinx.coroutines.runBlocking

@Composable
fun HolidayContent() {
    val scrollable = rememberScrollState()
    var holidays by remember { mutableStateOf<List<HolidayItem>>(emptyList()) }
    val showDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<HolidayItem?>(null) }
    var deleteDialogShown by remember { mutableStateOf(false) }
    val selectedItemId = remember { mutableStateOf<String?>(null) } // Added selectedItemId

    var isLoading by remember { mutableStateOf(false) }

    suspend fun fetchHolidaysAndUpdateState() {
        isLoading = true // Set loading indicator to true
        holidays = emptyList() // Clear previous holidays
        val fetchedHolidays = fetchHolidays()
        holidays = fetchedHolidays
        isLoading = false // Set loading indicator to false after data is fetched
        println(holidays)
    }
    LaunchedEffect(Unit) {
        fetchHolidaysAndUpdateState()
    }


    Column(modifier = Modifier.verticalScroll(state = scrollable, enabled = true)) {
        if (isLoading) {
            // Show loading indicator
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            )
        } else {
            holidays.forEachIndexed { index, holiday ->
                val cardColor = when (index % 3) {
                    0 -> Color.Yellow
                    1 -> MaterialTheme.colors.secondaryVariant
                    else -> MaterialTheme.colors.primaryVariant
                }
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(10.dp),
                    backgroundColor = Color(0xFFF5F5F5),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            IconButton(onClick = { selectedItem.value = holiday }) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit Holiday",
                                    tint = Color(0xFF11009E)
                                )
                            }
                            IconButton(onClick = {
                                selectedItemId.value = holiday.id // Update selectedItemId
                                deleteDialogShown = true
                            }) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete Holiday",
                                    tint = Color.Red
                                )
                            }
                        }
                        Text(
                            text = holiday.description,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.Start),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = holiday.date,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }

    selectedItem.value?.let { selectedHoliday ->
        HolidayCustomDialog(
            showDialog = selectedItem.value != null,
            onDismiss = { selectedItem.value = null },
            dialogTitle = "Edit Holiday",
            holidayDate = selectedHoliday.date,
            holidayDescription = selectedHoliday.description,
            onConfirm = { editedDate, editedDescription ->
                runBlocking {
                    isLoading = true // Set loading indicator to true
                    val result = updateHoliday(selectedHoliday.id, editedDate, editedDescription)
                    fetchHolidaysAndUpdateState() // Refetch holidays after update
                }
                selectedItem.value = null
            }
        )
    }

    AskingDialog(
        showDialog = deleteDialogShown,
        onDismiss = { deleteDialogShown = false },
        dialogTitle = "Delete Holiday",
        announcementText = "Are you sure you want to delete this holiday?",
        onConfirm = {
            runBlocking {
                val result = selectedItemId.value?.let { deleteHoliday(it) } // Use selectedItemId
                fetchHolidaysAndUpdateState()
                println(result)
            }
            selectedItem.value = null
            selectedItemId.value = null // Reset selectedItemId
        }
    )
}
