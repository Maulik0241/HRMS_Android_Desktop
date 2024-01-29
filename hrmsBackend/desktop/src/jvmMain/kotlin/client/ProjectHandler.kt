package client

import database.model.ProjectItem
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

@OptIn(InternalAPI::class)
suspend fun addProject(name: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/addProject")
        body = FormDataContent(Parameters.build {
            append("name", name)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}

@OptIn(InternalAPI::class)
suspend fun fetchProjects(): List<ProjectItem> {
    val response: HttpResponse = HttpClientManager.client.get("http://localhost:3000/getProjects")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    val fetchedProjects = json.decodeFromString<List<ProjectItem>>(responseBody)

    // Assign project numbers
    val projectsWithNumbers = fetchedProjects.mapIndexed { index, project ->
        project.copy(number = index + 1)
    }

    return projectsWithNumbers
}


fun printProjects() {
    try {
        val projects = runBlocking { fetchProjects() }
        projects.forEach { project ->
            println(project.id)
            println(project.name)
        }
    } catch (e: Exception) {
        println(e.message)
    }
}

@OptIn(InternalAPI::class)
suspend fun updateProject(id: String, name: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Put
        url("http://localhost:3000/updateProject/$id")
        body = FormDataContent(Parameters.build {
            append("name", name)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}

@OptIn(InternalAPI::class)
suspend fun deleteProject(id: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Delete
        url("http://localhost:3000/deleteProject/$id")
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}
