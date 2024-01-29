import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.Screen
import navigation.logout
import navigation.rememberNavController
import screens.*
import java.util.prefs.Preferences

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.LogInScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    // Check if the email is already stored
    val storedEmail = remember { mutableStateOf("") }
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend") // Replace with your application package name
    storedEmail.value = preferences.get("userEmail", "")

    LaunchedEffect(storedEmail.value) {

        if (storedEmail.value.isNotEmpty()) {
            navController.navigate(Screen.HomeScreen.name)
        }
    }

    Surface(
        modifier = Modifier.background(color = MaterialTheme.colors.background)
    ) {
        Box(Modifier.fillMaxSize()) {
             if (currentScreen != Screen.LogInScreen.name) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {

                    Column(
                        modifier = Modifier.width(150.dp).fillMaxHeight().background(presentation.Colors.bgSideMenu),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                            Column(
                            modifier = Modifier,

                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 50.dp)
                                    .size(48.dp)
                                    .background(Color.White, CircleShape),
                            )
                                Spacer(modifier = Modifier.height(5.dp))

                            TextButton(onClick = {
                                    navController.navigate(Screen.ProfileScreens.name)
                            }){
                                Text(
                                text = extractNameFromEmail(storedEmail.value),
                                style = MaterialTheme.typography.body1,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()

                            )
                            }
                        }

                        screens.filterNot { it == Screen.LogInScreen || it == Screen.ProfileScreens}.forEach { screen ->
                            TextButton(
                                onClick = {
                                    navController.navigate(screen.name)
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (currentScreen == screen.name) Color.Yellow else Color.White
                                ),
                                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.Start),
                                contentPadding = PaddingValues(start = 12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.label,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = screen.label,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }


                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        when (currentScreen) {
                            Screen.HomeScreen.name -> {
                                HomeScreen(navController = navController)
                            }
                            Screen.EmployeeManagement.name -> {
                                EmployeeManagement(navController = navController)
                            }
                            Screen.ProjectManagement.name->{
                                ProjectManagement(navController = navController)
                            }
                            Screen.ProfileScreens.name->{
                                ProfileScreen(navController = navController)
                            }
                            Screen.SettingsScreen.name->{
                                SettingScreen(navController = navController)
                            }
                            Screen.LogOutScreen.name->{
                                logout(navController = navController)
                            }
                        }
                    }
                }
            } else {
                LogInScreen(navController = navController)
            }
        }
    }
}

private fun extractNameFromEmail(email: String): String {
    val atIndex = email.indexOf('@')
    if (atIndex != -1) {
        return email.substring(0, atIndex)
    }
    return ""
}
