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

import static com.example.medley.medicalrecord.ClassServerInfo.DATABASE_NAME;

//import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Iterator;

import org.threeten.bp.*;

/**
 * Adapted from https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 * and from http://www.java2s.com/Code/Java/Database-SQL-JDBC/InsertpicturetoMySQL.htm
 * and from https://stackoverflow.com/questions/10618325/how-to-create-a-blob-from-bitmap-in-android-activity/10618678
 */

/**
 *
 */
class ClassQueryMakers {

	String queryString;
	ArrayList<Object> parameterArray;
	ArrayList<Integer> paramTypeArray;

	ClassQueryMakers() {
		parameterArray = new ArrayList<Object>();
		paramTypeArray = new ArrayList<Integer>();
	}

	/**
	 * TODO: For each method: 1. Store all variables in order to List parameters
	 * immediately after the string. TODO: For each method: 2. Replace all variables
	 * in the string with question marks. TODO: For each method: 3. Add exception
	 * handling for blank values and invalid values TODO: Rules for blanks: if it is
	 * a SEARCH as in uses a SELECT statement (newPatientQuery, TODO:
	 * examRecordQuery): for blank values, replace with % to search all. TODO: If it
	 * is a STORE as in uses an INSERT INTO or UPDATE statement (all others), TODO:
	 * for blank values, set flag to present warning to user about missing info FOR
	 * THAT FIELD TODO: For each method: Rules for validating values are
	 * variable-dependent.
	 */
	/**
	 * @param newPatientFirstName The current patient's first name input by the
	 *                            user.
	 * @param newPatientLastName  The current patient's last name input by the user.
	 * @param newPatientSex       The current patient's sex input by the user.
	 * @param newPatientDOB       The current patient's date of birth input by the
	 *                            user.
	 */
	void selectBasicQuery(String newPatientFirstName, String newPatientLastName, LocalDate newPatientDOB,
			String newPatientSex) {

		queryString = "USE " + DATABASE_NAME + ";SELECT [Person ID],[First Name],[Last Name],[Date of Birth],[Sex] "
				+ "FROM dbo.Person " + "WHERE ([First Name] LIKE (?) AND [Last Name] LIKE (?) "
				+ "AND [Date of Birth] LIKE (?) " + "AND Sex LIKE (?)" + ");";

		parameterArray.add(newPatientFirstName);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(newPatientLastName);
		paramTypeArray.add(Types.VARCHAR);

		// java.sql.Date dateTemp = new java.sql.Date(newPatientDOB.getTime());
		// System.out.println("sql date:"+dateTemp);
		// System.out.println("sql date tostring:"+newPatientDOB.toString());
		java.sql.Date newPatientDOBSql = null;
		if(newPatientDOB != null) {
		newPatientDOBSql = DateTimeUtils.toSqlDate(newPatientDOB);
		
		// System.out.println("sql date:"+date);
		parameterArray.add(newPatientDOBSql);
		paramTypeArray.add(Types.DATE);
		}
		else {
			parameterArray.add("%");
			paramTypeArray.add(Types.VARCHAR);
				
		}
		parameterArray.add(newPatientSex);
		paramTypeArray.add(Types.VARCHAR);

	}

	/**
	 * Untested
	 * 
	 * @param personIds
	 */
	void selectWithFaceRecognitionQuery(ArrayList<Integer> personIds) {
		if (!personIds.isEmpty()) {
			queryString = "USE " + DATABASE_NAME + ";SELECT Top 1 *,X.MostRecent " + "FROM Images I "
					+ "JOIN (SELECT [Exam Record].[Record ID],MAX([Exam Record].[Date of Visit]) AS MostRecent "
					+ "FROM [Exam Record] " + "WHERE ";

			StringBuilder stringBuilder = new StringBuilder(queryString);

			Iterator listIterator = personIds.iterator();
			stringBuilder.append("[Exam Record].[Person ID] = (?) ");
			parameterArray.add(personIds.get(0));
			paramTypeArray.add(Types.INTEGER);
			listIterator.next();
			while (listIterator.hasNext()) {
				stringBuilder.append("OR [Exam Record].[Person ID] = (?)");
				parameterArray.add(listIterator.next());
				paramTypeArray.add(Types.INTEGER);
			}
			stringBuilder.append(
					" GROUP BY [Record ID]) AS X ON I.[Record ID]=X.[Record ID] " + "  ORDER BY X.[MostRecent] DESC");
			queryString = stringBuilder.append(";").toString();
		} else

		{
			// TODO: add error handling code
		}
	}

	/**
	 * Untested
	 * 
	 * @param bitmap
	 * @param personId
	 * @param currentEncoding
	 */
	void makeImageRecordQuery(byte[] bitmap, int personId, double[] currentEncoding) {
		queryString = "Use Testdb;INSERT INTO [dbo].[Images] ([Bitmap],[Person ID],"
				+ "[Num 1],[Num 2],[Num 3],[Num 4],[Num 5],[Num 6],[Num 7],[Num 8],[Num 9],"
				+ "[Num 10],[Num 11],[Num 12],[Num 13],[Num 14],[Num 15],[Num 16],[Num 17],"
				+ "[Num 18],[Num 19],[Num 20],[Num 21],[Num 22],[Num 23],[Num 24],[Num 25],"
				+ "[Num 26],[Num 27],[Num 28],[Num 29],[Num 30],[Num 31],[Num 32],[Num 33],"
				+ "[Num 34],[Num 35],[Num 36],[Num 37],[Num 38],[Num 39],[Num 40],[Num 41],"
				+ "[Num 42],[Num 43],[Num 44],[Num 45],[Num 46],[Num 47],[Num 48],[Num 49],"
				+ "[Num 50],[Num 51],[Num 52],[Num 53],[Num 54],[Num 55],[Num 56],[Num 57],"
				+ "[Num 58],[Num 59],[Num 60],[Num 61],[Num 62],[Num 63],[Num 64],[Num 65],"
				+ "[Num 66],[Num 67],[Num 68],[Num 69],[Num 70],[Num 71],[Num 72],[Num 73],"
				+ "[Num 74],[Num 75],[Num 76],[Num 77],[Num 78],[Num 79],[Num 80],[Num 81],"
				+ "[Num 82],[Num 83],[Num 84],[Num 85],[Num 86],[Num 87],[Num 88],[Num 89],"
				+ "[Num 90],[Num 91],[Num 92],[Num 93],[Num 94],[Num 95],[Num 96],[Num 97],"
				+ "[Num 98],[Num 99],[Num 100],[Num 101],[Num 102],[Num 103],[Num 104],"
				+ "[Num 105],[Num 106],[Num 107],[Num 108],[Num 109],[Num 110],[Num 111],"
				+ "[Num 112],[Num 113],[Num 114],[Num 115],[Num 116],[Num 117],[Num 118],"
				+ "[Num 119],[Num 120],[Num 121],[Num 122],[Num 123],[Num 124],[Num 125],"
				+ "[Num 126],[Num 127],[Num 128])" + "VALUES ((?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		// byte[] bitmapByteArray = bos.toByteArray();
		parameterArray.add(bitmap);
		paramTypeArray.add(Types.VARBINARY);
		parameterArray.add(personId);
		paramTypeArray.add(Types.INTEGER);
		for (int count = 0; count < 128; count++) {
			parameterArray.add(currentEncoding[count]);
			paramTypeArray.add(Types.DOUBLE);
		}
	}

	void selectHaitiExamRecordQuery(int examPersonId) {
		queryString = "USE Testdb;" + "SELECT [Exam Record].[Person ID]," + "[Exam Record].[Triage], "
				+ "[Exam Record].[Date of Visit], " + "[Exam Record].[Chart Number], " + "[Haiti Record].[Village], "
				+ "[Exam Record].[Body Temperature], " + "[Exam Record].[Diastolic BP], "
				+ "[Exam Record].[Systolic BP], " + "[Exam Record].[Height], " + "[Exam Record].[Weight], "
				+ "[Exam Record].[BMI], " + "[Haiti Record].[Hemoglobin], " + "[Haiti Record].[History DmDx], "
				+ "[Haiti Record].[History ETOH], " + "[Haiti Record].[History FamilyhxDm], "
				+ "[Haiti Record].[History FamilyhxHTN], " + "[Haiti Record].[History FamilyhxStroke], "
				+ "[Haiti Record].[History HTNDx], " + "[Haiti Record].[History Polydipsia], "
				+ "[Haiti Record].[History Polyphasia], " + "[Haiti Record].[History Polyuria], "
				+ "[Haiti Record].[History Smoker], " + "[Haiti Record].[History Stroke], "
				+ "[Exam Record].[Other History], " + "[Exam Record].[Diet], " + "[Haiti Record].[Amount of Maggi], "
				+ "[Exam Record].[Chief Complaint], " + "[Exam Record].[History of Present Illness], "
				+ "[Exam Record].[Assessment], " + "[Exam Record].[Physical Examination],"
				+ "[Haiti Record].[Treatment Plan], " + "[Exam Record].[Clinician First Name], "
				+ "[Exam Record].[Clinician Last Name]," + "[Exam Record].[Signature]" + "FROM [Exam Record]"
				+ "INNER JOIN [Haiti Record] ON [Exam Record].[Record ID]=[Haiti Record].[Record ID]"
				+ "WHERE [Exam Record].[Person ID] = (?);";
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);
	}

	void selectDefaultExamRecordQuery(int examPersonId) {
		queryString = "USE Testdb;" + "SELECT [Exam Record].[Person ID]," + "[Exam Record].[Triage], "
				+ "[Exam Record].[Date of Visit], " + "[Exam Record].[Chart Number], " + "[Default Record].[Location], "
				+ "[Exam Record].[Body Temperature], " + "[Exam Record].[Diastolic BP], "
				+ "[Exam Record].[Systolic BP], " + "[Exam Record].[Height], " + "[Exam Record].[Weight], "
				+ "[Exam Record].[BMI], " + "[Exam Record].[Other History], " + "[Exam Record].[Diet], "
				+ "[Exam Record].[Chief Complaint], " + "[Exam Record].[History of Present Illness], "
				+ "[Exam Record].[Assessment], " + "[Exam Record].[Physical Examination],"
				+ "[Default Record].[Treatment Plan], " + "[Exam Record].[Clinician First Name], "
				+ "[Exam Record].[Clinician Last Name]," + "[Exam Record].[Signature]" + "FROM [Exam Record]"
				+ "INNER JOIN [Default Record] ON [Exam Record].[Record ID]=[Default Record].[Record ID]"
				+ "WHERE [Exam Record].[Person ID] = (?);";
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);
	}

	void makeBothRecordsQuery(String examFirstName, String examLastName, LocalDate examDOB, String examSex,
			String examTriage, LocalDate examDateOfVisit, int examChartNumber, float examBodyTemperature,
			int examDiastolicBP, int examSystolicBP, int examHeight, int examWeight, float examBMI,
			String examOtherHistory, String examDiet, String examChiefComplaint, String examHistPresentIll,
			String examAssessment, String examPhysicalExam, String examClinicianFirst, String examClinicianLast,
			String examPrescription, String examSignature, String examLocationRecord) {
		queryString = "Use Testdb;Declare @Id1 int;Declare @Id2 int;INSERT INTO [dbo].[Person] (" + "[First Name],"
				+ "[Last Name]," + "[Date of Birth]," + "[Sex])" + "VALUES ((?),(?),(?),(?));"
				+ "Set @Id1 = SCOPE_IDENTITY();INSERT INTO [dbo].[Exam Record] (" + "[Person ID]," + "[Triage],"
				+ "[Date of Visit]," + "[Chart Number]," + "[Body Temperature]," + "[Diastolic BP]," + "[Systolic BP],"
				+ "[Height]," + "[Weight]," + "[BMI]," + "[Other History]," + "[Diet]," + "[Chief Complaint],"
				+ "[History of Present Illness]," + "[Assessment]," + "[Physical Examination],"
				+ "[Clinician First Name]," + "[Clinician Last Name]," + "[Prescription]," + "[Signature],"
				+ "[Location Record])" + "VALUES (@Id1,(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";

		parameterArray.add(examFirstName);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examLastName);
		paramTypeArray.add(Types.VARCHAR);
		java.sql.Date examDOBSql = DateTimeUtils.toSqlDate(examDOB);
		parameterArray.add(examDOBSql);
		paramTypeArray.add(Types.DATE);
		parameterArray.add(examSex);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examTriage);
		paramTypeArray.add(Types.VARCHAR);
		java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
		parameterArray.add(examDateOfVisitSql);
		paramTypeArray.add(Types.DATE);
		parameterArray.add(examChartNumber);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBodyTemperature);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examDiastolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examSystolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examHeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examWeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBMI);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examOtherHistory);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examDiet);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examChiefComplaint);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examHistPresentIll);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examAssessment);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPhysicalExam);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianFirst);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianLast);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPrescription);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examSignature);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examLocationRecord);
		paramTypeArray.add(Types.VARCHAR);

	}

	/**
	 * Written to be called after makeExamRecordQuery or makeBothRecords to add
	 * specific record location query information to the queryString,
	 * parameterArray, and paramTypeArray variables.
	 *
	 * @param examVillage
	 * @param examHemoglobin
	 * @param examHTNDx
	 * @param examDmDx
	 * @param examStroke
	 * @param examFamilyhxHTN
	 * @param examFamilyhxDm
	 * @param examFamilyhxStroke
	 * @param examPolyphasia
	 * @param examPolydipsia
	 * @param examPolyuria
	 * @param examSmoker
	 * @param examETOH
	 * @param examAmountOfMaggi
	 * @param examTreatmentPlan
	 */
	void makeHaitiRecordQuery(String examVillage, int examHemoglobin, Boolean examDmDx, Boolean examETOH,
			Boolean examHTNDx, Boolean examFamilyhxDm, Boolean examFamilyhxHTN, Boolean examFamilyhxStroke,
			Boolean examPolydipsia, Boolean examPolyphasia, Boolean examPolyuria, Boolean examSmoker,
			Boolean examStroke, int examAmountOfMaggi, String examTreatmentPlan) {
		queryString = queryString + "Set @Id2 = SCOPE_IDENTITY();" + "INSERT INTO dbo.[Haiti Record] (" + "[Person ID],"
				+ "[Record ID]," + "[Village]," + "[Hemoglobin]," + "[History DmDx]," + "[History ETOH],"
				+ "[History FamilyhxDm]," + "[History FamilyhxHTN]," + "[History FamilyhxStroke]," + "[History HTNDx],"
				+ "[History Polydipsia]," + "[History Polyphasia]," + "[History Polyuria]," + "[History Smoker],"
				+ "[History Stroke]," + "[Amount of Maggi]," + "[Treatment Plan])"
				+ "VALUES (@Id1,@Id2,(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";
		parameterArray.add(examVillage);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examHemoglobin);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examDmDx);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examETOH);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxDm);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxHTN);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxStroke);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examHTNDx);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolydipsia);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolyphasia);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolyuria);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examSmoker);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examStroke);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examAmountOfMaggi);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examTreatmentPlan);
		paramTypeArray.add(Types.VARCHAR);

	}

	/**
	 * Written to be called after makeExamRecordQuery or makeBothRecords to add
	 * specific record location query information to the queryString,
	 * parameterArray, and paramTypeArray variables.
	 *
	 * @param examLocation
	 * @param examTreatmentPlan
	 */
	void makeDefaultRecordQuery(String examLocation, String examTreatmentPlan) {
		queryString = queryString + "Set @Id2 = SCOPE_IDENTITY();" + "INSERT INTO dbo.[Default Record] ("
				+ "[Person ID]," + "[Record ID]," + "[Location]," + "[Treatment Plan])" + "VALUES (@Id1,@ID2,(?),(?));";
		parameterArray.add(examLocation);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examTreatmentPlan);
		paramTypeArray.add(Types.VARCHAR);
	}

	void updateBasicQuery(String examFirstName, String examLastName, LocalDate examDOB, String examSex,
			int examPersonId) {
		queryString = "USE Testdb;UPDATE dbo.Person SET[First Name]=(?),[Last Name]=(?),"
				+ "[Date of Birth]=(?),[Sex]=(?)WHERE [Person ID] = (?);";
		parameterArray.add(examFirstName);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examLastName);
		paramTypeArray.add(Types.VARCHAR);
		java.sql.Date examDOBSql = DateTimeUtils.toSqlDate(examDOB);
		parameterArray.add(examDOBSql);
		paramTypeArray.add(Types.DATE);
		parameterArray.add(examSex);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);
	}

	void updateExamRecordQuery(String examTriage, LocalDate examDateOfVisit, int examChartNumber,
			float examBodyTemperature, int examDiastolicBP, int examSystolicBP, int examHeight, int examWeight,
			float examBMI, String examOtherHistory, String examDiet, String examChiefComplaint,
			String examHistPresentIll, String examAssessment, String examPhysicalExam, String examClinicianFirst,
			String examClinicianLast, String examPrescription, String examSignature, String examLocationRecord,
			int examPersonId) {
		queryString = "USE Testdb;" + "UPDATE [dbo].[Exam Record] " + "SET " + "[Triage]=" + "(?)" + ",[Date of Visit]="
				+ "(?)" + ",[Chart Number]=" + "(?)" + ",[Body Temperature]=" + "(?)" + ",[Diastolic BP]=" + "(?)"
				+ ",[Systolic BP]=" + "(?)" + ",[Height]=" + "(?)" + ",[Weight]=" + "(?)" + ",[BMI]=" + "(?)"
				+ ",[Other History]=" + "(?)" + ",[Diet]=" + "(?)" + ",[Chief Complaint]=" + "(?)"
				+ ",[History of Present Illness]=" + "(?)" + ",[Assessment]=" + "(?)" + ",[Physical Examination]="
				+ "(?)" + ",[Clinician First Name]=" + "(?)" + ",[Clinician Last Name]=" + "(?)" + ",[Prescription]="
				+ "(?)" + ",[Signature]=" + "(?)" + ",[Location Record]=" + "(?)" + " " + "WHERE [Person ID] = (?);";

		parameterArray.add(examTriage);
		paramTypeArray.add(Types.VARCHAR);
		java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
		parameterArray.add(examDateOfVisitSql);
		paramTypeArray.add(Types.DATE);
		parameterArray.add(examChartNumber);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBodyTemperature);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examDiastolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examSystolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examHeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examWeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBMI);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examOtherHistory);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examDiet);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examChiefComplaint);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examHistPresentIll);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examAssessment);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPhysicalExam);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianFirst);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianLast);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPrescription);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examSignature);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examLocationRecord);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);

	}

	void updateHaitiExamRecordQuery(String examVillage, Integer examHemoglobin, Boolean examDmDx, Boolean examETOH,
			Boolean examHTNDx, Boolean examFamilyhxDm, Boolean examFamilyhxHTN, Boolean examFamilyhxStroke,
			Boolean examPolydipsia, Boolean examPolyphasia, Boolean examPolyuria, Boolean examSmoker,
			Boolean examStroke, int examAmountOfMaggi, String examTreatmentPlan, int examPersonId) {
		queryString += "USE Testdb;" + "UPDATE [dbo].[Haiti Record] " + "SET " + "[Village]=" + "(?)" + ",[Hemoglobin]="
				+ "(?)" + ",[History DmDx]=" + "(?)" + ",[History ETOH]=" + "(?)" + ",[History FamilyhxDm]=" + "(?)"
				+ ",[History FamilyhxHTN]=" + "(?)" + ",[History FamilyhxStroke]=" + "(?)" + ",[History HTNDx]=" + "(?)"
				+ ",[History Polydipsia]=" + "(?)" + ",[History Polyphasia]=" + "(?)" + ",[History Polyuria]=" + "(?)"
				+ ",[History Smoker]=" + "(?)" + ",[History Stroke]=" + "(?)" + ",[Amount of Maggi]=" + "(?)"
				+ ",[Treatment Plan]=" + "(?)" + " WHERE [Person ID] = (?);";

		parameterArray.add(examVillage);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examHemoglobin);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examDmDx);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examETOH);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxDm);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxHTN);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examFamilyhxStroke);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examHTNDx);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolydipsia);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolyphasia);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examPolyuria);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examSmoker);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examStroke);
		paramTypeArray.add(Types.BIT);
		parameterArray.add(examAmountOfMaggi);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examTreatmentPlan);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);
	}

	void updateDefaultExamRecordQuery(String examLocationRecord, String examTreatmentPlan, int examPersonId) {
		queryString = "USE Testdb;" + "UPDATE [dbo].[Default Record] " + "SET " + ",[Location]=" + "(?)"
				+ ",[Treatment Plan]=" + "(?)" + " WHERE [Person ID] = (?);";
		parameterArray.add(examLocationRecord);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examTreatmentPlan);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPersonId);
		paramTypeArray.add(Types.INTEGER);
	}

	/**
	 * Written so that makeHaitiRecord and makeDefaultRecord can be called to add to
	 * the queryString, parameterArray, and paramTypeArray variables.
	 *
	 * @param examTriage
	 * @param examDateOfVisit
	 * @param examChartNumber
	 * @param examBodyTemperature
	 * @param examDiastolicBP
	 * @param examSystolicBP
	 * @param examHeight
	 * @param examWeight
	 * @param examBMI
	 * @param examOtherHistory
	 * @param examDiet
	 * @param examChiefComplaint
	 * @param examHistPresentIll
	 * @param examAssessment
	 * @param examPhysicalExam
	 * @param examClinicianFirst
	 * @param examClinicianLast
	 * @param examPrescription
	 * @param examSignature
	 * @param examLocationRecord
	 */
	void makeExamRecordQuery(String examTriage, LocalDate examDateOfVisit, int examChartNumber,
			float examBodyTemperature, int examDiastolicBP, int examSystolicBP, int examHeight, int examWeight,
			float examBMI, String examOtherHistory, String examDiet, String examChiefComplaint,
			String examHistPresentIll, String examAssessment, String examPhysicalExam, String examClinicianFirst,
			String examClinicianLast, String examPrescription, String examSignature, String examLocationRecord) {
		queryString = "USE Testdb;" + "Declare @Id2 int;INSERT INTO [dbo].[Exam Record] (" + "[Person ID],"
				+ "[Triage]," + "[Date of Visit]," + "[Chart Number]," + "[Body Temperature]," + "[Diastolic BP],"
				+ "[Systolic BP]," + "[Height]," + "[Weight]," + "[BMI]," + "[Other History]," + "[Diet],"
				+ "[Chief Complaint]," + "[History of Present Illness]," + "[Assessment]," + "[Physical Examination],"
				+ "[Clinician First Name]," + "[Clinician Last Name]," + "[Prescription]," + "[Signature]"
				+ "[Location Record]" + "VALUES (@Id1,(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";

		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examTriage);
		paramTypeArray.add(Types.VARCHAR);
		java.sql.Date examDateOfVisitSql = DateTimeUtils.toSqlDate(examDateOfVisit);
		parameterArray.add(examDateOfVisitSql);
		paramTypeArray.add(Types.DATE);
		parameterArray.add(examChartNumber);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBodyTemperature);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examDiastolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examSystolicBP);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examHeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examWeight);
		paramTypeArray.add(Types.INTEGER);
		parameterArray.add(examBMI);
		paramTypeArray.add(Types.FLOAT);
		parameterArray.add(examOtherHistory);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examDiet);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examChiefComplaint);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examHistPresentIll);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examAssessment);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPhysicalExam);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianFirst);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examClinicianLast);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examPrescription);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examSignature);
		paramTypeArray.add(Types.VARCHAR);
		parameterArray.add(examLocationRecord);
		paramTypeArray.add(Types.VARCHAR);
	}
}
