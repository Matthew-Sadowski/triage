package com.group0943.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewRecord {
	
	/** The name of the patient_visit_records text file. */
	protected static final String PATIENT_VISIT_RECORDS = "patient_visit_records.txt";
	
	/**
	 * Returns Patient's vitals information and any other information stored
	 * during their visit and null if Patient does not exist.
	 * @param healthCardNum
	 * 		The heatlh card number of the Patient.
	 * @param dir
	 * 		The directory of the application (where to get the information).
	 * 		Most of the time expecting this to be this.getApplicationContext().getFilesDir()
	 * 		of the current Application Context.
	 * @throws FileNotFoundException if the file path to the file can not be found.
	 * @return A unique Patient's visit history and associated information to them.
	 */
	public ArrayList<ArrayList<String>> getPatientRecord(String healthCardNum, File dir) throws FileNotFoundException{
		
		File file = new File(dir, PATIENT_VISIT_RECORDS);
		String filepath = file.getPath();
		
		Scanner scanner = new Scanner(new FileInputStream(filepath));
		ArrayList<ArrayList<String>> patient_info = new ArrayList<ArrayList<String>>();
		String[] visitArray;
		String[] healthCardArrayCheck;
		boolean patientExists = false;
		
		while (scanner.hasNextLine()) {
			
			String nextline = scanner.nextLine();
			healthCardArrayCheck = nextline.split(";");
			
			if (healthCardArrayCheck[0].equals(healthCardNum)){
				
				patientExists = true;
				visitArray = healthCardArrayCheck[1].split("!");
				
				for (String visit_info : visitArray){
					
					// There will be an empty String element at the end do to there
					// being a "!" at the end of each line in the file.
					if (!visit_info.equals("")){
						String[] infoArray = visit_info.split(",");
						ArrayList<String> visit_list_information = new ArrayList<String>();
						for (String piece_of_info : infoArray){
							visit_list_information.add(piece_of_info);
						}
						patient_info.add(visit_list_information);
					}
					
				}
			}
			

		}
		
		scanner.close();
		
		if (patientExists){
			return patient_info;
		} else {
			return null;
		}
		
		
	}
	

}
