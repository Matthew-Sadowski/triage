package com.group0943.triage;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CreateVisitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_visit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_visit, menu);
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
	 * Takes the information from the screen of CreateVisitActivity, as well as 
	 * generates its own time and stores it in a Map
	 * where the HealthCardNumber is the key and the value is a set of set of time and vitals
	 * this allows for updating and retaining.
	 * @param view
	 */
	public void createVisit(View view){
		
		DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		
		TextView createStatus = (TextView) findViewById(R.id.createStatus);
		
		EditText healthcardNumText = (EditText) findViewById(R.id.healthcard_number_field);
		String healthcardNum = healthcardNumText.getText().toString();
		
		EditText temperatureText = (EditText) findViewById(R.id.temperature_field);
		String temperature = temperatureText.getText().toString();
		
		EditText bloodPressureText = (EditText) findViewById(R.id.blood_pressure_field);
		String bloodPressure = bloodPressureText.getText().toString();
		
		EditText heartRateText = (EditText) findViewById(R.id.heart_rate_field);
		String heartRate = heartRateText.getText().toString();
		
		CheckBox seenByDoctorCheckBox = (CheckBox) findViewById(R.id.createSeenByDoctorCheckBox);
		boolean seenByDoc = seenByDoctorCheckBox.isChecked();
		String seenByDocStr = String.valueOf(seenByDoc);
		
		// Ensure valid input.
		if (Pattern.matches("[0-9]+", healthcardNum) && Pattern.matches("[0-9]+", temperature) && 
				Pattern.matches("[0-9]+/[0-9]+", bloodPressure) && Pattern.matches("[0-9]+", heartRate)){

			ArrayList<String> information = new ArrayList<String>();
			information.add(time);
			information.add(temperature);
			information.add(bloodPressure);
			information.add(heartRate);
			information.add(seenByDocStr);
			
			// Need a list in a list in order to keep track of multiple visits.
			ArrayList<ArrayList<String>> visit_record_instance = new ArrayList<ArrayList<String>>();
			visit_record_instance.add(information);
			
			Map<String, ArrayList<ArrayList<String>>> visitRecordsMap;
			
			ReadFromFile patientVisitsInfo = new ReadFromFile();
			SaveRecord saveRecord = new SaveRecord();
			ViewRecord viewRecord = new ViewRecord();
			boolean patientDoesNotExist = false;

			try {
				if (viewRecord.getPatientRecord(healthcardNum, this.getApplicationContext().getFilesDir()) == null){
					patientDoesNotExist = true;
				} else {
					createStatus.setText("Patient Visit Info Already Exists.");
				}
			} catch (FileNotFoundException e1) {
				patientDoesNotExist = true;
			}
			
			if (patientDoesNotExist){
				createStatus.setText("SUCCESS!");
				try {
					visitRecordsMap = patientVisitsInfo.readEntireFile(this.getApplicationContext().getFilesDir());
					visitRecordsMap.put(healthcardNum, visit_record_instance);
				    saveRecord.saveToFile(visitRecordsMap, this.getApplicationContext().getFilesDir());
				    
				} catch (FileNotFoundException e) {
					// File was not yet created and thus needs to be created.
					Map<String, ArrayList<ArrayList<String>>> first_visit_map = 
							new HashMap<String, ArrayList<ArrayList<String>>>();
					first_visit_map.put(healthcardNum, visit_record_instance);
					saveRecord.saveToFile(first_visit_map, this.getApplicationContext().getFilesDir());
				}
			}
		} else {
			createStatus.setText("Please complete and enter valid input.");
		}
		

		
		
	}	

}
