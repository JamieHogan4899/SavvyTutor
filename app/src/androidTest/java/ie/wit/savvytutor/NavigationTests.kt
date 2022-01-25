package ie.wit.savvytutor

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import ie.wit.savvytutor.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTests {
    @Test
    fun homeScreenTest() {
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed())) //Check the home page is correct
    }

    @Test
    fun useNavDrawer() {
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed())) //Check the home page is correct
            .perform(DrawerActions.open()); // Open Nav Drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.chat)); //select chat option
        onView(withId(R.id.chat_fragment)).check(matches(isDisplayed())) //check chat fragment opens


    }

}
