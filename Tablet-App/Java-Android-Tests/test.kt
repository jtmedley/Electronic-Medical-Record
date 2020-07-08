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

package com.example.medley.medicalrecord

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.util.Log
import org.junit.*


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentationTest {

    //activityTestRule is initialized
    @Rule
    @JvmField
    public val rule  = getRule()


    private val username_tobe_typed="Ajesh"
    private val correct_password ="password"
    private val wrong_password = "passme123"



    private fun getRule(): ActivityTestRule<PageLogin> {
        Log.e("Initalising activityTestRule","getting Mainactivity")
        return ActivityTestRule(PageLogin::class.java)
    }


    companion object {

        @BeforeClass
        @JvmStatic
        fun before_class_method(){
            Log.e("@Before Class","Hi this is run before anything")
        }

        @AfterClass
        @JvmStatic
        fun after_class_method(){
            Log.e("@After Class","Hi this is run after everything")
        }

    }

    @Before
    fun before_test_method(){
        Log.e("@Before","Hi this is run before every test function")
    }




    @After
    fun after_test_method() {
        Log.e("@After", "Hi this is run after every test function")
    }

}