package database.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectItem(
    val id:String,
    val name:String,
    val number:Int = 0
)
