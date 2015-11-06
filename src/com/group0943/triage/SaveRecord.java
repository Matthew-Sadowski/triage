package com.group0943.triage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileWriter;


public class SaveRecord { 
	
	/** The name of the patient_visit_records text file. */
	protected static final String PATIENT_VISIT_RECORDS = "patient_visit_records.txt";
	
	/**
	 * Saves the health card number, time, and vital signs 
	 * of all the Patients to a text file.
	 * @param patient_info 
	 * 		A map where the key is a Patient's health card number and
	 * 		the value is a 2d arraylist containing information on each visit.
	 * @param dir
	 * 		The directory of the application (where to save the information).
	 * 		Most of the time expecting this to be this.getApplicationContext().getFilesDir()
	 * 		of the current Application Context.
	 */
	public void saveToFile(Map<String, ArrayList<ArrayList<String>>> patient_info, File dir) {
		
		File file = new File(dir, PATIENT_VISIT_RECORDS);
		
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : patient_info.entrySet()) {
				
			    String health_card_num = entry.getKey();
			    String patient_str_to_write = health_card_num + ";";
			    ArrayList<ArrayList<String>> info = entry.getValue();
			    
			    for (ArrayList<String> visit_info : info){
			    	for (String piece_of_info : visit_info){
			    		patient_str_to_write += piece_of_info + ",";
			    	}
			    	
			    	// Get rid of the last comma.
			    	patient_str_to_write = patient_str_to_write.substring(0, patient_str_to_write.length()-1);
			    	// '|' indicates the start of a separate visit.
			    	patient_str_to_write += "!";
			    }
			    
			    // Get rid of the last '!'.
			    //patient_str_to_write = patient_str_to_write.substring(0, patient_str_to_write.length()-1);
			    bufferedWriter.write(patient_str_to_write+"\n");			    
			    
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	
}
