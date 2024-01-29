package client

import database.model.AnnouncementItem
import database.model.EmployeeItem
import database.model.ProjectItem
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

suspend fun getAllUsers():List<EmployeeItem>{
    val response: HttpResponse = HttpClientManager.client.get("http://localhost:3000/getUsers")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    val fetchEmployee = json.decodeFromString<List<EmployeeItem>>(responseBody)

    val employeeWithNumbers = fetchEmployee.mapIndexed { index, employee ->
        employee.copy(number = index + 1)
    }

    return employeeWithNumbers
}




@OptIn(InternalAPI::class)
suspend fun updateEmployee(id: String, name: String,email:String,contactNumber:String,role:String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/updateUserProfile/$id")
        body = FormDataContent(Parameters.build {
            append("name", name)
            append("email",email)
            append("contactNumber",contactNumber)
            append("role",role)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}


suspend fun deleteUserById(id: String): String {
    val response = HttpClientManager.client.request {
        method = HttpMethod.Delete
        url("http://localhost:3000/deleteUser/$id")
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}