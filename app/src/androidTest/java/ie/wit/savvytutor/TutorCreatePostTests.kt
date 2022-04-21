package ie.wit.savvytutor

import android.text.method.Touch.scrollTo
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ie.wit.savvytutor.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4ClassRunner::class)
class TutorCreatePostTests {
    @Test
    fun login(){
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        Espresso.onView(ViewMatchers.withId(R.id.loginEmail)).perform(
            ViewActions.typeText("jamiehogan4848@gmail.com"),
            ViewActions.closeSoftKeyboard()
        );//type in email field
        Espresso.onView(ViewMatchers.withId(R.id.loginPassword)).perform(
            ViewActions.typeText("Test123"),
            ViewActions.closeSoftKeyboard()
        );//type in email field
        Espresso.onView(ViewMatchers.withId(R.id.loginbtn)).perform(ViewActions.longClick()) //hit login
    }

    @Test
    fun tutorCreatePostsTests(){

        login()
        TimeUnit.SECONDS.sleep(5)
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open()) //open nav drawer
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(
            NavigationViewActions.navigateTo(
                R.id.tutorCreate
            )
        ) //click into create a post
        Espresso.onView(ViewMatchers.withId(R.id.tutorcreateapost_fragment)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        ) //check does it display correct fragment
        TimeUnit.SECONDS.sleep(2)

        Espresso.onView(ViewMatchers.withId(R.id.tutorPostTitle)).perform(
            ViewActions.typeText("Automatic Tutor Test Post"),
            ViewActions.closeSoftKeyboard()
        );//type in title field


        Espresso.onView(ViewMatchers.withId(R.id.tutorchooseSubject)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Maths")).perform(ViewActions.click());//select Subject

        Espresso.onView(ViewMatchers.withId(R.id.tutorChooseLocation)).perform(
            ViewActions.typeText(
                "Kilmore"
            ), ViewActions.closeSoftKeyboard()
        ); //type in location


        Espresso.onView(ViewMatchers.withId(R.id.tutorChooseLevel)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Leaving Cert")).perform(ViewActions.click()); //select Level

        Espresso.onView(ViewMatchers.withId(R.id.tutorChooseAvailability)).perform(
            ViewActions.typeText(
                "6-8 Weekdays and all weekends"
            ), ViewActions.closeSoftKeyboard()
        ); //Type in Description
        Espresso.onView(ViewMatchers.withId(R.id.tutorDescription)).perform(
            ViewActions.typeText("This is a test blah blah blah"),
            ViewActions.closeSoftKeyboard()
        ); //Type in Description
        TimeUnit.SECONDS.sleep(2)


        onView(withId(R.id.TutorCreateSV)).perform(ViewActions.swipeUp())
        onView(withId(R.id.tutorcreateapostbtn)).perform(click()) //hit post!
        TimeUnit.SECONDS.sleep(2)
    }


}