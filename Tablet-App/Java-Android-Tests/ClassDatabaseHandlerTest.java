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

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ClassDatabaseHandlerTest {

    /**
     * A series of unique strings for testing.
     */
    private String badString2 = "Bad String";
    private String userName = "jon";
    private String passWord = "medley";
    private String goodString1 = "Jon";
    private String goodString2 = "Medley";
    private String goodString3 = "Male";
    private String goodString4 = "mh mhx";
    private String goodHistory = "";
    private String goodDiet = "";
    private String goodChiefC = "zdgbetb";
    private String goodHistoryIll = "zebath";
    private String goodAssess = "zetbaeth";

    private String goodPhysical = "zebEtbyjrxyjxjyfcugvweu jgdw wjgcvwej wecugvwcjcuwevcqjecgvecjgWEudtawchgvWcutwfCwj";
    private String goodTreat = null;
    private String goodDoctorF = null;
    private String goodDoctorL = null;
    private String goodPer = "";
    private String goodSig = "jzegfaierh";
    private String goodLoc = "Haiti";
    private String goodVillage = "Washington DC";
    private String goodCurrentTriage = "mh mhx";
    private String badUsername = "capstone";
    private String badPassword = "capstone";
    private String goodLocation = "Haiti";
    private String badString1 = null;
    private String goodUsername = Double.toString(Math.random());
    /**
     * A series of unique dates for testing.
     */
    private LocalDate goodDate1 = LocalDate.now();
    private LocalDate goodDate2 = LocalDate.of(1997, 03, 05);
    private LocalDate goodDate3 = LocalDate.of(2019, 05, 06);
    private LocalDate badDate1 = null;
    private LocalDate badDate2 = LocalDate.now();


    /**
     * A series of unique integers for testing.
     */
    private Integer diastolicBP = null;
    private Integer systolicBP = null;
    private Integer height = 10;
    private Integer weight = 10;
    private Integer goodHemo = 12;
    private Integer goodMaggi = 27;
    private Integer goodChartNum = 1;
    private Integer badInteger1 = null;
    private int personID1 = 14268;
    private int personID2 = 1;
    private int personID3 = 14294;
    private int personID4 = 14258;

    private String goodString5 = "Tabitha";
    private String goodString6 = "Huff";
    private LocalDate goodDate4 = LocalDate.of(1997,6,13);
    private String goodString7 = "Female";

    /**
     * A series of unique floats for testing.
     */
    private Float goodBodyTemp = 3f;
    private Float goodFloat1 = 0f;
    private Float badFloat1 = null;

    /**
     * A unique byte array for testing.
     */
    private byte[] badBitmap;
    private byte[] goodBitmap = {1, 2, 3, 4};
    /**
     * A series of unique list of arrays for testing.
     */
    private ArrayList<Double> badEncoding1;
    private ArrayList<Double> badEncoding2 = new ArrayList<>();
    private ArrayList<Double> goodEncoding = new ArrayList<>();
    private ArrayList<Integer> goodArrayList1 = new ArrayList<>();
    private ArrayList<Integer> badArrayList1 = null;

    /**
     * A series of private Boolean values for testing.
     */
    private Boolean boolean1 = true;
    private Boolean boolean2 = false;

    private Boolean goodTrue = true;
    private Boolean goodFalse = false;
    private Boolean aBoolean1 = true;
    private Boolean aBoolean2 = false;
    private Boolean aBoolean3 = null;


    private ClassDatabaseHandler databaseHandler;
    private ClassDatabaseHandler databaseHandler2;
    private String goodUsername1 = "jon";
    private String goodPassword1 = "medley";
    private String badUsername1 = "bad";
    private String badPassword1 = "other";
    private BaseActivityClass baseActivity;
    private Activity activity;
    private ActivityTestRule activityTestRule;
    private ClassQueryMakers goodQueryMaker2;
    int goodQueryIdentifier2;
    private ClassQueryMakers goodQueryMaker3;
    private ClassDatabaseHandler databaseHandler3;
    private int recordID1 = 3;
    private Float goodBMI = 5.6f;
    private int recordID2 = -2;

    @Rule
    public ActivityTestRule getActivityTestRule() {
        activityTestRule = new ActivityTestRule<>(PageLogin.class);

        return activityTestRule;
    }

    private ClassQueryMakers goodQueryMaker1;
    private ClassQueryMakers badQueryMaker1;

    @Before
    public void setUp() throws Exception {
        ApplicationCustom.setLocationSettings(ClassConstants.EXAM_STRING_HAITI);
        activity = activityTestRule.getActivity();
        baseActivity = (BaseActivityClass) activity;
        badBitmap = null;
        goodArrayList1.add(personID1);
        for (int count = 0; count < 128;
             count++) {
            goodEncoding.add(1.2 + count);
        }
        ClassQueryMakers queryMakerSetUp = new ClassQueryMakers();
        queryMakerSetUp.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean1, goodString4, personID1);
        queryMakerSetUp.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        queryMakerSetUp.updateHaitiExamRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi, recordID1);
        int goodQueryIdentifier = 6;
        ClassDatabaseHandler databaseHandlerSetUp = new ClassDatabaseHandler(baseActivity, queryMakerSetUp, goodQueryIdentifier);
        try {
            databaseHandlerSetUp.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        ClassQueryMakers queryMakerSetUp = new ClassQueryMakers();

        queryMakerSetUp.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean1, goodString4, personID1);
        queryMakerSetUp.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        queryMakerSetUp.updateHaitiExamRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi, recordID1);
        int goodQueryIdentifier = 6;
        ClassDatabaseHandler databaseHandlerSetUp = new ClassDatabaseHandler(baseActivity, queryMakerSetUp, goodQueryIdentifier);
        try {
            databaseHandlerSetUp.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }

    }

    @Test
    public void getPortNumber() {
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        assertEquals(databaseHandler.getPortNumber(), ClassConstants.DATABASE_SOCKET_PORT_NUMBER);
    }

    @Test
    public void socketOperation() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.getLogin(badUsername1, badPassword1);
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        assertNotNull(databaseHandler.cThread);
        assertTrue(databaseHandler.cThread.isAlive());
        TestMethods.notAnyToasts(activity, R.string.databaseError);
    }

    @Test
    public void socketOperation2() {
        badQueryMaker1 = new ClassQueryMakers();
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, badQueryMaker1, goodQueryIdentifier);
        TestMethods.compareToasts(activity, R.string.databaseError);
        assertNull(databaseHandler.cThread);
    }


    @Test
    public void socketOperation3() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.getLogin(badUsername1, badPassword1);
        int goodQueryIdentifier = -10;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        TestMethods.compareToasts(activity, R.string.databaseError);
        assertNull(databaseHandler.cThread);
    }

    @Test
    public void socketOperation4() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.getLogin(badUsername1, badPassword1);
        int goodQueryIdentifier = 40;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        TestMethods.compareToasts(activity, R.string.databaseError);
        assertNull(databaseHandler.cThread);
    }

    @Test
    public void badSelectWithOnlyFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectOnlyFaceQuery(badArrayList1);
        int goodQueryIdentifier = -1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());
    }

    @Test
    public void goodSelectWithOnlyFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectOnlyFaceQuery(goodArrayList1);
        int goodQueryIdentifier = -1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertEquals(databaseHandler.results.get(0).get("First Name"), goodString1);
        assertEquals(databaseHandler.results.get(0).get("Last Name"), goodString2);
        assertEquals(databaseHandler.results.get(0).get("Date of Birth"), goodDate2.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Sex"), goodString3);
        assertEquals(databaseHandler.results.get(0).get("Person ID"), goodArrayList1.get(0));
        assertEquals(databaseHandler.results.get(0).get("Discharged"), boolean1);
        assertEquals(databaseHandler.results.get(0).get("Current Triage"), "mh mhx");
    }

    @Test
    public void getBadLoginQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.getLogin(badUsername1, badPassword1);
        int goodQueryIdentifier = 0;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        assertNotNull(databaseHandler.cThread);
        assertTrue(databaseHandler.cThread.isAlive());
        assertTrue(databaseHandler.results.isEmpty());
    }


    @Test
    public void getGoodLoginQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.getLogin(goodUsername1, goodPassword1);
        int goodQueryIdentifier = 0;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        assertNotNull(databaseHandler.cThread);
        assertTrue(databaseHandler.cThread.isAlive());
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertEquals(databaseHandler.results.get(0).get("Person ID"), personID4);
        assertEquals(databaseHandler.results.get(0).get("User ID"), goodUsername1);
        assertEquals(databaseHandler.results.get(0).get("Password"), goodPassword1);
        assertEquals(databaseHandler.results.get(0).get("Location"), "Haiti");
        assertEquals(databaseHandler.results.get(0).get("Privileges"), boolean1);
    }

    @Test
    public void selectNothingBasicQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectBasicQuery(badString1, badString1, badDate1, badString1);
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey(("Person ID")));
        assertTrue(databaseHandler.results.get(0).containsKey("First Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Last Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Birth"));
        assertTrue(databaseHandler.results.get(0).containsKey("Sex"));
        assertTrue(databaseHandler.results.get(0).containsKey("Current Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("Discharged"));
    }

    @Test
    public void selectBadBasicQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectBasicQuery(badString1, badString2, badDate1, badString1);
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());
    }
    
    @Test
    public void selectGoodBasicQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectBasicQuery(goodString5, goodString6, goodDate4, goodString7);
        int goodQueryIdentifier = 1;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertEquals(databaseHandler.results.get(0).get("First Name"), goodString5);
        assertEquals(databaseHandler.results.get(0).get("Last Name"), goodString6);
        assertEquals(databaseHandler.results.get(0).get("Date of Birth"), goodDate4.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Sex"), goodString7);
        assertEquals(databaseHandler.results.get(0).get("Person ID"), personID3);
        assertEquals(databaseHandler.results.get(0).get("Discharged"), boolean1);
        assertEquals(databaseHandler.results.get(0).get("Current Triage"), "Normal");

    }

    @Test
    public void badBasicBadFaceSelectWithFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectWithFaceQuery(badString2, badString2, badDate1, badString2, badArrayList1);
        int goodQueryIdentifier = 2;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());
    }

    @Test
    public void goodBasicBadFaceSelectWithFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectWithFaceQuery(goodString5, goodString6, goodDate4, goodString7, badArrayList1);
        int goodQueryIdentifier = 2;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertEquals(databaseHandler.results.get(0).get("First Name"), goodString5);
        assertEquals(databaseHandler.results.get(0).get("Last Name"), goodString6);
        assertEquals(databaseHandler.results.get(0).get("Date of Birth"), goodDate4.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Sex"), goodString7);
        assertEquals(databaseHandler.results.get(0).get("Person ID"), personID3);
        assertEquals(databaseHandler.results.get(0).get("Discharged"), boolean1);
        assertEquals(databaseHandler.results.get(0).get("Current Triage"), "Normal");
    }

    @Test
    public void noBasicGoodFaceSelectWithFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectWithFaceQuery(badString1, badString1, badDate1, badString1, goodArrayList1);
        int goodQueryIdentifier = 2;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey(("Person ID")));
        assertTrue(databaseHandler.results.get(0).containsKey("First Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Last Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Birth"));
        assertTrue(databaseHandler.results.get(0).containsKey("Sex"));
        assertTrue(databaseHandler.results.get(0).containsKey("Current Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("Discharged"));
    }

    @Test
    public void badBasicGoodFaceSelectWithFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectWithFaceQuery(badString1, badString2, badDate1, badString1, goodArrayList1);
        int goodQueryIdentifier = 2;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey(("Person ID")));
        assertTrue(databaseHandler.results.get(0).containsKey("First Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Last Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Birth"));
        assertTrue(databaseHandler.results.get(0).containsKey("Sex"));
        assertTrue(databaseHandler.results.get(0).containsKey("Current Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("Discharged"));

    }

    @Test
    public void goodBasicGoodFaceSelectWithFaceQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectWithFaceQuery(goodString1, goodString2, goodDate2, goodString3, goodArrayList1);
        int goodQueryIdentifier = 2;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertEquals(databaseHandler.results.get(0).get("First Name"), goodString1);
        assertEquals(databaseHandler.results.get(0).get("Last Name"), goodString2);
        assertEquals(databaseHandler.results.get(0).get("Date of Birth"),goodDate2.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Sex"), goodString3);
        assertEquals(databaseHandler.results.get(0).get("Person ID"), personID4);
        assertEquals(databaseHandler.results.get(0).get("Discharged"), boolean2);
        assertEquals(databaseHandler.results.get(0).get("Current Triage"), "mh mhx");
    }

    @Test
    public void selectBadPatientRecordsQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectPatientRecordsQuery(personID2);
        int goodQueryIdentifier = 3;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());
    }

    @Test
    public void selectGoodPatientRecordsQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectPatientRecordsQuery(personID1);
        int goodQueryIdentifier = 3;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey("Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("ImageBitmap"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Visit"));
        assertTrue(databaseHandler.results.get(0).containsKey("Record ID"));
        assertTrue(databaseHandler.results.get(0).containsKey("Location Record"));
    }

    @Test
    public void selectBadHaitiExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectHaitiExamRecordQuery(personID2);
        int goodQueryIdentifier = 4;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());
    }

    @Test
    public void selectGoodHaitiExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectHaitiExamRecordQuery(recordID1);
        int goodQueryIdentifier = 4;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey("Person ID"));
        assertTrue(databaseHandler.results.get(0).containsKey("Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Visit"));
        assertTrue(databaseHandler.results.get(0).containsKey("Chart Number"));
        assertTrue(databaseHandler.results.get(0).containsKey("Village"));
        assertTrue(databaseHandler.results.get(0).containsKey("Body Temperature"));
        assertTrue(databaseHandler.results.get(0).containsKey("Diastolic BP"));
        assertTrue(databaseHandler.results.get(0).containsKey("Systolic BP"));
        assertTrue(databaseHandler.results.get(0).containsKey("Height"));
        assertTrue(databaseHandler.results.get(0).containsKey("Weight"));
        assertTrue(databaseHandler.results.get(0).containsKey("BMI"));
        assertTrue(databaseHandler.results.get(0).containsKey("Hemoglobin"));
        assertTrue(databaseHandler.results.get(0).containsKey("History DmDx"));
        assertTrue(databaseHandler.results.get(0).containsKey("History ETOH"));
        assertTrue(databaseHandler.results.get(0).containsKey("History FamilyHxDm"));
        assertTrue(databaseHandler.results.get(0).containsKey("History FamilyHxHTN"));
        assertTrue(databaseHandler.results.get(0).containsKey("History FamilyHxStroke"));
        assertTrue(databaseHandler.results.get(0).containsKey("History Polyphagia"));
        assertTrue(databaseHandler.results.get(0).containsKey("History Polydipsia"));
        assertTrue(databaseHandler.results.get(0).containsKey("History Polyuria"));
        assertTrue(databaseHandler.results.get(0).containsKey("History Smoker"));
        assertTrue(databaseHandler.results.get(0).containsKey("History Stroke"));
        assertTrue(databaseHandler.results.get(0).containsKey("Other History"));
        assertTrue(databaseHandler.results.get(0).containsKey("Diet"));
        assertTrue(databaseHandler.results.get(0).containsKey("Amount of Maggi"));
        assertTrue(databaseHandler.results.get(0).containsKey("Chief Complaint"));
        assertTrue(databaseHandler.results.get(0).containsKey("History of Present Illness"));
        assertTrue(databaseHandler.results.get(0).containsKey("Assessment"));
        assertTrue(databaseHandler.results.get(0).containsKey("Physical Examination"));
        assertTrue(databaseHandler.results.get(0).containsKey("Treatment Plan"));
        assertTrue(databaseHandler.results.get(0).containsKey("Clinician First Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Clinician Last Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Prescription"));
        assertTrue(databaseHandler.results.get(0).containsKey("Signature"));
        assertTrue(databaseHandler.results.get(0).containsKey("Bitmap"));
        assertEquals(databaseHandler.results.get(0).get("Person ID"),personID1);
        assertEquals(databaseHandler.results.get(0).get("Triage"),"mh mhx");
        assertEquals(databaseHandler.results.get(0).get("Date of Visit"),goodDate2.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Chart Number"),1);
        assertEquals(databaseHandler.results.get(0).get("Village"),"Washington DC");
        assertEquals(((BigDecimal) databaseHandler.results.get(0).get("Body Temperature")).floatValue(),3.00F,0);
        assertNull(databaseHandler.results.get(0).get("Diastolic BP"));
        assertNull(databaseHandler.results.get(0).get("Systolic BP"));
        assertEquals(databaseHandler.results.get(0).get("Height"),10);
        assertEquals(databaseHandler.results.get(0).get("Weight"),10);
        assertEquals(((BigDecimal)databaseHandler.results.get(0).get("BMI")).floatValue(),5.6F,0);
        assertEquals(databaseHandler.results.get(0).get("Hemoglobin"),12);
        assertEquals(databaseHandler.results.get(0).get("History DmDx"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History ETOH"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History FamilyHxDm"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History FamilyHxHTN"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History FamilyHxStroke"),boolean1);
        assertEquals(databaseHandler.results.get(0).get("History HTNDx"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History Polyphagia"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History Polydipsia"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History Polyuria"),boolean2);
        assertEquals(databaseHandler.results.get(0).get("History Smoker"),boolean1);
        assertEquals(databaseHandler.results.get(0).get("History Stroke"),boolean1);
        assertEquals(databaseHandler.results.get(0).get("Other History"),"");
        assertEquals(databaseHandler.results.get(0).get("Diet"),"");
        assertEquals(databaseHandler.results.get(0).get("Amount of Maggi"),goodMaggi);
        assertEquals(databaseHandler.results.get(0).get("Chief Complaint"),"zdgbetb");
        assertEquals(databaseHandler.results.get(0).get("History of Present Illness"),"zebath");
        assertEquals(databaseHandler.results.get(0).get("Assessment"),"zetbaeth");
        assertEquals(databaseHandler.results.get(0).get("Physical Examination"),"zebEtbyjrxyjxjyfcugvweu jgdw wjgcvwej wecugvwcjcuwevcqjecgvecjgWEudtawchgvWcutwfCwj");
        assertNull(databaseHandler.results.get(0).get("Treatment Plan"));
        assertNull(databaseHandler.results.get(0).get("Clinician First Name"));
        assertNull(databaseHandler.results.get(0).get("Clinician Last Name"));
        assertEquals(databaseHandler.results.get(0).get("Prescription"),"");
        assertEquals(databaseHandler.results.get(0).get("Signature"),"jzegfaierh");
        assertNull(databaseHandler.results.get(0).get("Bitmap"));
    }

    @Test
    public void selectBadExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectExamRecordQuery(recordID2);
        int goodQueryIdentifier = 5;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(databaseHandler.results.isEmpty());;
    }

    @Test
    public void selectGoodExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.selectExamRecordQuery(recordID1);
        int goodQueryIdentifier = 5;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertTrue(!databaseHandler.results.isEmpty());
        assertTrue(databaseHandler.results.get(0).containsKey("Person ID"));
        assertTrue(databaseHandler.results.get(0).containsKey("Triage"));
        assertTrue(databaseHandler.results.get(0).containsKey("Date of Visit"));
        assertTrue(databaseHandler.results.get(0).containsKey("Chart Number"));
        assertTrue(databaseHandler.results.get(0).containsKey("Body Temperature"));
        assertTrue(databaseHandler.results.get(0).containsKey("Diastolic BP"));
        assertTrue(databaseHandler.results.get(0).containsKey("Systolic BP"));
        assertTrue(databaseHandler.results.get(0).containsKey("Height"));
        assertTrue(databaseHandler.results.get(0).containsKey("Weight"));
        assertTrue(databaseHandler.results.get(0).containsKey("BMI"));
        assertTrue(databaseHandler.results.get(0).containsKey("Other History"));
        assertTrue(databaseHandler.results.get(0).containsKey("Diet"));
        assertTrue(databaseHandler.results.get(0).containsKey("Chief Complaint"));
        assertTrue(databaseHandler.results.get(0).containsKey("History of Present Illness"));
        assertTrue(databaseHandler.results.get(0).containsKey("Assessment"));
        assertTrue(databaseHandler.results.get(0).containsKey("Physical Examination"));
        assertTrue(databaseHandler.results.get(0).containsKey("Treatment Plan"));
        assertTrue(databaseHandler.results.get(0).containsKey("Clinician First Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Clinician Last Name"));
        assertTrue(databaseHandler.results.get(0).containsKey("Prescription"));
        assertTrue(databaseHandler.results.get(0).containsKey("Signature"));
        assertTrue(databaseHandler.results.get(0).containsKey("Bitmap"));
        assertEquals(databaseHandler.results.get(0).get("Person ID"),personID1);
        assertEquals(databaseHandler.results.get(0).get("Triage"),"mh mhx");
        assertEquals(databaseHandler.results.get(0).get("Date of Visit"),goodDate2.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals(databaseHandler.results.get(0).get("Chart Number"),1);
        assertEquals(((BigDecimal) databaseHandler.results.get(0).get("Body Temperature")).floatValue(),3.00F,0);
        assertNull(databaseHandler.results.get(0).get("Diastolic BP"));
        assertNull(databaseHandler.results.get(0).get("Systolic BP"));
        assertEquals(databaseHandler.results.get(0).get("Height"),10);
        assertEquals(databaseHandler.results.get(0).get("Weight"),10);
        assertEquals(((BigDecimal)databaseHandler.results.get(0).get("BMI")).floatValue(),5.6F,0);
        assertEquals(databaseHandler.results.get(0).get("Other History"),"");
        assertEquals(databaseHandler.results.get(0).get("Diet"),"");
        assertEquals(databaseHandler.results.get(0).get("Chief Complaint"),"zdgbetb");
        assertEquals(databaseHandler.results.get(0).get("History of Present Illness"),"zebath");
        assertEquals(databaseHandler.results.get(0).get("Assessment"),"zetbaeth");
        assertEquals(databaseHandler.results.get(0).get("Physical Examination"),"zebEtbyjrxyjxjyfcugvweu jgdw wjgcvwej wecugvwcjcuwevcqjecgvecjgWEudtawchgvWcutwfCwj");
        assertNull(databaseHandler.results.get(0).get("Treatment Plan"));
        assertNull(databaseHandler.results.get(0).get("Clinician First Name"));
        assertNull(databaseHandler.results.get(0).get("Clinician Last Name"));
        assertEquals(databaseHandler.results.get(0).get("Prescription"),"");
        assertEquals(databaseHandler.results.get(0).get("Signature"),"jzegfaierh");
        assertNull(databaseHandler.results.get(0).get("Bitmap"));
    }


    @Test
    public void updateBasicExamAndHaitiBadQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, goodString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.updateExamRecordQuery(badString2, badDate2, badInteger1, badFloat1, badInteger1, badInteger1, badInteger1, badInteger1, badFloat1,
                badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, personID2);
        goodQueryMaker1.updateHaitiExamRecordQuery(badString2, badInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, badInteger1, personID2);
        int goodQueryIdentifier = 6;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);

    }

    @Test
    public void updateBasicExamAndHaitiGoodQuery() {
        System.out.println("0");
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString2, goodString3, goodDate1, goodString4, boolean2, goodString2, personID4);
        goodQueryMaker1.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        goodQueryMaker1.updateHaitiExamRecordQuery(goodVillage, goodHemo, goodTrue, goodTrue, goodFalse, goodTrue,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi, recordID1);
        int goodQueryIdentifier = 6;
        System.out.println("1");
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        System.out.println("2");
        System.out.println("3");
        try {
            System.out.println("4");
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            System.out.println("5");
            assert false;
            e.printStackTrace();
        }

        assertEquals(databaseHandler.affected, 1);

    }

    @Test
    public void updateBasicAndExamBadQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, badString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.updateExamRecordQuery(badString2, badDate2, badInteger1, badFloat1, badInteger1, badInteger1, badInteger1, badInteger1, badFloat1,
                badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, personID2);
        int goodQueryIdentifier = 7;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void updateBasicAndExamGoodQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString1, goodString2, goodDate1, goodString3, boolean2, goodString4, personID4);
        goodQueryMaker1.updateExamRecordQuery(goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        int goodQueryIdentifier = 7;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        goodQueryMaker2 = new ClassQueryMakers();
        assertEquals(databaseHandler.affected, 1);

    }

    @Test
    public void updateExamAndHaitiBadQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, personID2);
        goodQueryMaker1.updateHaitiExamRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi, personID2);
        int goodQueryIdentifier = 8;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);

    }

    @Test
    public void updateExamAndHaitiGoodQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        goodQueryMaker1.updateHaitiExamRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi, recordID1);
        int goodQueryIdentifier = 8;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);

    }

    @Test
    public void updateBadExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateExamRecordQuery(badString2, badDate2, badInteger1, badFloat1, badInteger1, badInteger1, badInteger1, badInteger1, badFloat1,
                badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, personID2);
        int goodQueryIdentifier = 9;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void UpdateGoodExamRecordQuery4() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateExamRecordQuery(goodString4, goodDate2, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc, recordID1);
        int goodQueryIdentifier = 9;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void updateBasicMakeBadExamRecordQuery1() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, badString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeHaitiRecordQuery(badString2, badInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, badInteger1);
        goodQueryMaker1.makeImageRecordQuery(badBitmap, badEncoding1);
        int goodQueryIdentifier = 10;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void UpdateBasicMakeGoodExamRecordQuery1() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean2, goodString4, personID4);
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);
        int goodQueryIdentifier = 10;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void UpdateBasicMakeBadExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, badString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeImageRecordQuery(badBitmap, badEncoding1);
        int goodQueryIdentifier = 11;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void UpdateBasicMakeGoodExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean2, goodString4, personID4);
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);
        int goodQueryIdentifier = 11;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeBadExamRecordQuery1() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeHaitiRecordQuery(badString2, badInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, badInteger1);
        goodQueryMaker1.makeImageRecordQuery(badBitmap, badEncoding1);
        int goodQueryIdentifier = 12;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void MakeGoodExamRecordQuery1() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);
        int goodQueryIdentifier = 12;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeBadExamRecordQuery2() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeImageRecordQuery(badBitmap, badEncoding1);
        int goodQueryIdentifier = 13;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void MakeGoodExamRecordQuery2() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);
        int goodQueryIdentifier = 13;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void UpdateBadBasicExamHaitiRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, badString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeHaitiRecordQuery(badString2, badInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, badInteger1);
        int goodQueryIdentifier = 14;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void UpdateGoodBasicExamHaitiRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean2, goodString4, personID4);
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        int goodQueryIdentifier = 14;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void UpdateBadBasicExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(badString2, badString2, badDate2, badString2, boolean1, badString2, personID2);
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        int goodQueryIdentifier = 15;

        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void UpdateGoodBasicExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.updateBasicQuery(goodString1, goodString2, goodDate2, goodString3, boolean2, goodString4, personID4);
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        int goodQueryIdentifier = 15;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeBadExamHaitiRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        goodQueryMaker1.makeHaitiRecordQuery(badString2, badInteger1, aBoolean1, aBoolean2, aBoolean3,
                aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, aBoolean3, aBoolean1, aBoolean2, badInteger1);
        int goodQueryIdentifier = 16;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void MakeGoodExamHaitiRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        int goodQueryIdentifier = 16;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeBadExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(badInteger1, badString2, badDate2, badInteger1, badFloat1,
                badInteger1, badInteger1, badInteger1, badInteger1, badFloat1, badString2,
                badString2, badString2, badString2, badString2, badString2, badString2,
                badString2, badString2, badString2, badString2, badString2);
        int goodQueryIdentifier = 17;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void MakeGoodExamRecordQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeExamRecordQuery(personID4, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        int goodQueryIdentifier = 17;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeGoodBothRecordsQuery4() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeBothRecordsQuery(goodString1, goodString2, goodDate1, goodString3, goodFalse,
                goodCurrentTriage, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);

        int goodQueryIdentifier = 18;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeGoodBothRecordsQuery3() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeBothRecordsQuery(goodString1, goodString2, goodDate1, goodString3, goodFalse,
                goodCurrentTriage, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeImageRecordQuery(goodBitmap, goodEncoding);

        int goodQueryIdentifier = 19;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }


    @Test
    public void MakeGoodBothRecordsQuery2() {
        assert false;
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeBothRecordsQuery(goodString1, goodString2, goodDate1, goodString3, goodFalse,
                goodCurrentTriage, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        goodQueryMaker1.makeHaitiRecordQuery(goodVillage, goodHemo, goodFalse, goodFalse, goodFalse, goodFalse,
                goodTrue, goodFalse, goodFalse, goodFalse, goodFalse, goodTrue, goodTrue, goodMaggi);
        int goodQueryIdentifier = 20;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void MakeGoodBothRecordsQuery() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.makeBothRecordsQuery(goodString1, goodString2, goodDate1, goodString3, goodFalse,
                goodCurrentTriage, goodString4, goodDate3, goodChartNum, goodBodyTemp, diastolicBP, systolicBP, height, weight, goodBMI,
                goodHistory, goodDiet, goodChiefC, goodHistoryIll, goodAssess, goodPhysical, goodTreat, goodDoctorF, goodDoctorL, goodPer, goodSig,
                goodLoc);
        int goodQueryIdentifier = 21;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

    @Test
    public void setBadLogin() {
        goodQueryMaker1 = new ClassQueryMakers();

        goodQueryMaker1.setLogin(badString2, badString2, badUsername, badPassword1, badString2, boolean1);
        int goodQueryIdentifier = 22;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 0);
    }

    @Test
    public void setGoodLogin() {
        goodQueryMaker1 = new ClassQueryMakers();
        goodQueryMaker1.setLogin(userName, passWord, goodUsername, goodPassword1, goodLocation, boolean1);
        int goodQueryIdentifier = 22;
        databaseHandler = new ClassDatabaseHandler(baseActivity, goodQueryMaker1, goodQueryIdentifier);
        try {
            databaseHandler.cThread.join();
        } catch (InterruptedException e) {
            assert false;
            e.printStackTrace();
        }
        assertEquals(databaseHandler.affected, 1);
    }

}