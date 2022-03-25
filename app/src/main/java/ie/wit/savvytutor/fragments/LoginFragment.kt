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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


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
                        println(Fuser)


                       if(mAuth.currentUser?.isEmailVerified == true){

                           checkUid(layout)
                           checkRole()



                       } else (
                               Toast.makeText(
                                   getActivity(),
                                   "You Must Verify Your Email Address Before Logging In",
                                   Toast.LENGTH_LONG
                               ).show()
                       )

                    }else{

                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(getActivity(), "Error, ", Toast.LENGTH_LONG).show()
                    }

                    }

                }
        }


    fun setOnCreateAccount(layout: View){
        val createAccountBtn = layout.findViewById<Button>(ie.wit.savvytutor.R.id.createAccountBtn)

        createAccountBtn.setOnClickListener {

            val fragment = RegisterFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(ie.wit.savvytutor.R.id.fragment_container, fragment)?.commit()
        }

    }


    fun checkUid(layout: View) {
        val email = layout.findViewById<EditText>(ie.wit.savvytutor.R.id.loginEmail).text.toString()
        val userDatabase =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Users"
                )

        val emailCheck = userDatabase.equalTo(email)

        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //get The Uid for that user
                    
                    println("yay")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("no!")
            }
        }
        emailCheck.addListenerForSingleValueEvent(eventListener)


    }



    fun checkRole() {

        val userDatabase =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Users"
                ).child("-Mz1FR2jJnrBa1Ws-aVU")

        userDatabase.child("role").get().addOnSuccessListener {

            if (it.exists()) {
                val usersRole = it.value

                if (usersRole == "Parent") {

                    val fragment = HomeFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(ie.wit.savvytutor.R.id.fragment_container, fragment)?.commit()

                } else {
                    Toast.makeText(
                        getActivity(),
                        "Error",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }





}


