package com.example.medley.medicalrecord;

import java.util.Date;
import java.util.Iterator;

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
import java.util.Arrays;
import java.util.Calendar;

public class Main {
	static Long before;
	static Long after;
	static Long elapsed;
	static int queryIdentifier;
	static ClientForJavaSocket2 testSocketHandler;

	public static void main(String args[]) {
		String username = "jonathan";
		String password = "medley";
		
		ArrayList<Long> elapsedList = new ArrayList<Long>();
		LocalDate examDateOfVisit = LocalDate.of(2019, 04, 03);
		LocalDate examDOB = LocalDate.of(1997, 03, 05);

		String examFirstName = "Jon";
		String examLastName = "Medley";
		String examSex = "Male";
		String examVillage = "Washington DC";
		String examCurrentTriage = "mh mhx";
		Integer examHemoglobin = null;
		Boolean examDischarged = true;
		Boolean examDmDx = true;
		Boolean examETOH = true;
		Boolean examHTNDx = true;
		Boolean examFamilyhxDm = true;
		Boolean examFamilyhxHTN = true;
		Boolean examFamilyhxStroke = true;
		Boolean examPolydipsia = true;
		Boolean exampolyphagia = true;
		Boolean examPolyuria = true;
		Boolean examSmoker = true;
		Boolean examStroke = true;
		int examAmountOfMaggi = 8;
		String examTreatmentPlan = null;
	

		String examTriage = "mh mhx";
		int examChartNumber = 1;
		float examBodyTemperature = 3f;
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

		String file_name = "C:\\Images\\Input\\hyunji_pic.jpg";
		File file = new File(file_name);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch
			e.printStackTrace();
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		//byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
		byte[] bytes = byteArrayOutputStream.toByteArray();
		
		Double[] encodingArray = {-0.15382788 , 0.00816037 , 0.0538962 , -0.05175451 , -0.14083922 , 0.07377105,
                -0.10023099 , -0.04121803 , 0.1347629   , -0.1213267  , 0.14709429  , 0.09005648,
                -0.15921442 , -0.01715159 , -0.05823774 , 0.07480818  , -0.10787214 ,- 0.07707427,
                -0.04753904 , -0.09108765 , -0.01276829 , 0.04771585  , -0.0127448  , 0.0270322,
                -0.10044438 , -0.33767584 , -0.08319399 , -0.08555525 , 0.1344786   , -0.0822144,
                -0.071601   , -0.0571208  , -0.17422314 , -0.00684046 , 0.0257952   , 0.18566766,
                -0.00146725 , -0.03476365 , 0.15320933  , -0.00143736 , -0.12871712 , -0.03651862,
                 0.03911775 , 0.21156368  , 0.15555206  , 0.022697    , 0.10007614  , -0.0955667,
                 0.17104557 , -0.23166944 , 0.07824943  , 0.08321585  , 0.08797862  , 0.05046749,
                 0.05812386 , -0.19110918 , -0.04029935 , 0.18673098  , -0.12307521 , 0.14690334,
                 0.00511312 , 0.01165629  , -0.02542832 , -0.00904295 , 0.23770514  , 0.10324246,
                -0.13781756 , -0.20774628 , 0.16817731  , -0.20280546 , 0.03702178  , 0.10769756,
                -0.05422088 , -0.18155663 , -0.1997437  , 0.11683324  , 0.50590283  , 0.18388778,
                -0.11011137 , 0.02309527  , -0.13137795 , -0.11061931 , 0.06805475  , 0.072898,
                -0.07072275 , 0.04825223  , -0.06043788 , 0.07611954  , 0.27555624  , 0.03084761,
                0.05009493  , 0.1712113   , 0.02884733  , 0.11866401  , -0.00502058 , 0.0857092,
                -0.15470186 , 0.02421967  , -0.08775298 , -0.01396845 , 0.00230809  , - 0.09263307,
                0.10649296  , 0.08512437  , -0.15350103 , 0.1708698   , -0.04955552 , - 0.03566932,
                0.01902231  , 0.03205912  , -0.12246352 , -0.0460559  , 0.20764986  , -0.2675935,
                0.17459634  , 0.18082869  , 0.08673508  , 0.18978776  , 0.12260341  , 0.082412,
                0.08611859  , -0.03215224 , -0.12480718 , -0.01073789 , -0.03434595 , -0.12347004,
                0.02106265  , 0.01916024};
		ArrayList<Double> currentEncoding = new ArrayList<Double>(Arrays.asList(encodingArray));
		
		ClassQueryMakers queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.getLogin(username,password);
		queryIdentifier = 0;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		 queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectBasicQuery("%", "%", null, "%");
		queryIdentifier = 1;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectBasicQuery("examFirstName", "examLastName", examDOB, "examSex");
		queryIdentifier = 1;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectWithFaceQuery(examFirstName, examLastName, examDOB, examSex, personIds);
		queryIdentifier = 2;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectPatientRecordsQuery(99085);
		queryIdentifier = 3;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectHaitiExamRecordQuery(14271);
		queryIdentifier = 4;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.selectExamRecordQuery(14271);
		queryIdentifier = 5;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examCurrentTriage,
				99085);
		queryMaker.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord, 85195);
		queryMaker.updateHaitiExamRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx,
				examFamilyhxDm, examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria,
				examSmoker, examStroke, examAmountOfMaggi, 85195);
		queryIdentifier = 6;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examCurrentTriage,
				99085);
		queryMaker.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord, 85195);
		queryIdentifier = 7;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan,examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord, 85195);
		queryMaker.updateHaitiExamRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx,
				examFamilyhxDm, examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria,
				examSmoker, examStroke, examAmountOfMaggi,  85195);
		queryIdentifier = 8;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateExamRecordQuery(examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam,  examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord, 85195);
		queryIdentifier = 9;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examTriage, 14271);
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam,  examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 10;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examTriage, 14271);
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam,  examTreatmentPlan,examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 11;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 12;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
	
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 13;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examTriage, 14271);
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		
		queryIdentifier = 14;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.updateBasicQuery(examFirstName, examLastName, examDOB, examSex, examDischarged, examTriage, 14271);
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryIdentifier = 15;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		queryIdentifier = 16;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeExamRecordQuery(14271, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature,
				examDiastolicBP, examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet,
				examChiefComplaint, examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst,
				examClinicianLast, examPrescription, examSignature, examLocationRecord);
		queryIdentifier = 17;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeBothRecordsQuery(examFirstName, examLastName, examDOB, examSex, examDischarged,
				examCurrentTriage, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature, examDiastolicBP,
				examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet, examChiefComplaint,
				examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst, examClinicianLast,
				examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 18;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeBothRecordsQuery(examFirstName, examLastName, examDOB, examSex, examDischarged,
				examCurrentTriage, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature, examDiastolicBP,
				examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet, examChiefComplaint,
				examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst, examClinicianLast,
				examPrescription, examSignature, examLocationRecord);
		queryMaker.makeImageRecordQuery(bytes, currentEncoding);
		queryIdentifier = 19;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeBothRecordsQuery(examFirstName, examLastName, examDOB, examSex, examDischarged,
				examCurrentTriage, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature, examDiastolicBP,
				examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet, examChiefComplaint,
				examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst, examClinicianLast,
				examPrescription, examSignature, examLocationRecord);
		queryMaker.makeHaitiRecordQuery(examVillage, examHemoglobin, examDmDx, examETOH, examHTNDx, examFamilyhxDm,
				examFamilyhxHTN, examFamilyhxStroke, examPolydipsia, exampolyphagia, examPolyuria, examSmoker,
				examStroke, examAmountOfMaggi);
		queryIdentifier = 20;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);

		queryMaker = new ClassQueryMakers();
		before = new Long(0);
		before = System.nanoTime();
		queryMaker.makeBothRecordsQuery(examFirstName, examLastName, examDOB, examSex, examDischarged,
				examCurrentTriage, examTriage, examDateOfVisit, examChartNumber, examBodyTemperature, examDiastolicBP,
				examSystolicBP, examHeight, examWeight, examBMI, examOtherHistory, examDiet, examChiefComplaint,
				examHistPresentIll, examAssessment, examPhysicalExam, examTreatmentPlan, examClinicianFirst, examClinicianLast,
				examPrescription, examSignature, examLocationRecord);
	
		queryIdentifier = 21;
		execute(queryMaker, queryIdentifier);
		elapsed = after - before;
		elapsedList.add(elapsed);
		
		
		Iterator<Long> iterator = elapsedList.iterator();
		while (iterator.hasNext()) {
			long longTime = (Long) iterator.next();
			float time = longTime;
			System.out.println(time / 1000000000);
		}
		
	}

	static void execute(ClassQueryMakers queryMaker, int queryIdentifier) {
		testSocketHandler = new ClientForJavaSocket2(queryMaker, queryIdentifier);
		try {
			testSocketHandler.cThread.join();
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		after = System.nanoTime();
	}

}
