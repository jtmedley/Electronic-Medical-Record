package mypackage;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.*;

// Adapted from https://sites.google.com/site/ddmwsst/create-your-own-certificate-and-ca/ssl-socket-communication

public class Server {
	static SSLServerSocketFactory factory;

	// main program
	public static void main(String argv[]) throws Exception {

		/*
		 * Set up a key manager for client authentication if asked by the server. Use
		 * the implementation's default TrustStore and secureRandom routines.
		 */
		System.out.println("Starting Server.");
		try {
			System.out.println("1 Starting Server.");
			SSLContext ctx;
			KeyManagerFactory kmf;
			KeyStore ks;
			char[] passphrase = "BMECapstone2019".toCharArray();
			System.setProperty("javax.net.ssl.keyStore", "C:\\Cert\\certs.jks");

			System.setProperty("javax.net.ssl.trustStore", "C:\\Cert\\certs.jks");
			ctx = SSLContext.getInstance("TLS");
			kmf = KeyManagerFactory.getInstance("SunX509");
			ks = KeyStore.getInstance("JKS");
			System.out.println("2 Starting Server.");
			ks.load(new FileInputStream("C:\\Cert\\certs.jks"), passphrase);

			kmf.init(ks, passphrase);
			ctx.init(kmf.getKeyManagers(), null, null);

			factory = ctx.getServerSocketFactory();
			System.out.println("3 Starting Server.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		System.out.println("4 Starting Server.");
		Server server = new Server();
		System.out.println("5 Starting Server.");
		server.startServer();
	}

	// Start server
	public void startServer() {
		try {

			SSLServerSocket serversocket = (SSLServerSocket) factory
					.createServerSocket(ClassConstants.SOCKET_PORT_NUMBER);

			while (true) {
				Socket client = serversocket.accept();
				new ProcessRequest(client);
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}
}

class ProcessRequest extends Thread {

	Socket client;
	ObjectInputStream is;
	ObjectOutputStream out;

	public ProcessRequest(Socket s) { // constructor
		client = s;
		try {
			is = new ObjectInputStream(client.getInputStream());

			out = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		this.start(); // Thread starts here...this start() will call run()
	}

	public void run() {
		try {
			final ArrayList<Object> parameterArray = new ArrayList<Object>();
			final ArrayList<Integer> paramTypeArray = new ArrayList<Integer>();
			int queryIdentifier = 0;
			Object result = is.readObject();
			if (result instanceof Integer) {
				queryIdentifier = (int) result;

				System.out.println("Query Identifier: " + result.toString());
				Object parameter = null;
				Object parameterCheck = null;
				do {
					parameter = is.readObject();
					if (parameter != null) {
						parameterCheck = parameter;
					}else {
						parameterCheck = ("Found null, continuing");
					}
					
					if (parameter instanceof String) {
						String paramString = (String) parameter;
						if (!paramString.equals("breakpoint")) {
							parameterArray.add(parameter);
						}
					} else {
						parameterArray.add(parameter);
					}
					
				} while (!parameterCheck.equals("breakpoint"));
				Iterator<Object> parameterIterator = parameterArray.iterator();
				while (parameterIterator.hasNext()) {
					System.out.println("Parameter received from Client: " + parameterIterator.next());
				}
				System.out.println("Parameters:" + Integer.toString(parameterArray.size()));
				Integer paramType = null;
				Object paramTypeResult = null;
				do {
					paramTypeResult = is.readObject();
					if (paramTypeResult instanceof Integer) {

						paramType = (int) paramTypeResult;
						if (paramType != 999) {
							paramTypeArray.add(paramType);
						}
					}else {
						paramType = -1;
					}
				} while (!paramType.equals(999));
				Iterator<Integer> paramTypeIterator = paramTypeArray.iterator();
				while (paramTypeIterator.hasNext()) {
					System.out.println("Parameter type received from Client: " + paramTypeIterator.next());
				}
				System.out.println("Parameter types:" + Integer.toString(paramTypeArray.size()));
				try {
					ClassQueryMakers queryMaker = new ClassQueryMakers(queryIdentifier, parameterArray, paramTypeArray);
					ClassConnectionHelper connServer = new ClassConnectionHelper(queryMaker);
					connServer.executeStatement();
					
					if (queryIdentifier < 6) {
						final ArrayList<HashMap<String, Object>> results = connServer.getMyResults.foundResults;
						System.out.println("Number of results: " + Integer.toString(results.size()));
						
						out.writeObject(results.size());
						int resultCount = 0;
						while (resultCount < results.size()) {
							out.writeObject(results.get(resultCount));
							resultCount++;
						}
					} else {
						final int affected = connServer.updateResponse;
						// We would expect this to be 1, but records update when affected returns as 0.
						out.writeObject(affected);
						System.out.println("Number of rows affected: " + Integer.toString(affected));
					}
					System.out.println("Query: " + queryMaker.queryString);
				} finally {
					out.close();
					try {
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				System.out.println("Error");
			}
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}