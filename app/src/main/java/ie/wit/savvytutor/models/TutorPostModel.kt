package ie.wit.savvytutor.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TutorPostModel(
    var uid: String? = "",
    var title: String = "",
    var subject: String = "",
    var location: String = "",
    var level: String = "",
    var availability: String = "",
    var description: String = ""
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "subject" to subject,
            "location" to location,
            "level" to level,
            "availabilty" to availability,
            "description" to description
        )
    }
}