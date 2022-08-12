package com.example.nasa.feature

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.nasa.R
import com.example.nasa.di.AppModule
import com.example.nasa.ui.main.view.activity.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Abhin.
 */
@UninstallModules(AppModule::class)
@HiltAndroidTest
class ImageDetailScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun imageDetailTest() {
        Espresso.onView(ViewMatchers.withId(R.id.fl_splash_main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                ViewActions.click()
            )
        )
        Thread.sleep(1000)
        val backButton = Espresso.onView(ViewMatchers.withId(R.id.imageBack))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        backButton.perform(ViewActions.click())
        Thread.sleep(1000)
    }
}