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

import android.util.Log;
import android.widget.EditText;

/**
 *
 */
public class PageUpdateRecordDefault extends BaseUpdateRecord {

    /**
     *
     */
    private String examSetLocation;

    /**
     *
     */
    private EditText editTextLocation;

    /**
     *
     */
    public void setLocationXML() {
        setContentView(R.layout.activity_default_exam_record);
    }

    /**
     * Set the view in the record to their assigned xml ids.
     * Set a text watcher to display an error message if required field First Name is left blank
     */
    @Override
    void setLocationViews() {
        editTextLocation = findViewById(R.id.editTextLocation);
    }

    /**
     *
     */
    @Override
    public void getLocationPage() {
        Log.d("Override success", "true");
        examSetLocation = editTextLocation.getText().toString();
    }

    /**
     *
     */
    @Override
    public void setLocationPage() {
        editTextLocation.setText((String) fullResults.get(0).get("Location"));
    }

    /**
     *
     */
    void makeQuery2() {
        queryMaker2 = new ClassQueryMakers();
        if (actionFlag == ClassConstants.ACTION_FLAG_UPDATE_BOTH) {
            Log.d("tag24", "Updating an existing patient and their record," +
                    "This does not change their current triage");
            queryMaker2.updateBasicQuery(examFirstName, examLastName, examDOB,
                    examSex, examDischarged, currentTriage, currentPersonId);
            queryMaker2.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber,
                    examBodyTemperature, examDiastolicBP,
                    examSystolicBP, examHeight, examWeight,
                    examBMI, examOtherHistory,
                    examDiet, examChiefComplaint,
                    examHistPresentIll, examAssessment,
                    examPhysicalExam, examTreatmentPlan, examClinicianFirst,
                    examClinicianLast, examPrescription,
                    examSignature, examSetLocation,
                    currentRecordId);
        } else if (actionFlag == ClassConstants.ACTION_FLAG_UPDATE_RECORD) {
            Log.d("tag24", "updating an existing record without updating basic patient info");
            queryMaker2.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber,
                    examBodyTemperature, examDiastolicBP,
                    examSystolicBP, examHeight, examWeight,
                    examBMI, examOtherHistory,
                    examDiet, examChiefComplaint,
                    examHistPresentIll, examAssessment,
                    examPhysicalExam, examTreatmentPlan, examClinicianFirst,
                    examClinicianLast, examPrescription,
                    examSignature, examSetLocation,
                    currentPersonId);
        }
    }

    /**
     *
     */
    void setQueryId() {
        if (updateBasicFlag) {
            queryIdentifier = 7;
        } else {
            queryIdentifier = 9;
        }
    }

    /**
     *
     */
    @Override
    void makeQuery() {
        queryMaker.selectExamRecordQuery(currentRecordId);
    }
}

