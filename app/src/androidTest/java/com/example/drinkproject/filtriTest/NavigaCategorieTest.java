package com.example.drinkproject.filtriTest;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.drinkproject.MainActivity;
import com.example.drinkproject.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigaCategorieTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void navigaCategorieTest() {
        ViewInteraction appCompatEditText = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.editTextUsername),
                        childAtPosition(
                                allOf(withId(R.id.mainLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextPassword),
                        childAtPosition(
                                allOf(withId(R.id.mainLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.loginButton), withContentDescription("next step"),
                        childAtPosition(
                                allOf(withId(R.id.mainLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.nomeDrink), withText("Acqua"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView.check(matches(withText("Acqua")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nomeDrink), withText("Acqua Di Belle Delphine"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView2.check(matches(withText("Acqua Di Belle Delphine")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.nomeDrink), withText("Alexander"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView3.check(matches(withText("Alexander")));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        materialTextView.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.nomeDrink), withText("Bellini"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView4.check(matches(withText("Bellini")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.nomeDrink), withText("Bloody Mary"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView5.check(matches(withText("Bloody Mary")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.nomeDrink), withText("Caipirinha"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView6.check(matches(withText("Caipirinha")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.nomeDrink), withText("Caipirinha"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView7.check(matches(withText("Caipirinha")));

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.nomeDrink), withText("Acqua"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView8.check(matches(withText("Acqua")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.nomeDrink), withText("Coca-Cola"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView9.check(matches(withText("Coca-Cola")));

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction materialTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        materialTextView3.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.nomeDrink), withText("Acqua Di Belle Delphine"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView10.check(matches(withText("Acqua Di Belle Delphine")));

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        DataInteraction materialTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        materialTextView4.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.nomeDrink), withText("Alexander"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView11.check(matches(withText("Alexander")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.nomeDrink), withText("Negroni"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView12.check(matches(withText("Negroni")));

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        DataInteraction materialTextView5 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        materialTextView5.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.nomeDrink), withText("Ananas-Cocco"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView13.check(matches(withText("Ananas-Cocco")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.nomeDrink), withText("Banana-Cioccolato"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView14.check(matches(withText("Banana-Cioccolato")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.nomeDrink), withText("Fragola-Banana"),
                        withParent(withParent(withId(R.id.drinkRecyclerView))),
                        isDisplayed()));
        textView15.check(matches(withText("Fragola-Banana")));

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.dropDownMenu),
                        childAtPosition(
                                allOf(withId(R.id.drinkLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner6.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
