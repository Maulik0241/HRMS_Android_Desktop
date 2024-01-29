package client

import database.model.HolidayItem
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

@OptIn(InternalAPI::class)
suspend fun addHoliday(date: String, description: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/addHoliday")
        body = FormDataContent(Parameters.build {
            append("date", date)
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
suspend fun fetchHolidays(): List<HolidayItem> {
    val response: HttpResponse = HttpClientManager.client.get("http://localhost:3000/getHolidays")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    return json.decodeFromString(responseBody)
}

fun printHolidays() {
    try {
        val holidays = runBlocking { fetchHolidays() }
        holidays.forEach { holiday ->
            println(holiday.id)
            println(holiday.date)
            println(holiday.description)
        }
    } catch (e: Exception) {
        println(e.message)
    }
}

@OptIn(InternalAPI::class)
suspend fun updateHoliday(id: String, date: String, description: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Put
        url("http://localhost:3000/updateHoliday/$id")
        body = FormDataContent(Parameters.build {
            append("date", date)
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
suspend fun deleteHoliday(id: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Delete
        url("http://localhost:3000/deleteHoliday/$id")
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}
