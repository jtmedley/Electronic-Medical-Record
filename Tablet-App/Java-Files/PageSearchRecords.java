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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.NumberFormat;

/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * and from "Balaji"
 * https://stackoverflow.com/questions/4394293/create-a-new-textview-programmatically-then-display-it-below-another-textview
 * and from "Robby Pond"
 * https://stackoverflow.com/questions/5255184/android-and-setting-width-and-height-programmatically-in-dp-units
 */
public class PageSearchRecords extends BaseSearch {

    /**
     *
     */
    private String currentTriage;

    /**
     *
     */
    private TextView textViewPatientInfo;

    /**
     *
     */
    private final View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int chosen = v.getId();
            Log.d("Patient row: updating", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(chosen));
            try {
                int currentRecordId = (int) searchResults.get(chosen).get("Record ID");
                Log.d("Selected Record ID", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(currentRecordId));
                Intent intent = startUpdate();
                intent.putExtra("imageFlag", imageFlag);
                if (imageFlag) {
                    intent.putExtra("currentEncoding", currentEncoding);
                }
                intent.putExtra("currentTriage", currentTriage);
                intent.putExtra("currentRecordId", currentRecordId);
                intent.putExtra("currentPersonId", currentPersonId);
                intent.putExtra("foundFirstName", foundFirstName);
                intent.putExtra("foundLastName", foundLastName);
                if (foundDOB != null) {
                    intent.putExtra("yearDOB", foundDOB.getYear());
                    intent.putExtra("monthDOB", foundDOB.getMonthValue());
                    intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
                }
                intent.putExtra("foundSex", foundSex);
                intent.putExtra("foundDischarged", foundDischarged);
                intent.putExtra("actionFlag", ClassConstants.ACTION_FLAG_UPDATE_BOTH);
                Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.beingOpened), Toast.LENGTH_LONG).show();
                startActivity(intent);
            } catch (NullPointerException e) {
                toastUiThread(getString(R.string.databaseError));
                e.printStackTrace();
            }
        }
    };

    /**
     *
     */
    private final View.OnClickListener viewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int chosen = v.getId();
            Log.d("Record row: viewing:", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(chosen));
            try {
                Integer recordIdInt = (Integer) searchResults.get(chosen).get("Record ID");

                if (recordIdInt != null) {
                    int currentRecordId = recordIdInt;
                    Intent intent = startView();
                    if (imageFlag) {
                        intent.putExtra("currentEncoding", currentEncoding);
                    }
                    intent.putExtra("currentRecordId", currentRecordId);
                    intent.putExtra("currentPersonId", currentPersonId);
                    intent.putExtra("foundFirstName", foundFirstName);
                    intent.putExtra("foundLastName", foundLastName);
                    if (foundDOB != null) {
                        intent.putExtra("yearDOB", foundDOB.getYear());
                        intent.putExtra("monthDOB", foundDOB.getMonthValue());
                        intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
                    }
                    intent.putExtra("foundSex", foundSex);
                    intent.putExtra("foundDischarged", foundDischarged);
                    intent.putExtra("actionFlag", ClassConstants.ACTION_FLAG_VIEW);
                    Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.beingOpened), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    toastUiThread(getString(R.string.databaseError));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                toastUiThread(getString(R.string.databaseError));
            }
        }
    };

    /**
     *
     */
    private final View.OnClickListener createButtonListener = new View.OnClickListener() {

        /**
         * @param view
         */
        @Override
        public void onClick(View view) {

            try {
                Intent intent = startNew();
                intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_RECORD);
                intent.putExtra("imageFlag", imageFlag);
                if (imageFlag) {
                    //image should be loaded from newImage
                    intent.putExtra("currentEncoding", currentEncoding);
                }
                intent.putExtra("currentPersonId", currentPersonId);
                intent.putExtra("foundFirstName", foundFirstName);
                intent.putExtra("foundLastName", foundLastName);
                if (foundDOB != null) {
                    intent.putExtra("yearDOB", foundDOB.getYear());
                    intent.putExtra("monthDOB", foundDOB.getMonthValue());
                    intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
                }
                intent.putExtra("foundSex", foundSex);
                intent.putExtra("foundDischarged", foundDischarged);
                startActivity(intent);
            } catch (
                    NullPointerException e) {
                toastUiThread(getString(R.string.databaseError));
                e.printStackTrace();
            }
        }
    };

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_search_records);
        textViewPatientInfo = findViewById(R.id.tvPatientInfo);
        currentTriage = getIntent().getStringExtra("currentTriage");
        currentPersonId = getIntent().getIntExtra("currentPersonId", 0);
        getBasicInfo();
        imageFlag = getIntent().getBooleanExtra("imageFlag", false);
        if (imageFlag) {
            currentEncoding = getIntent().getDoubleArrayExtra("currentEncoding");
        }
        Log.d("Image Flag Received", Boolean.toString(imageFlag));
        super.onCreate(savedInstanceState);
    }

    /**
     *
     */
    @Override
    void makeQuery() {
        queryMaker = new ClassQueryMakers();
        queryMaker.selectPatientRecordsQuery(currentPersonId);
        connServer = new ClassDatabaseHandler(PageSearchRecords.this, queryMaker, 3);
    }

    @Override
    void checkResults() {
        Thread setTextThread = new Thread() {
            public void run() {
                String currentTriageString = "None";
                if (currentTriage != null) {
                    currentTriageString = currentTriage;
                }
                String textToSet = getString(R.string.recordsFor) + " " + foundFirstName + " " + foundLastName + "\n"
                        + getString(R.string.currentTriage) + " " + currentTriageString;
                if (foundDOB != null) {
                    String displayDOBString = foundDOB.format(DateTimeFormatter.ISO_LOCAL_DATE);
                    textToSet += " " + getString(R.string.top_dob) + displayDOBString;
                } else {
                    textToSet += " " + getString(R.string.top_dob_unknown);
                }
                String dischargedString = "\n" +getString(R.string.not_discharged);
                if (foundDischarged) {
                    dischargedString = "\n" +getString(R.string.not_checked_in);
                }
                textToSet += dischargedString;
                textViewPatientInfo.setText(textToSet);
            }
        };
        runOnUiThread(setTextThread);
        if (connServer.results.size() > 0) {
            toastUiThread(getString(R.string.someFound));
            Log.d("tag44", "Matches2");
            searchResults = connServer.results;
            final Thread displayThread = new Thread() {
                public void run() {
                    displayResults();
                }
            };
            runOnUiThread(displayThread);
        } else {
            toastUiThread(getString(R.string.no_results));
            Log.d("tag20", "No Matches");
            toastUiThread(getString(R.string.noMatches));
            Intent intent = startNew();
            intent.putExtra("foundFirstName", foundFirstName);
            intent.putExtra("foundLastName", foundLastName);
            if (foundDOB != null) {
                intent.putExtra("yearDOB", foundDOB.getYear());
                intent.putExtra("monthDOB", foundDOB.getMonthValue());
                intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
            }
            intent.putExtra("foundSex", foundSex);
            intent.putExtra("currentEncoding", imageFlag);
            if (imageFlag) {
                intent.putExtra("currentEncoding", currentEncoding);
            }
            intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_RECORD);
            startActivity(intent);
        }
    }

    /**
     *
     */
    @Override
    void putPatient() {
        final Button updateButton = makeButton();
        final Button viewButton = makeButton();
        updateButton.setText(getString(R.string.updateRecord));
        viewButton.setText(getString(R.string.viewRecord));
        viewButton.setId(displayCount);
        final TableRow tableRow = new TableRow(ApplicationCustom.getAppContext());
        final ImageView imageView = setImage();
        final TextView rowTextView = setRowText();
        if (imageView != null) {
            tableRow.addView(imageView);
        }
        tableRow.addView(rowTextView);
        tableRow.setId(displayCount);
        String rowDisplay = getRowDisplay();
        rowTextView.setText(rowDisplay);
        final LinearLayout tempLayout = new LinearLayout(ApplicationCustom.getAppContext());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        tempLayout.addView(updateButton);
        tempLayout.addView(viewButton);
        tableRow.addView(tempLayout);
        searchTable.addView(tableRow);
        final TableRow lineBreakRow = new TableRow(ApplicationCustom.getAppContext());
        lineBreakRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final TextView textViewLineBreak = new TextView(ApplicationCustom.getAppContext());
        textViewLineBreak.setHeight(20);
        lineBreakRow.addView(textViewLineBreak);
        searchTable.addView(lineBreakRow);
        updateButton.setOnClickListener(updateListener);
        viewButton.setOnClickListener(viewListener);
        viewButton.setId(displayCount);
        updateButton.setId(displayCount);
    }

    /**
     *
     * @return
     */
    String getRowDisplay() {
        String rowDisplay;

        try {
            Log.d("Display number", String.valueOf(displayCount));
            String displayDOVString = (String) searchResults.get(displayCount).get("Date of Visit");

            if (displayDOVString != null) {
                LocalDate displayDOV = LocalDate.parse(displayDOVString, DateTimeFormatter.ISO_LOCAL_DATE);
                displayDOVString = displayDOV.format(DateTimeFormatter.ISO_LOCAL_DATE);
                rowDisplay = "\n\n" +
                        getString(R.string.row_visit) + displayDOVString;
            } else {
                rowDisplay = "\n\n" +
                        getString(R.string.row_visit_unknown);
            }

            rowDisplay += "\n\n" +
                    getString(R.string.row_identifier) + searchResults
                    .get(displayCount).get("Current Triage");

        } catch (NullPointerException e) {
            rowDisplay = getString(R.string.loading_error);
            e.printStackTrace();
        }
        return rowDisplay;
    }

    /**
     * @return
     */
    private Button makeButton() {
        Button button = new Button(getActivityContext());
        TableRow.LayoutParams updateParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
        updateParams.leftMargin = 10;
        button.setTextSize((float) 30);
        button.setPadding(20, 50, 20, 50);
        button.setLayoutParams(updateParams);
        button.setGravity(Gravity.CENTER);
        return button;
    }

    /**
     *
     * @return
     */
    @Override
    View.OnClickListener makeCreateButtonListener() {
        return createButtonListener;
    }

    /**
     *
     */
    private void getBasicInfo() {
        foundFirstName = getIntent().getStringExtra("foundFirstName");
        foundLastName = getIntent().getStringExtra("foundLastName");
        foundSex = getIntent().getStringExtra("foundSex");

        int year = getIntent().getIntExtra("yearDOB", 0);
        int month = getIntent().getIntExtra("monthDOB", 0);
        int day = getIntent().getIntExtra("dayDOB", 0);
        if (year == 0 || month == 0 || day == 0) {
            //error getting date
            Log.d("Get Date Error:", "error getting date");
            foundDOB = LocalDate.now();
            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.ensure_date_before), Toast.LENGTH_LONG).show();
        } else {
            foundDOB = LocalDate.of(year, month, day);
        }
        Log.d("Output:", foundFirstName + " " + foundLastName + " " +
                foundDOB + " " + foundSex);
    }
}