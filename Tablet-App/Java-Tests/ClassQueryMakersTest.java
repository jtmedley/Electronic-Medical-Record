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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;

import java.sql.Types;
import java.util.ArrayList;

/**
 * Implements testing methods for ClassQueryMakers class.
 */
public class ClassQueryMakersTest {
    /**
     * A query object to be used for SQL database communications,
     * containing an array of parameters for the SQL query and an array of
     * parameters to be applied to a prepared SQL statement
     */
    private ClassQueryMakers queryMaker;

    /**
     * A series of unique strings for testing.
     */
    String goodString1 = "Good String 1";
    String goodString2 = "Good String 2";
    String goodString3 = "Good String 3";
    String goodString4 = "Good String 4";
    String goodString5 = "Good String 5";
    String goodString6 = "Good String 6";
    String goodString7 = "Good String 7";
    String goodString8 = "Good String 8";
    String goodString9 = "Good String 9";
    String goodString10 = "Good String 10";
    String goodString11 = "Good String 11";
    String goodString12 = "Good String 12";
    String goodString13 = "Good String 13";
    String goodString14 = "Good String 14";
    String goodString15 = "Good String 15";
    String goodString16 = "Good String 16";
    String goodString17 = "Good String 17";
    String badString1 = null;

    /**
     * A series of unique dates for testing.
     */
    LocalDate goodDate1 = LocalDate.now();
    LocalDate badDate1 = null;

    /**
     * A series of unique integers for testing.
     */
    Integer goodInteger1 = 0;
    Integer goodInteger2 = -1;
    Integer goodInteger3 = 1;
    Integer goodInteger4 = 2;
    Integer goodInteger5 = -3;
    Integer goodInteger6 = 4;
    Integer badInteger1 = null;
    int personID1 = 14;
    int personID2 = 15;

    /**
     * A series of unique floats for testing.
     */
    Float goodFloat1 = 0f;
    Float goodFloat2 = -1f;
    Float goodFloat3 = 1f;
    Float goodFloat4 = 0.1f;
    Float goodFloat5 = -0.1f;
    Float badFloat1 = null;

    /**
     * A unique byte array for testing.
     */
    byte[] goodbitmap = "Good Byte".getBytes();

    /**
     * A series of unique list of arrays for testing.
     */
    ArrayList<Double> goodEncoding = new ArrayList<>();
    ArrayList<Integer> goodArrayList1 = new ArrayList<>();
    ArrayList<Integer> badArrayList1 = null;
    ArrayList<Integer> badArrayList2 = new ArrayList<>();

    /**
     * A series of boolean values for testing.
     */
    boolean boolean1 = true;
    Boolean aBoolean1 = true;
    Boolean aBoolean2 = false;
    Boolean aBoolean3 = null;
    Boolean aBoolean4 = true;
    Boolean aBoolean5 = true;
    Boolean aBoolean6 = true;
    Boolean aBoolean7 = true;
    Boolean aBoolean8 = true;
    Boolean aBoolean9 = true;
    Boolean aBoolean10 = true;
    Boolean aBoolean11 = true;

    /**
     * Initializes queryMarker.
     * Initializes goodArrayList1 to be used in a testing that two unique personID were added.
     * Initializes goodEncoding to be used in a testing that unique 128 double values were added.
     */
    @Before
    public void setUp() {
        queryMaker = new ClassQueryMakers();
        goodArrayList1.add(personID1);
        goodArrayList1.add(personID2);
        for (int count = 0; count < 128; count++) {
            goodEncoding.add(1.2 + count);
        }
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information (First name, last name, DOB, and sex).
     *  Tests for bad patient information.
     */
    @Test
    public void selectBasicQuery() {
        queryMaker.selectBasicQuery(badString1, badString1, badDate1, badString1);
        assert queryMaker.parameterArray.size() == 5;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%");
        assert queryMaker.parameterArray.get(2).equals("%");
        assert queryMaker.parameterArray.get(3).equals("%");
        assert queryMaker.parameterArray.get(4).equals("%");
        assert queryMaker.paramTypeArray.size() == 5;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information (First name, last name, DOB, and sex).
     *  Tests for bad first and last patient names, good date of birth, and bad sex.
     */
    @Test
    public void selectBasicQuery2() {
        queryMaker.selectBasicQuery(badString1, badString1, goodDate1, badString1);
        assert queryMaker.parameterArray.size() == 5;

        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%");
        assert queryMaker.parameterArray.get(2).equals("%");
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals("%");
        assert queryMaker.paramTypeArray.size() == 5;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information (First name, last name, DOB, and sex).
     *  Tests for good patient information.
     */
    @Test
    public void selectBasicQuery3() {
        queryMaker.selectBasicQuery(goodString1, goodString2, goodDate1, goodString3);
        assert queryMaker.parameterArray.size() == 5;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.paramTypeArray.size() == 5;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good patient information followed by a null matched patient array.
     */
    @Test
    public void selectWithFaceQuery() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, goodDate1, goodString3, badArrayList1);
        assert queryMaker.parameterArray.size() == 6;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(0);
        assert queryMaker.paramTypeArray.size() == 6;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good patient information followed by a empty matched patient array.
     */
    @Test
    public void selectWithFaceQuery2() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, goodDate1, goodString3, badArrayList2);
        assert queryMaker.parameterArray.size() == 6;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(0);
        assert queryMaker.paramTypeArray.size() == 6;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good patient information followed by a matched patient array.
     */
    @Test
    public void selectWithFaceQuery3() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, goodDate1, goodString3, goodArrayList1);
        assert queryMaker.parameterArray.size() == 7;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(goodArrayList1.get(0));
        assert queryMaker.parameterArray.get(6).equals(goodArrayList1.get(1));
        assert queryMaker.paramTypeArray.size() == 7;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(6).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good first name, last name, bad date of birth, and good sex,
     *  followed by a null matched patient array.
     */
    @Test
    public void selectWithFaceQuery4() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, badDate1, goodString3, badArrayList1);
        assert queryMaker.parameterArray.size() == 6;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals("%");
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(0);
        assert queryMaker.paramTypeArray.size() == 6;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good first name, last name, bad date of birth, and good sex,
     *  followed by a empty matched patient array.
     */
    @Test
    public void selectWithFaceQuery5() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, badDate1, goodString3, badArrayList2);
        assert queryMaker.parameterArray.size() == 6;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals("%");
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(0);
        assert queryMaker.paramTypeArray.size() == 6;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects basic patient information from the image recognition.
     *  Tests for good first name, last name, bad date of birth, and good sex,
     *  followed by a matched patient array.
     */
    @Test
    public void selectWithFaceQuery6() {
        queryMaker.selectWithFaceQuery(goodString1, goodString2, badDate1, goodString3, goodArrayList1);
        assert queryMaker.parameterArray.size() == 7;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals("%" + goodString1 + "%");
        assert queryMaker.parameterArray.get(2).equals("%" + goodString2 + "%");
        assert queryMaker.parameterArray.get(3).equals("%");
        assert queryMaker.parameterArray.get(4).equals(goodString3);
        assert queryMaker.parameterArray.get(5).equals(goodArrayList1.get(0));
        assert queryMaker.parameterArray.get(6).equals(goodArrayList1.get(1));
        assert queryMaker.paramTypeArray.size() == 7;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(6).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects the matched patient ID from the image recognition.
     *  Tests for a null matched patient array.
     */
    @Test
    public void selectOnlyFaceQuery() {
        queryMaker.selectOnlyFaceQuery(badArrayList1);
        assert queryMaker.parameterArray.size() == 2;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals(0);
        assert queryMaker.paramTypeArray.size() == 2;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects the matched patient ID from the image recognition.
     *  Tests for a empty matched patient array.
     */
    @Test
    public void selectOnlyFaceQuery2() {
        queryMaker.selectOnlyFaceQuery(badArrayList2);
        assert queryMaker.parameterArray.size() == 2;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals(0);
        assert queryMaker.paramTypeArray.size() == 2;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects the matched patient ID from the image recognition.
     *  Tests for a matched patient array.
     */
    @Test
    public void selectOnlyFaceQuery3() {
        queryMaker.selectOnlyFaceQuery(goodArrayList1);
        assert queryMaker.parameterArray.size() == 3;
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.get(1).equals(goodArrayList1.get(0));
        assert queryMaker.parameterArray.get(2).equals(goodArrayList1.get(1));
        assert queryMaker.paramTypeArray.size() == 3;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(2).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects general patient record from the given personID.
     *  Tests for good patient ID.
     */
    @Test
    public void selectPatientRecordsQuery() {
        queryMaker.selectPatientRecordsQuery(personID1);
        assert queryMaker.parameterArray.get(0).equals(ApplicationCustom.locationString);
        assert queryMaker.parameterArray.size() == 2;
        assert queryMaker.parameterArray.get(1).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 2;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects Haiti patient record from the given personID.
     *  Tests for good patient ID.
     */
    @Test
    public void selectHaitiExamRecordQuery() {
        queryMaker.selectHaitiExamRecordQuery(personID1);
        assert queryMaker.parameterArray.size() == 1;
        assert queryMaker.parameterArray.get(0).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 1;
        assert queryMaker.paramTypeArray.get(0).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * selects general examination record from the given personID.
     *  Tests for good patient ID.
     */
    @Test
    public void selectExamRecordQuery() {
        queryMaker.selectExamRecordQuery(personID1);
        assert queryMaker.parameterArray.size() == 1;
        assert queryMaker.parameterArray.get(0).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 1;
        assert queryMaker.paramTypeArray.get(0).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * updates a patient information for the patient.
     *  Tests for good patient information(Fist name, last name, DOB, sex, patientID).
     */
    @Test
    public void updateBasicQuery() {
        queryMaker.updateBasicQuery(goodString1, goodString2, goodDate1, goodString3, boolean1, goodString4, personID1);
        assert queryMaker.parameterArray.size() == 7;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodString2);
        assert queryMaker.parameterArray.get(2).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(3).equals(goodString3);
        assert queryMaker.parameterArray.get(4).equals(boolean1);
        assert queryMaker.parameterArray.get(5).equals(goodString4);
        assert queryMaker.parameterArray.get(6).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 7;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.DATE);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(5).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(6).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * updates a patient examination record the patient.
     *  Tests for good examination information (Triage, Date of visit, chart number, body temperature,
     *  diastolic, systolic, height, weight, BMI, history, diet, chief complaint...etc.).
     */
    @Test
    public void updateExamRecordQuery() {
        queryMaker.updateExamRecordQuery(goodString1, goodDate1, goodInteger1, goodFloat1, goodInteger2, goodInteger3, goodInteger4, goodInteger5, goodFloat2,
                goodString2, goodString3, goodString4, goodString5, goodString6, goodString7, goodString8, goodString9, goodString10, goodString11, goodString12,
                goodString13, personID1);
        assert queryMaker.parameterArray.size() == 22;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(2).equals(goodInteger1);
        assert queryMaker.parameterArray.get(3).equals(goodFloat1);
        assert queryMaker.parameterArray.get(4).equals(goodInteger2);
        assert queryMaker.parameterArray.get(5).equals(goodInteger3);
        assert queryMaker.parameterArray.get(6).equals(goodInteger4);
        assert queryMaker.parameterArray.get(7).equals(goodInteger5);
        assert queryMaker.parameterArray.get(8).equals(goodFloat2);
        assert queryMaker.parameterArray.get(9).equals(goodString2);
        assert queryMaker.parameterArray.get(10).equals(goodString3);
        assert queryMaker.parameterArray.get(11).equals(goodString4);
        assert queryMaker.parameterArray.get(12).equals(goodString5);
        assert queryMaker.parameterArray.get(13).equals(goodString6);
        assert queryMaker.parameterArray.get(14).equals(goodString7);
        assert queryMaker.parameterArray.get(15).equals(goodString8);
        assert queryMaker.parameterArray.get(16).equals(goodString9);
        assert queryMaker.parameterArray.get(17).equals(goodString10);
        assert queryMaker.parameterArray.get(18).equals(goodString11);
        assert queryMaker.parameterArray.get(19).equals(goodString12);
        assert queryMaker.parameterArray.get(20).equals(goodString13);
        assert queryMaker.parameterArray.get(21).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 22;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.DATE);
        assert queryMaker.paramTypeArray.get(2).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(3).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(4).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(5).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(6).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(7).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(8).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(9).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(10).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(11).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(12).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(13).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(14).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(15).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(16).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(17).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(18).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(19).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(20).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(21).equals(Types.INTEGER);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * updates a patient examination record for the patient in Haiti.
     *  Tests for good examination information (Village, hemoglobin, DmDx, ETOh, Family HxDm..etc.).
     */
    @Test
    public void updateHaitiExamRecordQuery() {
        queryMaker.updateHaitiExamRecordQuery(goodString1, goodInteger1, aBoolean1, aBoolean2, aBoolean3, aBoolean4,
                aBoolean5, aBoolean6, aBoolean7, aBoolean8, aBoolean9, aBoolean10, aBoolean11, goodInteger2, personID1);
        //System.out.println(Boolean.toString((Boolean)queryMaker.parameterArray.get(4)));
        //System.out.println(queryMaker.parameterArray.get(4));
        assert queryMaker.parameterArray.size() == 15;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodInteger1);
        assert queryMaker.parameterArray.get(2).equals(aBoolean1);
        assert queryMaker.parameterArray.get(3).equals(aBoolean2);
        assert queryMaker.parameterArray.get(4) == (aBoolean3);
        assert queryMaker.parameterArray.get(5).equals(aBoolean4);
        assert queryMaker.parameterArray.get(6).equals(aBoolean5);
        assert queryMaker.parameterArray.get(7).equals(aBoolean6);
        assert queryMaker.parameterArray.get(8).equals(aBoolean7);
        assert queryMaker.parameterArray.get(9).equals(aBoolean8);
        assert queryMaker.parameterArray.get(10).equals(aBoolean9);
        assert queryMaker.parameterArray.get(11).equals(aBoolean10);
        assert queryMaker.parameterArray.get(12).equals(aBoolean11);
        assert queryMaker.parameterArray.get(13).equals(goodInteger2);
        assert queryMaker.parameterArray.get(14).equals(personID1);
        assert queryMaker.paramTypeArray.size() == 15;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(2).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(3).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(4).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(5).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(6).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(7).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(8).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(9).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(10).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(11).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(12).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(13).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(14).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * creates a examination record for the patient.
     *  Tests for good examination information (PatientID, Triage, Date of visit, chart number, body temperature,
     *  diastolic, systolic, height, weight, BMI, history, diet, chief complaint...etc.).
     */
    @Test
    public void makeExamRecordQuery() {
        queryMaker.makeExamRecordQuery(goodInteger1, goodString1, goodDate1, goodInteger2, goodFloat1,
                goodInteger3, goodInteger4, goodInteger5, goodInteger6, goodFloat2, goodString2,
                goodString3, goodString4, goodString5, goodString6, goodString7, goodString8,
                goodString9, goodString10, goodString11, goodString12, goodString13);
        assert queryMaker.parameterArray.size() == 23;
        assert queryMaker.parameterArray.get(0).equals(goodInteger1);
        assert queryMaker.parameterArray.get(1).equals(goodInteger1);
        assert queryMaker.parameterArray.get(2).equals(goodString1);
        assert queryMaker.parameterArray.get(3).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(4).equals(goodInteger2);
        assert queryMaker.parameterArray.get(5).equals(goodFloat1);
        assert queryMaker.parameterArray.get(6).equals(goodInteger3);
        assert queryMaker.parameterArray.get(7).equals(goodInteger4);
        assert queryMaker.parameterArray.get(8).equals(goodInteger5);
        assert queryMaker.parameterArray.get(9).equals(goodInteger6);
        assert queryMaker.parameterArray.get(10).equals(goodFloat2);
        assert queryMaker.parameterArray.get(11).equals(goodString2);
        assert queryMaker.parameterArray.get(12).equals(goodString3);
        assert queryMaker.parameterArray.get(13).equals(goodString4);
        assert queryMaker.parameterArray.get(14).equals(goodString5);
        assert queryMaker.parameterArray.get(15).equals(goodString6);
        assert queryMaker.parameterArray.get(16).equals(goodString7);
        assert queryMaker.parameterArray.get(17).equals(goodString8);
        assert queryMaker.parameterArray.get(18).equals(goodString9);
        assert queryMaker.parameterArray.get(19).equals(goodString10);
        assert queryMaker.parameterArray.get(20).equals(goodString11);
        assert queryMaker.parameterArray.get(21).equals(goodString12);
        assert queryMaker.parameterArray.get(22).equals(goodString13);
        assert queryMaker.paramTypeArray.size() == 23;
        assert queryMaker.paramTypeArray.get(0).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.DATE);
        assert queryMaker.paramTypeArray.get(4).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(5).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(6).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(7).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(8).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(9).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(10).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(11).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(12).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(13).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(14).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(15).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(16).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(17).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(18).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(19).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(20).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(21).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(22).equals(Types.VARCHAR);


    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * creates a examination record for the patient in Haiti.
     *  Tests for good examination information (PatientID, Triage, Date of visit, chart number, body temperature,
     *  diastolic, systolic, height, weight, BMI, history, diet, chief complaint...etc.).
     */
    @Test
    public void makeHaitiRecordQuery() {
        queryMaker.makeHaitiRecordQuery(goodString1, goodInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean4, aBoolean5, aBoolean6, aBoolean7, aBoolean8, aBoolean9, aBoolean10,
                aBoolean11, goodInteger2);
        assert queryMaker.parameterArray.size() == 14;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodInteger1);
        assert queryMaker.parameterArray.get(2).equals(aBoolean1);
        assert queryMaker.parameterArray.get(3).equals(aBoolean2);
        assert queryMaker.parameterArray.get(4) == (aBoolean3);
        assert queryMaker.parameterArray.get(5).equals(aBoolean4);
        assert queryMaker.parameterArray.get(6).equals(aBoolean5);
        assert queryMaker.parameterArray.get(7).equals(aBoolean6);
        assert queryMaker.parameterArray.get(8).equals(aBoolean7);
        assert queryMaker.parameterArray.get(9).equals(aBoolean8);
        assert queryMaker.parameterArray.get(10).equals(aBoolean9);
        assert queryMaker.parameterArray.get(11).equals(aBoolean10);
        assert queryMaker.parameterArray.get(12).equals(aBoolean11);
        assert queryMaker.parameterArray.get(13).equals(goodInteger2);
        assert queryMaker.paramTypeArray.size() == 14;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(2).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(3).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(4).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(5).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(6).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(7).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(8).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(9).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(10).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(11).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(12).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(13).equals(Types.INTEGER);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * creates both a basic patient record and a examination record.
     *  Tests for good basic patient information and examination information.
     */
    @Test
    public void makeBothRecordsQuery() {
        queryMaker.makeBothRecordsQuery(goodString1, goodString2, goodDate1, goodString3, aBoolean1,
                goodString4, goodString5, goodDate1, goodInteger1, goodFloat1, goodInteger2,
                goodInteger3, goodInteger4, goodInteger5, goodFloat2, goodString6, goodString7,
                goodString8, goodString9, goodString10, goodString11, goodString12,
                goodString13, goodString14, goodString15, goodString16, goodString17);
        assert queryMaker.parameterArray.size() == 27;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodString2);
        assert queryMaker.parameterArray.get(2).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(3).equals(goodString3);
        assert queryMaker.parameterArray.get(4).equals(aBoolean1);
        assert queryMaker.parameterArray.get(5).equals(goodString4);
        assert queryMaker.parameterArray.get(6).equals(goodString5);
        assert queryMaker.parameterArray.get(7).equals(DateTimeUtils.toSqlDate(goodDate1));
        assert queryMaker.parameterArray.get(8).equals(goodInteger1);
        assert queryMaker.parameterArray.get(9).equals(goodFloat1);
        assert queryMaker.parameterArray.get(10).equals(goodInteger2);
        assert queryMaker.parameterArray.get(11).equals(goodInteger3);
        assert queryMaker.parameterArray.get(12).equals(goodInteger4);
        assert queryMaker.parameterArray.get(13).equals(goodInteger5);
        assert queryMaker.parameterArray.get(14).equals(goodFloat2);
        assert queryMaker.parameterArray.get(15).equals(goodString6);
        assert queryMaker.parameterArray.get(16).equals(goodString7);
        assert queryMaker.parameterArray.get(17).equals(goodString8);
        assert queryMaker.parameterArray.get(18).equals(goodString9);
        assert queryMaker.parameterArray.get(19).equals(goodString10);
        assert queryMaker.parameterArray.get(20).equals(goodString11);
        assert queryMaker.parameterArray.get(21).equals(goodString12);
        assert queryMaker.parameterArray.get(22).equals(goodString13);
        assert queryMaker.parameterArray.get(23).equals(goodString14);
        assert queryMaker.parameterArray.get(24).equals(goodString15);
        assert queryMaker.parameterArray.get(25).equals(goodString16);
        assert queryMaker.parameterArray.get(26).equals(goodString17);
        assert queryMaker.paramTypeArray.size() == 27;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.DATE);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.BIT);
        assert queryMaker.paramTypeArray.get(5).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(6).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(7).equals(Types.DATE);
        assert queryMaker.paramTypeArray.get(8).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(9).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(10).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(11).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(12).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(13).equals(Types.INTEGER);
        assert queryMaker.paramTypeArray.get(14).equals(Types.DECIMAL);
        assert queryMaker.paramTypeArray.get(15).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(16).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(17).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(18).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(19).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(20).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(21).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(22).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(23).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(24).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(25).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(26).equals(Types.VARCHAR);
    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * inserts a thumbnail and an encoding of an image.
     *  Tests for a good image byte and a good 128 face encoding.
     */
    @Test
    public void makeImageRecordQuery() {
        queryMaker.makeImageRecordQuery(goodbitmap, goodEncoding);


        assert queryMaker.parameterArray.size() == 129;
        assert queryMaker.parameterArray.get(0).equals(goodbitmap);
        for (int count = 0; count < 128; count++) {
            assert queryMaker.parameterArray.get(count + 1).equals(goodEncoding.get(count));
        }
        assert queryMaker.paramTypeArray.size() == 129;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARBINARY);
        for (int count = 0; count < 128; count++) {
            assert queryMaker.paramTypeArray.get(count + 1).equals(Types.DOUBLE);
        }

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * receives a login information from the data base
     *  Tests for a good userID and password.
     */
    @Test
    public void getLogin() {
        queryMaker.getLogin(goodString1, goodString2);
        assert queryMaker.parameterArray.size() == 2;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodString2);
        assert queryMaker.paramTypeArray.size() == 2;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);

    }

    /**
     * Adds parameters to query maker to be used in a statement that
     * puts login information
     *  Tests for a good userID and password.
     */
    @Test
    public void setLogin() {
        queryMaker.setLogin(goodString1, goodString2, goodString3, goodString4, goodString5, boolean1);
        assert queryMaker.parameterArray.size() == 6;
        assert queryMaker.parameterArray.get(0).equals(goodString1);
        assert queryMaker.parameterArray.get(1).equals(goodString2);
        assert queryMaker.parameterArray.get(2).equals(goodString3);
        assert queryMaker.parameterArray.get(3).equals(goodString4);
        assert queryMaker.parameterArray.get(4).equals(goodString5);
        assert queryMaker.parameterArray.get(5).equals(boolean1);
        assert queryMaker.paramTypeArray.size() == 6;
        assert queryMaker.paramTypeArray.get(0).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(1).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(2).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(3).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(4).equals(Types.VARCHAR);
        assert queryMaker.paramTypeArray.get(5).equals(Types.BIT);
    }
}