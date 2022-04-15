package ie.wit.savvytutor.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.helpers.readImage
import ie.wit.savvytutor.helpers.showImagePicker
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.register_user_fragment.*
import kotlinx.android.synthetic.main.register_user_fragment.view.*


private lateinit var mAuth: FirebaseAuth
var user = UserModel()
val rtdb = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference
val IMAGE_REQUEST = 1


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
        val root = inflater.inflate(R.layout.register_user_fragment, container, false)
        setRegisterButtonListener(root)
        setAddProfilePicture(root)
        setBackToLogin(root)

        return root


    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    fun setRegisterButtonListener(layout: View) {
        val registerbtn = layout.findViewById<Button>(R.id.registerbtn)
        val email = layout.findViewById<EditText>(R.id.registerEmail)
        val password = layout.findViewById<EditText>(R.id.registerPassword)
        val role = layout.findViewById<Spinner>(R.id.chooseRole)
        val username = layout.findViewById<EditText>(R.id.registerUsername)
        val displayprofilepic = layout.findViewById<ImageView>(R.id.registershowprofilepic)

        registerbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()
            user.role = role.selectedItem.toString()
            user.phone = username.text.toString()

            if (user.email.isEmpty() || user.password.isEmpty() || user.role.isEmpty()  || user.role == "Choose Role" ||  user.phone.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all the details", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mAuth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val Fuser: FirebaseUser? = mAuth.currentUser
                            Toast.makeText(getActivity(), "Account Created ", Toast.LENGTH_LONG)
                                .show()
                            println(Fuser)


                            writeNewUser(
                                UserModel(
                                    email = user.email,
                                    password = user.password,
                                    role = user.role,
                                    profilepic = user.profilepic,
                                    phone = user.phone,

                                )
                            )

                            layout.findViewById<EditText>(R.id.registerEmail).text.clear()
                            layout.findViewById<EditText>(R.id.registerPassword).text.clear()
                            layout.findViewById<Spinner>(R.id.chooseRole).setSelection(0)

                            val user = FirebaseAuth.getInstance().currentUser

                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Email sent.")
                                    }
                                }


                            val fragment = LoginFragment()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, fragment)?.commit()


                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show()
                        }
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


    fun setAddProfilePicture(layout: View) {
        layout.addprofilepic.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    //take in the image and get the path
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    user.profilepic = data.getData().toString()
                    registershowprofilepic.setImageBitmap(readImage(this, resultCode, data))


                }
            }


        }
    }


    fun setBackToLogin(layout: View){
        val backToLogin = layout.findViewById<Button>(ie.wit.savvytutor.R.id.BackToLoginBtn)

        backToLogin.setOnClickListener {

            val fragment = LoginFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(ie.wit.savvytutor.R.id.fragment_container, fragment)?.commit()
        }

    }



}










