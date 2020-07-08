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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Connect to a secure socket, send an image, receive a facial recognition
 * encoding to store later and any matching patient id numbers to complement
 * search functions
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

class ClientForJavaSocket2 {
	ClassQueryMakers queryMaker;
	int queryIdentifier;
	Thread cThread;
	private boolean connected = false;
	ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
	int affected;
	/**
	 * Open an image file, write its contents to a byte array to be sent over a
	 * socket
	 *
	 * @param currentPhotoPath A file path to the image to be sent
	 */
	ClientForJavaSocket2(ClassQueryMakers queryMaker, int queryIdentifier) {
		this.queryMaker = queryMaker;
		this.queryIdentifier = queryIdentifier;
		startThread();
	}

	/**
	 * Starts a new thread to send data and receive a response
	 */
	private void startThread() {
		if (!connected) {
			cThread = new Thread(new ClientThread());
			cThread.start();
		}
	}

	/**
	 * Runs a thread for socket connection when the image is ready to be sent
	 */
	public class ClientThread implements Runnable {
		/**
		 * Connects to a new socket Writes the size of the image, then the byte array of
		 * the image, to the socket server Waits for data to be received Receives a
		 * confirmation of image processing, a logging error message, an array of any
		 * matching patient ids, and an array of the encoding for the image sent Closes
		 * the socket is the server does not close it first
		 */
		public void run() {

			SSLSocketFactory factory = null;
			try {
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				char[] passphrase = "BMECapstone2019".toCharArray();
				System.setProperty("javax.net.ssl.keyStore", "C:\\Cert\\certs.jks");

				System.setProperty("javax.net.ssl.trustStore", "C:\\Cert\\certs.jks");
				ctx = SSLContext.getInstance("TLS");
				kmf = KeyManagerFactory.getInstance("SunX509");
				ks = KeyStore.getInstance("JKS");

				ks.load(new FileInputStream("C:\\Cert\\certs.jks"), passphrase);

				kmf.init(ks, passphrase);
				ctx.init(kmf.getKeyManagers(), null, null);

				factory = ctx.getSocketFactory();
			} catch (Exception e) {
				e.printStackTrace();

			}

			/*
			 * This example demonstrates how to use a SSLSocket as client to send a HTTP
			 * request and get response from an HTTPS server. It assumes that the client is
			 * not behind a firewall
			 */

			// SSLSocket socket = (SSLSocket) factory.createSocket("192.168.1.109", 5063);
			SSLSocket socket = null;
			try {
				socket = (SSLSocket) factory.createSocket(ClassConstants.IP_ADDRESS, ClassConstants.socketPortNumber);

				System.out.println("Starting Handshake");
				try {
					socket.startHandshake();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Handshake Complete");
			ObjectOutputStream output = null;
			try {
				final ArrayList<Object> parameterArray = queryMaker.parameterArray;
				final ArrayList<Integer> paramTypeArray = queryMaker.paramTypeArray;
				
				output = new ObjectOutputStream(socket.getOutputStream());
				InputStream inputStream = socket.getInputStream(); // InputStream from where to receive the map,
																	// in case of network you get it from the
																	// Socket instance.
				final ObjectInputStream mapInputStream = new ObjectInputStream(inputStream);
				System.out.println("Sending " + Integer.toString(queryIdentifier));
				output.writeObject(queryIdentifier);
				Iterator<Object> parameterIterator = parameterArray.iterator();
				int printCount = 0;
				while (parameterIterator.hasNext()) {
					Object nextObject = parameterIterator.next();
					output.writeObject(nextObject);
					printCount++;
					System.out.println("Sending Object" + Integer.toString(printCount));
				}
				String breakpointString = "breakpoint";
				output.writeObject(breakpointString);
				System.out.println("Sending " + breakpointString);
				Iterator<Integer> paramTypeIterator = paramTypeArray.iterator();
				while (paramTypeIterator.hasNext()) {
					Integer nextType = paramTypeIterator.next();
					output.writeObject(nextType);
					System.out.println("Sending " + Integer.toString(nextType));
				}
				output.writeObject(999);
				if (queryIdentifier < 6) {
					Object sizeResult;
					try {
						sizeResult = mapInputStream.readObject();

						if (sizeResult instanceof Integer) {
							int size = (int) sizeResult;
							System.out.println("Receiving Size: " + Integer.toString(size));
							int count = 0;
							while (count < size) {
								try {
									Object received = mapInputStream.readObject();
									if (received instanceof HashMap) {
										final HashMap<String, Object> result = (HashMap<String, Object>) received;
										results.add(result);
									} else {
										System.out.println("No Results");
									}
									count++;
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							// Object received = mapInputStream.readObject();
							System.out.println("Final Size:" + results.size());
						} else {
							System.out.println("Terminating Thread");
							output.close();
						}

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						Object affectedResult = mapInputStream.readObject();

						if (affectedResult instanceof Integer) {
							affected = (int) affectedResult;
							System.out.println("Rows affected: " + Integer.toString(affected));
							
							
						} else {
							System.out.println("Terminating Thread");
							output.close();
						}

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				try {
					output.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	/*
	 * 
	 * protected void onStop() { super.onStop(); try { socket.close(); connected =
	 * false; } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * 
	 */
}
