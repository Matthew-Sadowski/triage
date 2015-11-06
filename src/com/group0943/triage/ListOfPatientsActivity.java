package com.group0943.triage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ListOfPatientsActivity extends Activity {

	/** The name of the patient_records text file. */
	public static final String PATIENT_RECORD = "patient_records.txt";
	
	/** The Android Spinner that contains all the 
	 * patients who have not yet been seen by a doctor. */
	protected Spinner patient_list_dropDown;
	
	/** The array holding all the patient info by
	 * decreasing urgency level. */
	protected String[] patient_list_by_urgency;
	
	/** The list holding all the patients with urgency values
	 * who have not seen a doctor level. */
	protected List<Patient> patient_list_urgent;
	
	/** The list holding all Patient objects. */
	protected List<Patient> patient_list;
	
	/** The database containing all Patient objects. */
	protected PatientDatabase patientDatabase;
	
	/** Patient urgenecy increases if under this age. */
	private static final int URGENCY_AGE = 2;
	
	/** Patient urgenecy increases if over this temperature. */
	private static final int URGENCY_TEMP = 39;
	
	/** Systolic blood pressure limit. */
	private static final int URGENCY_SYSTOLIC = 140;
	
	/** Diastolic blood pressure limit. */
	private static final int URGENCY_DIASTOLIC = 90;
	
	/** Upper Heart Rate limit. */
	private static final int UPPER_HR_LIMIT = 100;
	
	/** Lower Heart Rate limit. */
	private static final int LOWER_HR_LIMIT = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_patients);

        try {
			patientDatabase = new PatientDatabase(
					this.getApplicationContext().getFilesDir(), 
					PATIENT_RECORD);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        patient_list = patientDatabase.getPatients();
		setUpPatientList();
		
		patient_list_dropDown = (Spinner) findViewById(R.id.patient_list_spinner);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, patient_list_by_urgency);
		patient_list_dropDown.setAdapter(adapter);
		patient_list_dropDown.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                	
            		TextView patient_data = (TextView) findViewById(R.id.patient_info_display);
            		
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						
						String info_todisplay = patient_list_dropDown.getSelectedItem().toString();
						patient_data.setText(info_todisplay);
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						
						patient_data.setText("");
						
					}
                });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.list_of_patients, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	 * Sets up patient_list_by_urgency to include patients by decreasing
	 * urgency level who have not been seen by a doctor.
	 */
	private void setUpPatientList(){

		ViewRecord viewRecord = new ViewRecord();
		patient_list_urgent = new ArrayList<Patient>();
		
		for (Patient patient : patient_list){
			
			try {
				ArrayList<ArrayList<String>> patient_vitals = 
						viewRecord.getPatientRecord(patient.getHealth_card_number(), this.getApplicationContext().getFilesDir());
				
				if (!(patient_vitals == null)){

					// Get the most current visit.
					ArrayList<String> current_visit = patient_vitals.get(patient_vitals.size() - 1);
					String seen_by_doc = current_visit.get(4);
					
					if (seen_by_doc.equals("false")){

						int urgency = 0;
						
						int patient_age = patient.getAge();
						int patient_temp = Integer.parseInt(current_visit.get(1));
						String patient_blood_pressure = current_visit.get(2);
						String[] blood_pressureArray = patient_blood_pressure.split("/");
						int patient_systolic = Integer.parseInt(blood_pressureArray[0]);
						int patient_diastolic = Integer.parseInt(blood_pressureArray[1]);
						int patient_heart_rate = Integer.parseInt(current_visit.get(3));
						
						if (patient_age < URGENCY_AGE){
							urgency++;
						}
						if (patient_temp >= URGENCY_TEMP){
							urgency++;
						}
						if (patient_systolic >= URGENCY_SYSTOLIC || patient_diastolic >= URGENCY_DIASTOLIC){
							urgency++;
						}
						if (patient_heart_rate >= UPPER_HR_LIMIT || patient_heart_rate <= LOWER_HR_LIMIT){
							urgency++;
						}
						
						patient.setUrgencyLevel(urgency);
						patient_list_urgent.add(patient);
					}
					
				}
				
			} catch (FileNotFoundException fileNotFound) {
				
				// File not Yet created
				// Do nothing...
				
			}

		}

		Collections.sort(patient_list_urgent, new Comparator<Patient>() {
		    @Override
		    public int compare(Patient p1, Patient p2) {
		        return Integer.valueOf(p2.getUrgencyLevel()).compareTo(Integer.valueOf(p1.getUrgencyLevel()));
		    }
		});
		
		patient_list_by_urgency = new String[patient_list_urgent.size()];
		
		for (int i = 0; i < patient_list_urgent.size(); i++){
			patient_list_by_urgency[i] = patient_list_urgent.get(i).displayInfo();
		}
		
	}
}
