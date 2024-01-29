package database.model

import kotlinx.serialization.Serializable

@Serializable
data class HolidayItem(
    val id:String,
    val date: String,
    val description: String
)