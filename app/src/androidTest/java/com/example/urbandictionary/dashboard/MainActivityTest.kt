package com.example.urbandictionary.dashboard

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.urbandictionary.R
import com.example.urbandictionary.di.RepositoryModule
import com.example.urbandictionary.view.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MainActivityTest  {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun test_isMainActivityInView() {
        Espresso.onView(withId(R.id.mainActivity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        Espresso.onView(withId(R.id.listFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}