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

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Implements SSL certificate validation, SSL socket creation, SSL handshaking,
 * socket operation, and socket closure.
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
abstract class ClassSocketHandler {

    /**
     * The port number for the socket.
     */
    private int portNumber;

    /**
     * The thread on which the socket operation is performed.
     */
    Thread cThread;

    /**
     * The status of the socket connection, true if connected.
     */
    boolean connected;

    /**
     * The activity from which the class is called, where Toast messages can be returned.
     */
    BaseActivityClass activity;

    /**
     * The socket over which socket operations are performed.
     */
    SSLSocket socket;

    /**
     * Starts a new client thread.
     */
    void startThread() {
        portNumber = getPortNumber();
        connected = true;
        cThread = new Thread(new ClientThread());
        cThread.start();
    }

    /**
     * Allows multiple sockets to be created over designated unique ports.
     *
     * @return Returns the unique port number designated for the specific socket operation.
     */
    abstract int getPortNumber();

    /**
     * Implements the running of a thread that validates a certificate, creates an SSL socket object, performs an SSL handshake,
     * and performs a socket operation, and closes the socket.
     */
    class ClientThread implements Runnable {

        /**
         * Generates a certificate factory.
         * Reads a certificate from a file.
         * Loads the certificate into teh certificate factory.
         * Creates a KeyStore object containing already trusted Certificate Authorities.
         * Adds the certificate to the keystore.
         * Creates a TrustManager object that trusts the existing Certificate Authorities.
         * Validates the certificate using the TrustManager.
         * Creates an SSLContext object using the TrustManager.
         * Creates an SSLSocket socket object from the SSLContext object using an IP address and port number.
         * Performs an SSL handshake over the socket connection.
         * Performs a socket operation.
         * Closes the socket.
         */
        public void run() {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                Log.d("Current Directory", System.getProperty("user.dir"));
                String path = ClassConstants.CERTIFICATE;
                Context appContext = ApplicationCustom.getAppContext();


                try (InputStream caInput = new BufferedInputStream((appContext.getAssets().open(path)))) {
                    Certificate ca;
                    ca = cf.generateCertificate(caInput);
                    Log.d("Tag 89", "ca=" + ((X509Certificate) ca).getSubjectDN());


                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);


                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);


                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);
                    SSLSocketFactory factory = context.getSocketFactory();
                    socket = (SSLSocket) factory.createSocket(ClassConstants.IP_ADDRESS, portNumber);
                    Log.d("Handshake", "Starting Handshake");
                    socket.startHandshake();
                    Log.d("Handshake", "Handshake Complete");
                    socketOperation();
                    socket.close();
                }
            } catch (ConnectException | NoRouteToHostException | UnknownHostException e) {
                Log.d("tag 111", "tag 119");
                connected = false;
                e.printStackTrace();
                activity.toastUiThread(activity.getString(R.string.databaseError));
            } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e2) {
                    connected = true;
                    e2.printStackTrace();
                }

            }

        }
    }

    /**
     * The actions performed over the socket connection between this client and the SSL socket server
     * to which it connects.
     *
     * @throws IOException if socket operation fails.
     */
    abstract void socketOperation() throws IOException;
}
