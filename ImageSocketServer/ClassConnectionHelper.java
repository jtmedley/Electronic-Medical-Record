/*
 * Copyright (c) 2019. Tabitha Huff & Hyunji Kim & Jonathan Medley & Jack O'Connor
 *
 *                            Licensed under the Apache License, Version 2.0 (the "License");
 *                            you may not use this file except in compliance with the License.
 *                            You may obtain a copy of the License at
 *
 *                                http://www.apache.org/licenses/LICENSE-2.0
 *
 *                            Unless required by applicable law or agreed to in writing, software
 *                            distributed under the License is distributed on an "AS IS" BASIS,
 *                            WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *                            See the License for the specific language governing permissions and
 *                            limitations under the License.
 */

package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handle all database interactions inside specific asynchronous inner classes
 * Adapted from Chandu Yadav
 * https://www.tutorialspoint.com/android-asynctask-example-and-explanation
 */
class ClassConnectionHelper {

	Connection connection;
	ResultSet resultSet;
	int updateResponse;
	boolean doneflag;
	String queryString;
	private ArrayList<Object> parameterArray;
	private ArrayList<Integer> paramTypeArray;
	private int queryIdentifier;
	ClassGetResults getMyResults = null;
	/**
	 *
	 */
	ClassConnectionHelper(ClassQueryMakers queryMaker) {
		this.queryString = queryMaker.queryString;
		this.parameterArray = queryMaker.parameterArray;
		this.paramTypeArray = queryMaker.paramTypeArray;
		this.queryIdentifier = queryMaker.queryIdentifier;
		getMyResults = new ClassGetResults();
	}

	/**
	 * Handles database interactions for new database record creations on a separate
	 * asynchronous task thread.
	 */

	boolean goodConnection;
	boolean matches;
	

	/**
	 * Asynchronously connect to the database, send query, receive response, and
	 * close connection processes.
	 *
	 * @return An arbitrary success string.
	 */
	// @Override
	void executeStatement() {
		databaseConnect();
		if (this.queryIdentifier < 6) {
			myQuery();
			try {
				if (!connection.toString().isEmpty()) {
					// Log.d("tag", connection.toString());
					// Log.d("tag15", "goodConnection");
					// Log.d("tag17", resultSet.getString(1));

					goodConnection = true;
					getMyResults.getResults(resultSet, resultSet.getMetaData());
					matches = (getMyResults.listSize > 0);
				}
				connection.close();
				resultSet.close();
				// Log.d("tag18", getMyResults.foundResults.toString());
			} catch (SQLException e) {
				goodConnection = false;
				e.printStackTrace();
				// publishProgress("e");
			} catch (NullPointerException e) {
				goodConnection = false;
				e.printStackTrace();
				// publishProgress("e");
			}
		} else {
			myUpdate();
		}

	}


	/**
	 * Execute query and receive a ResultSet result.
	 */
	void myQuery() {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryString);
			for (int parameterIndex = 0; parameterIndex < parameterArray.size(); parameterIndex++) {
				preparedStatement.setObject(parameterIndex + 1, parameterArray.get(parameterIndex),
						paramTypeArray.get(parameterIndex));
				// System.out.println("sending"+parameterArray.get(parameterIndex)+" as " +
				// paramTypeArray.get(parameterIndex));
			}
			resultSet = preparedStatement.executeQuery();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute non-query and receive an integer result.
	 */
	void myUpdate() {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryString);
			for (int parameterIndex = 0; parameterIndex < parameterArray.size(); parameterIndex++) {
				preparedStatement.setObject(parameterIndex + 1, parameterArray.get(parameterIndex),
						paramTypeArray.get(parameterIndex));
				// System.out.println("sending"+parameterArray.get(parameterIndex)+" as " +
				// paramTypeArray.get(parameterIndex));
			}
			// We would expect this to be 1, but records update when updateResonse returns as 0.
            
			updateResponse = preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Establish a connection to the database.
	 */
	void databaseConnect() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");

			connection = DriverManager.getConnection(ClassConstants.SERVER_CONNECT_STRING,
					ClassConstants.SERVER_USER_ID, ClassConstants.SERVER_PASSWORD);

			connection.setAutoCommit(false);
			System.out.println("Connected Successfully");
		} catch (SQLException e) {

			e.printStackTrace();
			// if e.getErrorCode() ==
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}