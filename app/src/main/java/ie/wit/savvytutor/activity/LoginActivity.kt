package ie.wit.savvytutor.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.savvytutor.fragments.HomeFragment
import ie.wit.savvytutor.main.SavvyTutor


class LoginActivity : AppCompatActivity(){
    lateinit var app: SavvyTutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as SavvyTutor


        checkIsUserSignedIn()

    }

    private fun checkIsUserSignedIn(){
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){

            app.currentUser = FirebaseAuth.getInstance().currentUser!!
            //set what database(had to hardcode due to error)
            app.database = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference
            app.storage = FirebaseStorage.getInstance().reference

            println("Signed In - From Login Activity")
        } else{


        }



    }



}



