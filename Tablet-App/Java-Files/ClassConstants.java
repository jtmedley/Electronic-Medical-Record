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

/**
 * Bulk location of static constants, including connection variables
 */
class ClassConstants {
    /**
     *
     */
    static final String EXAM_STRING_HAITI = "Haiti";

    /**
     *
     */
    static final String EXAM_STRING_DEFAULT = "Default";

    /**
     *
     */
    static final String CERTIFICATE = "ExportedCertificateAndroid.cer";

    /**
     *
     */
    static final int IMAGE_SOCKET_PORT_NUMBER = 5063;

    /**
     *
     */
    static final int DATABASE_SOCKET_PORT_NUMBER = 5064;

    /**
     *
     */
    static final String IP_ADDRESS = "192.168.1.109";
    /**
     * Action flags indicating what the Exam Record query should be
     */
    static final int ACTION_FLAG_MAKE_RECORD = 1;

    /**
     *
     */
    static final int ACTION_FLAG_MAKE_BOTH = 2;

    /**
     *
     */
    static final int ACTION_FLAG_UPDATE_RECORD = 3;

    /**
     *
     */
    static final int ACTION_FLAG_UPDATE_BOTH = 4;

    /**
     *
     */
    static final int ACTION_FLAG_VIEW = 5;

    /**
     * Image citations
     */
    static final String[] IMAGE_URLS = {
            "https://openclipart.org/detail/32611/medical-kit",
            "https://openclipart.org/detail/211861/matticonssystemsearch"
    };
}
