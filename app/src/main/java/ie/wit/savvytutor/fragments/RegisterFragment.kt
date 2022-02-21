package ie.wit.savvytutor.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.register_user_fragment.*


private lateinit var mAuth: FirebaseAuth
var user = UserModel()


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
            inflater.inflate(ie.wit.savvytutor.R.layout.register_user_fragment, container, false)

        setRegisterButtonListener(root)



        return root
    }


    fun setRegisterButtonListener(layout: View) {
        val registerbtn = layout.findViewById<Button>(R.id.registerbtn)
        val email = layout.findViewById<EditText>(R.id.registerEmail)
        val password = layout.findViewById<EditText>(R.id.registerPassword)

        registerbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()

            mAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val Fuser: FirebaseUser? = mAuth.currentUser
                    Toast.makeText(getActivity(), "Account Created ", Toast.LENGTH_LONG).show()
                    println(Fuser)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show()
                }
            }

        }

    }


}


