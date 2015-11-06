package com.group0943.triage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePatientRecord extends Activity {

	/** The name of the patient_records text file. */
	public static final String PATIENT_RECORD = "patient_records.txt";
	
	/** The database containing all Patient objects. */
	private PatientDatabase patientDatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_patient_record);
		
        try {
			patientDatabase = new PatientDatabase(
					this.getApplicationContext().getFilesDir(), 
					PATIENT_RECORD);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_patient_record, menu);
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
	 * Starts/returns to Load_Patient_Records_Activity
	 * @param view The current activity view.
	 */
	public void back_to_patient_records(View view){
		
		Intent intent = new Intent(this, Load_Patient_Records_Activity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Saves a newly created patient record if it follows the required formats.
	 * @param view The current activity view.
	 */
	public void savePatientRecord(View view){
		
		EditText health_card_input = (EditText) findViewById(R.id.healthCardInput);
    	String health_card_num = health_card_input.getText().toString();
		EditText name_input = (EditText) findViewById(R.id.nameInput);
    	String patient_name = name_input.getText().toString();
		EditText date_of_birth_input = (EditText) findViewById(R.id.dobInput);
    	String patient_date_of_birth = date_of_birth_input.getText().toString();
    	
    	TextView success_notifier = (TextView) findViewById(R.id.createNotificationText);
    	
    	// Make sure format of input is correct.
    	if (Pattern.matches("[0-9]+", health_card_num) && 
    			Pattern.matches("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", patient_date_of_birth)){
    		
    		// Split date_of_birth into the year, month, and day to be compatible with the Patient class.
        	String[] parts_of_dob = patient_date_of_birth.split("-");
        	String patient_year = parts_of_dob[0];
        	String patient_month = parts_of_dob[1];
        	String patient_day = parts_of_dob[2];
        	
        	// Split the patient name into parts.
        	String[] patient_name_parts = patient_name.split(" ");
        
        	
        	Patient newPatient = new Patient(patient_name_parts, health_card_num, patient_year, patient_month, patient_day);
    		File file = new File(this.getApplicationContext().getFilesDir(), 
    				PATIENT_RECORD);
    		

        	try {
        		
    			FileWriter fileWriter = new FileWriter(file);
    			
    	    	// Make patient does not already exist.
    	    	if (!patientDatabase.patient_in_database(newPatient)){
    	    		success_notifier.setText("SUCCESS!");
    	    		
    	    	// Updates the patient if health card number already exists in the file.
    	    	} else {
    	    		patientDatabase.remove(health_card_num);
    	    		success_notifier.setText("Updated Existing Record.");
    	    	}	    	
    	    	patientDatabase.add(newPatient);
    	    	patientDatabase.saveToFile(fileWriter);
    	    	
    		} catch (IOException e1) {
    			e1.printStackTrace();	
        	}
        	
    	} else {
    		success_notifier.setText("Please enter the data in the correct format.");
    	}
		
	}

	
}
