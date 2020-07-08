package com.example.medley.medicalrecord;

import java.io.BufferedReader;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.ArrayList;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

// Adapted from https://coderanch.com/t/591112/java/File-transfer-Client-Server-side
// and from https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
public class ClientForJavaSocket {
	public static final int BUFFER_SIZE = 100;

	public ClientForJavaSocket(ClassQueryMakers queryMaker) {
		/*
		 * Set up a key manager for client authentication if asked by the server. Use
		 * the implementation's default TrustStore and secureRandom routines.
		 */
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

		//SSLSocket socket = (SSLSocket) factory.createSocket("192.168.1.109", 5063);
		SSLSocket socket = null;
		try {
			socket = (SSLSocket) factory.createSocket("localhost", 5063);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Starting Handshake");
		try {
			socket.startHandshake();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Handshake Complete");
		// Change to port 5056 for testing, 5057 for deploying
		// Socket socket = new Socket("192.168.1.109", 5063);
		// Socket socket = new Socket("localhost", 5063);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
		OutputStream writer = null;
		try {
			writer = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected to socket");
		
		
		try {
			writer.write(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print(writer.toString());
		try {
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Data flushed");

		
		System.out.println("Waiting for Data");
		
		String socketResponse = null;
		while (true) {
			try {
				socketResponse = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (socketResponse != null) {
				break;
			}
		}
		System.out.println("Data received");
		System.out.println("Socket Response: " + socketResponse);
		// list.add(socketResponse);

		String facesInImageError = null;
		while (true) {
			try {
				facesInImageError = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (facesInImageError != null) {
				break;
			}
		}
		// String facesInImageErrorString = Integer.toString(facesInImageError);
		System.out.println("Data received");
		System.out.println("Number of faces = " + facesInImageError);
		// list.add(socketString);
		ArrayList<Integer> patientIds = new ArrayList<>();
		String matchingPatientIds = null;
		do {
			try {
				matchingPatientIds = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (matchingPatientIds != null && !matchingPatientIds.equals("\n") && !matchingPatientIds.equals("")
					&& !matchingPatientIds.equals("X")) {

				System.out.println("Previous matches: " + patientIds);
				patientIds.add(Integer.parseInt(matchingPatientIds));
				System.out.println("New Matches: " + patientIds);
			}

		} while (!matchingPatientIds.equals("X"));
		
		System.out.println("Data received");
		System.out.println("Total Matches: " + patientIds);
		
		ArrayList<Double> totalEncoding = new ArrayList();
		String encodingReturned = null;
		do {
			for (int count = 0; count < 128; count++) {
				while (true) {
					try {
						encodingReturned = reader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (encodingReturned != null && !encodingReturned.equals("\n") && !encodingReturned.equals("X")
							&& !encodingReturned.equals("")) {
						
						totalEncoding.add(Double.parseDouble(encodingReturned));
						
						break;
					}
				}
				

				if (encodingReturned.equals("0")) {
					System.out.println("tag 2");
					break;
				}

			}
			break;
		} while (!encodingReturned.equals("0"));
		if (encodingReturned.equals("\n")) {
			System.out.println("No Encoding");
		} else {
			System.out.println("Printing" + totalEncoding);
		}
		/*
		 * float[][] encoding = new float[32][4]; for (int column = 0; column < 32;
		 * column++) { for (int row = 0; row < 32; row++) { encodingReturned =
		 * reader.readLine(); if (encodingReturned != null) { Float[] floats =
		 * Arrays.stream(encodingReturned).map(Float::valueOf).toArray(Float[]::new);
		 * encoding[row][column] } } }
		 */
		// String encodingSizeString = Integer.toString(encodingSize);
		// System.out.println("Data received");
		// System.out.println("Encoding: " + String.valueOf(encodingReturned));
		// list.add(socketString);

		/*
		 * while (true) { try { Object socketResponse = reader.read(charBuffer); String
		 * socketString = (String) socketResponse; if (socketString != null) { if
		 * (!socketString.isEmpty()) { System.out.println("Data received");
		 * System.out.println(socketString); list.add(socketString); } } } catch
		 * (EOFException e) { break; }
		 * 
		 * }
		 */
		/*
		 * Iterator<String> listIterator = list.iterator(); while
		 * (listIterator.hasNext()) { System.out.println(listIterator.next()); }
		 */
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// fis.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class MyData {
		int pid;
		double[] myDoubleArray = new double[128];

		MyData() {
			for (int i = 0; i < 128; i++) {
				myDoubleArray[i] = -0.0961398780345916;
			}
		}
	}
}