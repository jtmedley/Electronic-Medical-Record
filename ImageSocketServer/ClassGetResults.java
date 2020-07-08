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

package mypackage;




import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parse the results of a query into a serializable object to be used repeatedly
 * <p>
 * Adapted from Jakub H
 * https://stackoverflow.com/questions/7507121/efficient-way-to-handle-resultset-in-java
 */
class ClassGetResults implements Serializable {

    private static final long serialVersionUID = 1L;
    int listSize;
    boolean overSearch;
    ArrayList<HashMap<String, Object>> foundResults;

    /**
     * Parse the results of a query in order until exceeding the maximum number of allows results
     *
     * @param myResponse
     * @param md
     */
    void getResults(ResultSet myResponse, ResultSetMetaData md) {
        foundResults = new ArrayList<>();
        try {
            int columns = md.getColumnCount();
            int resultCount = 0;
            while (resultCount < ClassConstants.MAX_RESULTS) {
                while (myResponse.next()) {
                    final HashMap<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columns; i++) {

                        if (md.getColumnClassName(i).equals("java.sql.Blob")) {
                            row.put(md.getColumnName(i), myResponse.getBytes(i));
                        } else {
                            row.put(md.getColumnName(i), myResponse.getObject(i));

                        }
                        //Log.d("Received Data", "Received: " + md.getColumnName(i) +
                        // ", of type: " + md.getColumnClassName(i) + ", with value: "
                        // + myResponse.getObject(i));
                    }
                    foundResults.add(row);
                }
                resultCount++;
                if (resultCount >= ClassConstants.MAX_RESULTS) {
                    overSearch = true;
                }
            }

            listSize = foundResults.size();
            
        } catch (
                SQLException e) {
            //TODO: add error message
            e.printStackTrace();
        }
    }
}
