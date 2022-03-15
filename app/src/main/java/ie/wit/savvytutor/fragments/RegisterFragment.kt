package ie.wit.savvytutor.fragments

import android.content.ContentValues.TAG
import android.hardware.usb.UsbRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.register_user_fragment.*


private lateinit var mAuth: FirebaseAuth
var user = UserModel()

val rtdb =
    FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference


class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = Firebase.auth



    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root =
            inflater.inflate(R.layout.register_user_fragment, container, false)

        setRegisterButtonListener(root)



        return root
    }


    fun setRegisterButtonListener(layout: View) {
        val registerbtn = layout.findViewById<Button>(R.id.registerbtn)
        val email = layout.findViewById<EditText>(R.id.registerEmail)
        val password = layout.findViewById<EditText>(R.id.registerPassword)
        val role = layout.findViewById<Spinner>(R.id.chooseRole)

        registerbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()
            user.role = role.selectedItem.toString()




            mAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val Fuser: FirebaseUser? = mAuth.currentUser
                        Toast.makeText(getActivity(), "Account Created ", Toast.LENGTH_LONG).show()
                        println(Fuser)


                        writeNewUser(
                            UserModel(
                                email = user.email,
                                password = user.password,
                                role =  user.role

                            )
                        )


                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show()
                    }
                }

        }

    }


    private fun writeNewUser(userModel: UserModel) {
        val key = rtdb.child("users").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for users")
            return
        }

        user.uid = key
        val userValues = user.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/Users/$key" to userValues,

            )

        database.updateChildren(childUpdates)
    }


}










