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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Create a query object to be used for database communications, containing the
 * query string, an array of parameters for the query, and an array of the
 * parameter types to be applied to a prepared statement.
 * <p>
 * Adapted from
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html and from
 * http://www.java2s.com/Code/Java/Database-SQL-JDBC/InsertpicturetoMySQL.htm
 * and from
 * https://stackoverflow.com/questions/10618325/how-to-create-a-blob-from-bitmap-in-android-activity/10618678
 */
class ClassQueryMakers {

	String queryString;
	int queryIdentifier;
	ArrayList<Object> parameterArray;
	ArrayList<Integer> paramTypeArray;

	ClassQueryMakers(int queryIdentifier, ArrayList<Object> parameterArray, ArrayList<Integer> paramTypeArray) {
		this.parameterArray = parameterArray;
		this.paramTypeArray = paramTypeArray;
		this.queryIdentifier = queryIdentifier;
		queryString = "USE " + ClassConstants.DATABASE_NAME + ";";
		switch (this.queryIdentifier) {
		case -1:
			selectOnlyFaceQuery();
			break;
		case 0:
			getLogin();
			break;
		case 1:
			selectBasicQuery();
			break;
		case 2:
			selectWithFaceQuery();
			break;
		case 3:
			selectPatientRecordsQuery();
			break;
		case 4:
			selectHaitiExamRecordQuery();
			break;
		case 5:
			selectExamRecordQuery();
			break;
		case 6:
			updateBasicQuery();
			updateExamRecordQuery();
			updateHaitiExamRecordQuery();
			break;
		case 7:
			updateBasicQuery();
			updateExamRecordQuery();
			break;
		case 8:
			updateExamRecordQuery();
			updateHaitiExamRecordQuery();
			break;
		case 9:
			updateExamRecordQuery();
			break;
		case 10:
			updateBasicQuery();
			makeExamRecordQuery();
			makeHaitiRecordQuery();
			makeImageRecordQuery();
			break;
		case 11:
			updateBasicQuery();
			makeExamRecordQuery();
			makeImageRecordQuery();
			break;
		case 12:
			makeExamRecordQuery();
			makeHaitiRecordQuery();
			makeImageRecordQuery();
			break;
		case 13:
			makeExamRecordQuery();
			makeImageRecordQuery();
			break;
		case 14:
			updateBasicQuery();
			makeExamRecordQuery();
			makeHaitiRecordQuery();
			break;
		case 15:
			updateBasicQuery();
			makeExamRecordQuery();
			break;
		case 16:
			makeExamRecordQuery();
			makeHaitiRecordQuery();
			break;
		case 17:
			makeExamRecordQuery();
			break;
		case 18:
			makeBothRecordsQuery();
			makeHaitiRecordQuery();
			makeImageRecordQuery();
			break;
		case 19:
			makeBothRecordsQuery();
			makeImageRecordQuery();
			break;
		case 20:
			makeBothRecordsQuery();
			makeHaitiRecordQuery();
			break;
		case 21:
			makeBothRecordsQuery();
			break;
		case 22:
			setLogin();
			break;
		}

	}

	/**
	 * Select basic or static patient information that matches input data
	 *
	 * @param newPatientFirstName The current patient's first name input by the
	 *                            user.
	 * @param newPatientLastName  The current patient's last name input by the user.
	 * @param newPatientSex       The current patient's sex input by the user.
	 * @param newPatientDOB       The current patient's date of birth input by the
	 *                            user.
	 */
	void selectBasicQuery() {

		queryString += "SELECT Top " + ClassConstants.MAX_RESULTS + " p.[Person ID], p.[First Name],p.[Last Name],"
				+ "p.[Date of Birth],p.[Sex],p.[Discharged],p.[Current Triage],"
				+ "imFinal.ImageBitmap, exFinal.[Date of Visit], exFinal.[Location Record] " + "FROM Person p "
				+ "LEFT OUTER JOIN " + "(SELECT [Person ID], [Bitmap] AS ImageBitmap FROM "
				+ "(SELECT ex1.[Person ID], im1.[Bitmap], ROW_NUMBER() OVER  (PARTITION BY ex1.[Person ID] "
				+ "ORDER BY ex1.[Date of Visit] DESC,ex1.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex1 INNER JOIN [Images] im1 " + "ON ex1.[Record ID] = im1.[Record ID]) imQuery "
				+ "WHERE RowNum = 1) imFinal " + "ON p.[Person ID] = imFinal.[Person ID] " + "LEFT OUTER JOIN "
				+ "(SELECT [Person ID], [Date of Visit],[Location Record] FROM "
				+ "(SELECT ex2.[Person ID], ex2.[Record ID], ex2.[Date of Visit],ex2.[Location Record], ROW_NUMBER() OVER  (PARTITION BY ex2.[Person ID] "
				+ "ORDER BY ex2.[Date of Visit] DESC,ex2.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex2)  exQuery " + "WHERE RowNum = 1) exFinal "
				+ "ON  p.[Person ID] = exFinal.[Person ID] "
				+ "WHERE exFinal.[Location Record] = (?) AND p.[First Name] LIKE (?) AND p.[Last Name] LIKE (?)  "
				+ "AND p.[Date of Birth] LIKE (?) AND p.Sex LIKE (?) " + "ORDER BY p.[Last Name], p.[First Name];";

	}

	/**
	 * Select basic or static patient information that matches input data or the
	 * patient ids received from image recognition
	 *
	 * @param newPatientFirstName The current patient's first name input by the
	 *                            user.
	 * @param newPatientLastName  The current patient's last name input by the user.
	 * @param newPatientSex       The current patient's sex input by the user.
	 * @param newPatientDOB       The current patient's date of birth input by the
	 *                            user.
	 * @param personIds           A list of possible matching patient ids from
	 *                            facial recognition
	 */
	void selectWithFaceQuery() {
		System.out.println("Start");
		System.out.println(queryString);
		queryString += "SELECT Top " + ClassConstants.MAX_RESULTS + " p.[Person ID], p.[First Name],"
				+ "p.[Last Name],p.[Date of Birth],p.[Sex],p.[Discharged],p.[Current Triage],"
				+ "imFinal.ImageBitmap, exFinal.[Date of Visit], exFinal.[Location Record] " + "FROM Person p "
				+ "LEFT OUTER JOIN " + "(SELECT [Person ID], [Bitmap] AS ImageBitmap FROM "
				+ "(SELECT ex1.[Person ID], im1.[Bitmap], ROW_NUMBER() OVER  (PARTITION BY ex1.[Person ID] "
				+ "ORDER BY ex1.[Date of Visit] DESC,ex1.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex1 INNER JOIN [Images] im1 " + "ON ex1.[Record ID] = im1.[Record ID]) imQuery "
				+ "WHERE RowNum = 1) imFinal " + "ON p.[Person ID] = imFinal.[Person ID] " + "LEFT OUTER JOIN "
				+ "(SELECT [Person ID], [Date of Visit] ,[Location Record] FROM "
				+ "(SELECT ex2.[Person ID], ex2.[Record ID], ex2.[Date of Visit],ex2.[Location Record], ROW_NUMBER() OVER  (PARTITION BY ex2.[Person ID] "
				+ "ORDER BY ex2.[Date of Visit] DESC,ex2.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex2)  exQuery " + "WHERE RowNum = 1) exFinal "
				+ "ON  p.[Person ID] = exFinal.[Person ID] "
				+ "WHERE exFinal.[Location Record] = (?) AND ((p.[First Name] LIKE (?) AND p.[Last Name] LIKE (?)  "
				+ "AND p.[Date of Birth] LIKE (?) AND p.Sex LIKE (?)) ";

		StringBuilder stringBuilder = new StringBuilder(queryString);
		System.out.println("Parameters: " + Integer.toString(parameterArray.size()));
		Iterator<Object> listIterator = parameterArray.iterator();
		listIterator.next();
		listIterator.next();
		listIterator.next();
		listIterator.next();
		listIterator.next();
		while (listIterator.hasNext()) {
			stringBuilder.append(" OR p.[Person ID] = (?)");
			listIterator.next();
		}
		queryString = stringBuilder.append(") ORDER BY p.[Last Name], p.[First Name];").toString();
		System.out.println("End");
		System.out.println(queryString);
	}

	void selectOnlyFaceQuery() {
		System.out.println("Start");
		System.out.println(queryString);
		queryString += "SELECT Top " + ClassConstants.MAX_RESULTS + " p.[Person ID], p.[First Name],"
				+ "p.[Last Name],p.[Date of Birth],p.[Sex],p.[Discharged],p.[Current Triage],"
				+ "imFinal.ImageBitmap, exFinal.[Date of Visit], exFinal.[Location Record] " + "FROM Person p "
				+ "LEFT OUTER JOIN " + "(SELECT [Person ID], [Bitmap] AS ImageBitmap FROM "
				+ "(SELECT ex1.[Person ID], im1.[Bitmap], ROW_NUMBER() OVER  (PARTITION BY ex1.[Person ID] "
				+ "ORDER BY ex1.[Date of Visit] DESC,ex1.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex1 INNER JOIN [Images] im1 " + "ON ex1.[Record ID] = im1.[Record ID]) imQuery "
				+ "WHERE RowNum = 1) imFinal " + "ON p.[Person ID] = imFinal.[Person ID] " + "LEFT OUTER JOIN "
				+ "(SELECT [Person ID], [Date of Visit],[Location Record] FROM "
				+ "(SELECT ex2.[Person ID], ex2.[Record ID], ex2.[Date of Visit],ex2.[Location Record], ROW_NUMBER() OVER  (PARTITION BY ex2.[Person ID] "
				+ "ORDER BY ex2.[Date of Visit] DESC,ex2.[Record ID] DESC) AS RowNum "
				+ "FROM [Exam Record] ex2)  exQuery " + "WHERE RowNum = 1) exFinal "
				+ "ON  p.[Person ID] = exFinal.[Person ID] "
				+ "WHERE exFinal.[Location Record] = (?) AND (p.[Person ID] = (?)";

		StringBuilder stringBuilder = new StringBuilder(queryString);
		System.out.println("Parameters: " + Integer.toString(parameterArray.size()));
		Iterator<Object> listIterator = parameterArray.iterator();
		listIterator.next();
		listIterator.next();
		
		while (listIterator.hasNext()) {
			stringBuilder.append(" OR p.[Person ID] = (?)");
			listIterator.next();
		}
		queryString = stringBuilder.append(") ORDER BY p.[Last Name], p.[First Name];").toString();
		System.out.println("End");
		System.out.println(queryString);
	}

	/**
	 * @param currentPersonId
	 */
	void selectPatientRecordsQuery() {

		queryString += "SELECT Top " + ClassConstants.MAX_RESULTS + " exFinal.[Triage], "
				+ "imFinal.ImageBitmap, exFinal.[Date of Visit],exFinal.[Record ID], exFinal.[Location Record]   FROM Person p "
				+ "INNER JOIN " + "(SELECT ex2.[Person ID], ex2.Triage,ex2.[Record ID], ex2.[Date of Visit],ex2.[Location Record] "
				+ "FROM [Exam Record] ex2) exFinal " + "ON  p.[Person ID] = exFinal.[Person ID] " + "LEFT OUTER JOIN "
				+ "(SELECT [Person ID],[Record ID],[Bitmap] AS ImageBitmap " + "FROM [Images]) imFinal "
				+ "ON exFinal.[Record ID] = imFinal.[Record ID] "
				+ "WHERE exFinal.[Location Record] = (?) AND p.[Person ID]=(?) "
				+ "ORDER BY p.[Last Name] DESC, p.[First Name];";
	}

	/**
	 * Select the general exam record data and the Haiti exam record data for the
	 * selected record ID
	 *
	 * @param examRecordId the record ID for the current exam record
	 */
	void selectHaitiExamRecordQuery() {
		queryString += "SELECT DISTINCT [Exam Record].[Person ID]," + "[Exam Record].[Triage], "
				+ "[Exam Record].[Date of Visit], " + "[Exam Record].[Chart Number], " + "[Haiti Record].[Village], "
				+ "[Exam Record].[Body Temperature], " + "[Exam Record].[Diastolic BP], "
				+ "[Exam Record].[Systolic BP], " + "[Exam Record].[Height], " + "[Exam Record].[Weight], "
				+ "[Exam Record].[BMI], " + "[Haiti Record].[Hemoglobin], " + "[Haiti Record].[History DmDx], "
				+ "[Haiti Record].[History ETOH], " + "[Haiti Record].[History FamilyHxDm], "
				+ "[Haiti Record].[History FamilyHxHTN], " + "[Haiti Record].[History FamilyHxStroke], "
				+ "[Haiti Record].[History HTNDx], " + "[Haiti Record].[History Polydipsia], "
				+ "[Haiti Record].[History Polyphagia], " + "[Haiti Record].[History Polyuria], "
				+ "[Haiti Record].[History Smoker], " + "[Haiti Record].[History Stroke], "
				+ "[Exam Record].[Other History], " + "[Exam Record].[Diet], " + "[Haiti Record].[Amount of Maggi], "
				+ "[Exam Record].[Chief Complaint], " + "[Exam Record].[History of Present Illness], "
				+ "[Exam Record].[Assessment], " + "[Exam Record].[Physical Examination],"
				+ "[Exam Record].[Treatment Plan], " + "[Exam Record].[Clinician First Name], "
				+ "[Exam Record].[Clinician Last Name]," + "[Exam Record].[Prescription],"
				+ "[Exam Record].[Signature]," + "[Images].[Bitmap] " + "FROM [Exam Record]"
				+ "LEFT OUTER JOIN [Haiti Record] ON [Exam Record].[Record ID]=[Haiti Record].[Record ID]"
				+ "LEFT OUTER JOIN " + "[Images] ON Images.[Record ID] = [Exam Record].[Record ID] WHERE "
				+ "[Exam Record].[Record ID] = (?);";

	}

	/**
	 * Select the general exam record data without a specific location for the
	 * selected record ID
	 *
	 * @param examRecordId the record ID for the current exam record
	 */
	void selectExamRecordQuery() {
		queryString += "SELECT [Exam Record].[Person ID]," + "[Exam Record].[Triage], "
				+ "[Exam Record].[Date of Visit], " + "[Exam Record].[Chart Number], "
				+ "[Exam Record].[Body Temperature], " + "[Exam Record].[Diastolic BP], "
				+ "[Exam Record].[Systolic BP], " + "[Exam Record].[Height], " + "[Exam Record].[Weight], "
				+ "[Exam Record].[BMI], " + "[Exam Record].[Other History], " + "[Exam Record].[Diet], "
				+ "[Exam Record].[Chief Complaint], " + "[Exam Record].[History of Present Illness], "
				+ "[Exam Record].[Assessment], " + "[Exam Record].[Physical Examination],"
				+ "[Exam Record].[Treatment Plan], " + "[Exam Record].[Clinician First Name], "
				+ "[Exam Record].[Clinician Last Name]," + "[Exam Record].[Prescription]," + "[Exam Record].[Signature]," 
				+ "[Images].[Bitmap] " + "FROM [Exam Record] "
				+ "LEFT OUTER JOIN " + "[Images] ON Images.[Record ID] = [Exam Record].[Record ID] "
				+ "WHERE [Exam Record].[Record ID] = (?);";
	}

	/**
	 * @param examFirstName
	 * @param examLastName
	 * @param examDOB
	 * @param examSex
	 * @param examPersonId
	 */
	void updateBasicQuery() {
		queryString += "UPDATE dbo.Person SET[First Name]=(?),[Last Name]=(?),"
				+ "[Date of Birth]=(?),[Sex]=(?),[Discharged] = (?),[Current Triage] = (?) WHERE [Person ID] = (?);";

	}

	void updateExamRecordQuery() {
		queryString += "UPDATE [dbo].[Exam Record] " + "SET " + "[Triage]=" + "(?)" + ",[Date of Visit]=" + "(?)"
				+ ",[Chart Number]=" + "(?)" + ",[Body Temperature]=" + "(?)" + ",[Diastolic BP]=" + "(?)"
				+ ",[Systolic BP]=" + "(?)" + ",[Height]=" + "(?)" + ",[Weight]=" + "(?)" + ",[BMI]=" + "(?)"
				+ ",[Other History]=" + "(?)" + ",[Diet]=" + "(?)" + ",[Chief Complaint]=" + "(?)"
				+ ",[History of Present Illness]=" + "(?)" + ",[Assessment]=" + "(?)" + ",[Physical Examination]="
				+ "(?)" + ",[Treatment Plan]=" + "(?)" + ",[Clinician First Name]=" + "(?)" + ",[Clinician Last Name]="
				+ "(?)" + ",[Prescription]=" + "(?)" + ",[Signature]=" + "(?)" + ",[Location Record]=" + "(?)" + " "
				+ "WHERE [Record ID] = (?);";
	}

	/**
	 * @param examVillage
	 * @param examHemoglobin
	 * @param examDmDx
	 * @param examETOH
	 * @param examHTNDx
	 * @param examFamilyhxDm
	 * @param examFamilyhxHTN
	 * @param examFamilyhxStroke
	 * @param examPolydipsia
	 * @param exampolyphagia
	 * @param examPolyuria
	 * @param examSmoker
	 * @param examStroke
	 * @param examAmountOfMaggi
	 * @param examTreatmentPlan
	 * @param examRecordId
	 */
	void updateHaitiExamRecordQuery() {
		queryString += "UPDATE [dbo].[Haiti Record] " + "SET " + "[Village]=" + "(?)" + ",[Hemoglobin]=" + "(?)"
				+ ",[History DmDx]=" + "(?)" + ",[History ETOH]=" + "(?)" + ",[History FamilyhxDm]=" + "(?)"
				+ ",[History FamilyhxHTN]=" + "(?)" + ",[History FamilyhxStroke]=" + "(?)" + ",[History HTNDx]=" + "(?)"
				+ ",[History Polydipsia]=" + "(?)" + ",[History polyphagia]=" + "(?)" + ",[History Polyuria]=" + "(?)"
				+ ",[History Smoker]=" + "(?)" + ",[History Stroke]=" + "(?)" + ",[Amount of Maggi]=" + "(?)"
				+ " WHERE [Record ID] = (?);";
	}

	/**
	 * Written so that makeHaitiRecord and makeExamRecord can be called to add to
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
	void makeExamRecordQuery() {
		queryString += "Declare @Id1 int;Set @Id1 = (?);Declare @Id2 int;INSERT INTO [dbo].[Exam Record] ("
				+ "[Person ID]," + "[Triage]," + "[Date of Visit]," + "[Chart Number]," + "[Body Temperature],"
				+ "[Diastolic BP]," + "[Systolic BP]," + "[Height]," + "[Weight]," + "[BMI]," + "[Other History],"
				+ "[Diet]," + "[Chief Complaint]," + "[History of Present Illness]," + "[Assessment],"
				+ "[Physical Examination]," + "[Treatment Plan]," + "[Clinician First Name]," + "[Clinician Last Name],"
				+ "[Prescription]," + "[Signature]," + "[Location Record])" + "VALUES ((?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));Set @Id2 = SCOPE_IDENTITY();";
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
	 * @param exampolyphagia
	 * @param examPolydipsia
	 * @param examPolyuria
	 * @param examSmoker
	 * @param examETOH
	 * @param examAmountOfMaggi
	 * @param examTreatmentPlan
	 */
	void makeHaitiRecordQuery() {
		queryString += "INSERT INTO dbo.[Haiti Record] (" + "[Person ID]," + "[Record ID]," + "[Village],"
				+ "[Hemoglobin]," + "[History DmDx]," + "[History ETOH]," + "[History FamilyHxDm],"
				+ "[History FamilyHxHTN]," + "[History FamilyHxStroke]," + "[History HTNDx]," + "[History Polydipsia],"
				+ "[History polyphagia]," + "[History Polyuria]," + "[History Smoker]," + "[History Stroke],"
				+ "[Amount of Maggi]" + ")"
				+ "VALUES (@Id1,@Id2,(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";
	}

	/**
	 * @param examFirstName
	 * @param examLastName
	 * @param examDOB
	 * @param examSex
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
	void makeBothRecordsQuery() {
		queryString += "Declare @Id1 int;Declare @Id2 int;INSERT INTO [dbo].[Person] (" + "[First Name],"
				+ "[Last Name]," + "[Date of Birth]," + "[Sex]," + "[Discharged]," + "[Current Triage])"
				+ "VALUES ((?),(?),(?),(?),(?),(?));" + "Set @Id1 = SCOPE_IDENTITY();INSERT INTO [dbo].[Exam Record] ("
				+ "[Person ID]," + "[Triage]," + "[Date of Visit]," + "[Chart Number]," + "[Body Temperature],"
				+ "[Diastolic BP]," + "[Systolic BP]," + "[Height]," + "[Weight]," + "[BMI]," + "[Other History],"
				+ "[Diet]," + "[Chief Complaint]," + "[History of Present Illness]," + "[Assessment],"
				+ "[Physical Examination]," + "[Treatment Plan]," + "[Clinician First Name]," + "[Clinician Last Name],"
				+ "[Prescription]," + "[Signature]," + "[Location Record])" + " VALUES (@Id1,(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));Set @Id2 = SCOPE_IDENTITY();";

	}

	/**
	 * Untested TODO: change query to insert record ID Inserts a thumbnail and an
	 * encoding of an image received from facial recognition into a newly created
	 * record
	 * 
	 * @param bitmap          an image stored as a byte array containing a thumbnail
	 *                        bitmap of the patient
	 * @param currentEncoding the image encoding array received from facial
	 *                        recognition
	 */
	void makeImageRecordQuery() {
		queryString += "INSERT INTO [dbo].[Images] ([Person ID],[Record ID],[Bitmap],"
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
				+ "[Num 126],[Num 127],[Num 128])" + "VALUES (@Id1,@Id2,(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),"
				+ "(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?));";
		System.out.println(queryString);
	}

	void getLogin() {
		queryString += "SELECT Top 1 * FROM [Administrators] WHERE [User ID] = (?) AND [Password] = (?)";
	}

	void setLogin() {
		queryString += "INSERT INTO Person ([First Name],[Last Name]) Values ((?),(?))"
				+ "INSERT INTO Administrators ([Person ID],[User ID],[Password],[Location],[Privileges]) "
				+ "VALUES (SCOPE_IDENTITY(),(?),(?),(?),(?))";
	}
}
