package ie.wit.savvytutor

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.adapters.UserAdapter
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4ClassRunner::class)
class TutorNavigationTests {

    @Test
    fun login(){
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        Espresso.onView(ViewMatchers.withId(R.id.loginEmail))
            .perform(ViewActions.typeText("zaragunner@hotmail.com"), ViewActions.closeSoftKeyboard());//type in email field
        Espresso.onView(ViewMatchers.withId(R.id.loginPassword))
            .perform(ViewActions.typeText("Test123"), ViewActions.closeSoftKeyboard());//type in email field
        Espresso.onView(ViewMatchers.withId(R.id.loginbtn)).perform(ViewActions.longClick()) //hit login
    }

    @Test
    fun usingNavDrawer() {

        login()
        TimeUnit.SECONDS.sleep(7)
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .perform(DrawerActions.open()) //open nav drawer
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.tutorCreate)) //click into create a post
        Espresso.onView(ViewMatchers.withId(R.id.tutorcreateapost_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment

        TimeUnit.SECONDS.sleep(3)
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tutorAbout)) //click into about
        Espresso.onView(ViewMatchers.withId(R.id.about_fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment.check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(2)

        TimeUnit.SECONDS.sleep(3)
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tutorChat)) //click into chat
        Espresso.onView(ViewMatchers.withId(R.id.tutorChat_fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(2)
        Espresso.onView(ViewMatchers.withId(R.id.userListView)).perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, ViewActions.click())) //click into 1st item
        Espresso.onView(ViewMatchers.withId(R.id.chatscreen)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(3)

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tutorViewOwnPosts)) //click into view own posts
        Espresso.onView(ViewMatchers.withId(R.id.tutorOwnPosts_Fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(3)

    }




    }