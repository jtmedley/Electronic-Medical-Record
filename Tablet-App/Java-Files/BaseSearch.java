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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * and from "Balaji"
 * https://stackoverflow.com/questions/4394293/create-a-new-textview-programmatically-then-display-it-below-another-textview
 * and from "Robby Pond"
 * https://stackoverflow.com/questions/5255184/android-and-setting-width-and-height-programmatically-in-dp-units
 */
public abstract class BaseSearch extends BaseActivityClass {

    /**
     *
     */
    ArrayList<HashMap<String, Object>> searchResults;

    /**
     *
     */
    int displayCount = 0;

    /**
     *
     */
    ClassDatabaseHandler connServer;

    /**
     *
     */
    ProgressBar progressBar;

    /**
     *
     */
    TableLayout searchTable;

    /**
     *
     */
    ClassQueryMakers queryMaker;

    /**
     *
     */
    boolean imageFlag;

    /**
     *
     */
    String foundFirstName;

    /**
     *
     */
    String foundLastName;

    /**
     *
     */
    String foundSex;

    /**
     *
     */
    LocalDate foundDOB;

    /**
     *
     */
    boolean foundDischarged;

    /**
     *
     */
    int currentPersonId;

    /**
     *
     */
    double[] currentEncoding;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchTable = findViewById(R.id.searchTable);
        progressBar = findViewById(R.id.progressBar);
        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(makeCreateButtonListener());
        getResults.start();
    }

    /**
     * @return
     */
    abstract View.OnClickListener makeCreateButtonListener();

    /**
     *
     */
    abstract void makeQuery();

    /**
     *
     */
    private final Thread getResults = new Thread() {
        @Override
        public void run() {
            makeQuery();
            if (connServer != null) {
                while (connServer.cThread.isAlive()) {
                    if (connServer != null) {
                        if (connServer.totalSize > 0) {
                            double progressAmount = connServer.results.size();
                            double progressTotal = connServer.totalSize;
                            if (progressTotal != 0) {
                                int progressSetting = (int) (progressAmount / progressTotal * 100);
                                Log.d("Progress", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(progressSetting));
                                progressBar.setProgress(progressSetting);
                            }
                        }
                    }
                }
            }
            handleResults();
        }
    };

    /**
     *
     */
    void handleResults() {
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

    /**
     *
     */
    abstract void checkResults();

    /**
     * @param
     */
    void displayResults() {

        if (searchResults.size() == 0) {
            Toast.makeText(ApplicationCustom.getAppContext(), getString(R.string.no_results), Toast.LENGTH_LONG).show();
        } else {
            displayCount = 0;
            while (displayCount < searchResults.size()) {
                putPatient();
                displayCount++;
            }
        }
    }

    /**
     *
     */
    abstract void putPatient();

    /**
     * @return
     */
    ImageView setImage() {
        final ImageView imageView = new ImageView(ApplicationCustom.getAppContext());
        TableRow.LayoutParams imageParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
        imageParams.rightMargin = 50;
        byte[] bytes = (byte[]) searchResults.get(displayCount).get("ImageBitmap");
        Bitmap bitmap = null;
        if (bytes != null) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 537, false);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    /**
     * @return
     */
    TextView setRowText() {
        TextView rowTextView = new TextView(getActivityContext());
        final float scale = ApplicationCustom.getAppContext().getResources().getDisplayMetrics().density;
        int textWidthSetting = (int) (550 * scale + 0.5f);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams
                (textWidthSetting,
                        TableRow.LayoutParams.WRAP_CONTENT);
        rowParams.leftMargin = 40;

        rowTextView.setTextSize((float) 30);
        rowTextView.setPadding(20, 20, 20, 0);
        rowTextView.setLayoutParams(rowParams);
        String rowDisplay = getRowDisplay();
        rowTextView.setText(rowDisplay);
        return rowTextView;
    }

    /**
     * @return
     */
    abstract String getRowDisplay();


}