package database

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import client.*
import components.AskingDialog
import components.CustomDialog
import database.model.AnnouncementItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import presentation.Colors
import javax.swing.Scrollable

@Composable
fun AnnouncementContent() {
    val scrollable = rememberScrollState()
    var announcement by remember { mutableStateOf<List<AnnouncementItem>>(emptyList()) }
    val showDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<AnnouncementItem?>(null) }
    var deleteDialogShown by remember { mutableStateOf(false) }
    val selectedItemId = remember { mutableStateOf<String?>(null) } // Added selectedItemId

    var isLoading by remember { mutableStateOf(false)   }

    suspend fun fetchAnnouncementAndUpdateState() {
        isLoading = true // Set loading indicator to true
        announcement = emptyList() // Clear previous holidays
        val fetchedAnnouncement = fetchAnnouncements()
        announcement = fetchedAnnouncement
        isLoading = false // Set loading indicator to false after data is fetched
        println(announcement)
    }
    LaunchedEffect(Unit) {
        fetchAnnouncementAndUpdateState()
    }
    Column(modifier = Modifier.verticalScroll(state = scrollable, enabled = true)) {
        if (isLoading) {
            // Show loading indicator
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            )
        } else {
            announcement.forEachIndexed { index, announcement ->
                val cardColor = when (index % 3) {
                    0 -> Color(0xFFbde5eb)
                    1 -> Color(0xFFd7d7ff)
                    else -> Color(0xFFf7dae7)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            IconButton(onClick = { selectedItem.value = announcement }) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit Announcement",
                                    tint = Color(0xFF11009E)
                                )
                            }
                            IconButton(onClick = {
                                selectedItemId.value = announcement.id // Update selectedItemId
                                deleteDialogShown = true
                            }) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete Announcement",
                                    tint = Color.Red
                                )
                            }
                        }
                        Text(
                            text = announcement.description,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.Start),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = announcement.createdOn,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }

    selectedItem.value?.let { selectedAnnouncement ->
        CustomDialog(
            showDialog = selectedItem.value != null,
            onDismiss = { selectedItem.value = null },
            dialogTitle = "Edit Announcement",
            announcementText = selectedAnnouncement.description,
            onConfirm = { editedText ->
                runBlocking {
                    val result = updateAnnouncement(selectedAnnouncement.id, editedText)
                    fetchAnnouncementAndUpdateState()
//                    println(result)
                }
                selectedItem.value = null
            }
        )
    }

    AskingDialog(
        showDialog = deleteDialogShown,
        onDismiss = { deleteDialogShown = false },
        dialogTitle = "Delete Announcement",
        announcementText = "Are you sure you want to delete this announcement?",
        onConfirm = {
            runBlocking {
                val result = selectedItemId.value?.let { deleteAnnouncement(it) } // Use selectedItemId
                fetchAnnouncementAndUpdateState()
                println(result)
            }
            selectedItem.value = null
            selectedItemId.value = null // Reset selectedItemId
        }
    )
}
