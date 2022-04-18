package ie.wit.savvytutor.activity


import ParentHomeFragment
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.*


lateinit var drawer: DrawerLayout
private lateinit var mAuth: FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = Firebase.auth

        val toolbar = findViewById<Toolbar>(R.id.toolbar) //use the new toolbar
        setSupportActionBar(toolbar) //set the new action bar to be my toolbar

        drawer = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //pass in listener for click in drawer


        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close //toolbar message when held

        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()


        //save what fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                LoginFragment()
            ).commit()
            navigationView.setCheckedItem(R.id.home)
        }

    }


    //listener to check for item id that was clicked
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ParentHomeFragment()
            ).commit()
            R.id.chat -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ChatFragment()
            ).commit()
            R.id.aboutSavvyTutor -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AboutFragment()
            ).commit()
            R.id.createAPost -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                CreateAPostFragment()
            ).commit()
            R.id.tutorHome -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                TutorHomeFragment()
            ).commit()
            R.id.tutorCreate -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                TutorCreatePostFragment()
            ).commit()
            R.id.tutorViewOwnPosts -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                TutorViewOwnPosts()
            ).commit()
            R.id.tutorChat -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                TutorChatFragment()
            ).commit()
            R.id.tutorAbout -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AboutFragment()
            ).commit()



        }
        drawer.closeDrawer(GravityCompat.START) //closer nav drawer when item clicked
        return true //return selected item
    }

    //close nav drawer when back button is pressed
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            mAuth.signOut()
            finish()
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }
        return true
    }
    
}
