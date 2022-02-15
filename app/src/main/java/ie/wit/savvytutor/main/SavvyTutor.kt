package ie.wit.savvytutor.main

import android.app.Application
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SavvyTutor : Application() {




    override fun onCreate() {
        super.onCreate()
        Log.v("App","SavvyTutor started")
    }

}