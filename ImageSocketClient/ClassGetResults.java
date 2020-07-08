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

package com.example.medley.medicalrecord;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapted from Jakub H
 * https://stackoverflow.com/questions/7507121/efficient-way-to-handle-resultset-in-java
 */
class ClassGetResults implements Serializable {
	private static final long serialVersionUID = 1L;
	int listSize;
	ArrayList<HashMap<String, Object>> foundResults;

	/**
	 *
	 * @param myResponse
	 * @param md
	 */
	void getResults(ResultSet myResponse, ResultSetMetaData md) {
		foundResults = new ArrayList<>();
		try {
			System.out.println("4");
			int columns = md.getColumnCount();
			int resultCount = 0;
			while (resultCount < ClassConstants.MAX_RESULTS) {
				while (myResponse.next()) {
					System.out.println("5");
					final HashMap<String, Object> row = new HashMap<>();
					for (int i = 1; i <= columns; i++) {
						row.put(md.getColumnName(i), myResponse.getObject(i));
						// Log.d("tag",myResponse.getString(i));
						//System.out.println(md.getColumnName(i) +": " + myResponse.getObject(i));
					}
					foundResults.add(row);
				}
				resultCount++;
			}
			listSize = foundResults.size();
			// Log.d("tag7", Integer.toString(listSize));
		} catch (SQLException e) {
			// TODO: add error message
			e.printStackTrace();
		}
	}
	/*
	 * PersonIDs[ResultCount] = MyResponse.getString("Person ID");
	 * FirstNames[ResultCount] = MyResponse.getString("First Name");
	 * LastNames[ResultCount] = MyResponse.getString("Last Name");
	 * Sexes[ResultCount] = MyResponse.getString("Sex"); DOBs[ResultCount] =
	 * MyResponse.getString("Date of Birth");
	 */
}
