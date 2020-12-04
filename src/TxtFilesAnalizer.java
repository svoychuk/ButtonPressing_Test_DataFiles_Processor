import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtFilesAnalizer {

	static String sex = "Not available";
	static int yearOfTest = 0;
	static String pivotTableURL = "D:\\Machine Learning/Java_Transformed/Pivot_table.txt";
	static String pivotTableForStatisticsURL = "D:\\Machine Learning/Java_Transformed/Pivot_table_for_Statistics.txt";
	static String testURL = "D:\\Machine Learning/Java_Transformed/test.txt"; // this element is not important and can be deleted at any time
	static String typeOfDesease = "dis_control";
	static String personID = "";
	static String fileName = "";
	static int currentDate = Calendar.getInstance().get(Calendar.YEAR);
	
	static Map<String, Number> left_1_SE = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> left_1_SS = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> left_1_EE = new HashMap<>(); // will contain both int and double values
	
	static Map<String, Number> left_2_SE = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> left_2_SS = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> left_2_EE = new HashMap<>(); // will contain both int and double values
	
	static Map<String, Number> right_1_SE = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> right_1_SS = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> right_1_EE = new HashMap<>(); // will contain both int and double values
	
	static Map<String, Number> right_2_SE = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> right_2_SS = new HashMap<>(); // will contain both int and double values
	static Map<String, Number> right_2_EE = new HashMap<>(); // will contain both int and double values
	
	static Map<String, Integer> bugs = new HashMap<>(); // will contain the quantity of "bugs" in the source *.txt data files
	
	/* This static block creates each time the fresh *.txt files in the D:\\Machine Learning/Java_Transformed/ directory 
	 * to store results of the source files processing. 
	 * */
	static {
		try (BufferedWriter bwPivotTable = new BufferedWriter(new FileWriter(pivotTableURL));
				/* bwTest is for occasional use if need to create or check work of a separate tested element thus it
				* not necessary element and can be deleted at any time without impact on any result 
				*/
				BufferedWriter bwTest = new BufferedWriter(new FileWriter(testURL));
				BufferedWriter bwPivotTableForStat = new BufferedWriter(new FileWriter(pivotTableForStatisticsURL))
				) {
			String head_1 = "Hand\tStart\tDiffSE\tDiffSS\tDiffEE\tSEX\tYearOfBirth\tTypeOfDesease"; 
			bwPivotTable.write(head_1);
			bwPivotTable.newLine();
			String head_2 = "File_Name\t"
					+ "Bugs_Right_1\t Bugs_Left_1\t Bugs_Right_2\t Bugs_Left_2\t"
					+ "Person ID\t"
					+ "TypeOfDesease\t"
					+ "SEX\t"
					+ "YearOfTest\t"
					
					+ "R1SE_Q1_Mean \t R1SE_Q2_Mean \t R1SE_Q3_Mean \t R1SE_Q4_Mean \t"
					+ "R1SS_Q1_Mean \t R1SS_Q2_Mean \t R1SS_Q3_Mean \t R1SS_Q4_Mean \t"
					+ "R1EE_Q1_Mean \t R1EE_Q2_Mean \t R1EE_Q3_Mean \t R1EE_Q4_Mean \t"

					+ "R2SE_Q1_Mean \t R2SE_Q2_Mean \t R2SE_Q3_Mean \t R2SE_Q4_Mean \t"
					+ "R2SS_Q1_Mean \t R2SS_Q2_Mean \t R2SS_Q3_Mean \t R2SS_Q4_Mean \t"
					+ "R2EE_Q1_Mean \t R2EE_Q2_Mean \t R2EE_Q3_Mean \t R2EE_Q4_Mean \t"
					
					+ "L1SE_Q1_Mean \t L1SE_Q2_Mean \t L1SE_Q3_Mean \t L1SE_Q4_Mean \t"
					+ "L1SS_Q1_Mean \t L1SS_Q2_Mean \t L1SS_Q3_Mean \t L1SS_Q4_Mean \t"
					+ "L1EE_Q1_Mean \t L1EE_Q2_Mean \t L1EE_Q3_Mean \t L1EE_Q4_Mean \t"
					
					+ "L2SE_Q1_Mean \t L2SE_Q2_Mean \t L2SE_Q3_Mean \t L2SE_Q4_Mean \t"
					+ "L2SS_Q1_Mean \t L2SS_Q2_Mean \t L2SS_Q3_Mean \t L2SS_Q4_Mean \t"
					+ "L2EE_Q1_Mean \t L2EE_Q2_Mean \t L2EE_Q3_Mean \t L2EE_Q4_Mean \t"
					
					+ "Right_1_SE median\t"
					+ "Left_1_SE median\t"
					+ "Right_2_SE median\t"
					+ "Left_2_SE medain\t"
					+ "Right_1_SE mean\tRight_1_SE Sd\tRight_1_SE CV\t"
					+ "Left_1_SE mean\tLeft_1_SE Sd\tLeft_1_SE CV\t"
					+ "Right_2_SE mean\tRight_2_SE Sd\tRight_2_SE CV\t"
					+ "Left_2_SE mean\tLeft_2_SE Sd\tLeft_2_SE CV\t"
//					+ "Right_1_SE max\tLeft_1_SE max\tRight_2_SE max\tLeft_2_SE max\t"
					+ "Right_1_SE min\tLeft_1_SE min\tRight_2_SE min\tLeft_2_SE min\t"
					
					+ "Right_1_SS median\t"
					+ "Left_1_SS median\t"
					+ "Right_2_SS median\t"
					+ "Left_2_SS medain\t"
					+ "Right_1_SS mean\tRight_1_SS Sd\tRight_1_SS CV\t"
					+ "Left_1_SS mean\tLeft_1_SS Sd\tLeft_1_SS CV\t"
					+ "Right_2_SS mean\tRight_2_SS Sd\tRight_2_SS CV\t"
					+ "Left_2_SS mean\tLeft_2_SS Sd\tLeft_2_SS CV\t"
//					+ "Right_1_SS max\tLeft_1_SS max\tRight_2_SS max\tLeft_2_SS max\t"
					+ "Right_1_SS min\tLeft_1_SS min\tRight_2_SS min\tLeft_2_SS min\t"
					
					+ "Right_1_EE median\t"
					+ "Left_1_EE median\t"
					+ "Right_2_EE median\t"
					+ "Left_2_EE medain\t"
//					+ "Right_1_EE mean\tRight_1_EE Sd\tRight_1_EE CV\t"
//					+ "Left_1_EE mean\tLeft_1_EE Sd\tLeft_1_EE CV\t"
//					+ "Right_2_EE mean\tRight_2_EE Sd\tRight_2_EE CV\t"
//					+ "Left_2_EE mean\tLeft_2_EE Sd\tLeft_2_EE CV\t"
//					+ "Right_1_EE max\tLeft_1_EE max\tRight_2_EE max\tLeft_2_EE max\t"
					+ "Right_1_EE min\tLeft_1_EE min\tRight_2_EE min\tLeft_2_EE min\t"
					; 
			
			bwPivotTableForStat.write(head_2);
			bwPivotTableForStat.newLine();
			
			String head_3 = "DiffSS \t DiffEE";
			bwTest.write(head_3);
			bwTest.newLine();
			
		} catch (Exception e) {
			System.out.println("File for the output not found");
		}
	}
	public static void main(String[] args) {
		
		/*  These are the names of the folders that contain the source *.txt data files in the D:\\Machine Learning/ directory. 
		 	The same names will be used to crate new sub-folders in the same directory but in the Java_Transformed folder 
		 	that will contain the processed *.txt data files.
		*/
		String [] URLs = {
				"dis_control/", 
				"disease1/",
				"disease2/"
				};
		
		int numberOfFiles = 0; //Count and show how many files were processe in total
		
		for (String URL : URLs) {
			File directoryPath = new File("D:\\Machine Learning/" + URL);
			typeOfDesease = URL.replace("/", "");
			/*
			 *  List of all files in the directories
			 */
			File [] filesList = directoryPath.listFiles();
			for (File file : filesList) {
				System.out.println("File-Name: " + file.getName());
				fileName = file.getName();
				personID = file.getName().substring(0, 5);
				sex = getSexOfPerson(file);
				yearOfTest = getYearOfTest(file);
				proceedFile("D:\\Machine Learning/" + URL + file.getName(), "D:\\Machine Learning/Java_Transformed/" + URL + file.getName());
				numberOfFiles++;
				sex = "Not available";
//				break;
			}
//			break;
		}
		System.out.println("Number of processed Files = " + numberOfFiles);
	}

	public static int getYearOfTest(File file) {
		int year = 2000;
		while (year <= currentDate) {
			if (file.getName().contains(year + "-")) return year;
			year++;
		}
		return 0;
	}

	public static String getSexOfPerson(File file) {
		String fileName = file.getName().toLowerCase();
		return (fileName.contains("female") ||
				fileName.contains("fee") ||
				fileName.contains("fem") ||
				fileName.contains("fml")) ?
			"FEMALE" :
				(fileName.contains("male") || 
				fileName.contains("mle") ||
				fileName.contains("mae")) ? 
			"MALE" :
			"Not available";
	}

	public static void proceedFile(String sourceFileName, String destFileName) {
		/*These two arrays will store all Start and End values found in the *.txt data files for further processing*/
		java.util.List<String> valuesOfS = new java.util.ArrayList<>();
		java.util.List<String> valuesOfE = new java.util.ArrayList<>();
		/*
		 * The databases of calculated differences between: 
		 * 1. Start & End   -> diffSE
		 * 2. Start & Start -> diffSS
		 * 3. End 	& End   -> diffEE
		 * 
		 */
		List<Integer> diffSE = new ArrayList<>(); //Stores differences between every Start & End (diffSE = Start1 - End1)
		List<Integer> diffSS = new ArrayList<>(); //Stores differences between every Start & Start (diffSS = Start0 - Start1)
		List<Integer> diffEE = new ArrayList<>(); //Stores differences between every End & End (diffEE = End0 - End1)

		
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileName))){
			String line;
			int startAtLineBefore = 0; //This will store value of Start in the line before the next Start to find the diffSS
			int endAtLineBefore = 0; 	//This will store value of End in the line before the next End to find the diffEE
			
			int start = 0;		//
			int end = 0;		//
			int countS = 0;		//
			int countE = 0;		//
			int numberRight = 1;	//This used to add number "1" to String "Right_" to get "Right_1", later it will transform to number "2" to make "Right_2"
			int numberLeft = 1;		//This used to add number "1" to String "Left_" to get "Left_1", later it will transform to number "2" to make "Left_2"
			int indexOfValueOfSInArrayToCalculateMedianRight = 0; //This index indicates the number in the diffEE array from which statistics should be evaluated
			int indexOfValueOfSInArrayToCalculateMedianLeft = 0;
			String head = ""; // This accepts the "Right" or "Left" titles that used in the *.txt data files to separate various parts of the test.
			int bugsCountRight = 0; // Counts amount of "bugs" within each "Right" parts of the test.
			int bugsCountLeft = 0;  // Counts amount of "bugs" within each "Left" parts of the test.
			
			while ((line = br.readLine()) != null) {
				/*
				 * If data in the *.txt file starts with "Right" or "Left", they will be used to generate the corresponding heads 
				 * of titles in the valuesOfS and valuesOfE arrays and the corresponding "0" number will be generated in the diffSE 
				 * array to keep same size of all these three arrays. 
				 */
				if (line.contains("Right") || line.contains("Left")) {
					if (line.contains("Right")) {
						head = "Right";
						if (numberLeft == 2) {
							bugs.put("Bugs_Left_1", bugsCountLeft);
							bugsCountLeft = 0;
						}
						if (numberRight == 2) {
							bugs.put("Bugs_Right_1", bugsCountRight);	
							bugsCountRight = 0;
						}
						indexOfValueOfSInArrayToCalculateMedianRight = diffSE.size()+1;
						valuesOfS.add(line + "_" + numberRight);
						valuesOfE.add(line + "_" + numberRight);
						numberRight++;
						/*
						 * We do Calculations at the moment the new line with mark "Right" or "Left" appears. 
						 * Calculations are carried out for a part of data entered before. 
						 * Thus the range of the data to be statistically processed starts with the index of 
						 * the next line that will appear after the line with "Right" or "Left" marks 
						 * that is corresponds to the current size of the diffSE array plus 1 (diffSE.size()+1)
						 * and ends with the index of one line before the corresponding "Right" or "Left" marking lines
						 * that is corresponds to the current size of the diffSE array minus 1 (diffSE.size()-1)
						 */
						if (indexOfValueOfSInArrayToCalculateMedianLeft != 0) { 
							if (numberLeft == 2) {  		// Corresponds to Left_1
								calculate(left_1_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianLeft);
								calculate(left_1_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianLeft+1);
								calculate(left_1_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianLeft+1);
								indexOfValueOfSInArrayToCalculateMedianLeft = 0;
							}
//							if(numberLeft == 3) {			// Corresponds to Left_2
//								calculate(left_2_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianLeft);
//								calculate(left_2_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianLeft+1);
//								calculate(left_2_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianLeft+1);
//								indexOfValueOfSInArrayToCalculateMedianLeft = 0;
//							}
						}
					}
					if (line.contains("Left")) {
						head = "Left";
						if (numberRight == 3) {
							bugs.put("Bugs_Right_2", bugsCountRight);	
							bugsCountRight = 0;
						}
						
						indexOfValueOfSInArrayToCalculateMedianLeft = diffSE.size()+1;
						valuesOfS.add(line + "_" + numberLeft);
						valuesOfE.add(line + "_" + numberLeft);
						numberLeft++;
						
						if (indexOfValueOfSInArrayToCalculateMedianRight != 0) { 
							if (numberRight == 2) { 		// Corresponds to Right_1
								calculate(right_1_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianRight);
								calculate(right_1_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianRight+1);
								calculate(right_1_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianRight+1);
								indexOfValueOfSInArrayToCalculateMedianRight = 0;
							}
							if (numberRight == 3) {  		// Corresponds to Right_2
								calculate(right_2_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianRight);
								calculate(right_2_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianRight+1);
								calculate(right_2_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianRight+1);
								indexOfValueOfSInArrayToCalculateMedianRight = 0;
							}
						}
					}
					diffSE.add(0);	
					diffEE.add(0);	
					diffSS.add(0);	
				}
				/*
				 * 1. This part of code checks if the lines starts with "S" or "E" and extracts the corresponding numbers of 
				 * Start and End from each such line.
				 * 
				 * 2. In addition this code checks for the duplicates like Start-Start or End-End sequences that may appear in 
				 * the *.txt data files and exclude them from further analysis considering them as a "bugs". 
				 * 
				 * 3. In the cases when the "bug-free" pair of Start-End values was generated their difference 
				 * are determined and added to diffSE array.
				 */
				if (line.startsWith(" S") || line.startsWith("  S") ) {
					end = 0;
					countE = 0;
					countS++;
					int n = (line.startsWith("  S")) ? 3 : 2;
					
					if (countS > 0) {
						/* At this point Checking if "Start" not follows the "Start" instead of "Start-End-..." sequence - If Yes, then 
						 * this is a bug and such data should be excluded from the analysis, thus we remove the last 
						 * entered Start value and miss entering of a new Start value
						 */
						if (countS > 1) { 
							valuesOfS.remove(valuesOfS.size()-1);
							/* At this point the counting of the bugs in the data files occurs*/
							if (head.equals("Right")) bugsCountRight++;
							else bugsCountLeft++;							
						}
						String s = line.split(" ")[n].trim();
						start = Integer.parseInt(s);
						valuesOfS.add(s);
					}
					if ((head.equals("Left") && valuesOfS.size() - 1 == indexOfValueOfSInArrayToCalculateMedianLeft) ||
							(head.equals("Right") && valuesOfS.size() - 1 == indexOfValueOfSInArrayToCalculateMedianRight)) 
						{
							startAtLineBefore = start;
						}
				}
				if (line.startsWith(" E") || line.startsWith("  E")) {
					countS = 0;
					countE++;
					int n = (line.startsWith("  E")) ? 3 : 2;
					
					if (countE == 1 && start != 0) {
						String e = line.split(" ")[n].trim();
						end = Integer.parseInt(e);
						valuesOfE.add(e);
					}
					if ((head.equals("Left") && valuesOfE.size() - 1 == indexOfValueOfSInArrayToCalculateMedianLeft) ||
							(head.equals("Right") && valuesOfE.size() - 1 == indexOfValueOfSInArrayToCalculateMedianRight)) 
						{
							endAtLineBefore = end;
						}
				}

				/*Determines differences between Start and End and add them to diffSE*/
				if (start != 0 && end != 0) {
					if ((head.equals("Left") && valuesOfS.size() > indexOfValueOfSInArrayToCalculateMedianLeft) ||
							(head.equals("Right") && valuesOfS.size() > indexOfValueOfSInArrayToCalculateMedianRight)) 
					{
						diffSS.add(start - startAtLineBefore);
						diffEE.add(end - endAtLineBefore);
						startAtLineBefore = start;
						endAtLineBefore = end;	
					}
					else {
						diffSS.add(0);
						diffEE.add(0);
					}
					
					if (end - start < 0) {start = end;} //All possible negative values transforms to "0". One such case was really determined.
					diffSE.add(end - start);
					start = 0;
					end = 0;
				}
			}

			/*
			 * This part of code makes calculations of the necessary statistical variables if the lines in the *.txt data file were finished 
			 * and no fresh line with "Right" or "Left" marks appear.
			 */
			
			/*I've excluded the files that contained only Right or Left tests thus the below part of code (A and B) lost it sense*/
			
			/* A. If txt.file contained only Right_1 data (some files really had only this info). 
			if (indexOfValueOfSInArrayToCalculateMedianLeft == 0) { // Corresponds to Right_1
				calculate(right_1_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianRight);
				calculate(right_1_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianRight+1);
				calculate(right_1_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianRight+1);
			}
			*/
			
			/* B. If txt.file contained only Left_1 data. 
			if (indexOfValueOfSInArrayToCalculateMedianRight == 0) { // Corresponds to Left_1
				calculate(left_1_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianLeft);
				calculate(left_1_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianLeft+1);
				calculate(left_1_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianLeft+1);
			}
			*/
			
			/*  C. If lines in the txt.file finished but the left_2 and right_2 values were not calculated 
			 *     (in most cases this is true for a "left_2" calculations)*/
			if (indexOfValueOfSInArrayToCalculateMedianLeft != 0 && numberLeft == 3) { // Corresponds to Left_2
				/*Add Left_2 bugs to Bugs_Left_2*/
				bugs.put("Bugs_Left_2", bugsCountLeft);
				bugsCountLeft = 0;
				/*Calculate all necessary statistics */
				calculate(left_2_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianLeft);
				calculate(left_2_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianLeft+1);
				calculate(left_2_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianLeft+1);
			}
//			if (indexOfValueOfSInArrayToCalculateMedianRight != 0 && numberRight == 3) { // Corresponds to Right_2
//				calculate(right_2_SE, diffSE, indexOfValueOfSInArrayToCalculateMedianRight);
//				calculate(right_2_SS, diffSS, indexOfValueOfSInArrayToCalculateMedianRight+1);
//				calculate(right_2_EE, diffEE, indexOfValueOfSInArrayToCalculateMedianRight+1);
//			}
			countE = 0;
			countS = 0;
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("The source file is empty");
		}
		
		
		/* 
		 * 
		 * Write to a *.txt file
		 * 
		 */
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(destFileName));
				BufferedWriter bwPivotTable = new BufferedWriter(new FileWriter(pivotTableURL, true));
				BufferedWriter bwTest = new BufferedWriter(new FileWriter(testURL, true));
				BufferedWriter bwPivotTableForStat = new BufferedWriter(new FileWriter(pivotTableForStatisticsURL, true))
				) {
			
			String head = "Hand\tStart\tEnd\tDiffSE\tDiffSS\tDiffEE\tSEX\tYearOfTest\tTypeOfDesease"; //This is a head to present the result of processing of data presented in the corresponding *.txt files 
			bw.write(head);
			bw.newLine();
			/* The output_1 is a form for the pivotTable.txt */
			String output_1 = "";
			/* The output_2 is a form for the pivotTableForStatisitcs.txt */
			String output_2 = "" 
					+ fileName + "\t"
					+ bugs.get("Bugs_Right_1") + "\t" + bugs.get("Bugs_Left_1") + "\t" + bugs.get("Bugs_Right_2") + "\t" + bugs.get("Bugs_Left_2") + "\t" 
					+ personID + "\t" 
					+ typeOfDesease + "\t"
					+ sex + "\t" 
					+ yearOfTest + "\t" 
					
					+ right_1_SE.get("Q1_Mean") + "\t" + right_1_SE.get("Q2_Mean") + "\t" + right_1_SE.get("Q3_Mean") + "\t" + right_1_SE.get("Q4_Mean") + "\t"
					+ right_1_SS.get("Q1_Mean") + "\t" + right_1_SS.get("Q2_Mean") + "\t" + right_1_SS.get("Q3_Mean") + "\t" + right_1_SS.get("Q4_Mean") + "\t"
					+ right_1_EE.get("Q1_Mean") + "\t" + right_1_EE.get("Q2_Mean") + "\t" + right_1_EE.get("Q3_Mean") + "\t" + right_1_EE.get("Q4_Mean") + "\t"
					
					+ right_2_SE.get("Q1_Mean") + "\t" + right_2_SE.get("Q2_Mean") + "\t" + right_2_SE.get("Q3_Mean") + "\t" + right_2_SE.get("Q4_Mean") + "\t"
					+ right_2_SS.get("Q1_Mean") + "\t" + right_2_SS.get("Q2_Mean") + "\t" + right_2_SS.get("Q3_Mean") + "\t" + right_2_SS.get("Q4_Mean") + "\t"
					+ right_2_EE.get("Q1_Mean") + "\t" + right_2_EE.get("Q2_Mean") + "\t" + right_2_EE.get("Q3_Mean") + "\t" + right_2_EE.get("Q4_Mean") + "\t"

					+ left_1_SE.get("Q1_Mean") + "\t" + left_1_SE.get("Q2_Mean") + "\t" + left_1_SE.get("Q3_Mean") + "\t" + left_1_SE.get("Q4_Mean") + "\t"
					+ left_1_SS.get("Q1_Mean") + "\t" + left_1_SS.get("Q2_Mean") + "\t" + left_1_SS.get("Q3_Mean") + "\t" + left_1_SS.get("Q4_Mean") + "\t"
					+ left_1_EE.get("Q1_Mean") + "\t" + left_1_EE.get("Q2_Mean") + "\t" + left_1_EE.get("Q3_Mean") + "\t" + left_1_EE.get("Q4_Mean") + "\t"
					
					+ left_2_SE.get("Q1_Mean") + "\t" + left_2_SE.get("Q2_Mean") + "\t" + left_2_SE.get("Q3_Mean") + "\t" + left_2_SE.get("Q4_Mean") + "\t"
					+ left_2_SS.get("Q1_Mean") + "\t" + left_2_SS.get("Q2_Mean") + "\t" + left_2_SS.get("Q3_Mean") + "\t" + left_2_SS.get("Q4_Mean") + "\t"
					+ left_2_EE.get("Q1_Mean") + "\t" + left_2_EE.get("Q2_Mean") + "\t" + left_2_EE.get("Q3_Mean") + "\t" + left_2_EE.get("Q4_Mean") + "\t"

					
					+ right_1_SE.get("Median") + "\t" + left_1_SE.get("Median") + "\t" + right_2_SE.get("Median") + "\t" + left_2_SE.get("Median") + "\t" 
					+ right_1_SE.get("Mean") + "\t" + comma(right_1_SE.get("Sd")) + "\t" + comma(right_1_SE.get("CV")) + "\t" 
					+ left_1_SE.get("Mean") + "\t" + comma(left_1_SE.get("Sd")) + "\t" + comma(left_1_SE.get("CV")) + "\t"
					+ right_2_SE.get("Mean") + "\t" + comma(right_2_SE.get("Sd")) + "\t" + comma(right_2_SE.get("CV")) + "\t"
					+ left_2_SE.get("Mean") + "\t" + comma(left_2_SE.get("Sd")) + "\t" + comma(left_2_SE.get("CV")) + "\t"
//					+ right_1_SE.get("Max") + "\t" + left_1_SE.get("Max") + "\t" + right_2_SE.get("Max") + "\t" + left_2_SE.get("Max") + "\t"
					+ right_1_SE.get("Min") + "\t" + left_1_SE.get("Min") + "\t" + right_2_SE.get("Min") + "\t" + left_2_SE.get("Min") + "\t"
							+ right_1_SS.get("Median") + "\t" + left_1_SS.get("Median") + "\t" + right_2_SS.get("Median") + "\t" + left_2_SS.get("Median") + "\t" 
							+ right_1_SS.get("Mean") + "\t" + comma(right_1_SS.get("Sd")) + "\t" + comma(right_1_SS.get("CV")) + "\t" 
							+ left_1_SS.get("Mean") + "\t" + comma(left_1_SS.get("Sd")) + "\t" + comma(left_1_SS.get("CV")) + "\t"
							+ right_2_SS.get("Mean") + "\t" + comma(right_2_SS.get("Sd")) + "\t" + comma(right_2_SS.get("CV")) + "\t"
							+ left_2_SS.get("Mean") + "\t" + comma(left_2_SS.get("Sd")) + "\t" + comma(left_2_SS.get("CV")) + "\t"
//							+ right_1_SS.get("Max") + "\t" + left_1_SS.get("Max") + "\t" + right_2_SS.get("Max") + "\t" + left_2_SS.get("Max") + "\t"
							+ right_1_SS.get("Min") + "\t" + left_1_SS.get("Min") + "\t" + right_2_SS.get("Min") + "\t" + left_2_SS.get("Min") + "\t"
									+ right_1_EE.get("Median") + "\t" + left_1_EE.get("Median") + "\t" + right_2_EE.get("Median") + "\t" + left_2_EE.get("Median") + "\t" 
//									+ right_1_EE.get("Mean") + "\t" + comma(right_1_EE.get("Sd")) + "\t" + comma(right_1_EE.get("CV")) + "\t" 
//									+ left_1_EE.get("Mean") + "\t" + comma(left_1_EE.get("Sd")) + "\t" + comma(left_1_EE.get("CV")) + "\t"
//									+ right_2_EE.get("Mean") + "\t" + comma(right_2_EE.get("Sd")) + "\t" + comma(right_2_EE.get("CV")) + "\t"
//									+ left_2_EE.get("Mean") + "\t" + comma(left_2_EE.get("Sd")) + "\t" + comma(left_2_EE.get("CV")) + "\t"
//									+ right_1_EE.get("Max") + "\t" + left_1_EE.get("Max") + "\t" + right_2_EE.get("Max") + "\t" + left_2_EE.get("Max") + "\t"
									+ right_1_EE.get("Min") + "\t" + left_1_EE.get("Min") + "\t" + right_2_EE.get("Min") + "\t" + left_2_EE.get("Min") + "\t"
					;
			String hand = "Right_1";

			bwPivotTableForStat.write(output_2);
			bwPivotTableForStat.newLine();

			for (int i = 1; i < valuesOfS.size()-1; i++) {
				if (valuesOfS.get(i).contains("Right") || valuesOfS.get(i).contains("Left")) {
					hand = valuesOfS.get(i);
					i++;
				}
				output_1 = "" + hand + "\t" + valuesOfS.get(i) + "\t" + valuesOfE.get(i) + "\t" 
							+ diffSE.get(i) + "\t" 
							+ diffSS.get(i) + "\t"
							+ diffEE.get(i) + "\t"
							+ sex + "\t" + yearOfTest + "\t" + typeOfDesease;
				
				String output_3 = "" + diffSS.get(i) + "\t" + diffEE.get(i) + "\t";
				bwTest.write(output_3);
				bwTest.newLine();
				
				bw.write(output_1);
				bw.newLine();
				bwPivotTable.write(output_1);
				bwPivotTable.newLine();
				output_1 = "";
				
			}
			output_2 = "";
			yearOfTest = 0;
		} catch (Exception e) {
			System.out.println("File for the output not found");
		}
	}

	public static void calculate(Map<String, Number> target, List<Integer> source, int start) {
		target.put("Median", median(source, start, source.size()-1));
		target.put("Mean", mean(source, start, source.size()-1));
		target.put("Sd", sd(source, start, source.size()-1)); 				//double
		target.put("CV", variation(source, start, source.size()-1));		//double
		target.put("Max", max(source, start, source.size()-1));
		target.put("Min", min(source, start, source.size()-1));
		target.put("Q1_Mean", quartileMean(source, start, source.size()-1, 1));
		target.put("Q2_Mean", quartileMean(source, start, source.size()-1, 2));
		target.put("Q3_Mean", quartileMean(source, start, source.size()-1, 3));
		target.put("Q4_Mean", quartileMean(source, start, source.size()-1, 4));
	}

/*Formulas of statistical variables */
	
	private static String comma(Number number) {
		String temp = "" + new BigDecimal((double)number).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return temp.replace(".", ",");
	}

	/* MIN */
	private static int min(List<Integer> source, int start, int end) {
		return (start == end) ? source.get(start) : Collections.min(source.subList(start, end));	
	}

	/* MAX */
	private static int max(List<Integer> source, int start, int end) {
		return (start == end) ? source.get(start) : Collections.max(source.subList(start, end));
	}
	
	/* MEAN */
	private static int mean(List<Integer> source, int start, int end) {
		if (start == end) return source.get(start);
		int sum = source.subList(start, end).stream().mapToInt(e -> e).sum();
		int n = source.subList(start, end).size();
		return sum / n;
	}
	
	/* Q1-Q4 MEAN values */
	private static int quartileMean(List<Integer> source, int start, int end, int quartileNumber) {
		if (start == end) return source.get(start);
		
		List<Integer> partOfSource = source.subList(start, end);
		int [] quartilesMeans = {0, 0, 0, 0};
		List<List<Integer>> quartiles = Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		
		if (partOfSource.size() < 5) {
			switch (partOfSource.size()) {
			case 0 : 	System.out.println("Case0"); break;
			case 1 : 	quartilesMeans[1] = partOfSource.get(0);
						break;
			case 2 : 	quartilesMeans[1] = partOfSource.get(0);
						quartilesMeans[2] = partOfSource.get(1);
						break;
			case 3 :	quartilesMeans[0] = partOfSource.get(0);
						quartilesMeans[1] = partOfSource.get(1);
						quartilesMeans[2] = partOfSource.get(2);
						break;
			case 4 : 	quartilesMeans = partOfSource.stream().mapToInt(e -> e).toArray();
						break;
			}
			return quartilesMeans[quartileNumber - 1];
		}
		
		else {
			if (partOfSource.size() % 4 == 0) { // If subArray divided into 4 equal parts (quartiles)
				int index = 0;
				for (List <Integer> quartile : quartiles) {
					quartile.addAll(partOfSource.subList(index, index + partOfSource.size() / 4));
					index += partOfSource.size() / 4;
				}
			}
			
			else { // If subArray is NOT divided into 4 equal parts
				/* Step 1. Divide subArray into two semiArrays (subList1 and subList2). In case the subArray is not even 
				 * (not divided by 2) the first semiArray (subList1) should be larger by one then the second semiArray (subList2).
				 */
				int oneMore = (partOfSource.size() % 2 != 0) ? 1 : 0; 
				List<Integer> subList1 = new ArrayList<>();
				for (int i = 0; i < partOfSource.size() / 2 + oneMore; i++) {
					subList1.add(partOfSource.get(i));
				}
				List<Integer> subList2 = new ArrayList<>();
				for (int i = partOfSource.size() / 2 + oneMore; i < partOfSource.size(); i++) {
					subList2.add(partOfSource.get(i));
				}
				
				/* Step 2. Divide each of the semiArarys (subList1 and subList2) into two quartiles. In case the semiArrays are 
				 * not even (not divided by 2) the first of quartiles should be bigger by one then the second one. 
				 */
				oneMore = (subList1.size() % 2 != 0) ? 1 : 0;
				quartiles.get(0).addAll(subList1.subList(0, subList1.size() / 2 + oneMore));
				quartiles.get(1).addAll(subList1.subList(subList1.size() / 2 + oneMore, subList1.size()));
				
				oneMore = (subList2.size() % 2 != 0) ? 1 : 0;
				quartiles.get(2).addAll(subList2.subList(0, subList2.size() / 2 + oneMore));
				quartiles.get(3).addAll(subList2.subList(subList2.size() / 2 + oneMore, subList2.size()));
			}
		}
		
		return mean(quartiles.get(quartileNumber-1), 0, quartiles.get(quartileNumber-1).size());
	}

	/* SD */
	private static double sd(List<Integer> source, int start, int end) {
		if (start == end) return 0;
		
		int mean = mean(source, start, end);	
		int sumOfSqrs = source.subList(start, end).stream().mapToInt(i -> (i - mean) * (i - mean)).sum();
		int n = source.subList(start, end).size();
		
		return (n > 30) ? Math.sqrt(sumOfSqrs/(n - 1)) : Math.sqrt(sumOfSqrs/n);
	}
	
	/* COEFICIENT of VARIATION in %*/
	private static double variation(List<Integer> source, int start, int end) {
		return (start == end) ? 0 : (sd(source, start, end) / mean(source, start, end) * 100); 
	}
	
	/* MEDIAN */
	private static int median(List<Integer> source, int start, int end) {
		if (start == end) return source.get(start);
		List<Integer> partOfSource = source.subList(start, end); // The partOfSource is already sorted since the subList() function sorted it
		int indexOfMedian = partOfSource.size()/2;
		
		return (partOfSource.size() % 2 == 0) ?
				(partOfSource.get(indexOfMedian) + partOfSource.get(indexOfMedian-1)) / 2 : // If partOfSource is odd
				partOfSource.get(indexOfMedian) 											// If partOfSource is even
				;
	}


}
