package com.group0943.triage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecordSymptomsActivity extends Activity {

	/** The name of the patient_prescription text file. */
	protected static final String PATIENT_PRESCRIPTION_INFO = "patient_prescription.txt";
	
	/** The map of information in the file. */
	protected Map<String, String> prescription_info;
	
	/** Boolean value to indicate whether file was created yet or not. */
	protected boolean madeYet = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_symptoms);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_symptoms, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Reads the file or creates a new map if file is not made yet.
	 */
	public void getFileToRead(){
		try {
			prescription_info = readFile();
		} catch (FileNotFoundException e) {
			madeYet = false;
			prescription_info = new HashMap<String,String>();
		}
	}
	
	/**
	 * Starts PhysicianActivity
	 * @param view The current activity view.
	 */
	public void load_PhysicianMainScreen(View view){
		
		Intent intent = new Intent(this, PhysicianActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Saves any string of info a Physician has entered.
	 * @param view The current activity view
	 */
	public void savePatientInfo(View view){
		
		EditText healthcardNumText = (EditText) findViewById(R.id.health_card_input);
		String healthcardNum = healthcardNumText.getText().toString();
		
		EditText prescriptionInput = (EditText) findViewById(R.id.prescriptionInput);
		String prescription = prescriptionInput.getText().toString();
		
		TextView statusText = (TextView) findViewById(R.id.prescriptionText);
		
		if (healthcardNum.length() > 0 && prescription.length() > 0){
			File dir = this.getApplicationContext().getFilesDir();
			File file = new File(dir, PATIENT_PRESCRIPTION_INFO);

			getFileToRead();
			
			try {

				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				if (madeYet){
					if (loadPatientInfo(healthcardNum) != null){
						prescription_info.remove(healthcardNum);
					}
				} else {
					madeYet = true;
				}

				prescription_info.put(healthcardNum, prescription);
				
				for (Map.Entry<String, String> entry : prescription_info.entrySet()) {
					
					    String health_card_num = entry.getKey();
					    String patient_str_to_write = health_card_num + ";";
					    String info = entry.getValue();
					    bufferedWriter.write(patient_str_to_write+info+"\n");
				}
				
				statusText.setText("SUCCESS!");
				bufferedWriter.flush();
				bufferedWriter.close();
				
			} catch (IOException e) {
				// DO nothing
			}
		} else {
			statusText.setText("Please enter non-empty information for saving.");
		}
		
		
		
		
	}
	
	/**
	 * Returns the contents of patient_prescription.
	 * @throws FileNotFoundException if the file is not found.
	 * @return The prescription info for all the Patients.
	 */
	public Map<String, String> readFile() throws FileNotFoundException{
		
		File file = new File(this.getApplicationContext().getFilesDir(), PATIENT_PRESCRIPTION_INFO);
		String filepath = file.getPath();
		Scanner scanner = new Scanner(new FileInputStream(filepath));
		Map<String, String> patient_info = new HashMap<String, String>();
		

		while (scanner.hasNextLine()) {
			
			String[] infoArray;
			String nextline = scanner.nextLine();
			infoArray = nextline.split(";");
			patient_info.put(infoArray[0], infoArray[1]);
						
		}
		scanner.close();
		
		return patient_info;
		
	}
	
	/**
	 * Loads and returns prescription information for a specific Patient.
	 * @param healthCardNum The health card number of the Patient.
	 * @throws FileNotFoundException if the file is not found.
	 * @return The prescription info for a Patient.
	 */
	public String loadPatientInfo(String healthCardNum) throws FileNotFoundException{
		
		File file = new File(this.getApplicationContext().getFilesDir(), PATIENT_PRESCRIPTION_INFO);
		String filepath = file.getPath();
		
		Scanner scanner = new Scanner(new FileInputStream(filepath));
		String info = null;
		
		while (scanner.hasNextLine()) {
			
			String[] infoArray;
			String nextline = scanner.nextLine();
			infoArray = nextline.split(";");
			if (healthCardNum.equals(infoArray[0])){
				info = infoArray[1];
			}
						
		}
		scanner.close();
		return info;
		
	}
	
	/**
	 * Displays prescription information to the screen for a specific Patient.
	 * @param view The current activity view.
	 */
	public void viewPatientInfo(View view){
		
		TextView statusText = (TextView) findViewById(R.id.prescriptionText);
		
		EditText healthcardNumText = (EditText) findViewById(R.id.health_card_input);
		String healthcardNum = healthcardNumText.getText().toString();

		try {
			String info_to_display = loadPatientInfo(healthcardNum);
			if (info_to_display != null){
				statusText.setText(info_to_display);
			} else {
				statusText.setText("Patient does not have prescription information.");
			}
			
		} catch (FileNotFoundException e) {

			statusText.setText("Patient does not have prescription information.");
		}
		
		
	}
	
}
