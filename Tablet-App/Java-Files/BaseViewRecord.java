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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
public abstract class BaseViewRecord extends BaseRecord {

    /**
     *
     */
    private Button createSimilarButton;

    /**
     *
     */
    private Button editRecordButton;

    /**
     * Set the method of interaction based on received flag from other pages indicating the preference
     * assigned.
     *
     * @param savedInstanceState Bundle: If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data
     *                           it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = findViewById(R.id.Scroller);
        lockText(scrollView, false);
        lockText(editRecordButton, true);
        lockText(createSimilarButton, true);
    }

    /**
     * Prevent the text from being edited, called if the record should be set to a view only mode,
     * or release it for editing when view only mode is released
     *
     * @param view     the layout view containing text to be locked from editing
     * @param editable a boolean identifying whether the text should be locked or unlocked
     */
    private void lockText(View view, boolean editable) {
        if (!(view instanceof Button || view instanceof ViewGroup)) {
            view.setFocusable(editable);
            view.setClickable(editable);
            view.setEnabled(editable);

        }
        if (view instanceof RadioButton) {
            view.setFocusable(editable);
            view.setClickable(editable);
            view.setEnabled(editable);
        }
        if (view instanceof CheckBox) {
            view.setFocusable(editable);
            view.setClickable(editable);
            view.setEnabled(editable);
        }
        if (view instanceof DatePicker) {
            view.setFocusable(editable);
            view.setClickable(editable);
            view.setEnabled(editable);
        }
        if (view instanceof RadioGroup || view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                lockText(innerView, editable);
            }
        }
    }

    /**
     *
     */
    private final View.OnClickListener createSimilarListener = new View.OnClickListener() {
        /**
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
            builder.setTitle(getString(R.string.creating_new_title));
            builder.setMessage(getString(R.string.creating_new_message));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = startNew();
                    Toast.makeText(getApplicationContext(), getString(R.string.creating_new), Toast.LENGTH_SHORT).show();
                    intent.putExtra("foundFirstName", foundFirstName);
                    intent.putExtra("foundLastName", foundLastName);
                    if (foundDOB != null) {
                        intent.putExtra("yearDOB", foundDOB.getYear());
                        intent.putExtra("monthDOB", foundDOB.getMonthValue());
                        intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
                    }
                    intent.putExtra("createSimilarFlag", true);
                    intent.putExtra("foundSex", foundSex);
                    intent.putExtra("currentRecordId", currentRecordId);
                    intent.putExtra("currentPersonId", currentPersonId);
                    intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_RECORD);

                    startActivity(intent);

                }
            });

            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), getString(R.string.against_creating_new), Toast.LENGTH_SHORT).show();
                }
            });
            final AlertDialog alertDialog = builder.create();
            Log.d("Dialog", String.valueOf(builder));
            classDialogHelper(alertDialog);
        }
    };

    /**
     *
     */
    @Override
    void setButtons() {
        editRecordButton = makeButton();
        editRecordButton.setText(getString(R.string.editThis));
        bottomTable.addView(editRecordButton);
        editRecordButton.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = confirmDialog();
                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }

            AlertDialog.Builder confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
                builder.setTitle(getString(R.string.edit_title));
                builder.setMessage(getString(R.string.edit_message));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = startUpdate();
                            Toast.makeText(getApplicationContext(), getString(R.string.unlocking),
                                    Toast.LENGTH_SHORT).show();
                            intent.putExtra("foundFirstName", foundFirstName);
                            intent.putExtra("foundLastName", foundLastName);
                            if (foundDOB != null) {
                                intent.putExtra("yearDOB", foundDOB.getYear());
                                intent.putExtra("monthDOB", foundDOB.getMonthValue());
                                intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
                            }
                            intent.putExtra("foundSex", foundSex);
                            intent.putExtra("currentRecordId", currentRecordId);
                            intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_UPDATE_BOTH);
                            intent.putExtra("currentPersonId", currentPersonId);
                            startActivity(intent);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_editing),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                return builder;
            }
        });
        createSimilarButton = makeButton();
        createSimilarButton.setText(getString(R.string.template));
        createSimilarButton.setOnClickListener(createSimilarListener);
        bottomTable.addView(createSimilarButton);
    }
}
