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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 *
 */
public class PageCreateRecordHaiti extends BaseCreateRecord {
    /**
     *
     */
    private EditText editTextVillage;

    /**
     *
     */
    private EditText editTextHemoglobin;

    /**
     *
     */
    private CheckBox checkBoxHTNDx;

    /**
     *
     */
    private CheckBox checkBoxDmDx;

    /**
     *
     */
    private CheckBox checkBoxStroke;

    /**
     *
     */
    private CheckBox checkBoxFamilyHxHTN;

    /**
     *
     */
    private CheckBox checkBoxFamilyHxDm;

    /**
     *
     */
    private CheckBox checkBoxFamilyHxStroke;

    /**
     *
     */
    private CheckBox checkBoxPolyphagia;

    /**
     *
     */
    private CheckBox checkBoxPolydipsia;

    /**
     *
     */
    private CheckBox checkBoxPolyuria;

    /**
     *
     */
    private CheckBox checkBoxSmoker;

    /**
     *
     */
    private CheckBox checkBoxETOH;

    /**
     *
     */
    private EditText editTextAmountOfMaggi;

    /**
     *
     */
    private RadioButton rbEducation;

    /**
     *
     */
    private RadioButton rbBoth;

    /**
     *
     */
    private String examVillage;

    /**
     *
     */
    private boolean examHTNDx;

    /**
     *
     */
    private Integer examHemoglobin;

    /**
     *
     */
    private boolean examDmDx;

    /**
     *
     */
    private boolean examStroke;

    /**
     *
     */
    private boolean examFamilyHxHTN;

    /**
     *
     */
    private boolean examFamilyHxDm;

    /**
     *
     */
    private boolean examFamilyHxStroke;

    /**
     *
     */
    private boolean examPolyphagia;

    /**
     *
     */
    private boolean examPolydipsia;

    /**
     *
     */
    private boolean examPolyuria;

    /**
     *
     */
    private boolean examSmoker;

    /**
     *
     */
    private boolean examETOH;

    /**
     *
     */
    private Integer examAmountOfMaggi;

    /**
     *
     */
    @Override
    public void setLocationXML() {
        setContentView(R.layout.activity_haiti_exam_record);
    }

    /**
     * Set the view in the record to their assigned xml ids.
     * Set a text watcher to display an error message if required field First Name is left blank
     */
    @Override
    void setLocationViews() {
        editTextVillage = findViewById(R.id.editTextVillage);
        editTextHemoglobin = findViewById(R.id.editTextHemoglobin);
        checkBoxHTNDx = findViewById(R.id.checkBoxHTNDx);
        checkBoxDmDx = findViewById(R.id.checkBoxDmDx);
        checkBoxStroke = findViewById(R.id.checkBoxStroke);
        checkBoxFamilyHxHTN = findViewById(R.id.checkBoxFamilyHxHTN);
        checkBoxFamilyHxDm = findViewById(R.id.checkBoxFamilyHxDm);
        checkBoxFamilyHxStroke = findViewById(R.id.checkBoxFamilyHxStroke);
        checkBoxPolyphagia = findViewById(R.id.checkBoxPolyphagia);
        checkBoxPolydipsia = findViewById(R.id.checkBoxPolydipsia);
        checkBoxPolyuria = findViewById(R.id.checkBoxPolyuria);
        checkBoxSmoker = findViewById(R.id.checkBoxSmoker);
        checkBoxETOH = findViewById(R.id.checkBoxETOH);
        editTextAmountOfMaggi = findViewById(R.id.editTextAmountOfMaggi);
        rbEducation = findViewById(R.id.rbEducation);
        rbBoth = findViewById(R.id.rbBoth);
        checkBoxDischarged = findViewById(R.id.checkBoxDischarged);
        editTextClinicianSignature = findViewById(R.id.editTextClinicianSignature);
        editTextPrescription = findViewById(R.id.editTextPrescription);
    }

    /**
     *
     */
    @Override
    public void getLocationPage() {
        Log.d("Override success", "true");
        examVillage = editTextVillage.getText().toString();
        String examHemoglobinString = editTextHemoglobin.getText().toString();

        if (!examHemoglobinString.isEmpty()) {
            examHemoglobin = Integer.parseInt(examHemoglobinString);
        }
        getHistory();
        String examMaggiString = editTextAmountOfMaggi.getText().toString();
        if (!examMaggiString.isEmpty()) {
            examAmountOfMaggi = Integer.parseInt(examMaggiString);
        }
        examTreatmentPlan = getTreatmentPlan();
    }

    /**
     *
     */
    @Override
    public void setLocationPage() {
        editTextVillage.setText((String) fullResults.get(0).get("Village"));
        Integer hemoglobinInt = (Integer) fullResults.get(0).get("Hemoglobin");
        if (hemoglobinInt != null) {
            editTextHemoglobin.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(hemoglobinInt));
        }
        setHistory();
        Integer maggiInt = (Integer) fullResults.get(0).get("Amount of Maggi");
        if (maggiInt != null) {
            editTextAmountOfMaggi.setText(NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(maggiInt));
        }
        String treatmentPlan = (String) fullResults.get(0).get("Treatment Plan");
        if (treatmentPlan != null) {
            if (treatmentPlan.equals("Both")) {
                rbBoth.toggle();
            } else if (treatmentPlan.equals("Education")) {
                rbEducation.toggle();
            }
        }
    }

    /**
     *
     */
    private void getHistory() {
        examHTNDx = checkBoxHTNDx.isChecked();
        examDmDx = checkBoxDmDx.isChecked();
        examStroke = checkBoxStroke.isChecked();
        examFamilyHxHTN = checkBoxFamilyHxHTN.isChecked();
        examFamilyHxDm = checkBoxFamilyHxDm.isChecked();
        examFamilyHxStroke = checkBoxFamilyHxStroke.isChecked();
        examPolyphagia = checkBoxPolyphagia.isChecked();
        examPolydipsia = checkBoxPolydipsia.isChecked();
        examPolyuria = checkBoxPolyuria.isChecked();
        examSmoker = checkBoxSmoker.isChecked();
        examETOH = checkBoxETOH.isChecked();
    }

    /**
     *
     */
    private void setHistory() {
        // Check if the object is null
        // Cast to boolean
        // Set checkbox if true
        // Set unchecked otherwise
        if (fullResults.get(0).get("History HTNDx") != null) {
            checkBoxHTNDx.setChecked((boolean) fullResults.get(0).get("History HTNDx"));
        } else {
            checkBoxHTNDx.setChecked(false);
        }
        if (fullResults.get(0).get("History DmDx") != null) {
            checkBoxDmDx.setChecked((boolean) fullResults.get(0).get("History DmDx"));
        } else {
            checkBoxDmDx.setChecked(false);
        }
        if (fullResults.get(0).get("History Stroke") != null) {
            checkBoxStroke.setChecked((boolean) fullResults.get(0).get("History Stroke"));
        } else {
            checkBoxStroke.setChecked(false);
        }
        if (fullResults.get(0).get("History FamilyHxHTN") != null) {
            checkBoxFamilyHxHTN.setChecked((boolean) fullResults.get(0).get("History FamilyHxHTN"));
        } else {
            checkBoxFamilyHxHTN.setChecked(false);
        }
        if (fullResults.get(0).get("History FamilyHxDm") != null) {
            checkBoxFamilyHxDm.setChecked((boolean) fullResults.get(0).get("History FamilyHxDm"));
        }
        if (fullResults.get(0).get("History FamilyHxStroke") != null) {
            checkBoxFamilyHxStroke.setChecked((boolean) fullResults.get(0).get("History FamilyHxStroke"));
        } else {
            checkBoxFamilyHxStroke.setChecked(false);
        }
        if (fullResults.get(0).get("History Polyphagia") != null) {
            checkBoxPolyphagia.setChecked((boolean) fullResults.get(0).get("History Polyphagia"));
        } else {
            checkBoxPolyphagia.setChecked(false);
        }
        if (fullResults.get(0).get("History Polydipsia") != null) {
            checkBoxPolydipsia.setChecked((boolean) fullResults.get(0).get("History Polydipsia"));
        } else {
            checkBoxPolydipsia.setChecked(false);
        }
        if (fullResults.get(0).get("History Polyuria") != null) {
            checkBoxPolyuria.setChecked((boolean) fullResults.get(0).get("History Polyuria"));
        } else {
            checkBoxPolyuria.setChecked(false);
        }
        if (fullResults.get(0).get("History Smoker") != null) {
            checkBoxSmoker.setChecked((boolean) fullResults.get(0).get("History Smoker"));
        } else {
            checkBoxSmoker.setChecked(false);
        }
        if (fullResults.get(0).get("History ETOH") != null) {
            checkBoxETOH.setChecked((boolean) fullResults.get(0).get("History ETOH"));
        } else {
            checkBoxETOH.setChecked(false);
        }
    }

    /**
     * @return
     */
    private String getTreatmentPlan() {
        if (rbBoth.isChecked()) {
            return "Both";
        }
        if (rbEducation.isChecked()) {
            return "Education";
        } else {
            return "Unknown";
        }
    }

    /**
     * Set the values in the exam record to match the content of the selected exam record
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
                    examPrescription, examSignature, examLocationRecord);
            queryMaker2.makeHaitiRecordQuery(examVillage,
                    examHemoglobin,
                    examDmDx,
                    examETOH,
                    examFamilyHxDm,
                    examFamilyHxHTN,
                    examFamilyHxStroke,
                    examHTNDx,
                    examPolydipsia,
                    examPolyphagia,
                    examPolyuria,
                    examSmoker,
                    examStroke,
                    examAmountOfMaggi);
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
                    examPrescription, examSignature, examLocationRecord);
            queryMaker2.makeHaitiRecordQuery(examVillage,
                    examHemoglobin,
                    examDmDx,
                    examETOH,
                    examFamilyHxDm,
                    examFamilyHxHTN,
                    examFamilyHxStroke,
                    examHTNDx,
                    examPolydipsia,
                    examPolyphagia,
                    examPolyuria,
                    examSmoker,
                    examStroke,
                    examAmountOfMaggi);
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
                    queryIdentifier = 10;
                } else {
                    queryIdentifier = 14;
                }
            } else {
                if (imageFlag) {
                    queryIdentifier = 12;
                } else {
                    queryIdentifier = 16;
                }
            }
        } else if (actionFlag == ClassConstants.ACTION_FLAG_MAKE_BOTH) {
            Log.d("tag99", "make both");
            if (imageFlag) {
                queryIdentifier = 18;
            } else {
                queryIdentifier = 20;
                Log.d("Tag99", "here");
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
        queryMaker.selectHaitiExamRecordQuery(currentRecordId);
    }
}