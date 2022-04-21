package ie.wit.savvytutor

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.adapters.UserAdapter
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4ClassRunner::class)
class ParentNavigationTests {
    @Test
    fun homeScreenTest() {
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed())) //Check the home page is correct
    }


    @Test
    fun login(){
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        onView(withId(R.id.loginEmail)).perform(ViewActions.typeText("jamiehogan4848@gmail.com"), ViewActions.closeSoftKeyboard());//type in email field
        onView(withId(R.id.loginPassword)).perform(ViewActions.typeText("Test123"), ViewActions.closeSoftKeyboard());//type in email field
        onView(withId(R.id.loginbtn)).perform(ViewActions.longClick()) //hit login
    }



    @Test
    fun usingNavDrawer(){

        login()
        TimeUnit.SECONDS.sleep(7)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.createAPost)) //click into create a post
        onView(withId(R.id.createapost_fragment)).check(matches(isDisplayed())) //check does it display correct fragment

       TimeUnit.SECONDS.sleep(3)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.aboutSavvyTutor)) //click into about
        onView(withId(R.id.about_fragment)).check(matches(isDisplayed())) //check does it display correct fragment

        TimeUnit.SECONDS.sleep(3)
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.chat)) //click into chat
        onView(withId(R.id.chat_fragment)).check(matches(isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(2)
        onView(withId(R.id.userListView)).perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, click())) //click into 1st item
        onView(withId(R.id.chatscreen)).check(matches(isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(3)




        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.parentsViewOwnPosts)) //click into view own posts
        onView(withId(R.id.parentViewTheirPostsPage)).check(matches(isDisplayed())) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(3)


    }

    }
