package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import javax.swing.Icon

enum class Screen(
    val label:String,
    val icon:ImageVector
){
//    SplashScreen(
//        label = "Splash",
//        icon = Icons.Default.DesktopAccessDisabled
//    ),
    LogInScreen(
        label = "Login",
        icon = Icons.Filled.Home
    ),
    HomeScreen(
        label = "Dashboard",
        icon = Icons.Filled.Home
    ),
    EmployeeManagement(
        label = "Employee Management",
        icon = Icons.Filled.ManageAccounts
    ),
    ProjectManagement(
        label = "Project Management",
        icon = Icons.Filled.DocumentScanner
    ),
    SettingsScreen(
        label = "Settings",
        icon = Icons.Filled.Settings
    ),
    ProfileScreens(
        label = "User Profile",
        icon = Icons.Filled.VerifiedUser
    ),
    LogOutScreen(
        label = "Log Out",
        icon = Icons.Filled.ExitToApp
    )

}