package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.model.ProjectItem
import kotlinx.coroutines.runBlocking
import navigation.NavController
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import client.*
import components.*
import components.common.Container
import navigation.Screen

@Composable
fun ProjectManagement(navController: NavController) {
    var projectList by remember { mutableStateOf<List<ProjectItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        projectList = fetchProjects()
        println(projectList)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Container {
            Column(modifier = Modifier.padding(16.dp)) {
                HeaderTitle(text = Screen.ProjectManagement.label)

                Spacer(modifier = Modifier.height(16.dp))

                if(projectList.isEmpty()){
                    Text("Loading.......")
                } else {
                    ProjectTable(projectList = projectList)
                }
            }
        }
    }
}

@Composable
fun ProjectTable(projectList: List<ProjectItem>) {
    // State to control the dialog visibility
    val showDialog = remember { mutableStateOf(false) }

    // State to hold the project name entered in the dialog
    var projectName by remember { mutableStateOf("") }

    // Function to handle the confirm action in the dialog
    val handleConfirm: (String) -> Unit = { name ->
        // Call the addProject API function with the entered project name
        runBlocking {
            val result = addProject(name)
            // Handle the result, e.g., show a toast or perform any necessary actions
            println(result)
        }
    }
    Card(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.7f)
            .padding(top = 10.dp , start = 30.dp , bottom = 30.dp),
        backgroundColor = Color.White,
        elevation = 7.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 10.dp),
                backgroundColor = Color.White,
                elevation = 7.dp
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Text(
                            text = "Project List",
                            fontSize = 24.sp,
                            color = Color(0xFF11009E),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showDialog.value = true },
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(text = "Add Project")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        ProjectHeaderRow()
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    val sortedProjectList = projectList.sortedBy { it.number }

                    items(sortedProjectList) { project ->
                        ProjectRow(project)
                    }
                }
            }
        }
    }
    CustomProjectDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = false },
        dialogTitle = "Add Project",
        projectName = projectName,
        onConfirm = handleConfirm
    )
}



@Composable
fun ProjectHeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(36.dp))
        Text(
            text = "No",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = "Project Name",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Action",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.7f)
        )
    }
}


@Composable
fun ProjectRow(project: ProjectItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(36.dp))
        Text(
            text = project.number.toString(),
            fontSize = 14.sp,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = project.name,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Row(modifier = Modifier.weight(0.7f)) {
            Box(Modifier.padding(horizontal = 4.dp)) {
                IconButton(
                    onClick = { /* Handle edit action */ },
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
                    onClick = { /* Handle delete action */ },
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


