package com.example.medley.medicalrecord;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.ConnectException;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import java.security.cert.CertificateException;

// Adapted from https://coderanch.com/t/591112/java/File-transfer-Client-Server-side
// and from https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
public class TestClientForPythonSocket {
	public static final int BUFFER_SIZE = 100;

	public static void main(String[] args) throws Exception {
		try {

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
				throw new IOException(e.getMessage());
			}

			/*
			 * This example demonstrates how to use a SSLSocket as client to send a HTTP
			 * request and get response from an HTTPS server. It assumes that the client is
			 * not behind a firewall
			 */

			SSLSocket socket = (SSLSocket) factory.createSocket("192.168.1.109", 5064);
			// (SSLSocket)factory.createSocket("localhost", 5063);
			System.out.println("Starting Handshake");
			socket.startHandshake();
			System.out.println("Handshake Complete");
			// Change to port 5056 for testing, 5057 for deploying
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			OutputStream writer = socket.getOutputStream();
			System.out.println("Connected to socket");
			
			
			String file_name = "C:\\Images\\Input\\hyunji_pic.jpg";
			File file = new File(file_name);
			BufferedImage bufferedImage = null;
			bufferedImage = ImageIO.read(file);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			
			writer.write(size);
			writer.write(byteArrayOutputStream.toByteArray());
			System.out.print(writer.toString());
			writer.flush();
			System.out.println("Data flushed");

			
			System.out.println("Waiting for Data");
			
			String socketResponse = null;
			while (true) {
				socketResponse = reader.readLine();
				if (socketResponse != null) {
					break;
				}
			}
			System.out.println("Data received");
			System.out.println("Socket Response: " + socketResponse);
			

			String facesInImageError = null;
			while (true) {
				facesInImageError = reader.readLine();
				if (facesInImageError != null) {
					break;
				}
			}
			
			System.out.println("Data received");
			System.out.println("Number of faces = " + facesInImageError);
			
			ArrayList<Integer> patientIds = new ArrayList<>();
			String matchingPatientIds = null;
			do {
				matchingPatientIds = reader.readLine();
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
						encodingReturned = reader.readLine();
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
			
			writer.close();
			reader.close();
			
			socket.close();
		} catch (ConnectException e) {
			e.printStackTrace();
			System.out.println("Connection Error\n");
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