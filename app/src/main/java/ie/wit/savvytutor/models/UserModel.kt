package ie.wit.savvytutor.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties



data class UserModel(
    var email: String = "",
    var password: String = ""
)