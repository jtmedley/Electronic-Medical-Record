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

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.util.Locale;

/**
 * Overrides base application with a custom class.
 * Implements deleting previous stored image methods, locale and translation methods,
 * clinic location methods, leak detection initialization methods, date-tiime utility initialization methods, and
 * application context methods.
 * <p>
 * Adapted from Square, Inc.
 * https://github.com/square/leakcanary
 * and from http://www.dev2qa.com/android-get-application-context-from-anywhere-example
 * and from https://androidwave.com/android-multi-language-support-best-practices/
 */
public class ApplicationCustom extends Application {
    /**
     * The application context.
     */
    private static Context appContext;

    /**
     * The current clinic location where the app is used.
     */
    static String locationString = ClassConstants.EXAM_STRING_DEFAULT;

    /**
     * The current locale setting specified by translation functions.
     */
    private static Locale localeSetting;

    /**
     * The status of translation operations indicating whether the activity should be restarted
     * with new language settings.
     */
    static boolean finishFlag;

    /**
     * The status of whether the current activity is the login activity to specify whether
     * the options menu should be invalidated.
     */
    static boolean login;

    /**
     * Called when the application is starting, before any other application objects have
     * been created.
     * <p>
     * Delete any temp files if they exist, starts LeakCanary, start ThreeTenBackport date
     * handling process, and start the application at its launcher activity
     */
    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext();
        deleteOldImage();
        startThreeTenUtility();
        installLeakCanary();
    }

    /**
     * Refreshes the appContext object to be equal to the current application context.
     */
    private void setAppContext() {
        appContext = getApplicationContext();
    }

    /**
     * Deletes old image files written during a previous application session if they exist.
     */
    private void deleteOldImage() {
        String directory = getFilesDir().getAbsolutePath();
        File byteFile = new File(directory, "newImage");
        if (byteFile.exists()) {
            boolean deleted = byteFile.delete();
            if (deleted) {
                Log.d("tag61", "Deleted previously written image");
            } else {
                Log.d("tag61", "Did not delete previously written image");
            }
        }
        File byteFile2 = new File(directory, "foundImage");
        if (byteFile2.exists()) {
            boolean deleted = byteFile.delete();
            if (deleted) {
                Log.d("tag61", "Deleted previously written image");
            } else {
                Log.d("tag61", "Did not delete previously written image");
            }
        }
    }

    /**
     * Starts date and time utility for Java 7.
     */
    private void startThreeTenUtility() {
        AndroidThreeTen.init(this);
    }

    /**
     * Starts leak detection and recording projects
     */
    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }

    /**
     * Sets the location of the application to one of the available clinic location options.
     * @param settingString The location setting for the application use location.
     */
    public static void setLocationSettings(String settingString) {
        if (settingString != null) {
            if (settingString.equals(ClassConstants.EXAM_STRING_DEFAULT) ||
                    settingString.equals(ClassConstants.EXAM_STRING_HAITI)) {

                locationString = settingString;

            } else {
                locationString = ClassConstants.EXAM_STRING_DEFAULT;
            }
        } else {
            locationString = ClassConstants.EXAM_STRING_DEFAULT;
        }
    }
    public static String getLocationString(){
        return locationString;
    }
    /**
     * Gets the current locale setting for translation purposes.
     *
     * @return Returns the current locale setting.
     */
    public static Locale getLocale() {
        return localeSetting;
    }

    /**
     * Sets the locale to a given locale setting for translation purposes, or defaults to locale US.
     *
     * @param locale the locale setting to be applied to the application.
     */
    public static void setLocale(Locale locale) {
        if (locale != null) {
            localeSetting = locale;
        } else {
            localeSetting = Locale.US;
        }
    }

    /**
     * Gets the application context for multi-activity operations.
     *
     * @return Returns the application context.
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * Called by the system when the device configuration changes while your component is running.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ClassLocaleManager.setLocale(this);
    }

    /**
     * Sets the locale for the base application overridden by this class.
     *
     * @param base the context of the application.
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ClassLocaleManager.setLocale(base));
    }
}