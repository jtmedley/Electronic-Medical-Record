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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Adapted from "hello2pal"
 * https://gist.github.com/hello2pal/4548094
 * and from https://developer.android.com/training/camera/photobasics#java
 * and from "Illegal Argument"
 * https://stackoverflow.com/questions/4352172/how-do-you-pass-images-bitmaps-between-android-activities-using-bundles/7890405#7890405
 * and from "jay tandel"
 * https://stackoverflow.com/questions/39803060/android-checkbox-show-and-hide
 * and from "Aryan"
 * https://stackoverflow.com/questions/9015372/how-to-rotate-a-bitmap-90-degrees
 */
public class PageNewPatient extends BaseActivityClass {
    /**
     *
     */
    private EditText editTextFirstName;

    /**
     *
     */
    private EditText editTextLastName;


    /**
     *
     */
    private EditText editTextSex;

    /**
     *
     */
    private CheckBox checkBoxDOB;

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
    private ImageView imageView;

    /**
     *
     */
    private ClassImageHandler imageHandler;

    /**
     *
     */
    private Button optOutButton;

    /**
     *
     */
    private TextView optOutText;

    /**
     *
     */
    private boolean imageFlag = false;

    /**
     *
     */
    private String currentPhotoPath;

    /**
     *
     */
    private static final int REQUEST_TAKE_PHOTO = 1313;

    /**
     *
     */
    private boolean optOutFlag = false;

    /**
     *
     */
    private final String fileName = "newImage";

    /**
     *
     */
    private RadioButton maleSex;

    /**
     *
     */
    private RadioButton femaleSex;

    /**
     *
     */
    private RadioButton otherSex;

    /**
     *
     */
    private DatePicker datePicker;
    /**
     *
     */
    private final View.OnClickListener checkBoxDOBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkBoxDOB.isChecked()) {
                datePicker.setVisibility(View.VISIBLE);
            } else {
                datePicker.setVisibility(View.INVISIBLE);

            }
        }
    };

    /**
     *
     */
    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        /**
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            boolean selectedSearch = false;
            if (view == findViewById(R.id.SearchNew)) {
                selectedSearch = true;
                Log.d("Tag 88", "1");
            } else {
                Log.d("Tag 88", "0");
            }
            newPatientFirstName = editTextFirstName.getText().toString();
            newPatientLastName = editTextLastName.getText().toString();
            if (checkBoxDOB.isChecked()) {
                int dayDOB = datePicker.getDayOfMonth();
                int monthDOB = 1 + datePicker.getMonth();
                int yearDOB = datePicker.getYear();
                newPatientDOB = LocalDate.of(yearDOB, monthDOB, dayDOB);
            } else {
                newPatientDOB = null;
            }
            if (maleSex.isChecked()) {
                newPatientSex = "Male";
            } else if (femaleSex.isChecked()) {
                newPatientSex = "Female";
            } else if (otherSex.isChecked()) {
                newPatientSex = editTextSex.getText().toString();
            }
            Log.d("Input:", newPatientFirstName + " " + newPatientLastName + " " +
                    newPatientDOB + " " + newPatientSex);
            try {
                if (imageHandler != null) {
                    if (imageHandler.cThread.isAlive()) {
                        imageHandler.cThread.join();
                    }
                }
            } catch (InterruptedException e) {
                Toast.makeText(ApplicationCustom.getAppContext(),
                        getString(R.string.another_or_continue_without), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            if (imageHandler != null) {
                if (imageHandler.facesInImageError == 1 || optOutFlag) {
                    selectButton(selectedSearch);
                } else {
                    toastUiThread(getString(R.string.take_another_not_detected));
                }
            } else {
                selectButton(selectedSearch);

            }
        }
    };


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String directory = getFilesDir().getAbsolutePath();
        File byteFile = new File(directory, fileName);
        if (byteFile.exists()) {
            boolean deleted = byteFile.delete();
            if (deleted) {
                Log.d("tag61", "Deleted previously written image");
            } else {
                Log.d("tag61", "Did not delete previously written image");
            }
        }
        setContentView(R.layout.activity_new_patient);
        imageView = findViewById(R.id.imageView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);

        editTextSex = findViewById(R.id.editTextSex);
        editTextSex.setVisibility(View.INVISIBLE);
        Button createButton = findViewById(R.id.CreateNew);
        Button searchButton = findViewById(R.id.SearchNew);
        checkBoxDOB = findViewById(R.id.checkBoxDOB);
        datePicker = this.findViewById(R.id.DatePicker);
        datePicker.init(2000, 0, 1, null);
        checkBoxDOB.setChecked(false);
        datePicker.setVisibility(View.INVISIBLE);
        createButton.setOnClickListener(buttonListener);
        searchButton.setOnClickListener(buttonListener);
        ImageButton cameraButton = findViewById(R.id.cameraButton);
        Button takePictureButton = findViewById(R.id.takePictureButton);
        optOutButton = findViewById(R.id.OptOut);
        optOutText = findViewById(R.id.optOutText);
        String optOutString = getString(R.string.picture_will) + "\n" + getString(R.string.be_used);
        optOutText.setText(optOutString);
        optOutButton.setText(getString(R.string.disable));
        cameraButton.setOnClickListener(new TakePhotoClicker());
        takePictureButton.setOnClickListener(new TakePhotoClicker());
        maleSex = findViewById(R.id.Male);
        femaleSex = findViewById(R.id.Female);
        otherSex = findViewById(R.id.Other);
        femaleSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPatientSex = "Male";
                editTextSex.setVisibility(View.INVISIBLE);
            }
        });
        maleSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSex.setVisibility(View.INVISIBLE);
                newPatientSex = "Female";
            }
        });
        otherSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSex.setVisibility(View.VISIBLE);
                newPatientSex = editTextSex.getText().toString();
            }
        });
        checkBoxDOB.setOnClickListener(checkBoxDOBListener);
    }

    /**
     *
     * @param selected
     */
    private void selectButton(boolean selected) {
        if (selected) {
            Log.d("Click result", "Creating search dialog");
            searchDialog();
        } else {
            Log.d("Click result", "Creating search dialog");
            createDialog();
        }
    }

    /**
     *
     */
    private void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
        builder.setCancelable(true);
        String addString = "";
        builder.setTitle(getString(R.string.clicked_create));
        if (!imageFlag) {
            addString = getString(R.string.not_yet_taken);
        } else if (optOutFlag) {
            addString += getString(R.string.would_not_be_used);
        } else {
            addString += getString(R.string.would_be_used);
        }
        builder.setMessage(getString(R.string.might_exist_message) + addString);
        builder.setPositiveButton(getString(R.string.check), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ApplicationCustom.getAppContext(), PageSearchPatients.class);
                searchOrCreatePatient(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.skip), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getString(R.string.making_new), Toast.LENGTH_SHORT).show();
                Intent intent = startNew();
                intent.putExtra("ActionFlag", ClassConstants.ACTION_FLAG_MAKE_BOTH);
                intent.putExtra("imageFlag", imageFlag);
                if (imageFlag && !optOutFlag) {

                    intent.putExtra("currentEncoding", imageHandler.currentEncoding);
                }
                intent.putExtra("foundFirstName", newPatientFirstName);
                intent.putExtra("foundLastName", newPatientLastName);
                intent.putExtra("foundSex", newPatientSex);
                if (newPatientDOB != null) {
                    intent.putExtra("yearDOB", newPatientDOB.getYear());
                    intent.putExtra("monthDOB", newPatientDOB.getMonthValue());
                    intent.putExtra("dayDOB", newPatientDOB.getDayOfMonth());
                }
                startActivity(intent);
            }
        });
        final AlertDialog alertDialog = builder.create();
        classDialogHelper(alertDialog);

    }

    /**
     *
     */
    private void searchDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityContext(),R.style.DialogTheme);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.search_for_existing));
        if (imageFlag) {
            if (optOutFlag) {
                builder.setMessage(getString(R.string.search_will_not_use));
            } else {
                builder.setMessage(getString(R.string.search_will_use));
            }
        } else {
            builder.setMessage(getString(R.string.sure_search_no_photo));
        }
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ApplicationCustom.getAppContext(), PageSearchPatients.class);
                searchOrCreatePatient(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getString(R.string.against_search), Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog alertDialog = builder.create();
        classDialogHelper(alertDialog);

    }

    /**
     *
     * @param intent
     */
    private void searchOrCreatePatient(Intent intent) {
        intent.putExtra("imageFlag", imageFlag);
        intent.putExtra("optOutFlag", optOutFlag);
        if (imageFlag && !optOutFlag) {
            intent.putExtra("currentEncoding", imageHandler.currentEncoding);
            intent.putExtra("faceMatchedPatientIds", imageHandler.patientIds);
        }
        intent.putExtra("newPatientFirstName", newPatientFirstName);
        intent.putExtra("newPatientLastName", newPatientLastName);
        intent.putExtra("newPatientSex", newPatientSex);
        if (newPatientDOB != null) {
            intent.putExtra("yearDOB", newPatientDOB.getYear());
            intent.putExtra("monthDOB", newPatientDOB.getMonthValue());
            intent.putExtra("dayDOB", newPatientDOB.getDayOfMonth());
        }
        this.startActivity(intent);
    }

    /**
     *
     */
    class TakePhotoClicker implements ImageButton.OnClickListener {
        /**
         * @param view
         */
        @Override
        public void onClick(View view) {

            Log.d("tag27", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(view.getId()));
            dispatchTakePictureIntent();
            if (optOutFlag) {
                Toast.makeText(ApplicationCustom.getAppContext(),
                        "You have opted out of photograph features. " +
                                "Opt in to resume using photograph features.",
                        Toast.LENGTH_LONG).show();

            }
        }

    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            imageFlag = true;
            try {
                File file = new File(currentPhotoPath);
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), Uri.fromFile(file));
                Log.d("Image Height", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(bitmap.getHeight()));
                Log.d("Image Width", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(bitmap.getWidth()));
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
                fo.write(bytes.toByteArray());
                bytes.close();
                fo.close();
                Log.d("Test tag", "Writing Image");
                imageHandler = new ClassImageHandler(optOutFlag, this, currentPhotoPath);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
                Toast.makeText(ApplicationCustom.getAppContext(),
                        getString(R.string.not_taken), Toast.LENGTH_LONG).show();
            }
        }
    }



    /**
     * @param v
     */
    public void optOutButtonClick(View v) {
        if (!optOutFlag) {
            optOutFlag = true;
            String displayString = getString(R.string.will) + "\n" + getString(R.string.not_used);
            optOutText.setText(displayString);
            optOutButton.setText(getString(R.string.enable));

            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.opt_out_of), Toast.LENGTH_LONG).show();
        } else {
            optOutFlag = false;
            String displayString = getString(R.string.will) + "\n" + getString(R.string.not_used);
            Toast.makeText(ApplicationCustom.getAppContext(),
                    getString(R.string.opt_into), Toast.LENGTH_LONG).show();
            optOutText.setText(displayString);
            optOutButton.setText(getString(R.string.disable));
        }
    }

    /**
     *
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d("tag30", "1");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("tag31", "1");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("tag32", "1");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.medley.medicalrecord.fileprovider",
                        photoFile);
                Log.d("tag33", photoURI.toString());
                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        DateFormat dateFormat = SimpleDateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String timeStamp = dateFormat.format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}