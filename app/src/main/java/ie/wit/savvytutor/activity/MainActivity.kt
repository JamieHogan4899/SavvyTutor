package ie.wit.savvytutor.activity


import ParentHomeFragment
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.*



lateinit var drawer: DrawerLayout


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        setLoginButtonListener()
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


    fun setLoginButtonListener() {
        val loginBtn = findViewById<Button>(R.id.LoginToolbarBtn)
        val fragment = LoginFragment()



        loginBtn.setOnClickListener {

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

            

        }


    }


}
