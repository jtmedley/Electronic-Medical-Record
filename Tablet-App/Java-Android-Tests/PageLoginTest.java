package com.example.medley.medicalrecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PageLoginTest {
    @Rule
    private ActivityTestRule<PageLogin> getRule() {
        return new ActivityTestRule<>(PageLogin.class);
    }

    private String goodUsername="jon";
    private String goodPassword ="medley";
    private String badUsername = "badusername";
    private String badPassword="badpassword";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
        onView((withId(R.id.username))).perform(ViewActions.typeText(goodUsername));
        onView((withId(R.id.password))).perform(ViewActions.typeText(goodPassword));
        onView((withId(R.id.buttonLogin))).perform(ViewActions.click());
        TestMethods.compareToasts(getRule().getActivity(),R.string.logging);

    }

    @Test
    public void onBackPressed() {
    }

    @Test
    public void setActionBar() {
    }
}