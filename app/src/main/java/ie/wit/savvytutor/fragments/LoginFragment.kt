package ie.wit.savvytutor.fragments

import android.R
import android.content.ContentValues
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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*

private lateinit var mAuth: FirebaseAuth


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = Firebase.auth

    }


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.login_fragment, container, false)
        setLoginButtonListener(root)
        return root
    }


    fun setLoginButtonListener(layout: View) {

        val email = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginEmail)
        val password = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginPassword)
        val loginbtn = layout.findViewById<Button>(ie.wit.savvytutor.R.id.loginbtn)

        loginbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()

            mAuth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val Fuser: FirebaseUser? = mAuth.currentUser
                        Toast.makeText(getActivity(), "Signed In", Toast.LENGTH_LONG).show()
                        println(Fuser)

                    }else{

                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show()
                    }

                    }

                }
        }



}
