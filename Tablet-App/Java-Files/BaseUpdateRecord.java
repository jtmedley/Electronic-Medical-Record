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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Allows users to interact with examination records.
 * <p>
 * <p>
 * <p>
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * and from "Never Quit"
 * https://stackoverflow.com/questions/10028211/how-can-i-get-the-date-from-the-edittext-and-then-store-it-in-database-in-androi/10028980
 * and from Xar E Ahmer
 * https://stackoverflow.com/questions/11547327/android-best-way-to-convert-byte-array-to-bitmap/11879370
 * and from "Robby Pond"
 * https://stackoverflow.com/questions/5255184/android-and-setting-width-and-height-programmatically-in-dp-units
 * and from "Aldrin Mathew"
 * https://stackoverflow.com/questions/43630146/make-all-edittext-not-editable-together/43630456
 * and from "shahab"
 * https://tausiq.wordpress.com/2013/01/19/android-input-field-validation/
 */
public abstract class BaseUpdateRecord extends BaseRecord {

    /**
     *
     */
    String currentTriage;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTriage = getIntent().getStringExtra("currentTriage");
        super.onCreate(savedInstanceState);
    }

    /**
     *
     */
    @Override
    void setButtons() {
        Button updateButton = makeButton();
        updateButton.setText(getString(R.string.updateThisRecord));
        bottomTable.addView(updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                boolean success = getPage();
                if (success) {
                    confirmDialog();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.required),
                            Toast.LENGTH_SHORT).show();
                }

            }

            /**
             *
             */
            void confirmDialog(){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
                builder.setTitle(getString(R.string.title_update));
                builder.setMessage(getString(R.string.update_message));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executeQuery();
                        goToWelcome();

                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_update),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }
        });

    }

    /**
     *
     */
    @Override
    void completedToast() {
        Toast.makeText(ApplicationCustom.getAppContext(),
                getString(R.string.update_success), Toast.LENGTH_LONG).show();
    }
}
