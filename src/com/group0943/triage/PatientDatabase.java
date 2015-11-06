package com.group0943.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientDatabase {

	/** The list containing all Patients. */
	private List<Patient> patients;

	/** 
	 * Creates a PatientDatabase which holds a list of all the Patients.
	 * @param dir The file directory to load from.
	 * @param fileName The name of the file that contains the Patient 
	 * information.
	 */
	public PatientDatabase(File dir, String fileName) throws IOException {
		this.patients = new ArrayList<Patient>();
		File file = new File(dir, fileName);
		this.loadPatientRecords(file.getPath());
		
	}

	/** 
	 * Adds a patient to the patient list.
	 * @param patient The Patient to add.
	 */
	public void add(Patient patient) {
		patients.add(patient);
	}
	
	/** 
	 * Removes a patient from the patient list.
	 * @param health_card_number The health card number of the patient to remove.
	 */
	public void remove(String health_card_number){
		for (Patient p: patients){
			if (p.getHealth_card_number().equals(health_card_number)){
				patients.remove(p);
			}
		}
	}
	
	/**
	 * Check if a patient is already in the database (same health card number).
	 * patient The patient to check.
	 * @return true if patient is already in the database; false otherwise.
	 */
	public boolean patient_in_database(Patient patient){
		for (Patient p: patients){
			if (patient.getHealth_card_number().equals(p.getHealth_card_number())){
				return true;
			}
		}
		return false;
	}

	/** 
	 * Returns a list of Patients in the database.
	 * @return The Patient list.
	 */
	public List<Patient> getPatients() {
		return patients;
	}

	/** 
	 * Returns an interpreted version of PatientDatabase as a String.
	 * @return A string representation of PersonDatabase.
	 */
	@Override
	public String toString() {
		return "PersonDatabase [patient=" + patients + "]";
	}
	
	/**
	 * Saves the patient list to internal storage.
	 * @param outputStream The outputStream to save to.
	 */
	public void saveToFile(FileWriter filewriter) {
		try {
			for (Patient p : patients) {
				filewriter.write(p.toString() + "\n");
			}
			filewriter.flush();
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /** 
     * Reads a file, loads the Patient records and adds them to the database.
     * @param filePath The file path of the Patient records file.
     * @throws FileNotFoundException if the file can not be found with the
     * given filePath.
     */
	private void loadPatientRecords(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String health_card_number = record[0];
			String[] name = record[1].split(" ");
			String[] dob = record[2].split("-");
			this.add(new Patient(name, health_card_number ,dob[0], dob[1], dob[2]));
		}
		scanner.close();
	}
}

