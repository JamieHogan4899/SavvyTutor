package ie.wit.savvytutor.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference


class SavvyTutor : Application() {

    lateinit var database: DatabaseReference
    lateinit var currentUser: FirebaseUser




    override fun onCreate() {
        super.onCreate()
        Log.v("App","SavvyTutor started")
    }

}