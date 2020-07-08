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


import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;

import java.sql.Types;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * Implements methods to create a query object to be used for database communications, containing
 * an array of parameters for the query, and an array of the parameter types to be applied to
 * a prepared SQL statement.
 * <p>
 * Adapted from https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 * and from http://www.java2s.com/Code/Java/Database-SQL-JDBC/InsertpicturetoMySQL.htm
 * and from https://stackoverflow.com/questions/10618325/how-to-create-a-blob-from-bitmap-in-android-activity/10618678
 */
class ClassQueryMakers {

    /**
     *
     */
    final ArrayList<Object> parameterArray;

    /**
     *
     */
    final ArrayList<Integer> paramTypeArray;

    /**
     *
     */
    private final String locationRecord;

    /**
     * Constructor
     * Assigns parameter and parameter type fields to given inputs.
     * Receives location string from current application settings.
     */
    ClassQueryMakers() {
        parameterArray = new ArrayList<>();
        paramTypeArray = new ArrayList<>();
        locationRecord = "Haiti";
    }

    /**
     * Select basic patient information that matches input data
     *
     * @param newPatientFirstName The current patient's first name input by the
     *                            user.
     * @param newPatientLastName  The current patient's last name input by the user.
     * @param newPatientSex       The current patient's sex input by the user.
     * @param newPatientDOB       The current patient's date of birth input by the
     *                            user.
     */
    void selectBasicQuery(String newPatientFirstName, String newPatientLastName, LocalDate newPatientDOB,
                          String newPatientSex) {
        java.sql.Date newPatientDOBSql = null;
        String newPatientDOBString = null;
        if (newPatientFirstName == null) {
            newPatientFirstName = "%";
        } else {
            if (newPatientFirstName.isEmpty()) {
                newPatientFirstName = "%";
            } else {
                newPatientFirstName = "%" + newPatientFirstName + "%";
            }
        }
        if (newPatientLastName == null) {
            newPatientLastName = "%";
        } else {
            if (newPatientLastName.isEmpty()) {
                newPatientLastName = "%";
            } else {
                newPatientLastName = "%" + newPatientLastName + "%";
            }
        }
        if (newPatientDOB == null) {
            newPatientDOBString = "%";
        } else {
            newPatientDOBSql = DateTimeUtils.toSqlDate(newPatientDOB);
        }
        if (newPatientSex == null) {
            newPatientSex = "%";
        } else {
            if (newPatientSex.isEmpty()) {
                newPatientSex = "%";
            }
        }
        parameterArray.add(locationRecord);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(newPatientFirstName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(newPatientLastName);
        paramTypeArray.add(Types.VARCHAR);
        if (newPatientDOB != null) {
            parameterArray.add(newPatientDOBSql);
            paramTypeArray.add(Types.VARCHAR);
        } else {
            parameterArray.add(newPatientDOBString);
            paramTypeArray.add(Types.VARCHAR);
        }
        parameterArray.add(newPatientSex);
        paramTypeArray.add(Types.VARCHAR);

    }

    /**
     * Select basic or static patient information that matches input data or the patient ids
     * received from image recognition
     *
     * @param newPatientFirstName The current patient's first name input by the
     *                            user.
     * @param newPatientLastName  The current patient's last name input by the user.
     * @param newPatientSex       The current patient's sex input by the user.
     * @param newPatientDOB       The current patient's date of birth input by the
     *                            user.
     * @param personIds           A list of possible matching patient ids from facial recognition
     */
    void selectWithFaceQuery(String newPatientFirstName, String newPatientLastName, LocalDate newPatientDOB,
                             String newPatientSex, ArrayList<Integer> personIds) {
        java.sql.Date newPatientDOBSql = null;
        String newPatientDOBString = null;
        if (newPatientFirstName == null) {
            newPatientFirstName = "%";
        } else {
            if (newPatientFirstName.isEmpty()) {
                newPatientFirstName = "%";
            } else {
                newPatientFirstName = "%" + newPatientFirstName + "%";
            }
        }
        if (newPatientLastName == null) {
            newPatientLastName = "%";
        } else {
            if (newPatientLastName.isEmpty()) {
                newPatientLastName = "%";
            } else {
                newPatientLastName = "%" + newPatientLastName + "%";
            }
        }
        if (newPatientDOB == null) {
            newPatientDOBString = "%";
        } else {
            newPatientDOBSql = DateTimeUtils.toSqlDate(newPatientDOB);
        }
        if (newPatientSex == null) {
            newPatientSex = "%";
        } else {
            if (newPatientSex.isEmpty()) {
                newPatientSex = "%";
            }
        }
        parameterArray.add(locationRecord);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(newPatientFirstName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(newPatientLastName);
        paramTypeArray.add(Types.VARCHAR);

        if (newPatientDOB != null) {
            parameterArray.add(newPatientDOBSql);
            paramTypeArray.add(Types.VARCHAR);
        } else {
            parameterArray.add(newPatientDOBString);
            paramTypeArray.add(Types.VARCHAR);
        }
        parameterArray.add(newPatientSex);
        paramTypeArray.add(Types.VARCHAR);

        if (personIds != null) {
            if (!personIds.isEmpty()) {
                for (Object next : personIds) {
                    parameterArray.add(next);
                    paramTypeArray.add(Types.INTEGER);
                }
            } else {
                parameterArray.add(0);
                paramTypeArray.add(Types.INTEGER);
            }
        } else {
            parameterArray.add(0);
            paramTypeArray.add(Types.INTEGER);
        }

    }

    /**
     * @param personIds
     */
    void selectOnlyFaceQuery(ArrayList<Integer> personIds) {
        parameterArray.add(locationRecord);
        paramTypeArray.add(Types.VARCHAR);
        if (personIds != null) {
            if (!personIds.isEmpty()) {
                for (Object next : personIds) {
                    parameterArray.add(next);
                    paramTypeArray.add(Types.INTEGER);
                }
            } else {
                parameterArray.add(0);
                paramTypeArray.add(Types.INTEGER);
            }
        } else {
            parameterArray.add(0);
            paramTypeArray.add(Types.INTEGER);
        }
    }

    /**
     * @param currentPersonId
     */
    void selectPatientRecordsQuery(int currentPersonId) {

        parameterArray.add(locationRecord);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(currentPersonId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * Select the general exam record data and the Haiti exam record data for the selected record
     * ID
     *
     * @param examRecordId the record ID for the current exam record
     */
    void selectHaitiExamRecordQuery(int examRecordId) {
        parameterArray.add(examRecordId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * Select the general exam record data and the default location exam record data for the
     * selected record ID
     *
     * @param examRecordId the record ID for the current exam record
     */
    void selectExamRecordQuery(int examRecordId) {
        parameterArray.add(examRecordId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * @param examFirstName
     * @param examLastName
     * @param examDOB
     * @param examSex
     * @param examDischarged
     * @param currentTriage
     * @param examPersonId
     */
    void updateBasicQuery(
            String examFirstName, String examLastName, LocalDate examDOB, String examSex,
            boolean examDischarged, String currentTriage, int examPersonId) {


        parameterArray.add(examFirstName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examLastName);
        paramTypeArray.add(Types.VARCHAR);
        java.sql.Date examDOBSql = DateTimeUtils.toSqlDate(examDOB);
        parameterArray.add(examDOBSql);
        paramTypeArray.add(Types.DATE);
        parameterArray.add(examSex);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examDischarged);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(currentTriage);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPersonId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * @param examTriage
     * @param examDateOfVisit
     * @param examChartNumber
     * @param examBodyTemperature
     * @param examDiastolicBP
     * @param examSystolicBP
     * @param examHeight
     * @param examWeight
     * @param examBMI
     * @param examOtherHistory
     * @param examDiet
     * @param examChiefComplaint
     * @param examHistPresentIll
     * @param examAssessment
     * @param examPhysicalExam
     * @param examTreatmentPlan
     * @param examClinicianFirst
     * @param examClinicianLast
     * @param examPrescription
     * @param examSignature
     * @param examRecordId
     */
    void updateExamRecordQuery(
            String examTriage, LocalDate examDateOfVisit, Integer examChartNumber,
            Float examBodyTemperature, Integer examDiastolicBP, Integer examSystolicBP, Integer examHeight, Integer examWeight,
            Float examBMI, String examOtherHistory, String examDiet, String examChiefComplaint,
            String examHistPresentIll, String examAssessment, String examPhysicalExam, String examTreatmentPlan, String examClinicianFirst,
            String examClinicianLast, String examPrescription, String examSignature, String examLocationRecord,
            int examRecordId) {

        parameterArray.add(examTriage);
        paramTypeArray.add(Types.VARCHAR);
        java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
        parameterArray.add(examDateOfVisitSql);
        paramTypeArray.add(Types.DATE);
        parameterArray.add(examChartNumber);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBodyTemperature);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examDiastolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examSystolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examHeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examWeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBMI);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examOtherHistory);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examDiet);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examChiefComplaint);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examHistPresentIll);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examAssessment);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPhysicalExam);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examTreatmentPlan);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianFirst);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianLast);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPrescription);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examSignature);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examLocationRecord);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examRecordId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * @param examVillage
     * @param examHemoglobin
     * @param examDmDx
     * @param examETOH
     * @param examHTNDx
     * @param examFamilyHxDm
     * @param examFamilyHxHTN
     * @param examFamilyHxStroke
     * @param examPolydipsia
     * @param examPolyphagia
     * @param examPolyuria
     * @param examSmoker
     * @param examStroke
     * @param examAmountOfMaggi
     * @param examRecordId
     */
    void updateHaitiExamRecordQuery(String examVillage, Integer examHemoglobin, Boolean examDmDx, Boolean examETOH,
                                    Boolean examFamilyHxDm, Boolean examFamilyHxHTN, Boolean examFamilyHxStroke, Boolean examHTNDx,
                                    Boolean examPolydipsia, Boolean examPolyphagia, Boolean examPolyuria, Boolean examSmoker,
                                    Boolean examStroke, Integer examAmountOfMaggi, int examRecordId) {


        parameterArray.add(examVillage);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examHemoglobin);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examDmDx);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examETOH);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxDm);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxHTN);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxStroke);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examHTNDx);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolydipsia);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolyphagia);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolyuria);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examSmoker);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examStroke);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examAmountOfMaggi);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examRecordId);
        paramTypeArray.add(Types.INTEGER);
    }

    /**
     * Written so that makeHaitiRecord and makeDefaultRecord can be called to add to
     * the queryString, parameterArray, and paramTypeArray variables.
     *
     * @param examPersonID
     * @param examTriage
     * @param examDateOfVisit
     * @param examChartNumber
     * @param examBodyTemperature
     * @param examDiastolicBP
     * @param examSystolicBP
     * @param examHeight
     * @param examWeight
     * @param examBMI
     * @param examOtherHistory
     * @param examDiet
     * @param examChiefComplaint
     * @param examHistPresentIll
     * @param examAssessment
     * @param examPhysicalExam
     * @param examTreatmentPlan
     * @param examClinicianFirst
     * @param examClinicianLast
     * @param examPrescription
     * @param examSignature
     * @param examLocationRecord
     */
    void makeExamRecordQuery(Integer examPersonID, String examTriage, LocalDate examDateOfVisit, Integer examChartNumber,
                             Float examBodyTemperature, Integer examDiastolicBP, Integer examSystolicBP, Integer examHeight, Integer examWeight,
                             Float examBMI, String examOtherHistory, String examDiet, String examChiefComplaint,
                             String examHistPresentIll, String examAssessment, String examPhysicalExam, String examTreatmentPlan, String examClinicianFirst,
                             String examClinicianLast, String examPrescription, String examSignature, String examLocationRecord) {

        parameterArray.add(examPersonID);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examPersonID);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examTriage);
        paramTypeArray.add(Types.VARCHAR);
        java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
        parameterArray.add(examDateOfVisitSql);
        paramTypeArray.add(Types.DATE);
        parameterArray.add(examChartNumber);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBodyTemperature);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examDiastolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examSystolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examHeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examWeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBMI);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examOtherHistory);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examDiet);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examChiefComplaint);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examHistPresentIll);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examAssessment);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPhysicalExam);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examTreatmentPlan);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianFirst);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianLast);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPrescription);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examSignature);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examLocationRecord);
        paramTypeArray.add(Types.VARCHAR);
    }

    /**
     * Written to be called after makeExamRecordQuery or makeBothRecords to add
     * specific record location query information to the queryString,
     * parameterArray, and paramTypeArray variables.
     *
     * @param examVillage
     * @param examHemoglobin
     * @param examHTNDx
     * @param examDmDx
     * @param examStroke
     * @param examFamilyHxHTN
     * @param examFamilyHxDm
     * @param examFamilyHxStroke
     * @param examPolyphagia
     * @param examPolydipsia
     * @param examPolyuria
     * @param examSmoker
     * @param examETOH
     * @param examAmountOfMaggi
     */
    void makeHaitiRecordQuery(String examVillage, Integer examHemoglobin, Boolean examDmDx, Boolean examETOH,
                              Boolean examFamilyHxDm, Boolean examFamilyHxHTN, Boolean examFamilyHxStroke, Boolean examHTNDx,
                              Boolean examPolydipsia, Boolean examPolyphagia, Boolean examPolyuria, Boolean examSmoker,
                              Boolean examStroke, Integer examAmountOfMaggi) {

        parameterArray.add(examVillage);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examHemoglobin);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examDmDx);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examETOH);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxDm);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxHTN);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examFamilyHxStroke);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examHTNDx);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolydipsia);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolyphagia);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examPolyuria);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examSmoker);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examStroke);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examAmountOfMaggi);
        paramTypeArray.add(Types.INTEGER);


    }

    /**
     * @param examFirstName
     * @param examLastName
     * @param examDOB
     * @param examSex
     * @param examTriage
     * @param examDateOfVisit
     * @param examChartNumber
     * @param examBodyTemperature
     * @param examDiastolicBP
     * @param examSystolicBP
     * @param examHeight
     * @param examWeight
     * @param examBMI
     * @param examOtherHistory
     * @param examDiet
     * @param examChiefComplaint
     * @param examHistPresentIll
     * @param examAssessment
     * @param examPhysicalExam
     * @param examTreatmentPlan
     * @param examClinicianFirst
     * @param examClinicianLast
     * @param examPrescription
     * @param examSignature
     * @param examLocationRecord
     */
    void makeBothRecordsQuery(String examFirstName, String examLastName, LocalDate examDOB, String examSex, Boolean examDischarged,
                              String examCurrentTriage, String examTriage, LocalDate examDateOfVisit, Integer examChartNumber, Float examBodyTemperature,
                              Integer examDiastolicBP, Integer examSystolicBP, Integer examHeight, Integer examWeight, Float examBMI,
                              String examOtherHistory, String examDiet, String examChiefComplaint, String examHistPresentIll,
                              String examAssessment, String examPhysicalExam, String examTreatmentPlan, String examClinicianFirst, String examClinicianLast,
                              String examPrescription, String examSignature, String examLocationRecord) {


        parameterArray.add(examFirstName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examLastName);
        paramTypeArray.add(Types.VARCHAR);
        java.sql.Date examDOBSql = DateTimeUtils.toSqlDate(examDOB);
        parameterArray.add(examDOBSql);
        paramTypeArray.add(Types.DATE);
        parameterArray.add(examSex);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examDischarged);
        paramTypeArray.add(Types.BIT);
        parameterArray.add(examCurrentTriage);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examTriage);
        paramTypeArray.add(Types.VARCHAR);
        java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
        parameterArray.add(examDateOfVisitSql);
        paramTypeArray.add(Types.DATE);
        parameterArray.add(examChartNumber);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBodyTemperature);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examDiastolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examSystolicBP);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examHeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examWeight);
        paramTypeArray.add(Types.INTEGER);
        parameterArray.add(examBMI);
        paramTypeArray.add(Types.DECIMAL);
        parameterArray.add(examOtherHistory);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examDiet);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examChiefComplaint);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examHistPresentIll);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examAssessment);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPhysicalExam);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examTreatmentPlan);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianFirst);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examClinicianLast);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examPrescription);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examSignature);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(examLocationRecord);
        paramTypeArray.add(Types.VARCHAR);

    }

    /**
     * <p>
     * Inserts a thumbnail and an encoding of an image received from facial recognition into a
     * newly created record
     *
     * @param bitmap          an image stored as a byte array containing a thumbnail bitmap of the patient
     * @param currentEncoding the image encoding array received from facial recognition
     */
    void makeImageRecordQuery(byte[] bitmap, ArrayList<Double> currentEncoding) {

        parameterArray.add(bitmap);
        paramTypeArray.add(Types.VARBINARY);
        if(currentEncoding!=null) {
            if(!currentEncoding.isEmpty()) {
                for (int count = 0; count < 128; count++) {
                    parameterArray.add(currentEncoding.get(count));
                    paramTypeArray.add(Types.DOUBLE);
                }
            }else{
                for (int count = 0; count < 128; count++) {
                    parameterArray.add(null);
                    paramTypeArray.add(Types.DOUBLE);
                }
            }

        }else{
            for (int count = 0; count < 128; count++) {
                parameterArray.add(null);
                paramTypeArray.add(Types.DOUBLE);
            }
        }

    }

    /**
     * @param username
     * @param password
     */
    void getLogin(String username, String password) {
        parameterArray.add(username);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(password);
        paramTypeArray.add(Types.VARCHAR);
    }

    /**
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param location
     * @param privileges
     */
    void setLogin(String firstName, String lastName, String username, String password, String location, boolean privileges) {
        parameterArray.add(firstName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(lastName);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(username);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(password);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(location);
        paramTypeArray.add(Types.VARCHAR);
        parameterArray.add(privileges);
        paramTypeArray.add(Types.BIT);
    }
}
