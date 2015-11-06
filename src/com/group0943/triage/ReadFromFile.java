package com.group0943.triage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadFromFile {
	
	/** The name of the patient_visit_records text file. */
	protected static final String PATIENT_VISIT_RECORDS = "patient_visit_records.txt";
	
	/**
	 * Gets all the contents of the patient_visit_records and returns it using 
	 * a map, where the keys are Patient health card numbers and the values 
	 * are lists with visit information.
	 * @param dir
	 * 		The directory of the application (where to get the information).
	 * 		Most of the time expecting this to be this.getApplicationContext().getFilesDir()
	 * 		of the current Application Context.
	 * @throws FileNotFoundException if the file path to the file can not be found.
	 * @return A map containing the visit information of all recorded Patients.
	 */
	public Map<String, ArrayList<ArrayList<String>>> readEntireFile(File dir) throws FileNotFoundException{
		
		File file = new File(dir, PATIENT_VISIT_RECORDS);
		String filepath = file.getPath();
		
		Scanner scanner = new Scanner(new FileInputStream(filepath));
		
		String[] visitArray;
		String[] mapSplitArray;
		Map<String, ArrayList<ArrayList<String>>> visit_database = new HashMap<String, ArrayList<ArrayList<String>>>();
		
		while (scanner.hasNextLine()) {
			
			ArrayList<ArrayList<String>> patient_info = new ArrayList<ArrayList<String>>();
			String nextline = scanner.nextLine();
			mapSplitArray = nextline.split(";");
			
			String healthCardNum = mapSplitArray[0];
			
			visitArray = mapSplitArray[1].split("!");
				
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
			
			visit_database.put(healthCardNum, patient_info);
			
			

		}
		
		scanner.close();
		
		return visit_database;
	}

}