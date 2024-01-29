package database

import java.util.prefs.Preferences

 fun storeUserId(userId: String) {
    // Implement the logic to store the user ID
    // For example, you can use SharedPreferences or any other storage mechanism
    // Here's an example using SharedPreferences:
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend")
    preferences.put("userId", userId)
    preferences.flush()
}

 fun storeUserEmail(email: String) {
    // Implement the logic to store the user email
    // For example, you can use SharedPreferences or any other storage mechanism
    // Here's an example using SharedPreferences:
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend")
    preferences.put("userEmail", email)
    preferences.flush()
}