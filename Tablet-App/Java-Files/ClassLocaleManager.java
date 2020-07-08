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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Adapted from https://androidwave.com/android-multi-language-support-best-practices/
 */
class ClassLocaleManager {

    /**
     * For US (English) locale
     */
    static final String LANGUAGE_KEY_US = "en_us";

    /**
     * for French locale
     */

    static final String LANGUAGE_KEY_FRENCH = "fr";

    /***
     * for Haiti (Haitian Creole) locale
     */
    static final String LANGUAGE_KEY_HAITIAN_CREOLE = "ht";

    /***
     *  for Korean locale
     */
    static final String LANGUAGE_KEY_KOREAN = "ko";

    /**
     * SharedPreferences Key
     */
    private static final String LANGUAGE_KEY = "language_key";

    /**
     * set current pref locale
     *
     * @param mContext
     * @return
     */
    static Context setLocale(Context mContext) {

        return updateResources(mContext, getLanguagePref(mContext));
    }

    /**
     * Set new Locale with context
     *
     * @param mContext
     * @param mLocaleKey
     * @return
     */
    static void setNewLocale(Context mContext, String mLocaleKey) {
        setLanguagePref(mContext, mLocaleKey);
        updateResources(mContext, mLocaleKey);
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    private static String getLanguagePref(Context mContext) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return mPreferences.getString(LANGUAGE_KEY, LANGUAGE_KEY_US);
    }

    /**
     * set pref key
     *
     * @param mContext
     * @param localeKey
     */
    private static void setLanguagePref(Context mContext, String localeKey) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mPreferences.edit().putString(LANGUAGE_KEY, localeKey).apply();
    }

    /**
     * update resource
     *
     * @param context
     * @param language
     * @return
     */
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        ApplicationCustom.setLocale(locale);
        return context;
    }
}
