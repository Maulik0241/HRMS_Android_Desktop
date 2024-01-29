package database.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeItem(
    val number:Int = 0,
    val id:String,
    val name: String,
    val email: String,
    val contactNumber: String,
    val role: String,
    val signUpDate:String,
)