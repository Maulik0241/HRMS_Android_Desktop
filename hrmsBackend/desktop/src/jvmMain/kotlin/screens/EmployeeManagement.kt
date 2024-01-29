package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import client.getAllUsers
import components.common.Container
import components.HeaderTitle
import components.userManagement.EmployeeTable
import database.model.EmployeeItem
import navigation.NavController
import navigation.Screen


@Composable
fun EmployeeManagement(navController: NavController) {
    var employeeList by remember { mutableStateOf<List<EmployeeItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        employeeList = getAllUsers()
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        Header(navController = navController)

        Container {
            Column(modifier = Modifier.padding(16.dp)) {
                HeaderTitle(text = Screen.EmployeeManagement.label)

                Spacer(modifier = Modifier.height(16.dp))
                    EmployeeTable(employeeList = employeeList)
                }
            }
        }
    }






