package ie.wit.savvytutor.models

import android.widget.Spinner
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserModel(
    var uid: String? = "",
    var email: String = "",
    var password: String = "",
    var role: String = "",
    var profilepic: String = "",
    var username: String= ""


){


@Exclude
fun toMap(): Map<String, Any?> {
    return mapOf(
        "uid" to uid,
        "email" to email,
        "password" to password,
        "role" to role,
        "profilepic" to profilepic,
        "username" to username

    )
}
}
