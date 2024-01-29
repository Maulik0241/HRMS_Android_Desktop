package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import client.SignIn
import database.storeUserEmail
import kotlinx.coroutines.runBlocking
import navigation.NavController
import navigation.Screen

@Composable
fun LogInScreen(navController: NavController) {
    val userEmail = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showErrorMsg = remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource("images/background.jpg"),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier.width(400.dp).height(500.dp)
                        .background(Color(0xFFffffff))
                        .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterStart,

                    ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = "Login", style = MaterialTheme.typography.h4)

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = userEmail.value.trim(),
                            onValueChange = { userEmail.value = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = password.value.trim(),
                            onValueChange = { password.value = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                    )
                                }
                            }
                        )

                        if (showErrorMsg.value) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }


                        Button(
                            onClick = {

                                val email = userEmail.value.trim()
                                val pass = password.value.trim()
                                if (email.isNotEmpty() && pass.isNotEmpty()) {
                                    val result = runBlocking { SignIn(email, pass) }
                                    if (result != "failure") {
                                        storeUserEmail(email)
                                        println(result)
                                        navController.navigate(Screen.HomeScreen.name)
                                    } else {
                                        when (result) {
                                            "auth/user-not-found" -> {
                                                showErrorMsg.value = true
                                                errorMessage = "User not found"
                                            }
                                            "auth/wrong-password" -> {
                                                showErrorMsg.value = true
                                                errorMessage = "Wrong password"
                                            }
                                            else -> {
                                                showErrorMsg.value = true
                                                errorMessage = "Authentication failed"
                                            }
                                        }
                                        println(result)
                                    }
                                } else {
                                    showErrorMsg.value = true
                                    errorMessage = "Please enter email and password"
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                        ) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    }
}
