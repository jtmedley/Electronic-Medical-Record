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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements SSL socket operations that send a request for a particular SQL query to be performed
 * and the parameters to be added to that query, and operations that read the results of that query.
 * <p>
 * Adapted from "seotoolzz"
 * http://seotoolzz.com/android/upload-image-from-android-to-mssql-server.php
 */
class ClassDatabaseHandler extends ClassSocketHandler {

    /**
     * The query maker where SQL parameters and parameter types are stored.
     */
    private ClassQueryMakers queryMaker;

    /**
     * The identifier indicating which query is associated with the values stored in the query maker.
     * These identifiers are sorted by type.
     * Queries which return results, like searches, have query identifiers less than 6.
     * Queries which do not return results, or "non-queries," have query identifiers equal to or greater than 6.
     */
    private int queryIdentifier;

    /**
     * The list of results returned from the server after a query has been executed.
     * Each list object contains one row of results.
     * The results are stored as HashMap objects.
     * The HashMap key for each result is a string containing the field names of the query result.
     * The HashMap value for each result is a SQL type object containing the value of the field in that row.
     */
    final ArrayList<HashMap<String, Object>> results = new ArrayList<>();

    /**
     * The number of affected results returned by the server after a non-query has been executed.
     */
    int affected;

    /**
     * The total size of the results object indicating the number of rows returned from a query.
     */
    int totalSize;

    /**
     * Constructor.
     * Checks for an appropriate query identifier
     * Checks that the lists of parameters and parameter types have been populated.
     * Starts a socket handler thread to perform the socket operation.
     * Toasts the activity with an error message if the query identifier is inappropriate.
     * Toasts the activity with an error message if either the list of parameters or the
     * list of parameter types is empty.
     *
     * @param activity        The activity from which the class is called, where Toast messages can be returned.
     * @param queryMaker      The query maker where SQL parameters and parameter types are stored.
     * @param queryIdentifier The identifier indicating which query is associated with the values stored in the query maker.
     * @see ClassSocketHandler
     */
    ClassDatabaseHandler(BaseActivityClass activity, ClassQueryMakers queryMaker, int queryIdentifier) {
        if (queryMaker != null && queryIdentifier > -2 && queryIdentifier < 23) {
            if (!queryMaker.parameterArray.isEmpty() && !queryMaker.paramTypeArray.isEmpty()) {
                this.activity = activity;
                this.queryMaker = queryMaker;
                this.queryIdentifier = queryIdentifier;
                Log.d("Query Tag 1", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(queryIdentifier));
                startThread();
            } else {
                activity.toastUiThread(activity.getString(R.string.databaseError));
            }
        } else {
            activity.toastUiThread(activity.getString(R.string.databaseError));
        }
    }

    /**
     * Allows multiple sockets to be created over designated unique ports.
     *
     * @return Returns the unique port number designated for connections to the database server.
     * <p>
     */
    @Override
    int getPortNumber() {
        return ClassConstants.DATABASE_SOCKET_PORT_NUMBER;
    }

    /**
     * Writes the query identifier, the parameter array, a breakpoint object,
     * and the parameter type array over the socket connection.
     * Checks that the result being received is a HashMap and
     * reads each results into an ArrayList of HashMaps if the query identifier
     * indicates the query should return results.
     * Reads the number of rows affected if the query identifier indicates that
     * the query should not return results.
     *
     * @throws IOException if the input or output operations on the socket fail.
     */
    @Override
    void socketOperation() throws IOException {
        final ArrayList<Object> parameterArray = queryMaker.parameterArray;
        final ArrayList<Integer> paramTypeArray = queryMaker.paramTypeArray;
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        InputStream socketInputStream = socket.getInputStream();
        final ObjectInputStream input = new ObjectInputStream(socketInputStream);
        Log.d("Tag 89", "Sending Query " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(queryIdentifier));
        output.writeObject(queryIdentifier);
        for (Object nextObject : parameterArray) {
            output.writeObject(nextObject);
            if (nextObject == null) {
                Log.d("Tag 89", "Sending Object null");
            } else {
                Log.d("Tag 89", "Sending Object " + nextObject.toString());
            }
        }
        String breakpointString = "breakpoint";
        output.writeObject(breakpointString);
        Log.d("Tag 89", "Sending " + breakpointString);
        for (Integer nextType : paramTypeArray) {
            output.writeObject(nextType);
            Log.d("Tag 89", "Sending " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(nextType));
        }
        output.writeObject(999);
        if (queryIdentifier < 6) {
            Object sizeResult;
            try {
                sizeResult = input.readObject();
                if (sizeResult instanceof Integer) {
                    totalSize = (int) sizeResult;
                    Log.d("Tag 104", "Receiving Size: " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(totalSize));
                    int count = 0;
                    while (count < totalSize) {
                        try {
                            Object received = input.readObject();
                            if (received instanceof HashMap) {
                                @SuppressWarnings("unchecked") final HashMap<String, Object> result = (HashMap<String, Object>) received;
                                results.add(result);
                            } else {
                                Log.d("Tag 89", "No Results");
                            }
                            count++;
                        } catch (ClassNotFoundException e) {
                            Log.d("tag 111", "tag 116");
                            e.printStackTrace();
                        }
                    }
                    Log.d("Tag 103", "Final Size:" + results.size());
                } else {
                    Log.d("Tag 102", "Terminating Thread");
                    output.close();
                }
            } catch (ClassNotFoundException e1) {
                Log.d("tag 111", "tag 117");
                connected = false;
                e1.printStackTrace();
            }
        } else {
            try {
                Object affectedResult = input.readObject();
                if (affectedResult instanceof Integer) {
                    affected = (int) affectedResult;
                    Log.d("Tag 101", "Rows affected: " + NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(affected));
                } else {
                    Log.d("Tag 100", "Terminating Thread");
                    output.close();
                }
            } catch (ClassNotFoundException e1) {
                connected = false;
                Log.d("tag 111", "tag 118");
                e1.printStackTrace();
            }
        }
        socket.close();
    }

}
