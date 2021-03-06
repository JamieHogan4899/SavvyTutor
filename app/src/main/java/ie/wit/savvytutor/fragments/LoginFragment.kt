package ie.wit.savvytutor.fragments

import ParentHomeFragment
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.activity.drawer
import kotlinx.android.synthetic.main.activity_main.view.*

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
        setOnCreateAccount(root)

        (activity as AppCompatActivity?)!!.supportActionBar?.title = "SavvyTutor"

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }


    fun setLoginButtonListener(layout: View) {

        val email = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginEmail)
        val password = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginPassword)
        val loginbtn = layout.findViewById<Button>(ie.wit.savvytutor.R.id.loginbtn)

        password.transformationMethod = PasswordTransformationMethod.getInstance();

        loginbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()

            if (user.email.isEmpty() || user.password.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter all the details", Toast.LENGTH_SHORT)
                    .show()
            } else {

                mAuth.signInWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val Fuser: FirebaseUser? = mAuth.currentUser
                            println(Fuser)


                            if (mAuth.currentUser?.isEmailVerified == true) {


                                checkUserRole(layout)
                                loginbtn.onEditorAction(EditorInfo.IME_ACTION_DONE)


                            } else (
                                    Toast.makeText(
                                        getActivity(),
                                        "You Must Verify Your Email Address Before Logging In",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    )

                        } else {

                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                getActivity(),
                                "Error, Please check all details are filled in correctly",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }

            }

        }
    }


    fun setOnCreateAccount(layout: View) {
        val createAccountBtn = layout.findViewById<Button>(ie.wit.savvytutor.R.id.createAccountBtn)

        createAccountBtn.setOnClickListener {

            val fragment = RegisterFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(ie.wit.savvytutor.R.id.fragment_container, fragment)?.commit()
        }

    }


    fun checkUserRole(layout: View) {
        val email = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginEmail).text.toString()
        val userDatabase =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference

        val check = userDatabase.child("Users").orderByChild("email").equalTo(email)
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //get The Uid for that user

                    println(dataSnapshot)
                    val uid = dataSnapshot.children.first().key
                    println("User Id: " + uid)

                    val individualDb = uid?.let { userDatabase.child("Users").child(it) }
                    println(individualDb)

                    if (individualDb != null) {
                        individualDb.child("role").get().addOnSuccessListener {
                            if (it.exists()) {
                                val usersRole = it.value

                                if (usersRole == "Parent") {
                                    val fragment = ParentHomeFragment()
                                    activity?.supportFragmentManager?.beginTransaction()
                                        ?.replace(
                                            ie.wit.savvytutor.R.id.fragment_container,
                                            fragment
                                        )?.commit()

                                } else {

                                    val fragment = TutorHomeFragment()
                                    activity?.supportFragmentManager?.beginTransaction()
                                        ?.replace(
                                            ie.wit.savvytutor.R.id.fragment_container,
                                            fragment
                                        )?.commit()

                                }
                            }

                        }
                    }

                } else {
                    println("error")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Operation Cancelled due to Error")
            }
        }
        check.addListenerForSingleValueEvent(eventListener)
    }

}


