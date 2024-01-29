package  screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import components.common.Container
import components.HeaderTitle
import navigation.NavController
import navigation.Screen

@Composable
fun SettingScreen(
    navController: NavController
) {
    Container {
        HeaderTitle(Screen.SettingsScreen.label)

        Column {
            Text("My name is Maulik")
        }
    }
}