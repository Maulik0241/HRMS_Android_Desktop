package database.model

import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementItem(
    val id:String,
    val createdOn: String,
    val description: String
)