package ie.wit.savvytutor

import android.content.Intent
import android.service.autofill.FieldClassification
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ie.wit.savvytutor.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher

@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterUserTest {

    @Test
    fun RegisterUser(){
        ActivityScenario.launch(MainActivity::class.java) //Open the app
        //go to register page
        Espresso.onView(ViewMatchers.withId(R.id.LoginSV)).perform(ViewActions.swipeUp())
        Espresso.onView(ViewMatchers.withId(R.id.createAccountBtn)).perform(ViewActions.click())

        //enter details
        Espresso.onView(ViewMatchers.withId(R.id.registerEmail)).perform(
            ViewActions.typeText("lauramcb11@hotmail.com"),
            ViewActions.closeSoftKeyboard()
        );//type in email field

        Espresso.onView(ViewMatchers.withId(R.id.registerPassword)).perform(
            ViewActions.typeText("Armchair"),
            ViewActions.closeSoftKeyboard()
        );//type in password field

        Espresso.onView(ViewMatchers.withId(R.id.chooseRole)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Parent")).perform(ViewActions.click());//select Subject

        Espresso.onView(ViewMatchers.withId(R.id.registerUsername)).perform(
            ViewActions.typeText("789464"),
            ViewActions.closeSoftKeyboard()
        );//type in phone number field

      //profile picture test

        Espresso.onView(ViewMatchers.withId(R.id.registerbtn)).perform(ViewActions.click()) //hit post!
        TimeUnit.SECONDS.sleep(2)

        Espresso.onView(ViewMatchers.withId(R.id.loginFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //check does was register successful and brought user back to login screen









    }
}