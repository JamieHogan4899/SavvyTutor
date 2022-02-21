package ie.wit.savvytutor

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ie.wit.savvytutor.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4ClassRunner::class)
class ParentPostsTests {

    @Test
    fun addingAPostTest(){
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.createAPost)) //click into create a post
        onView(withId(R.id.createapost_fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment


        onView(withId(R.id.postTitle)).perform(typeText("Automatic Test Post"), closeSoftKeyboard());//type in title field

        onView(withId(R.id.chooseSubject)).perform(click())
        onView(withText("Maths")).perform(click());//select Subject

        onView(withId(R.id.chooseLocation)).perform(typeText("Kilmore"), closeSoftKeyboard()); //type in location

        onView(withId(R.id.chooseLevel)).perform(click())
        onView(withText("Leaving Cert")).perform(click()); //select Level

        onView(withId(R.id.description)).perform(typeText("Blah Blah Blah Blah. This is filler"), closeSoftKeyboard()); //Type in Description

        onView(withId(R.id.createapostbtn)).perform(click()) //hit post!
        TimeUnit.SECONDS.sleep(2)

    }

}