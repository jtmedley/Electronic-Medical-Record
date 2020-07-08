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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Connect to a secure socket, send an image, receive a facial recognition encoding to store later
 * and any matching patient id numbers to complement search functions
 * <p>
 * Adapted from " seotoolzz"
 * http://seotoolzz.com/android/upload-image-from-android-to-mssql-server.php
 * and from "jaywalker"
 * https://stackoverflow.com/questions/16602736/android-send-an-image-through-socket-programming
 * and from "Lukas Knuth"
 * https://stackoverflow.com/questions/18282497/android-how-to-sending-pictures-via-socket-correctly
 * and from "Aurand"
 * https://stackoverflow.com/questions/16312269/saving-a-jpg-to-byte-android
 */

class ClassImageHandler extends ClassSocketHandler {
    int facesInImageError = -1;
    private byte[] imgByte;
    ArrayList<Integer> patientIds;
    double[] currentEncoding;
    private final boolean optOutFlag;

    /**
     * Open an image file, write its contents to a byte array to be sent over a socket
     *
     * @param currentPhotoPath A file path to the image to be sent
     */
    ClassImageHandler(boolean optOutFlag, BaseActivityClass activity, String currentPhotoPath) {
        this.activity = activity;
        this.optOutFlag = optOutFlag;
        File imageFile = new File(currentPhotoPath);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            int fileLength = (int) imageFile.length();
            Log.d("Tag 56", NumberFormat.getNumberInstance(ApplicationCustom.getLocale()).format(fileLength));
            imgByte = new byte[fileLength];
            Log.d("Tag57", String.valueOf(imgByte.length));
            int readLength = fis.read(imgByte, 0, imgByte.length);
            Log.d("Tag57", String.valueOf(readLength));
            Log.d("Tag55", Arrays.toString(imgByte));
            startThread();
        } catch (FileNotFoundException e) {
            connected = false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    void socketOperation() {
        try {

            activity.toastUiThread(activity.getString(R.string.detecting_faces));

            Log.d("ClientActivity", "C: Sending command.");
            byte[] size = ByteBuffer.allocate(4).putInt(imgByte.length).array();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (socket.getInputStream()));
            byte optOutByte = 0;
            if (optOutFlag) {
                optOutByte = 1;
            }
            Log.d("Opt out byte", Byte.toString(optOutByte));
            output.write(optOutByte);
            output.write(size);
            output.write(imgByte);
            Log.d("Currently sending bytes", Arrays.toString(imgByte));
            Log.d("ClientActivity", "C: image writing.");
            output.flush();
            Log.d("ClientActivity", "C: Sent.");
            Log.d("Tag 89", "Waiting for Data");
            String socketResponse;
            do {
                socketResponse = reader.readLine();
            } while (socketResponse == null);
            Log.d("Tag 89", "Data received");
            Log.d("Tag 89", "Socket Response: " + socketResponse);
            String facesImageErrorStr;
            do {
                facesImageErrorStr = reader.readLine();
            } while (facesImageErrorStr == null);
            facesInImageError = Integer.parseInt(facesImageErrorStr);
            Log.d("Tag 89", "Data received");
            Log.d("Tag 89", "Number of faces = " + facesInImageError);

            if (facesInImageError == 1) {
                activity.toastUiThread(activity.getString(R.string.face_detected));
            } else if (facesInImageError == 2) {
                activity.toastUiThread(activity.getString(R.string.multiple_detected));
            } else if (facesInImageError == 0) {
                activity.toastUiThread(activity.getString(R.string.no_detected));
            } else {
                activity.toastUiThread(activity.getString(R.string.photo_error));

            }
            String matchingPatientIds;
            patientIds = new ArrayList<>();
            if (!optOutFlag && facesInImageError == 1) {
                activity.toastUiThread(activity.getString(R.string.match_search));
            }
            do {
                matchingPatientIds = reader.readLine();
                if (matchingPatientIds != null && !matchingPatientIds.equals("\n")
                        && !matchingPatientIds.equals("")
                        && !matchingPatientIds.equals("X")) {
                    patientIds.add(Integer.parseInt(matchingPatientIds));
                }
            } while (!Objects.equals(matchingPatientIds, "X"));
            Log.d("Data received", "Matches: " + patientIds);
            if (facesInImageError == 1 && !optOutFlag && !patientIds.isEmpty()) {
                activity.toastUiThread(activity.getString(R.string.matched_success));
            }
            String encodingReturned = null;
            if (!optOutFlag) {
                currentEncoding = new double[128];
            }
            for (int count = 0; count < 128; count++) {
                while (true) {
                    encodingReturned = reader.readLine();
                    Log.d("Encoding returned", encodingReturned);
                    if (encodingReturned != null && !encodingReturned.equals("\n")
                            && !encodingReturned.equals("X")
                            && !encodingReturned.equals("")) {
                        if (!optOutFlag) {
                            currentEncoding[count] = Double
                                    .parseDouble(encodingReturned);
                        }
                        break;

                    }
                }
                if (encodingReturned.equals("0")) {
                    Log.d("Tag 89", "tag 2");
                    break;
                }
            }
            if (encodingReturned.equals("\n")) {
                Log.d("tag62", "No Encoding");
            } else {
                Log.d("tag63", Arrays.toString(currentEncoding));
            }
            output.close();
            reader.close();
            socket.close();
            Log.d("ClientActivity", "C: Closed.");
        } catch (
                SocketException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Log.d("ClientActivity", "C: Closed.");
        } catch (
                Exception e) {
            Log.e("ClientActivity", "C: Error", e);
            e.printStackTrace();
            activity.toastUiThread(
                    activity.getString(R.string.databaseError));
        }

    }

    /**
     *
     * @return
     */
    @Override
    int getPortNumber() {
        return ClassConstants.IMAGE_SOCKET_PORT_NUMBER;
    }
}
