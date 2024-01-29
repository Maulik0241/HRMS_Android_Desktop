package screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.common.Container
import components.HeaderTitle
import navigation.NavController
import navigation.Screen


@Composable
fun ProfileScreen(navController: NavController) {
    val userEmail = remember { mutableStateOf("user@example.com") }
    val userPhone = remember { mutableStateOf("1234567890") }
    val userDepartment = remember { mutableStateOf("Engineering") }
    val userPosition = remember { mutableStateOf("Software Engineer") }
    val userProject = remember { mutableStateOf("Project X") }

    Container {
        HeaderTitle(Screen.ProfileScreens.label)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(120.dp)
                    )

                    TextField(
                        value = userEmail.value,
                        onValueChange = { userEmail.value = it },
                        label = { Text("Email") }
                    )

                    TextField(
                        value = userPhone.value,
                        onValueChange = { userPhone.value = it },
                        label = { Text("Phone Number") }
                    )

                    TextField(
                        value = userDepartment.value,
                        onValueChange = { userDepartment.value = it },
                        label = { Text("Department") }
                    )

                    TextField(
                        value = userPosition.value,
                        onValueChange = { userPosition.value = it },
                        label = { Text("Position") }
                    )

                    TextField(
                        value = userProject.value,
                        onValueChange = { userProject.value = it },
                        label = { Text("Project") }
                    )

                    Button(
                        onClick = {
                            navController.navigate(Screen.EmployeeManagement.name)
                        }
                    ) {
                        Text("Navigate to Employee Management")
                    }
                }
            }
        }
    }
}
