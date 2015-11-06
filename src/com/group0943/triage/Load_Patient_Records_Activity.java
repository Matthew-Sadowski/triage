package com.group0943.triage;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Load_Patient_Records_Activity extends Activity {

	/** The name of the patient_records text file. */
	public static final String PATIENT_RECORD = "patient_records.txt";
	
	/** The database containing all Patient objects. */
	private PatientDatabase patientDatabase;
	
	/** The list holding all Patient objects. */
	private List<Patient> patientList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_patient_records);
		
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
		getMenuInflater().inflate(R.menu.load__patient__records_, menu);
		return true;
	}
	
	/**
	 * Starts/returns to MainActivity
	 * @param view The current activity view.
	 */
	public void load_mainActivity(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts CreatePatientRecord.
	 * @param view The current activity view.
	 */
	public void createPatientRecord(View view){
		
		Intent intent = new Intent(this, CreatePatientRecord.class);
		startActivity(intent);
	}
	
	/** 
     * Loads and displays patient data.
     * @param view The view for the onClick method to work.
     */
    public void displayPatientData(View view){
    	

    	EditText health_card_text = (EditText) findViewById(R.id.editHealthCardNumber);
    	String health_card_number_input = health_card_text.getText().toString();
    	
    	TextView patient_health_card_number = (TextView) findViewById(R.id.patient_health_card_number);
    	TextView patient_name = (TextView) findViewById(R.id.patient_name);
    	TextView patient_dob = (TextView) findViewById(R.id.patient_dob);
    	TextView patient_does_not_exist_text = (TextView) findViewById(R.id.patientDoesNotExistWarning);
    	
    	patientList = patientDatabase.getPatients();

    	boolean patientExists = false;
    	
    	for (Patient p: patientList){
    		if (p.getHealth_card_number().equals(health_card_number_input)){
    			patientExists = true;
    			patient_does_not_exist_text.setText("");
    			patient_health_card_number.setText(p.getHealth_card_number());
    			patient_name.setText(p.getNameAsString());
    			patient_dob.setText(p.getDate_of_birth());
    		}
    	}
    	
    	if (!patientExists){
    		patient_does_not_exist_text.setText("Patient Does Not Exist.");
    		patient_health_card_number.setText("");
			patient_name.setText("");
			patient_dob.setText("");
    	}

    }

}
