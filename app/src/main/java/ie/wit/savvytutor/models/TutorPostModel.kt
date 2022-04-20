package ie.wit.savvytutor.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import ie.wit.savvytutor.fragments.user

@IgnoreExtraProperties
data class TutorPostModel(
    var uid: String? = "",
    var title: String = "",
    var subject: String = "",
    var location: String = "",
    var level: String = "",
    var availability: String = "",
    var description: String = "",
    var postId: String = ""
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "subject" to subject,
            "location" to location,
            "level" to level,
            "availability" to availability,
            "description" to description,
            "postId" to postId
        )
    }
}