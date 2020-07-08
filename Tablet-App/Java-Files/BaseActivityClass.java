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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.pm.PackageManager.GET_META_DATA;
import static com.example.medley.medicalrecord.ApplicationCustom.getAppContext;

/**
 * The superclass from which all activities in the application are called.
 * Implements options menu methods,action bar methods, keyboard hiding methods, activity context methods,
 * translation setting methods, commonly use intent methods, and dialog creation methods.
 * <p>
 * Adapted from https://androidwave.com/android-multi-language-support-best-practices/
 */
public abstract class BaseActivityClass extends AppCompatActivity {
    /**
     * Creates the activity.
     * Locks the menu function to add users if the activity loaded is the login page.
     * Resets the titles a appropriate for translation purposes.
     * Sets the action bar for the activity.
     *
     * @param savedInstanceState Contains any information saved if the activity is temporarily closed and then restored.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preventAddUsersAtLogin();
        resetTitles();
        setActionBar();

    }

    /**
     * Invalidates the options menu at login to prevent the user from adding new logins before logging in themselves.
     */
    private void preventAddUsersAtLogin() {
        if (!ApplicationCustom.login) {
            invalidateOptionsMenu();
        }
    }

    /**
     * Sets the action bar for the activity.
     */
    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

        }
    }

    /**
     * Hides the keyboard if a motion event is recorded outside the range of the keyboard.
     *
     * @param ev The event to be examined.
     * @return The result of the method this method overrides.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null) {
            if ((ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                    v instanceof EditText &&
                    !v.getClass().getName().startsWith("android.webkit.")) {
                int screenCoordinates[] = new int[2];
                v.getLocationOnScreen(screenCoordinates);
                float x = ev.getRawX() + v.getLeft() - screenCoordinates[0];
                float y = ev.getRawY() + v.getTop() - screenCoordinates[1];

                if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                    hideKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Hides the keyboard in the activity in which the hide request is made.
     *
     * @param activity The activity containing the keyboard to be hidden.
     */
    private static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * Performs the operation requested by the selection of an option in the options menu.
     * These operations move the UI to another activity.
     *
     * @param item The option menu item selected
     * @return Returns true as necessary for overridden method.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            goBack();
        } else {
            if (id == R.id.action_new_patient) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new New Patient activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageNewPatient.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            } else if (id == R.id.action_search_patient) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Search Records activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageSearchRecords.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            } else if (id == R.id.action_search_result) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Welcome Page activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageWelcome.class);

                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            } else if (id == R.id.action_examination_record) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Search Patient activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageSearchPatients.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            } else if (id == R.id.action_translate) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Translate activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageTranslate.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);

            } else if (id == R.id.action_help) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Help activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageHelp.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            } else if (id == R.id.action_admin) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
                builder.setTitle(getString(R.string.leave));
                builder.setMessage(getString(R.string.sure_leave));
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /**
                     * Opens a new Admin activity.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getAppContext(), PageAdmin.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    /**
                     * Dismisses the dialog.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = builder.create();
                classDialogHelper(alertDialog);
            }
        }
        return true;
    }

    /**
     *
     */
    private void goBack() {
        finish();
    }

    /**
     * Inflates an options menu that can direct the user to a page of their choice.
     *
     * @param menu The menu object be inflated using the specified menu setting
     * @return Returns true as necessary for overridden method.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        MenuItem admin = menu.findItem(R.id.action_admin);
        if (!ApplicationCustom.login) {
            admin.setVisible(false);
            admin.setEnabled(false);
        }
        return true;
    }

    /**
     * @param toastMessage
     */
    public void toastUiThread(final String toastMessage) {
        final Thread toastThread = new Thread() {
            public void run() {
                Toast.makeText(getAppContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
        };
        runOnUiThread(toastThread);
    }

    /**
     * Creates an alert dialog with generalized settings.
     *
     * @param alertDialog the alert dialog object to be displayed
     */
    void classDialogHelper(final AlertDialog alertDialog) {
        new AlertDialog.Builder(this, R.style.DialogTheme);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                Button buttonNegative = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
                buttonPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f);
                buttonNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f);
            }
        });
        alertDialog.show();
    }

    /**
     * Gets the context of the current activity.
     *
     * @return Returns the context of this activity.
     */
    Context getActivityContext() {
        return this;
    }

    /**
     * Attaches the base context to the application for translation setting purposes.
     *
     * @param base The context of the activity.
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ClassLocaleManager.setLocale(base));
    }

    /**
     * Resets any title settings to the locale in translation operations.
     */
    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the page using updated translation settings if the app closes
     * and a flag indicates that translation settings have been changed.
     * Resets the finish flag.
     */
    @Override
    protected void onRestart() {
        Log.d("Finish Flag2", Boolean.toString(ApplicationCustom.finishFlag));
        if (ApplicationCustom.finishFlag) {
            ApplicationCustom.finishFlag = false;
            recreate();
        }
        super.onRestart();

    }

    /**
     * Sets an intent object for the activity for creating records specified by the location setting.
     *
     * @return the intent for the new activity.
     */
    Intent startNew() {
        Intent intent;

        switch (ApplicationCustom.locationString) {
            case ClassConstants.EXAM_STRING_DEFAULT:
                intent = new Intent(getAppContext(), PageCreateRecordDefault.class);
                break;
            case ClassConstants.EXAM_STRING_HAITI:
                intent = new Intent(getAppContext(), PageCreateRecordHaiti.class);
                break;
            default:
                intent = new Intent(getAppContext(), PageCreateRecordDefault.class);
                break;
        }
        return intent;
    }

    /**
     * Sets an intent object for the activity for updating records specified by the location setting.
     *
     * @return the intent for the new activity.
     */
    Intent startUpdate() {
        Intent intent = null;
        if (ApplicationCustom.locationString.equals(ClassConstants.EXAM_STRING_DEFAULT)) {
            intent = new Intent(getAppContext(), PageUpdateRecordDefault.class);
        } else if (ApplicationCustom.locationString.equals(ClassConstants.EXAM_STRING_HAITI)) {
            intent = new Intent(getAppContext(), PageUpdateRecordHaiti.class);
        }
        return intent;
    }

    /**
     * Sets an intent object for the activity for viewing records specified by the location setting.
     *
     * @return the intent for the new activity.
     */
    Intent startView() {
        Intent intent = null;
        if (ApplicationCustom.locationString.equals(ClassConstants.EXAM_STRING_DEFAULT)) {
            intent = new Intent(getAppContext(), PageViewRecordDefault.class);
        } else if (ApplicationCustom.locationString.equals(ClassConstants.EXAM_STRING_HAITI)) {
            intent = new Intent(getAppContext(), PageViewHaitiRecord.class);
        }
        return intent;
    }

    /**
     * Sets an alert dialog for the user to confirm the decision to finish an activity and go back to the
     * previous activity.
     */
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getString(R.string.back));
        builder.setMessage(getString(R.string.sure));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog alertDialog = builder.create();
        classDialogHelper(alertDialog);
    }
}


