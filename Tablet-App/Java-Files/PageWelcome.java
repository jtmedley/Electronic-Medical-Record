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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * Adapted from "shashi mishra"
 * https://stackoverflow.com/questions/41957011/why-cant-the-inner-variable-is-being-initialised-with-value-when-button-is-click
 */
public class PageWelcome extends BaseActivityClass {

    /**
     *
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(PageWelcome.this,
                getString(R.string.click_logout), Toast.LENGTH_LONG).show();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        ImageButton newRecord = findViewById(R.id.NewRecordImage);
        newRecord.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {


                confirmDialog();

            }

            /**
             *
             */
            void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PageWelcome.this,R.style.DialogTheme);
                builder.setTitle(getString(R.string.clicked_new));
                builder.setMessage(getString(R.string.new_message));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PageWelcome.this, PageNewPatient.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), getString(R.string.begin_entering), Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_new), Toast.LENGTH_SHORT).show();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }

        });
        ImageButton searchRecord = findViewById(R.id.SearchRecordImage);
        searchRecord.setOnClickListener(new View.OnClickListener() {

            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                confirmDialog();

            }

            /**
             *
             */
            void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PageWelcome.this,R.style.DialogTheme);
                builder.setTitle(getString(R.string.clicked_search));
                builder.setMessage(getString(R.string.search_message));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PageWelcome.this, PageNewPatient.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), getString(R.string.begin_entering), Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_searching), Toast.LENGTH_SHORT).show();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }


        });
        ImageButton logout = findViewById(R.id.LogoutImage);
        logout.setOnClickListener(new View.OnClickListener() {

            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                confirmDialog();

            }

            /**
             *
             */
            void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PageWelcome.this,R.style.DialogTheme);
                builder.setTitle(getString(R.string.clicked_logout));
                builder.setMessage(getString(R.string.log_out));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PageWelcome.this, PageLogin.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_logout), Toast.LENGTH_SHORT).show();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }
        });
    }
}