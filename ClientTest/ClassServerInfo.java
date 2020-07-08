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
 *
 */
class ClassServerInfo {
    static final int socketPortNumber = 5063;
    static final String DATABASE_NAME = "Testdb";
    static final String SERVER_USER_ID = "sa";
    static final String IP_ADDRESS = "192.168.1.109";
    static final String TIME_ZONE = "America/Montreal";
    private static final String SSL_VERIFY_SETTING = "verifyServerCertificate=false;";
    private static final String SSL_USE_SETTING = "useSSL=true;";
    //private static final String SSL_SETTING = "ssl=require";
    private static final String SSL_SETTING = "requireSSL";
    private static final String SQL_PORT = "1433";
    static final String SERVER_CONNECT_STRING = "jdbc:jtds:sqlserver://" + IP_ADDRESS;// +":" +
            //SQL_PORT + "/" + SSL_VERIFY_SETTING + SSL_SETTING + SSL_USE_SETTING;
    static final String SERVER_PASSWORD = "BME4925CAP201STONE82019TABHYUJAJONCKNJIITHA4";
    // other constants
    static final int MAX_RESULTS = 8000;

//    public static final String Column
}
