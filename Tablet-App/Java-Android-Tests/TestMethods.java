/*
 *
 *  * Copyright (c) 2019. Tabitha Huff & Hyunji Kim & Jonathan Medley & Jack O'Connor
 *  *
 *  *                            Licensed under the Apache License, Version 2.0 (the "License");
 *  *                            you may not use this file except in compliance with the License.
 *  *                            You may obtain a copy of the License at
 *  *
 *  *                                http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *                            Unless required by applicable law or agreed to in writing, software
 *  *                            distributed under the License is distributed on an "AS IS" BASIS,
 *  *                            WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *                            See the License for the specific language governing permissions and
 *  *                            limitations under the License.
 *
 */

package com.example.medley.medicalrecord;

import android.app.Activity;
import android.support.test.espresso.NoMatchingRootException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Adapted from kowalcj0
 * https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso
 */
class TestMethods {

    static void compareToasts(Activity activity, Integer toastString) {
        onView(withText(toastString))
                .inRoot(withDecorView(not(activity.getWindow()
                        .getDecorView()))).check(matches(isDisplayed()));
    }

    static void notAnyToasts(Activity activity, Integer toastString) {
        try {
            onView(withText(toastString))
                    .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                    .check(doesNotExist());
            assert false;
        } catch (NoMatchingRootException e) {
            assert true;
        }
    }

}
