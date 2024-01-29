package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.AnnouncementContent
import navigation.NavController
import navigation.Screen

import androidx.compose.runtime.Composable
import client.fetchProjects
import client.getAllUsers
import components.*
import components.common.CardItem
import components.common.Container
import components.holiday.HolidayContent
import database.model.EmployeeItem
import database.model.ProjectItem
import kotlinx.coroutines.*

@Composable
fun HomeScreen(navController: NavController) {

    var projectList: List<ProjectItem>
    var projectCount = 0

    var employeeList:List<EmployeeItem>
    var employeeCount = 0

// Create a coroutine scope

// Launch a coroutine to fetch projects
    runBlocking  {
        projectList = fetchProjects()
        projectCount = projectList.size


        employeeList = getAllUsers()
        val employeeWithEmail = employeeList.filter{ it.email.isNotEmpty() }
        employeeCount = employeeWithEmail.size
        // Use the projectList and projectCount here
        // ...
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        Header(navController = navController)

        Container {
            HeaderTitle(Screen.HomeScreen.label)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp , start = 30.dp , bottom = 30.dp),
            ) {
                Column {
                    CardItem(
                        text = "Employee List",
                        icon = Icons.Default.Person,
                        modifier = Modifier.weight(1f),
                        count = employeeCount,
                        onClick = {
                            navController.navigate(Screen.EmployeeManagement.name)
                        }
                    )

                    CardItem(
                        text = "Projects",
                        icon = Icons.Default.Work,
                        modifier = Modifier.weight(1f),
                        count = projectCount,
                        onClick = {
                            navController.navigate(Screen.ProjectManagement.name)
                        }
                    )

                }
                AnnouncementCard(
                    navController,
                    text = "Announcements",
                    icon = Icons.Default.ManageAccounts,
                    modifier = Modifier.weight(1f),
                ) {
                    AnnouncementContent()
                }
                    HolidayCard(
                        navController = navController,
                        text = "Holidays",
                        icon = Icons.Default.ManageAccounts,
                        modifier = Modifier.weight(1f)
                    ) {
                        HolidayContent()
                    }

            }
        }
    }
}


