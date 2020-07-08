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

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * Adapted from "shashi mishra"
 * https://stackoverflow.com/questions/41957011/why-cant-the-inner-variable-is-being-initialised-with-value-when-button-is-click
 */
public class PageAdmin extends BaseActivityClass {

    /**
     *
     */
    private EditText firstName;

    /**
     *
     */
    private EditText lastName;

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
    private CheckBox privileges;

    /**
     *
     */
    private Button addAdministrator;

    /**
     *
     */
    private String usernameString = "";

    /**
     *
     */
    private String passwordString = "";

    /**
     *
     */
    private String firstNameString = "";

    /**
     *
     */
    private String lastNameString = "";

    /**
     *
     */
    private final TextWatcher firstNameTextWatcher = new TextWatcher() {

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
            firstNameString = firstName.getText().toString();
        }

        /**
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            boolean check = firstNameString.isEmpty();
            if (check) {
                Toast.makeText(ApplicationCustom.getAppContext(), "Please enter a first name.", Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     *
     */
    private final TextWatcher lastNameTextWatcher = new TextWatcher() {

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
            lastNameString= lastName.getText().toString();
        }

        /**
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            boolean check = lastNameString.isEmpty();
            if (check) {
                Toast.makeText(ApplicationCustom.getAppContext(), "Please enter a last name.", Toast.LENGTH_LONG).show();
            }
        }
    };

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
                Toast.makeText(ApplicationCustom.getAppContext(), "Please enter a username.", Toast.LENGTH_LONG).show();
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
                Toast.makeText(ApplicationCustom.getAppContext(), "Please enter a password.", Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        privileges = findViewById(R.id.cbPrivileges);
        addAdministrator = findViewById(R.id.addAdmin);
        firstName.addTextChangedListener(firstNameTextWatcher);
        lastName.addTextChangedListener(lastNameTextWatcher);
        username.addTextChangedListener(usernameTextWatcher);
        password.addTextChangedListener(passwordTextWatcher);

        addAdministrator.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(ApplicationCustom.getAppContext(), "Logging in.", Toast.LENGTH_LONG).show();
                validate();
            }
        });
    }



    /**
     *
     */
    private void validate() {
        if (firstNameString.trim().equals("") || lastNameString.trim().equals("") ||
                usernameString.trim().equals("") || passwordString.trim().equals(""))
            Toast.makeText(ApplicationCustom.getAppContext(), "Please enter all information", Toast.LENGTH_LONG).show();
        else {
            boolean privilegesChecked = privileges.isChecked();
            ClassQueryMakers queryMaker = new ClassQueryMakers();
            queryMaker.setLogin(firstNameString, lastNameString, usernameString, passwordString, ApplicationCustom.locationString, privilegesChecked);
            ClassDatabaseHandler connServer = new ClassDatabaseHandler(this,queryMaker, 22);
            try {
                if (connServer.cThread.isAlive()) {
                    connServer.cThread.join();
                }
                Log.d("tag86", "tag86");
                if (connServer.connected) {
                    Log.d("Tag92", "Tag92");
                    if(connServer.affected == 1) {
                        Toast.makeText(ApplicationCustom.getAppContext(), "User successfully added.", Toast.LENGTH_LONG).show();
                        addAdministrator.setEnabled(false);
                    } else{
                        Toast.makeText(ApplicationCustom.getAppContext(), "Database Error. Please try again.", Toast.LENGTH_LONG).show();
                        Log.d("Tag93", "Tag93");
                    }
                } else {
                    Toast.makeText(ApplicationCustom.getAppContext(), "Database Error. Please try again.", Toast.LENGTH_LONG).show();
                    Log.d("Tag94", "Tag94");
                }
            } catch (InterruptedException e) {
                Toast.makeText(ApplicationCustom.getAppContext(), "Database Error. Please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}