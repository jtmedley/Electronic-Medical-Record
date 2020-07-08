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

/**
 * Bulk location of static constants, including connection variables
 */
class ClassConstants {

	static final int SOCKET_PORT_NUMBER = 5064;
	static final String DATABASE_NAME = "Testdb";
	static final String SERVER_USER_ID = "sa";
	static final String IP_ADDRESS = "localhost";
	
	//static final String IP_ADDRESS = "192.168.1.109";
	private static final String SSL_VERIFY_SETTING = "verifyServerCertificate=false;";
	private static final String SSL_USE_SETTING = "useSSL=true;";
	private static final String SSL_SETTING = "ssl=require";
	// private static final String SSL_SETTING = "requireSSL";
	private static final String SQL_PORT = "1433";
	static final String SERVER_CONNECT_STRING = "jdbc:jtds:sqlserver://" + IP_ADDRESS + ":" + SQL_PORT + ";"
	// + SSL_SETTING;
			+ "/" + SSL_VERIFY_SETTING + SSL_SETTING + SSL_USE_SETTING;
	static final String SERVER_PASSWORD = "BME4925CAP201STONE82019TABHYUJAJONCKNJIITHA4";
	// other constants
	// The maximum number of results to display at a time
	static final int MAX_RESULTS = 700;
	// Setting for clinic location actions
	static final String EXAM_LOCATION_RECORD = "Haiti";
	// Action flags indicating what the action of the exam record completion button
	// should be
	static final int ACTION_FLAG_MAKE_RECORD = 0;
	static final int ACTION_FLAG_MAKE_BOTH = 1;
	static final int ACTION_FLAG_UPDATE_RECORD = 2;
	static final int ACTION_FLAG_UPDATE_BOTH = 3;
	static final int ACTION_FLAG_VIEW = 4;
	// Image citations
	private static final String[] IMAGE_URLS = { "https://openclipart.org/detail/32611/medical-kit",
			"https://openclipart.org/detail/211861/matticonssystemsearch" };
}
