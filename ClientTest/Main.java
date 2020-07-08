package com.example.medley.medicalrecord;

import java.util.Date;

import javax.imageio.ImageIO;

import org.threeten.bp.LocalDate;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * Test class for database communications over socket server
 * 
 * Different query combination selected each time, observed response verified in print logs
 *
 */
public class Main {
	public static void main(String args[]) {

		LocalDate examDateOfVisit = LocalDate.of(2019, 04, 03);
		LocalDate examDOB = LocalDate.of(2019, 04, 03);

		String examFirstName = "Ranger";
		String examLastName = "Medley";
		String examSex = "Male";
		String examVillage = "a";
		Integer examHemoglobin = null;
		Boolean examDmDx = true;
		Boolean examETOH = true;
		Boolean examHTNDx = true;
		Boolean examFamilyhxDm = true;
		Boolean examFamilyhxHTN = true;
		Boolean examFamilyhxStroke = true;
		Boolean examPolydipsia = true;
		Boolean examPolyphasia = true;
		Boolean examPolyuria = true;
		Boolean examSmoker = true;
		Boolean examStroke = true;
		int examAmountOfMaggi = 8;
		// String examTreatmentPlan = "a";
		String examLocation = "Nowhere";
		String examTreatmentPlan = "Rest";

		String examTriage = "sure";
		int examChartNumber = 41;
		float examBodyTemperature = 98.6f;
		int examDiastolicBP = 9;
		int examSystolicBP = 5;
		int examHeight = 6;
		int examWeight = 9;
		float examBMI = 8;
		String examOtherHistory = "sure";
		String examDiet = "sure";
		String examChiefComplaint = "sure";
		String examHistPresentIll = "sure";
		String examAssessment = "sure";
		String examPhysicalExam = "sure";
		String examClinicianFirst = "sure";
		String examClinicianLast = "sure";
		String examPrescription = "sure";
		String examSignature = "sure";
		String examLocationRecord = "sure";
		ArrayList<Integer> personIds = new ArrayList<Integer>();
		personIds.add(1);
		personIds.add(4);
		personIds.add(5);

		// java.sql.Date date = new java.sql.Date(dateTemp.getTime());
		ClassQueryMakers queryMaker = new ClassQueryMakers();
		queryMaker.selectBasicQuery("%", "%", null, "%");

		/*
		 * queryMaker.makeBothRecordsQuery(examFirstName, examLastName, examDOB,
		 * examSex, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
		 * examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI,
		 * examOtherHistory, examDiet, examChiefComplaint, examHistPresentIll,
		 * examAssessment, examPhysicalExam, examClinicianFirst, examClinicianLast,
		 * examPrescription, examSignature, examLocationRecord);
		 */
		// queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx,
		// examETOH, examHTNDx, examFamilyhxDm,
		// examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, examPolyphasia,
		// examPolyuria, examSmoker,
		// examStroke, examAmountOfMaggi, examTreatmentPlan);
		// queryMaker.makeDefaultRecordQuery(examLocation, examTreatmentPlan);
		// queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex,
		// 99085);
		// queryMaker.selectHaitiExamRecordQuery(99054);
		// queryMaker.updateExamRecordQuery(examTriage, examDateOfVisit,
		// examChartNumber, examBodyTemperature,
		// examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI,
		// examOtherHistory, examDiet,
		// examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam,
		// examClinicianFirst,
		// examClinicianLast, examPrescription, examSignature, examLocationRecord,
		// 2003);
		// queryMaker.updateHaitiExamRecordQuery(examVillage, examHemoglobin, examDmDx,
		// examETOH,
		// examHTNDx, examFamilyhxDm, examFamilyhxHTN, examFamilyhxStroke,
		// examPolydipsia, examPolyphasia, examPolyuria, examSmoker,
		// examStroke, examAmountOfMaggi, examTreatmentPlan, 2003);
		// queryMaker.selectWithFaceRecognitionQuery(personIds);

		ClassConnectionHelper connServer = new ClassConnectionHelper(queryMaker);

		ClassConnectionHelper.AsyncNew asyncNew = connServer.new AsyncNew();
		connServer.databaseConnect();
		connServer.myQuery();
		// connServer.myUpdate();
		try {

			connServer.connection.commit();
			asyncNew.getMyResults.getResults(connServer.resultSet, connServer.resultSet.getMetaData());

			int response = connServer.updateResponse;

			System.out.println("affected:" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String file_name = "C:\\Images\\Input\\hyunji_pic.jpg";
		File file = new File(file_name);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
		byte[] bytes = byteArrayOutputStream.toByteArray();

		System.out.println(size.toString());
		String s = new String(bytes);
		System.out.println(s);

	}
}
