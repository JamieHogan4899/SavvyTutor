package ie.wit.savvytutor.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CommentModel (

    var commentId: String? = "",
    var postId:String = "",
    var commenter:String = "",
    var comment:String=""
)