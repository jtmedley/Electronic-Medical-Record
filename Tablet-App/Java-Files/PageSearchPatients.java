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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * Adapted from Balaji
 * https://stackoverflow.com/questions/4394293/create-a-new-textview-programmatically-then-display-it-below-another-textview
 */
public class PageSearchPatients extends BaseSearch {
    /**
     *
     */
    private String currentTriage;

    /**
     *
     */
    private double[] currentEncoding;

    /**
     *
     */
    private ArrayList<Integer> matchedPatientIds;

    /**
     *
     */
    private String newPatientFirstName;

    /**
     *
     */
    private String newPatientLastName;

    /**
     *
     */
    private LocalDate newPatientDOB;

    /**
     *
     */
    private String newPatientSex;

    /**
     *
     */
    private ClassDatabaseHandler connServer;

    /**
     *
     */
    private boolean noResultsFlag;

    /**
     *
     */
    private boolean optOutFlag;

    /**
     *
     */
    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int chosen = v.getId();
            foundFirstName = (String) searchResults.get(chosen).get("First Name");
            foundLastName = (String) searchResults.get(chosen).get("Last Name");
            foundSex = (String) searchResults.get(chosen).get("Sex");
            String foundDOBString = (String) searchResults.get(chosen).get("Date of Birth");
            if (foundDOBString != null) {
                foundDOB = LocalDate.parse(foundDOBString, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            currentPersonId = (int) searchResults.get(chosen).get("Person ID");
            Log.d("Selected ID",Integer.toString(currentPersonId));
            foundDischarged = (boolean) searchResults.get(chosen).get("Discharged");
            Log.d("Patient row selected:", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(chosen));
            currentTriage = (String) searchResults.get(chosen).get("Current Triage");
            openRecords();
        }
    };

    /**
     *
     */
    private final View.OnClickListener createButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            confirmDialog();
        }

        void confirmDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
            builder.setTitle(getString(R.string.create_new_with_previous));
            builder.setMessage(getString(R.string.create_message));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ApplicationCustom.getAppContext(),
                            getString(R.string.creating), Toast.LENGTH_LONG).show();

                    Intent intent = startNew();
                    intent.putExtra("foundFirstName", newPatientFirstName);
                    intent.putExtra("foundLastName", newPatientLastName);
                    if (newPatientDOB != null) {
                        intent.putExtra("yearDOB", newPatientDOB.getYear());
                        intent.putExtra("monthDOB", newPatientDOB.getMonthValue());
                        intent.putExtra("dayDOB", newPatientDOB.getDayOfMonth());
                    }
                    intent.putExtra("foundSex", newPatientSex);
                    intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_BOTH);
                    intent.putExtra("imageFlag", imageFlag);
                    if (imageFlag) {
                        intent.putExtra("currentEncoding", currentEncoding);
                    }
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_additional), Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), getString(R.string.against_new_patient), Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_search_results);
        newPatientFirstName = getIntent().getStringExtra("newPatientFirstName");
        newPatientLastName = getIntent().getStringExtra("newPatientLastName");
        newPatientSex = getIntent().getStringExtra("newPatientSex");
        Log.d("Received Name and Sex: ", newPatientFirstName + newPatientLastName + newPatientSex);
        int year = getIntent().getIntExtra("yearDOB", 0);
        int month = getIntent().getIntExtra("monthDOB", 0);
        int day = getIntent().getIntExtra("dayDOB", 0);
        Log.d("Date Values:", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(year) + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(month) + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(day));
        if (year == 0 || month == 0 || day == 0) {
            //error getting date
            Log.d("Get Date Error:", "error getting date");
            newPatientDOB = null;
        } else {
            newPatientDOB = LocalDate.of(year, month, day);
            Log.d("Received DOB: ", newPatientDOB.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        optOutFlag = getIntent().getBooleanExtra("optOutFlag", false);
        imageFlag = getIntent().getBooleanExtra("imageFlag", false);
        if (imageFlag && !optOutFlag) {
            matchedPatientIds = getIntent().getIntegerArrayListExtra("faceMatchedPatientIds");
            Log.d("Number of matches", Integer.toString(matchedPatientIds.size()));
            if (!matchedPatientIds.isEmpty()) {
                Log.d("First patient ID", Integer.toString(matchedPatientIds.get(0)));
            }
            currentEncoding = getIntent().getDoubleArrayExtra("currentEncoding");
        }
        super.onCreate(savedInstanceState);
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
    void handleResults() {
        if (connServer != null) {
            if (connServer.cThread.isAlive()) {
                try {
                    connServer.cThread.join();
                } catch (InterruptedException e) {
                    toastUiThread(getString(R.string.databaseError));
                    e.printStackTrace();
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
            if (connServer.connected) {
                checkResults();
            } else {
                toastUiThread(getString(R.string.databaseError));
            }
        }
    }

    /**
     *
     */
    @Override
    void checkResults() {
        if (connServer.results.size() > 0) {
            toastUiThread(
                    getString(R.string.some_found));
            Log.d("tag44", "Matches2");
            searchResults = connServer.results;
            final Thread displayThread = new Thread() {
                public void run() {
                    displayResults();
                }
            };
            runOnUiThread(displayThread);
        } else {
            noResultsFlag = true;
            String error5 = getString(R.string.try_or_create);
            toastUiThread(error5);
            Log.d("tag20", "Matches2");
            String errString2 = getString(R.string.no_found_likely_new);
            toastUiThread(errString2);
            Intent intent = startNew();
            intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_BOTH);
            intent.putExtra("foundFirstName", newPatientFirstName);
            intent.putExtra("foundLastName", newPatientLastName);
            intent.putExtra("foundSex", newPatientSex);
            intent.putExtra("imageFlag", imageFlag);
            if (imageFlag) {
                intent.putExtra("currentEncoding", currentEncoding);
            }
            if (newPatientDOB != null) {
                intent.putExtra("yearDOB", newPatientDOB.getYear());
                intent.putExtra("monthDOB", newPatientDOB.getMonthValue());
                intent.putExtra("dayDOB", newPatientDOB.getDayOfMonth());
            }
            startActivity(intent);
        }
    }

    /**
     *
     */
    @Override
    void makeQuery() {
        queryMaker = new ClassQueryMakers();
        boolean allBlank = true;

        if (newPatientFirstName != null) {
            if (!newPatientFirstName.isEmpty()) {
                allBlank = false;
            }
        }
        if (newPatientLastName != null) {
            if (!newPatientLastName.isEmpty()) {
                allBlank = false;
            }
        }
        if (newPatientDOB != null) {
            allBlank = false;
        }
        if (newPatientSex != null) {
            if (!newPatientSex.isEmpty()) {
                allBlank = false;
            }
        }
        Log.d("Is everything blank?", Boolean.toString(allBlank));
        if (!allBlank) {
            if (imageFlag && !optOutFlag) {
                if (matchedPatientIds != null) {
                    if (!matchedPatientIds.isEmpty()) {
                        Log.d("Checking", "Matches found");
                        queryMaker.selectWithFaceQuery(newPatientFirstName,
                                newPatientLastName, newPatientDOB, newPatientSex, matchedPatientIds);
                        connServer = new ClassDatabaseHandler(PageSearchPatients.this, queryMaker, 2);
                    } else {
                        Log.d("Checking", "No matches found 1");
                        makeBasicQuery();
                    }
                } else {
                    Log.d("Checking", "No matches found 2");
                    makeBasicQuery();
                }
            } else {
                Log.d("Checking", "Opted out or took no image, switching to basic");
                makeBasicQuery();
            }
        } else if (imageFlag && !optOutFlag) {
            queryMaker.selectOnlyFaceQuery(matchedPatientIds);
            connServer = new ClassDatabaseHandler(PageSearchPatients.this, queryMaker, -1);
        } else {
            Log.d("Checking", "No image taken");
            makeBasicQuery();
        }
        for (
                int i = 0; i < queryMaker.parameterArray.size(); i++) {
            Log.d("Search parameters:", queryMaker.parameterArray.toString());
        }

    }

    /**
     *
     */
    private void makeBasicQuery() {

        queryMaker.selectBasicQuery(newPatientFirstName,
                newPatientLastName, newPatientDOB, newPatientSex);
        connServer = new ClassDatabaseHandler(PageSearchPatients.this, queryMaker, 1);

    }

    /**
     *
     */
    @Override
    void displayResults() {
        //final TextView[] myTextViews =
        // new TextView[results.listSize()]; // create an empty array;
        int sizeInt = searchResults.size();
        Log.d("Number of results:", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(sizeInt));
        displayCount = 0;
        if (matchedPatientIds != null) {
            while (displayCount < searchResults.size()) {
                int currentPatientID = (int) searchResults.get(displayCount).get("Person ID");
                if (matchedPatientIds.contains(currentPatientID)) {
                    putPatient();

                }
                displayCount++;
            }
            displayCount = 0;
            while (displayCount < searchResults.size()) {
                int currentPatientID = (int) searchResults.get(displayCount).get("Person ID");
                if (!matchedPatientIds.contains(currentPatientID)) {
                    putPatient();

                }
                displayCount++;
            }
        } else {
            while (displayCount < searchResults.size()) {
                putPatient();

                displayCount++;
            }
        }
    }

    /**
     *
     */
    @Override
    void putPatient() {
        final TableRow tableRow = new TableRow(ApplicationCustom.getAppContext());
        final ImageView imageView = setImage();
        final TextView rowTextView = setRowText();
        if (imageView != null) {
            tableRow.addView(imageView);
            imageView.setId(displayCount);
        }
        tableRow.addView(rowTextView);
        tableRow.setId(displayCount);
        String rowDisplay = getRowDisplay();
        rowTextView.setText(rowDisplay);
        rowTextView.setId(displayCount);

        searchTable.addView(tableRow);
        final TableRow lineBreakRow = new TableRow(ApplicationCustom.getAppContext());
        lineBreakRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final TextView textViewLineBreak = new TextView(ApplicationCustom.getAppContext());
        textViewLineBreak.setHeight(20);
        lineBreakRow.addView(textViewLineBreak);
        searchTable.addView(lineBreakRow);
        rowTextView.setOnClickListener(clickListener);
        if (imageView != null) {
            imageView.setOnClickListener(clickListener);
        }
    }

    /**
     *
     * @return
     */
    @Override
    String getRowDisplay() {
        String rowDisplay = getString(R.string.row_first) + searchResults.get(displayCount).get("First Name");
        rowDisplay += "\n\n" +
                getString(R.string.row_last) + searchResults.get(displayCount).get("Last Name");
        String displayDOBString = (String) searchResults.get(displayCount).get("Date of Birth");
        if (displayDOBString != null) {
            LocalDate displayDOB = LocalDate.parse(displayDOBString, DateTimeFormatter.ISO_LOCAL_DATE);
            displayDOBString = displayDOB.format(DateTimeFormatter.ISO_LOCAL_DATE);
            rowDisplay += "\n\n" +
                    getString(R.string.row_dob) + displayDOBString;
        } else {
            rowDisplay += "\n\n" +
                    getString(R.string.row_unknown);
        }
        rowDisplay += "\n\n" +
                getString(R.string.row_sex) + searchResults.get(displayCount).get("Sex");
        String displayDOVString = (String) searchResults.get(displayCount).get("Date of Visit");

        if (displayDOVString != null) {
            LocalDate displayDOV = LocalDate.parse(displayDOVString);
            displayDOVString = displayDOV.format(DateTimeFormatter.ISO_LOCAL_DATE);
            rowDisplay += "\n\n" +
                    getString(R.string.row_last_visit) + displayDOVString;
        } else {
            rowDisplay += "\n\n" +
                    getString(R.string.row_last_unknown);
        }
        return rowDisplay;
    }



    /**
     *
     */
    private void openRecords() {

        Intent intent = new Intent(this, PageSearchRecords.class);
        intent.putExtra("currentPersonId", currentPersonId);
        Toast.makeText(this, getString(R.string.found_loading), Toast.LENGTH_LONG).show();
        intent.putExtra("foundFirstName", foundFirstName);
        intent.putExtra("foundLastName", foundLastName);
        if (foundDOB != null) {
            intent.putExtra("yearDOB", foundDOB.getYear());
            intent.putExtra("monthDOB", foundDOB.getMonthValue());
            intent.putExtra("dayDOB", foundDOB.getDayOfMonth());
        }
        intent.putExtra("foundSex", foundSex);
        intent.putExtra("currentTriage", currentTriage);
        intent.putExtra("foundDischarged", foundDischarged);
        intent.putExtra("imageFlag", imageFlag);
        if (imageFlag) {
            intent.putExtra("currentEncoding", currentEncoding);
        }
        startActivity(intent);
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (noResultsFlag) {
            finish();
        }
    }
}


