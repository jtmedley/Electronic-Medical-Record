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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapted from https://gist.github.com/hello2pal/4548094
 * and from https://stackoverflow.com/questions/20713273/dismiss-keyboard-when-click-outside-of-edittext-in-android/32149756
 */
public class PageLogin extends BaseActivityClass {

    /**
     *
     */
    private String usernameString;

    /**
     *
     */
    private String passwordString;

    /**
     *
     */
    private EditText username;

    /**
     *
     */
    private EditText password;

    /**
     *
     */
    private TextView loginInfo;

    /**
     *
     */
    private Button login;

    /**
     *
     */
    private int counter = 5;

    /**
     *
     */
    private final TextWatcher usernameTextWatcher = new TextWatcher() {
        /**
         *
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         *
         * @param s
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            usernameString = username.getText().toString();
        }

        /**
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            boolean check = usernameString.isEmpty();
            if (check) {
                Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.please_username), Toast.LENGTH_LONG).show();
            }

        }
    };

    /**
     *
     */
    private final TextWatcher passwordTextWatcher = new TextWatcher() {

        /**
         *
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         *
         * @param s
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            passwordString = password.getText().toString();
        }

        /**
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            boolean check = passwordString.isEmpty();
            if (check) {
                Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.please_password), Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        for (String url : ClassConstants.IMAGE_URLS) {
            Log.d("Image URLs:", url);
        }
        username = findViewById(R.id.admin);
        password = findViewById(R.id.password);
        loginInfo = findViewById(R.id.tv_login);
        login = findViewById(R.id.buttonLogin);
        username.addTextChangedListener(usernameTextWatcher);
        password.addTextChangedListener(passwordTextWatcher);
        usernameString = "";
        passwordString = "";

        String loginMessage = "# " + getString(R.string.of_correct_attempts_left) + " 5";
        loginInfo.setText(loginMessage);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

            }
        });

    }

    /**
     *
     */
    private void validate() {
        toastUiThread(getString(R.string.logging));
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
        if (usernameString.trim().equals("") || passwordString.trim().equals(""))
            Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.please_enter), Toast.LENGTH_LONG).show();
        else {

            ClassQueryMakers queryMaker = new ClassQueryMakers();
            queryMaker.getLogin(usernameString, passwordString);
            ClassDatabaseHandler connServer = new ClassDatabaseHandler(this,queryMaker, 0);
            try {
                if (connServer.cThread != null) {
                    if (connServer.cThread.isAlive()) {
                        connServer.cThread.join();
                    }

                    Log.d("tag86", "tag86");
                    ArrayList<HashMap<String, Object>> loginResults = connServer.results;
                    if (connServer.connected) {
                        Log.d("Tag95", "Tag95");
                        if (loginResults.size() == 0) {
                            Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.incorrect), Toast.LENGTH_LONG).show();
                            counter--;

                            String loginMessage = "# " + getString(R.string.remaining) + String.valueOf(counter);
                            loginInfo.setText(loginMessage);

                            if (counter == 0) {
                                login.setEnabled(false);
                            }
                        } else {
                            String locationSetting = (String) loginResults.get(0).get("Location");
                            ApplicationCustom.setLocationSettings(locationSetting);
                            Log.d("Location received", locationSetting);
                            Log.d("Location set", ApplicationCustom.locationString);
                            ApplicationCustom.login = true;
                            Intent intent = new Intent(ApplicationCustom.getAppContext(), PageWelcome.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                        Log.d("Tag90", "Tag90");
                    }
                } else {
                    Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                    Log.d("Tag91", "Tag91");
                }
            } catch (InterruptedException e) {
                Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(ApplicationCustom.getAppContext(),
                getString(R.string.not_go_back),
                Toast.LENGTH_LONG).show();
    }

    /**
     *
     */
    @Override
    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }
    }
}