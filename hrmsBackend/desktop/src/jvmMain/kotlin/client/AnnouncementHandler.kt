package client

import database.model.AnnouncementItem
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

@OptIn(InternalAPI::class)
suspend fun addAnnouncement(description: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/addAnnouncement")
        body= FormDataContent(Parameters.build {
            append("description", description)
        })

    }

    return if(response.status.value == 200) {
        response.bodyAsText()
    }else{
        "failure"
    }

}


@OptIn(InternalAPI::class)
suspend fun fetchAnnouncements(): List<AnnouncementItem> {
    val response: HttpResponse = HttpClientManager.client.get("http://localhost:3000/getAnnouncement")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    return json.decodeFromString(responseBody)
}


fun printAnnouncement() {
    try {
        val announcements = runBlocking { fetchAnnouncements() }
        announcements.forEach { announcement ->
            println(announcement.id)
            println(announcement.description)
            println(announcement.createdOn)
        }
    } catch (e: Exception) {
        println(e.message)
    }
}

@OptIn(InternalAPI::class)
suspend fun updateAnnouncement(id: String, description: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/updateAnnouncement")
        body = FormDataContent(Parameters.build {
            append("id", id)
            append("description", description)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}

@OptIn(InternalAPI::class)
suspend fun deleteAnnouncement(id: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Delete
        url("http://localhost:3000/deleteAnnouncement/$id")
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}
