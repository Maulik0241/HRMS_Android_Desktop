package navigation



import screens.ProjectManagement
import screens.EmployeeManagement
import androidx.compose.runtime.Composable

import screens.*
import java.util.prefs.Preferences

@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.LogInScreen.name){
            LogInScreen(navController)
        }
        composable(Screen.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(Screen.EmployeeManagement.name) {
            EmployeeManagement(navController)
        }
        composable(Screen.SettingsScreen.name) {
            SettingScreen(navController)
        }
        composable(Screen.ProjectManagement.name){
            ProjectManagement(navController)
        }

        composable(Screen.ProfileScreens.name) {
            ProfileScreen(navController)
        }
        composable(Screen.LogOutScreen.name){
            logout(navController)
        }

    }.build()
}

fun logout(navController: NavController){
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend")
    preferences.remove("userEmail")
    preferences.remove("userId")
    navController.navigate(Screen.LogInScreen.name)
}
