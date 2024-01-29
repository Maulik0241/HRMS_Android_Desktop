package client

import database.storeUserId
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun SignIn(email: String, password: String): String {
    val response = HttpClientManager.client.request("http://localhost:3000/signIn") {
        method = HttpMethod.Post
        url {
            parameters.append("email", email)
            parameters.append("password", password)
        }
    }

    return if (response.status.value == 200) {
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val uid = json["uid"]?.jsonPrimitive?.content

        if (uid != null) {
            // Check if the user is an admin by fetching the user profile
            val userProfileResponse = HttpClientManager.client.get("http://localhost:3000/getUser/$uid")
            val userProfileJson = Json.parseToJsonElement(userProfileResponse.bodyAsText()).jsonObject
            val isUserAdmin = userProfileJson["isUser"]?.jsonPrimitive?.content == "Admin"

            if (isUserAdmin) {
                storeUserId(uid) // Store the UID locally
                uid
            } else {
                "failure"
            }
        } else {
            "failure"
        }
    } else {
        "failure"
    }
}







