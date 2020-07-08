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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * Adapted from "shashi mishra"
 * https://stackoverflow.com/questions/41957011/why-cant-the-inner-variable-is-being-initialised-with-value-when-button-is-click
 */
public class PageTranslate extends BaseActivityClass {

    /**
     *
     */
    private final View.OnClickListener translateListener = new View.OnClickListener() {

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            final String language;
            final String newLocaleString;
            //Locale newLocale = null;
            int idChosen = v.getId();
            switch (idChosen) {
                case R.id.buttonEnglish:
                    language = getString(R.string.english);
                    newLocaleString = ClassLocaleManager.LANGUAGE_KEY_US;
                    break;
                case R.id.buttonFrench:
                    language = getString(R.string.french);
                    newLocaleString = ClassLocaleManager.LANGUAGE_KEY_FRENCH;
                    break;
                case R.id.cbPrivileges:
                    language = getString(R.string.haitian_creole);
                    newLocaleString = ClassLocaleManager.LANGUAGE_KEY_HAITIAN_CREOLE;
                    break;
                case R.id.addAdmin:
                    language = getString(R.string.korean);
                    newLocaleString = ClassLocaleManager.LANGUAGE_KEY_KOREAN;
                    break;
                default:
                    language = getString(R.string.english);
                    newLocaleString = ClassLocaleManager.LANGUAGE_KEY_US;
                    break;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(PageTranslate.this,R.style.DialogTheme);
            builder.setTitle(getString(R.string.translate_to) + language);
            builder.setMessage(getString(R.string.really_translate) + language);
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClassLocaleManager.setNewLocale(PageTranslate.this, newLocaleString);
                    ApplicationCustom.finishFlag = true;
                    recreate();
                }
            });
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), getString(R.string.against_translating), Toast.LENGTH_SHORT).show();
                }
            });
            final AlertDialog alertDialog = builder.create();
            classDialogHelper(alertDialog);
        }
    };

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Button english = findViewById(R.id.buttonEnglish);
        Button french = findViewById(R.id.buttonFrench);
        Button haitianCreole = findViewById(R.id.cbPrivileges);
        Button korean = findViewById(R.id.addAdmin);
        english.setOnClickListener(translateListener);
        french.setOnClickListener(translateListener);
        haitianCreole.setOnClickListener(translateListener);
        korean.setOnClickListener(translateListener);
    }

    /**
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Finish Flag4", Boolean.toString(ApplicationCustom.finishFlag));
        recreate();
    }
}