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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements methods to display a medical examination page, set completion buttons specifying the
 * intended final action of the page, receive patient identifiers and other information
 * from previous activities, query the database for information about existing patients to display,
 * set the views of the page to match the results of that query, notify users about potentially improper
 * updates to basic patient information, check for the completion of required fields, read the views into a second query object,
 * and perform a completion action using this query object appropriate for the function requested by the user.
 * <p>
 * Adapted from Yairopro
 * https://stackoverflow.com/questions/8543449/how-to-use-the-textwatcher-class-in-android
 */
public abstract class BaseRecord extends BaseActivityClass {

    /**
     * The patient's systolic blood pressure.
     */
    Integer examSystolicBP;

    /**
     * The patient's date of birth.
     */
    LocalDate examDOB;

    /**
     * The date of the patient's exam visit.
     */
    LocalDate examDateOfVisit;

    /**
     * The patient's triage location identifier.
     */
    String examTriage;

    /**
     * The patient's chart number.
     */
    Integer examChartNumber;

    /**
     * The patient's first name.
     */
    String examFirstName;

    /**
     * The patient's last name.
     */
    String examLastName;

    /**
     * The patient's sex.
     */
    String examSex;

    /**
     * Teh patient's body temperature.
     */
    Float examBodyTemperature;

    /**
     * The patient's diastolic blood pressure.
     */
    Integer examDiastolicBP;

    /**
     * The patient's height.
     */
    Integer examHeight;

    /**
     * The patient's height.
     */
    Integer examWeight;

    /**
     * The patient's BMI.
     */
    Float examBMI;

    /**
     * The patient's exam history beyond preset options.
     */
    String examOtherHistory;

    /**
     * The patient's dietary habits.
     */
    String examDiet;

    /**
     * The patient's chief complaint.
     */
    String examChiefComplaint;

    /**
     * The history of the patient's present illness.
     */
    String examHistPresentIll;

    /**
     * The assessment given for the patient.
     */
    String examAssessment;

    /**
     * The results of the physical examination for the patient.
     */
    String examPhysicalExam;

    /**
     * The prescription given to the patient.
     */
    String examPrescription;

    /**
     * The status of whether the patient should be discharged at the end of the examination.
     */
    boolean examDischarged;

    /**
     * The treatment plan for the patient.
     */
    String examTreatmentPlan;

    /**
     * The clinician signature.
     */
    String examSignature;

    /**
     * The first name of the clinician parsed from the signature.
     */
    String examClinicianFirst;

    /**
     * The last name of the clinician parsed from the signature.
     */
    String examClinicianLast;

    /**
     * The first name provided from the previous activity.
     */
    String foundFirstName;

    /**
     * The last name provided from the previous activity.
     */
    String foundLastName;

    /**
     * The sex provided from the previous activity.
     */
    String foundSex;

    /**
     * The first name provided from the previous activity.
     */
    LocalDate foundDOB;

    /**
     * The list of results returned from the server after a query has been executed.
     * Each list object contains one row of results.
     * The results are stored as HashMap objects.
     * The HashMap key for each result is a string containing the field names of the query result.
     * The HashMap value for each result is a SQL type object containing the value of the field in that row.
     */
    ArrayList<HashMap<String, Object>> fullResults;

    /**
     * The specific action case requested by the patient from the previous activity.
     */
    int actionFlag;

    /**
     * The bitmap image of the patient to be displayed on the page.
     */
    Bitmap bitmap;

    /**
     * The query maker object to be used for database communications, containing
     * an array of parameters for the query, and an array of the parameter types to be applied to
     * a prepared SQL statement.
     * This object stores the query that receives exam record information when the page loads.
     */
    ClassQueryMakers queryMaker;

    /**
     * The exam Record ID for the current patient's exam visit.
     */
    int currentRecordId;

    /**
     * The Person ID for the current patient.
     */
    int currentPersonId;

    /**
     * The byte array storing the patient's image for reading and writing.
     */
    byte[] examImage = null;

    /**
     * The width setting for the completion buttons to be created at the bottom of the activity.
     */
    private int buttonWidthSetting;

    /**
     * The query maker object to be used for database communications, containing
     * an array of parameters for the query, and an array of the parameter types to be applied to
     * a prepared SQL statement.
     * This object stores the query that is sent when the examination is completed and one of the completion
     * buttons is selected.
     */
    ClassQueryMakers queryMaker2;

    /**
     * The database connection object associated with the completion of the exam record, sending the query containing the
     * examination information when one of the completion buttons is selected.
     */
    ClassDatabaseHandler connServer2;

    /**
     * The clinic location for the patient.
     */
    String examLocationRecord;

    /**
     * The status of whether the clinician is updating basic patient information, used to select the appropriate query statement
     * and to handle warning messages.
     */
    boolean updateBasicFlag;

    /**
     * The identifier indicating which query is associated with the values stored in the query maker.
     */
    int queryIdentifier = -1;

    /**
     * The status of whether a basic patient information field has been changed, used in displaying warnings.
     */
    private boolean changed;

    /**
     * The status of whether a basic patient information field has just been changed, used to prevent warnings from displaying twice in a row.
     */
    private boolean justChecked;

    /**
     * The status of whether a the date of birth DatePicker object has been changed, used in displaying warnings about changing basic
     * patient information.
     */
    private boolean dateChanged;

    /**
     * The flag indicating whether the user has requested to create a new record using a previously viewed record as a template.
     */
    boolean createSimilarFlag;

    /**
     * The DatePicker that allows the date of birth to be selected.
     */
    DatePicker examDOBDatePicker;

    /**
     * The DatePicker that allows the date of the patient's examination visit to be selected.
     */
    private DatePicker examDateOfVisitPicker;

    /**
     * The view where the triage identifier for the patient can be input.
     */
    EditText editTextTriage;

    /**
     * The view where the chartn number for the patient can be input.
     */
    private EditText editTextChartNumber;

    /**
     * The view where the patient's first name can be input.
     */
    private EditText editTextFirstName;

    /**
     * The view where the patient's last name can be input.
     */
    private EditText editTextLastName;

    /**
     * The view where the patient's non-male and non-female sex can be input.
     */
    private EditText editTextSex;

    /**
     * The view where the age of the patient is calculated and displayed.
     */
    TextView editTextAge;

    /**
     * The view where the patient's body temperature  can be input.
     */
    private EditText editTextBodyTemp;

    /**
     * The view where the patient's diastolic blood pressure can be input.
     */
    private EditText editTextDBP;

    /**
     * The view where the patient's systolic blood pressure can be input.
     */
    private EditText editTextSBP;

    /**
     * The view where the height of the patient can be input.
     */
    private EditText editTextHeight;

    /**
     * The view where the weight of the patient can be input.
     */
    private EditText editTextWeight;

    /**
     * The view where the BMI the patient is calculated and displayed.
     */
    private TextView editTextBMI;

    /**
     * The view where the non-specific history for the patient can be input.
     */
    private EditText editTextOtherHistory;

    /**
     * The view where the dietary habits of the patient can be input.
     */
    private EditText editTextDiet;

    /**
     * The view where the patient's chief complaint can be input.
     */
    private EditText editTextChiefComplaint;

    /**
     * The view where the history of the patient's present illness can be input.
     */
    private EditText editTextHistoryOfPresentIllness;

    /**
     * The view where the assessment for the patient can be input.
     */
    private EditText editTextAssessment;

    /**
     * The view where the physical examination results for the patient can be input.
     */
    private EditText editTextPhysicalExamination;

    /**
     * The checkbox indicating whether the patient should be discharged after the examination is completed.
     */
    CheckBox checkBoxDischarged;

    /**
     * The view where the clinician's signature can be input.
     */
    EditText editTextClinicianSignature;

    /**
     * The view where the prescription for the patient can be input.
     */
    EditText editTextPrescription;

    /**
     * The view where the treatment plan for the patient can be input.
     */
    EditText editTextTreatmentPlan;

    /**
     * The button with which the patient's male  sex can be selected.
     */
    private RadioButton maleSex;

    /**
     * The button with which the patient's female sex can be selected.
     */
    private RadioButton femaleSex;

    /**
     * The button with which the patient's non-male and non-female sex can be selected.
     */
    private RadioButton otherSex;

    /**
     * The view where the patient's image is displayed.
     */
    ImageView patientImage = null;

    /**
     * The lowest view of the page to which completion button views are attached.
     */
    TableLayout bottomTable;

    /**
     * A TextWatcher that detects changes in basic patient information fields.
     */
    private final TextWatcher updateBasicWatcher = new TextWatcher() {

        /**
         * Required override.
         *
         * @param s The text before any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The length of the changed part in the s sequence since the start position
         * @param after The length of the new sequence which will replace the part of the s sequence from start to start + count
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * Checks whether any basic patient information has been changed if the exam record has been specified for use with an existing patient by a flag.
         * Displays a warning message if the information has been changed and the warning message was not just displayed.
         * Resets the basic patient information to their previous values if the clinician chooses not to update basic patient information.
         * Sets the status of whether the basic patient information is being updated.
         *
         * @param s The text after any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The after parameter in the beforeTextChanged method
         * @param before The count parameter in the beforeTextChanged method
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!updateBasicFlag) {
                changed = changedBasic();

                if (changed && !justChecked) {
                    justChecked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(), R.style.DialogTheme);
                    builder.setTitle(getString(R.string.basic_title));
                    builder.setMessage(getString(R.string.sure_edit_basic));
                    builder.setCancelable(false);
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        /**
                         *
                         * @param dialog
                         * @param which
                         */
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), getString(R.string.edit_basic),
                                    Toast.LENGTH_SHORT).show();
                            updateBasicFlag = true;

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
                            Toast.makeText(getApplicationContext(), getString(R.string.against_edit_basic),
                                    Toast.LENGTH_SHORT).show();
                            setBasicPage();
                            setDOB();
                            updateBasicFlag = false;

                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    classDialogHelper(alertDialog);

                } else {
                    justChecked = false;
                }

            }
        }

        /**
         * Required override.
         * @param s The changed text.
         */
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * A TextWatcher that detects changes in patient height and weight fields to calculate and display the patient's BMI.
     */
    private final TextWatcher heightAndWeightWatcher = new TextWatcher() {
        /**
         * Required override.
         * @param s The text before any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The length of the changed part in the s sequence since the start position
         * @param after The length of the new sequence which will replace the part of the s sequence from start to start + count
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         *Checks whether height and weight views have been changed and recalculates the BMI from the height and weight values if they have changed.
         * Displays the calculated BMI value in the BMI view.
         *
         * @param s The text after any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The after parameter in the beforeTextChanged method
         * @param before The count parameter in the beforeTextChanged method
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!(editTextHeight.getText().toString().isEmpty() || editTextWeight.getText().toString().isEmpty())) {

                try {
                    Integer height = Integer.parseInt(editTextHeight.getText().toString());
                    Integer weight = Integer.parseInt(editTextWeight.getText().toString());

                    String makeBMI = getBMI(height, weight);
                    editTextBMI.setText(makeBMI);
                } catch (NumberFormatException e) {
                    editTextBMI.setText(getString(R.string.enter_bmi));
                    e.printStackTrace();
                }
            } else {
                editTextBMI.setText(getString(R.string.enter_bmi));

            }
        }

        /**
         * Required override.
         * @param s The changed text.
         */
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * A listener that detects changes in the patient sex selected by RadioButton group.
     * Displays a warning message if the information has been changed and the warning message was not just displayed.
     * Resets the basic patient information to their previous values if the clinician chooses not to update basic patient information.
     * Sets the status of whether the basic patient information is being updated.
     */
    private final View.OnClickListener updateSexWatcher = new View.OnClickListener() {
        /**
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (!updateBasicFlag) {
                changed = changedBasic();

                if (changed && !sexJustChecked) {
                    sexJustChecked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(), R.style.DialogTheme);
                    builder.setTitle(getString(R.string.basic_title));
                    builder.setMessage(getString(R.string.sure_edit_basic));
                    builder.setCancelable(false);
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                        /**
                         *
                         * @param dialog
                         * @param which
                         */
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), getString(R.string.edit_basic),
                                    Toast.LENGTH_SHORT).show();
                            updateBasicFlag = true;

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
                            Toast.makeText(getApplicationContext(), getString(R.string.against_edit_basic),
                                    Toast.LENGTH_SHORT).show();
                            setBasicPage();
                            setDOB();
                            updateBasicFlag = false;

                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    classDialogHelper(alertDialog);

                } else {
                    justChecked = false;
                }

            }
        }

    };

    /**
     *
     */
    private final TextWatcher requiredWatcher = new TextWatcher() {

        /**
         * Displays an error message in views designated as required if they are not filled out.
         * @param s The changed text.
         */
        @Override
        public void afterTextChanged(Editable s) {
            if (editTextFirstName.getText().toString().isEmpty()) {
                editTextFirstName.setError(getString(R.string.first_required));
            }
            if (editTextClinicianSignature.getText().toString().isEmpty()) {
                editTextClinicianSignature.setError(getString(R.string.clinician_required));
            }
        }

        /**
         * Required override.
         *
         * @param s The text before any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The length of the changed part in the s sequence since the start position
         * @param after The length of the new sequence which will replace the part of the s sequence from start to start + count
         */
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * Required override.
         *
         * @param s The text after any change is applied.
         * @param start The position of the beginning of the changed part in the text.
         * @param count The after parameter in the beforeTextChanged method
         * @param before The count parameter in the beforeTextChanged method
         */
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    /**
     * The status of whether the patient sex RadioButton group has just been changed.
     */
    private boolean sexJustChecked;

    /**
     * @param savedInstanceState Contains any information saved if the activity is temporarily closed and then restored.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionFlag = getIntent().getIntExtra("ActionFlag", ClassConstants.ACTION_FLAG_VIEW);
        Log.d("ActionFlag", Integer.toString(actionFlag));
        float scale = getActivityContext().getResources().getDisplayMetrics().density;
        buttonWidthSetting = (int) (675 * scale + 0.5f);
        examLocationRecord = ApplicationCustom.locationString;
        setLocationXML();
        setViews();
        bottomTable = findViewById(R.id.tableLayout6);
        setButtons();
        getBasicInfo();
        setBasicPage();
        setAll();
        setSpecialStrings();
    }

    /**
     * Sets the date of birth DatePicker, onDateChangedListener, and all page views
     * if the clinician requested viewing, updating, or creating a similar exam record.
     */
    private void setAll() {
        if (createSimilarFlag || actionFlag == ClassConstants.ACTION_FLAG_VIEW ||
                actionFlag == ClassConstants.ACTION_FLAG_UPDATE_BOTH ||
                actionFlag == ClassConstants.ACTION_FLAG_UPDATE_RECORD) {
            editTextFirstName.addTextChangedListener(updateBasicWatcher);
            editTextLastName.addTextChangedListener(updateBasicWatcher);
            editTextSex.addTextChangedListener(updateBasicWatcher);
            maleSex.setOnClickListener(updateSexWatcher);
            femaleSex.setOnClickListener(updateSexWatcher);
            otherSex.setOnClickListener(updateSexWatcher);
            setDOB();
            setPage();
        }
    }

    /**
     * Sets the XML layout associated with the clinic location.
     */
    protected abstract void setLocationXML();

    /**
     * Sets the common examination views and the location-specific examination views to the identities specified in the XML layout.
     */
    private void setViews() {
        setExamViews();
        setLocationViews();
    }

    /**
     * Sets the completion action buttons at the bottom of the activity layout.
     */
    abstract void setButtons();

    /**
     * Receives patient exam information specified by previous input from database communications.
     * Sets the values of the common examination views and the location-specific examination views
     * in the layout if a single exam record result is received.
     */
    void setPage() {

        getFullResults();
        if (!(fullResults == null)) {
            int checkSize = fullResults.size();
            Log.d("tag20", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(checkSize));
            if (fullResults.size() == 1) {
                setExamPage();
                setLocationPage();
            }
        }
    }

    /**
     * Sets the date of birth DatePicker and OnDateChangedListener for entering the patient's date of birth.
     * Initializes the DatePicker fields to the values received from a previous activity.
     * Detects changes in patient height and weight fields to calculate and display the patient's BMI.
     */
    private void setDOB() {
        Log.d("Setting listener", "Standard listener");
        examDOBDatePicker.init(foundDOB.getYear(),
                foundDOB.getMonthValue() - 1, foundDOB.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {

                    /**
                     * Calculates the age of the patient and displays it in the Age view.
                     * Checks whether any basic patient information has been changed if the exam record has been specified for use with an existing patient by a flag.
                     * Displays a warning message if the information has been changed and the warning message was not just displayed.
                     * Resets the basic patient information to their previous values if the clinician chooses not to update basic patient information.
                     * Sets the status of whether the basic patient information is being updated.
                     *
                     * @param view The DatePicker view in the activity that was changed.
                     * @param year The year after a change occurs.
                     * @param monthOfYear The month of the year after a change occurs.
                     * @param dayOfMonth The day of the month after a change occurs.
                     */
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String makeAge = getAge(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                        editTextAge.setText(makeAge);
                        Log.d("Date changing", "Date changing");
                        Log.d("Old Stay Changed", Boolean.toString(updateBasicFlag));
                        Log.d("Just Checked", Boolean.toString(justChecked));
                        Log.d("Old Date Changed", Boolean.toString(dateChanged));

                        if (!updateBasicFlag) {
                            dateChanged = !(foundDOB.getYear() == year &&
                                    foundDOB.getMonthValue() == monthOfYear + 1 &&
                                    foundDOB.getDayOfMonth() == dayOfMonth);
                            Log.d("New Date Changed", Boolean.toString(dateChanged));

                            if (dateChanged) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(), R.style.DialogTheme);
                                builder.setTitle(getString(R.string.basic_title));
                                builder.setMessage(getString(R.string.sure_edit_basic));
                                builder.setCancelable(false);
                                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                                    /**
                                     *
                                     * @param dialog
                                     * @param which
                                     */
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.edit_basic),
                                                Toast.LENGTH_SHORT).show();
                                        updateBasicFlag = true;
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
                                        Toast.makeText(getApplicationContext(), getString(R.string.against_edit_basic),
                                                Toast.LENGTH_SHORT).show();
                                        String makeAge = getAge(foundDOB);
                                        editTextAge.setText(makeAge);
                                        examDOBDatePicker.updateDate(foundDOB.getYear(),
                                                foundDOB.getMonthValue() - 1, foundDOB.getDayOfMonth());
                                        updateBasicFlag = false;
                                    }
                                });
                                final AlertDialog alertDialog = builder.create();
                                classDialogHelper(alertDialog);
                                Log.d("New Just Checked", Boolean.toString(justChecked));

                            }
                        }
                    }
                });
    }

    /**
     * Sets specific views in the activity to have a # symbol follow the translated string to be displayed.
     * Necessary for translation purposes.
     */
    private void setSpecialStrings() {
        String textViewTriageString = getString(R.string.triage) + "# :";
        TextView textViewTriage = findViewById(R.id.tvTriage);
        textViewTriage.setText(textViewTriageString);
        TextView textViewChartNumber = findViewById(R.id.tvChartNumber);
        String textViewChartString = getString(R.string.chart) + " # :";
        String editTextChartString = getString(R.string.chart) + " #";
        textViewChartNumber.setText(textViewChartString);
        editTextChartNumber.setHint(editTextChartString);
    }

    /**
     * Receive current record ID selected from PageSearchRecords.
     * Query the database for all results for the selected record ID.
     */
    private void getFullResults() {
        try {
            currentRecordId = getIntent().getIntExtra("currentRecordId", 0);
            Log.d("Received Record ID", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(currentRecordId));
            queryMaker = new ClassQueryMakers();
            makeQuery();
            for (int i = 0; i < queryMaker.parameterArray.size(); i++) {
                Log.d("Search parameters:", queryMaker.parameterArray.toString());
            }
            ClassDatabaseHandler connServer = new ClassDatabaseHandler(this, queryMaker, 4);
            if (connServer.cThread.isAlive()) {
                try {
                    connServer.cThread.join();
                } catch (InterruptedException e) {
                    Toast.makeText(ApplicationCustom.getAppContext(),
                            getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            if (connServer.connected) {
                if (connServer.results.size() > 0) {
                    Toast.makeText(this,
                            getString(R.string.record_found), Toast.LENGTH_LONG)
                            .show();
                    Log.d("tag44", "Matches2");
                    fullResults = connServer.results;
                }
            } else {
                Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.databaseError), Toast.LENGTH_LONG).show();
            }
        } catch (
                NullPointerException e) {
            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.databaseError), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Receives basic patient information from a previous activity.
     */
    private void getBasicInfo() {
        currentPersonId = getIntent().getIntExtra("currentPersonId", 0);
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
                    getString(R.string.ensure_date), Toast.LENGTH_LONG).show();
        } else {
            foundDOB = LocalDate.of(year, month, day);
        }
        Log.d("Output:", Integer.toString(currentPersonId) + foundFirstName + " " + foundLastName + " " +
                foundDOB + " " + foundSex + " " + Integer.toString(actionFlag));
    }

    /**
     * Sets the values of the basic patient information fields to the values received from a previous activity.
     */
    private void setBasicPage() {
        if (foundFirstName != null) {
            editTextFirstName.setText(foundFirstName);
        }
        if (foundLastName != null) {
            editTextLastName.setText(foundLastName);
        }
        setSex();
        String makeAge = getAge(foundDOB);
        Log.d("Age found", makeAge);
        editTextAge.setText(makeAge);
        boolean foundDischarged = getIntent().getBooleanExtra("foundDischarged", false);
        if (foundDischarged) {
            checkBoxDischarged.setChecked(true);
        } else {
            checkBoxDischarged.setChecked(false);
        }
    }

    /**
     * Sets the values of the general examination fields to the associated results found from
     * the patient record received from database communications.
     */
    private void setExamPage() {
        try {
            setImage();
            editTextTriage.setText((String) fullResults.get(0).get("Triage"));
            Integer chartNumber = (Integer) fullResults.get(0).get("Chart Number");
            if (chartNumber != null) {
                editTextChartNumber.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(chartNumber));
            }
            final LocalDate examDateOfVisit;
            String dateOfVisitString = (String) fullResults.get(0).get("Date of Visit");
            if (dateOfVisitString != null) {
                examDateOfVisit = LocalDate.parse(dateOfVisitString);
            } else {
                examDateOfVisit = LocalDate.now();
            }
            examDateOfVisitPicker.init(examDateOfVisit.getYear(),
                    examDateOfVisit.getMonthValue() - 1, examDateOfVisit.getDayOfMonth(),
                    null);
            BigDecimal bodyTempDouble = (BigDecimal) fullResults.get(0).get("Body Temperature");
            if (bodyTempDouble != null) {
                editTextBodyTemp.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(bodyTempDouble));
            }
            Integer diastolicInt = (Integer) fullResults.get(0).get("Diastolic BP");
            if (diastolicInt != null) {
                editTextDBP.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(diastolicInt));
            }
            Integer systolicInt = (Integer) fullResults.get(0).get("Systolic BP");
            if (systolicInt != null) {
                editTextSBP.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(systolicInt));
            }
            Integer heightInt = (Integer) fullResults.get(0).get("Height");
            if (heightInt != null) {
                editTextHeight.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(heightInt));
            }
            Integer weightInt = (Integer) fullResults.get(0).get("Weight");
            if (weightInt != null) {
                editTextWeight.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(weightInt));
            }
            if (heightInt != null && weightInt != null) {
                String makeBMI = getBMI(heightInt, weightInt);
                editTextBMI.setText(makeBMI);
            } else {
                editTextBMI.setText(getString(R.string.enter_bmi));
            }
            editTextOtherHistory.setText((String) fullResults.get(0).get("Other History"));
            editTextDiet.setText((String) fullResults.get(0).get("Diet"));
            editTextChiefComplaint.setText((String) fullResults.get(0).get("Chief Complaint"));
            editTextHistoryOfPresentIllness.setText((String) fullResults.get(0)
                    .get("History of Present Illness"));
            editTextAssessment.setText((String) fullResults.get(0).get("Assessment"));
            editTextPhysicalExamination.setText((String) fullResults.get(0)
                    .get("Physical Examination"));
            editTextTreatmentPlan.setText((String) fullResults.get(0).get("Treatment Plan"));
            editTextPrescription.setText((String) fullResults.get(0).get("Prescription"));
            editTextClinicianSignature.setText((String) fullResults.get(0).get("Signature"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the values of the location-specific views in the activity to the associated results found
     * from the patient record received from database communications.
     */
    abstract void setLocationPage();

    /**
     * Sets the common examination views to the identities specified in the XML layout.
     */
    private void setExamViews() {
        examDOBDatePicker = findViewById(R.id.DOBDatePicker);
        examDateOfVisitPicker = findViewById(R.id.VisitDatePicker);
        patientImage = findViewById(R.id.patientImg);
        editTextTriage = findViewById(R.id.editTextTriage);
        editTextChartNumber = findViewById(R.id.editTextChartNumber);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.showAge);
        editTextSex = findViewById(R.id.editTextSex);
        editTextSex.setVisibility(View.INVISIBLE);
        maleSex = findViewById(R.id.Male);
        femaleSex = findViewById(R.id.Female);
        otherSex = findViewById(R.id.Other);
        femaleSex.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                if (femaleSex.isChecked()) {
                    editTextSex.setVisibility(View.INVISIBLE);
                }
            }
        });
        maleSex.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                if (maleSex.isChecked()) {
                    editTextSex.setVisibility(View.INVISIBLE);
                }
            }
        });
        otherSex.setOnClickListener(new View.OnClickListener() {

            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                if (otherSex.isChecked()) {
                    editTextSex.setVisibility(View.VISIBLE);
                }
            }
        });
        editTextBodyTemp = findViewById(R.id.editTextBodyTemp);
        editTextDBP = findViewById(R.id.editTextDBP);
        editTextSBP = findViewById(R.id.editTextSBP);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextBMI = findViewById(R.id.showBMI);
        editTextOtherHistory = findViewById(R.id.editTextOtherHistory);
        editTextDiet = findViewById(R.id.editTextDiet);
        editTextChiefComplaint = findViewById(R.id.editTextChiefComplaint);
        editTextHistoryOfPresentIllness = findViewById(R.id.editTextHistoryOfPresentIllness);
        editTextAssessment = findViewById(R.id.editTextAssessment);
        editTextPhysicalExamination = findViewById(R.id.editTextPhysicalExamination);
        editTextTreatmentPlan = findViewById(R.id.editTextTreatmentPlan);
        checkBoxDischarged = findViewById(R.id.checkBoxDischarged);
        editTextPrescription = findViewById(R.id.editTextPrescription);
        editTextOtherHistory.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextOtherHistory.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextDiet.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextDiet.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextChiefComplaint.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextChiefComplaint.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextHistoryOfPresentIllness.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextHistoryOfPresentIllness.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextAssessment.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextPhysicalExamination.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextAssessment.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextPhysicalExamination.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextClinicianSignature = findViewById(R.id.editTextClinicianSignature);
        setRequiredFields();
        editTextHeight.addTextChangedListener(heightAndWeightWatcher);
        editTextWeight.addTextChangedListener(heightAndWeightWatcher);
    }

    /**
     * Adds the TextWatcher objects to the views specified as required.
     */
    private void setRequiredFields() {
        editTextFirstName.addTextChangedListener(requiredWatcher);
        editTextClinicianSignature.addTextChangedListener(requiredWatcher);
    }

    /**
     * Sets the location-specific examination views to the identities specified in the XML layout.
     */
    abstract void setLocationViews();

    /**
     * Sets the image from the thumbnail associated with the examination record received from
     * database communications.
     */
    void setImage() {
        examImage = (byte[]) fullResults.get(0).get("Bitmap");
        if (examImage != null) {
            bitmap = BitmapFactory.decodeByteArray(examImage, 0, examImage.length);
            patientImage.setImageBitmap(bitmap);
        }
    }

    /**
     * Receives the values from the common examination views in preparation for an examination completion action.
     *
     * @throws NumberFormatException when numbers entered in the views are not within an acceptable format for parsing.
     */
    private void getExamPage() throws NumberFormatException {
        examFirstName = editTextFirstName.getText().toString();
        examLastName = editTextLastName.getText().toString();
        int dayDOB = examDOBDatePicker.getDayOfMonth();
        int monthDOB = 1 + examDOBDatePicker.getMonth();
        int yearDOB = examDOBDatePicker.getYear();
        int dayVisit = examDateOfVisitPicker.getDayOfMonth();
        int monthVisit = 1 + examDateOfVisitPicker.getMonth();
        int yearVisit = examDateOfVisitPicker.getYear();
        examDOB = LocalDate.of(yearDOB, monthDOB, dayDOB);
        examDateOfVisit = LocalDate.of(yearVisit, monthVisit, dayVisit);
        examTriage = editTextTriage.getText().toString();
        String examChartString = editTextChartNumber.getText().toString();
        if (!examChartString.isEmpty()) {
            examChartNumber = Integer.parseInt(examChartString);
        }
        examSex = getSex();
        String examBodyTempString = editTextBodyTemp.getText().toString();
        if (!examBodyTempString.isEmpty()) {
            examBodyTemperature = Float.parseFloat(examBodyTempString);
        }
        String examDiastolicString = editTextDBP.getText().toString();

        if (!examDiastolicString.isEmpty()) {
            examDiastolicBP = Integer.parseInt(examDiastolicString);
        }
        String examSystolicString = editTextDBP.getText().toString();
        if (!examSystolicString.isEmpty()) {
            examSystolicBP = Integer.parseInt(examSystolicString);
        }
        String examHeightString = editTextHeight.getText().toString();
        if (!examHeightString.isEmpty()) {
            examHeight = Integer.parseInt(examHeightString);
        }
        String examWeightString = editTextWeight.getText().toString();
        if (!examWeightString.isEmpty()) {
            examWeight = Integer.parseInt(examWeightString);
        }

        String examBMIString = editTextBMI.getText().toString();

        if (!(examBMIString.isEmpty() || examBMIString.equals(getString(R.string.enter_bmi)))) {
            try {
                examBMI = Float.parseFloat(examBMIString);
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
                examBMI = 0f;
            }
        } else {
            examBMI = 0f;
        }
        examOtherHistory = editTextOtherHistory.getText().toString();
        examDiet = editTextDiet.getText().toString();
        examChiefComplaint = editTextChiefComplaint.getText().toString();
        examHistPresentIll = editTextHistoryOfPresentIllness.getText().toString();
        examAssessment = editTextAssessment.getText().toString();
        examPhysicalExam = editTextPhysicalExamination.getText().toString();
        examPrescription = editTextPrescription.getText().toString();
        examDischarged = checkBoxDischarged.isChecked();
        examSignature = editTextClinicianSignature.getText().toString();
        if (examSignature.contains(" ")) {
            try {
                int space = examSignature.indexOf(' ');
                examClinicianFirst = examSignature.substring(0, space);
                examClinicianLast = examSignature.substring(space + 1);
            } catch (Exception e) {
                examClinicianFirst = null;
                examClinicianLast = null;
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks the patient sex RadioButton group to determine the sex selected by the user.
     *
     * @return the selected sex of the patient.
     */
    private String getSex() {
        if (maleSex.isChecked()) {
            return "Male";
        }
        if (femaleSex.isChecked()) {
            return "Female";
        } else {
            return editTextSex.getText().toString();
        }
    }

    /**
     * Receives the values from the common examination views and location-specific views in preparation for an examination completion action.
     * Checks whether the required fields were filled in by the user.
     *
     * @return the status of whether the required fields were filled by the user.
     */
    boolean getPage() {
        try {
            getExamPage();
            getLocationPage();
            boolean success = checkRequiredViews();
            if (success) {
                setQueryId();
                makeQuery2();
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            toastUiThread(getString(R.string.appropriate_numbers));
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets the unique query identifier for database communications.
     */
    abstract void setQueryId();

    /**
     * Makes the query object for database communications using the exam record information received from a previous activity.
     */
    abstract void makeQuery();

    /**
     * Makes the query object for database communications using the values received from the activity views.
     */
    abstract void makeQuery2();

    /**
     * Checks whether the required fields were filled in by the user.
     *
     * @return the status of whether the required fields were filled in by the user.
     */
    private boolean checkRequiredViews() {
        String checkFirstName = editTextFirstName.getText().toString();
        String checkSignature = editTextClinicianSignature.getText().toString();
        return !(checkFirstName.isEmpty() || checkSignature.isEmpty());
    }

    /**
     * Receives the values from the location-specific views in preparation for an examination completion action.
     */
    protected abstract void getLocationPage();

    /**
     * Calculates the age of the patient to be displayed in the Age view.
     *
     * @param dateOfBirth The patient's date of birth received from the date of birth DatePicker.
     * @return the String equivalent of the patient's calculated age.
     */
    String getAge(LocalDate dateOfBirth) {
        try {
            int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
            return NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(age);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Opens the welcome activity and clears existing activity history when the exam completion action has been completed.
     */
    void goToWelcome() {
        Intent intent = new Intent(ApplicationCustom.getAppContext(), PageWelcome.class);
        Toast.makeText(getApplicationContext(), getString(R.string.return_welcome),
                Toast.LENGTH_SHORT).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    /**
     * Checks whether basic patient information has ben changed.
     *
     * @return The status of whether basic patient information has ben changed.
     */
    private boolean changedBasic() {
        String changedSex = getSex();

        return !(editTextFirstName.getText().toString().equals(foundFirstName) &&
                editTextLastName.getText().toString().equals(foundLastName) &&
                changedSex.equals(foundSex));
    }

    /**
     * Creates a new SSL socket client handler instance for database communication.
     * Applies the query maker object containing examination record information received from activity views to the socket client handler for database communications.
     * Waits for the socket operation thread to finish and checks its result.
     * Toasts a success message on a successful socket operation or an error message othewise.
     */
    void executeQuery() {
        connServer2 = new ClassDatabaseHandler(this, queryMaker2, queryIdentifier);
        try {
            if (connServer2.cThread != null) {
                if (connServer2.cThread.isAlive()) {
                    connServer2.cThread.join();
                    Log.d("tag 60", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(connServer2.affected));
                    // We would expect this to be 1, but records update when affected returns as 0.
                    if (connServer2.affected == 0 || connServer2.affected == 1) {
                        completedToast();
                    } else {
                        Log.d("tag 111", "tag 111");
                        Toast.makeText(ApplicationCustom.getAppContext(),
                                getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("tag 111", "tag 112");
                    Toast.makeText(ApplicationCustom.getAppContext(),
                            getString(R.string.databaseError), Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d("tag 111", "tag 113");
                Toast.makeText(ApplicationCustom.getAppContext(),
                        getString(R.string.databaseError), Toast.LENGTH_LONG).show();
            }

        } catch (
                NullPointerException e) {
            Log.d("tag 111", "tag 115");
            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.databaseError), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.databaseError), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Sets the patient sex RadioButton group to the setting associated with the patient sex received from a previous activity.
     */
    private void setSex() {
        if (foundSex != null) {
            switch (foundSex) {
                case "Male":
                    maleSex.setChecked(true);
                    break;
                case "Female":
                    femaleSex.setChecked(true);
                    break;
                default:
                    editTextSex.setText(foundSex);
                    editTextSex.setVisibility(View.VISIBLE);
                    otherSex.setChecked(true);
                    break;
            }
        }
    }

    /**
     * calculates the BMI from the height and weight values.
     *
     * @param height the input patient height.
     * @param weight the input patient weight.
     * @return the String equivalent to the calculated BMI value.
     */
    private String getBMI(int height, int weight) {
        return NumberFormat.getNumberInstance().format((float) weight * 10000f / ((float) height * (float) height));
    }

    /**
     * Displays the examination completion action Toast message
     * depending on the activity function specified by user action in a previous activity.
     */
    abstract void completedToast();

    /**
     * Creates a completion button to be added to the activity layout depending on the
     * activity function specified by user action in a previous activity.
     * @return
     */
    Button makeButton() {
        Button button = new Button(this);
        TableLayout.LayoutParams params2 = new TableLayout.LayoutParams
                (buttonWidthSetting,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params2);
        button.setTextSize((float) 35);
        button.setTypeface(null, android.graphics.Typeface.BOLD);
        button.setPadding(0, 30, 0, 30);
        button.setGravity(Gravity.CENTER);
        return button;
    }
}