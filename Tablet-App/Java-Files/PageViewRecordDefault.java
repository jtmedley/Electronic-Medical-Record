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

import android.widget.EditText;

/**
 *
 */
public class PageViewRecordDefault extends BaseViewRecord {


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
    public void setLocationPage() {
        editTextTreatmentPlan.setText((String) fullResults.get(0).get("Treatment Plan"));
        editTextLocation.setText((String) fullResults.get(0).get("Location"));
    }

    /**
     *
     */
    @Override
    void setQueryId() {
    }

    /**
     *
     */
    @Override
    public void getLocationPage() {
    }

    /**
     *
     */
    @Override
    void completedToast() {
    }

    /**
     *
     */
    @Override
    void makeQuery() {
        queryMaker.selectExamRecordQuery(currentRecordId);
    }

    /**
     *
     */
    @Override
    void makeQuery2() {

    }
}