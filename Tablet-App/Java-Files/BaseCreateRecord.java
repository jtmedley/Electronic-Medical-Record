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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;


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
public abstract class BaseCreateRecord extends BaseRecord {

    /**
     * The encoding of the current patient image passed from an intent.
     */
    ArrayList<Double> currentEncoding;

    /**
     * The flag indicating whether an image was taken by the current patient passed from an intent.
     */
    boolean imageFlag;

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
        setPage();
    }

    /**
     * Sets the views in the page to the values specified by teh results of the search.
     * Sets the full page and clears the clinician signature and triage fields if the clinician is
     * using an existing record as a template for the patient.
     * Otherwise receives the current image encoding from the intent and puts the patient image into the
     * activity if a file containing the image has been written to its defaulkt location.
     */
    @Override
    void setPage() {
        createSimilarFlag = getIntent().getBooleanExtra("createSimilarFlag", false);
        Log.d("Creating similar?", Boolean.toString(createSimilarFlag));

        if (createSimilarFlag) {
            super.setPage();
            editTextClinicianSignature.setText("");
            editTextTriage.setText("");
        } else {
            examDOBDatePicker.init(foundDOB.getYear(), foundDOB.getMonthValue() - 1,
                    foundDOB.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String makeAge = getAge(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                            editTextAge.setText(makeAge);
                            Log.d("Date changing", "Date changing");

                        }
                    });

            imageFlag = getIntent().getBooleanExtra("imageFlag", false);
            if (imageFlag) {
                currentEncoding = new ArrayList<>();
                Object tempObject = getIntent().getDoubleArrayExtra("currentEncoding");
                Log.d("Face Received", (Arrays.toString((double[]) tempObject)));
                if (tempObject != null) {
                    double[] tempEncoding = (double[]) tempObject;
                    for (double aTempEncoding : tempEncoding) {
                        currentEncoding.add(aTempEncoding);
                    }
                }
                try {
                    bitmap = BitmapFactory.decodeStream(this
                            .openFileInput("newImage"));
                    Log.d("Test tag", "Reading Image");
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    patientImage.setImageBitmap(bitmap);
                    Log.d("Test tag", "Width = " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(width) + "Height = " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(height));
                    setImage();
                } catch (
                        FileNotFoundException e) {
                    Log.d("Image Error:", "No Image found");
                }
            }
        }
    }

    /**
     * Compresses the full patient image into a 30% compressed image that allows the thumbnail of the patient image to be
     * sent quickly over the socket connection and stored easily in the database.e
     */
    @Override
    void setImage() {
        Thread thread = new Thread() {
            public void run() {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                    examImage = stream.toByteArray();
                }
            }

        };
        thread.start();
    }

    /**
     * Sets the buttons on the page to create the new record.
     */
    void setButtons() {

        Button completeButton = makeButton();
        completeButton.setText(getString(R.string.complete));
        bottomTable.addView(completeButton);
        completeButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Displays a confirmation dialog if the results read from the page are appropirate and
             * the required fields have been entered.
             * @param v The view clicked.
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
             * Sets the dialog to finish the activity on a positive click.
             */
            void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(), R.style.DialogTheme);
                builder.setTitle(getString(R.string.to_complete));
                builder.setMessage(getString(R.string.want_to_complete));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Finishes the activity by executing the query going containing the
                     * input the user has added to the activity, then completes the activity and returns to the welcome page.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executeQuery();
                        goToWelcome();
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.against_new_record),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }
        });
    }

    /**
     * Toasts the application with an appropriate message about the action performed.
     */
    @Override
    void completedToast() {
        Toast.makeText(ApplicationCustom.getAppContext(),
                getString(R.string.saved_record), Toast.LENGTH_LONG).show();
    }
}

