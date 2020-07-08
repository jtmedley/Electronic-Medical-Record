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
import android.widget.Toast;

/**
 *
 */
public class PageCreateRecordDefault extends BaseCreateRecord {

    /**
     *
     */
    private EditText editTextTreatmentPlan;

    /**
     *
     */
    private EditText editTextLocation;

    /**
     *
     */
    private String examTreatmentPlan;

    /**
     *
     */
    private String examSetLocation;

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
        editTextTreatmentPlan = findViewById(R.id.editTextTreatmentPlan);
        editTextLocation = findViewById(R.id.editTextLocation);
    }

    /**
     *
     */
    @Override
    public void getLocationPage() {
        Log.d("Override success", "true");

        examTreatmentPlan = editTextTreatmentPlan.getText().toString();
        examSetLocation = editTextLocation.getText().toString();
    }

    /**
     *
     */
    @Override
    public void setLocationPage() {
        editTextTreatmentPlan.setText((String) fullResults.get(0).get("Treatment Plan"));
        editTextLocation.setText((String) fullResults.get(0).get("Location"));
    }

    /**
     *
     */
    @Override
    void makeQuery2(){queryMaker2 = new ClassQueryMakers();
        if (actionFlag == ClassConstants.ACTION_FLAG_MAKE_BOTH) {
            Log.d("tag24", "making both records");
            queryMaker2.makeBothRecordsQuery(examFirstName, examLastName, examDOB, examSex,
                    examDischarged, examTriage, examTriage, examDateOfVisit, examChartNumber,
                    examBodyTemperature, examDiastolicBP,
                    examSystolicBP, examHeight, examWeight,
                    examBMI, examOtherHistory,
                    examDiet, examChiefComplaint,
                    examHistPresentIll, examAssessment, examPhysicalExam,examTreatmentPlan,
                    examClinicianFirst, examClinicianLast,
                    examPrescription, examSignature, examSetLocation);
            if (imageFlag) {
                queryMaker2.makeImageRecordQuery(examImage, currentEncoding);
            }
        } else if (actionFlag == ClassConstants.ACTION_FLAG_MAKE_RECORD) {
            Log.d("tag24", "making a new record for an existing patient, " +
                    "and updating basic info if necessary");
            if (updateBasicFlag) {
                queryMaker2.updateBasicQuery(examFirstName, examLastName, examDOB,
                        examSex, examDischarged, examTriage, currentPersonId);
            }
            queryMaker2.makeExamRecordQuery(currentPersonId, examTriage, examDateOfVisit, examChartNumber,
                    examBodyTemperature, examDiastolicBP,
                    examSystolicBP, examHeight, examWeight,
                    examBMI, examOtherHistory,
                    examDiet, examChiefComplaint,
                    examHistPresentIll, examAssessment, examPhysicalExam,examTreatmentPlan,
                    examClinicianFirst, examClinicianLast,
                    examPrescription, examSignature, examSetLocation);
            if (imageFlag) {
                queryMaker2.makeImageRecordQuery(examImage, currentEncoding);
            }
        }
    }


    /**
     *
     */
    @Override
    void setQueryId() {
        if (actionFlag == ClassConstants.ACTION_FLAG_MAKE_RECORD) {
            Log.d("tag99", "make record");
            if (updateBasicFlag) {
                if (imageFlag) {
                    queryIdentifier = 11;
                } else {
                    queryIdentifier = 15;
                }
            } else {
                if (imageFlag) {
                    queryIdentifier = 13;
                } else {
                    queryIdentifier = 17;
                }
            }
        } else if (actionFlag == ClassConstants.ACTION_FLAG_MAKE_BOTH) {
            Log.d("tag99", "make both");
            if (imageFlag) {
                Log.d("Alive6", Boolean.toString(connServer2 != null));
                queryIdentifier = 19;
            } else {
                queryIdentifier = 21;

            }
        } else {
            Log.d("tag99", "error");
            Toast.makeText(ApplicationCustom.getAppContext(),
                    "An error has occurred. Please try again.", Toast.LENGTH_LONG).show();
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
