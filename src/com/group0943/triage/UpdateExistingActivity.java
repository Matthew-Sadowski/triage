package com.group0943.triage;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class UpdateExistingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_existing);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_existing, menu);
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
	 * Updates an existing visit record with provided information from 
	 * @param view health card number and vitals 
	 * creating its own time based off of internal settings.
	 */
	public void updateExisting(View view){
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");		
		String time = dateFormat.format(date);
		
		TextView updateStatus = (TextView) findViewById(R.id.updateStatus);
		
		EditText healthcardNumText = (EditText) findViewById(R.id.healthcard_number_field);
		String healthcardNum = healthcardNumText.getText().toString();
		
		EditText temperatureText = (EditText) findViewById(R.id.temperature_field);
		String temperature = temperatureText.getText().toString();
		
		EditText bloodPressureText = (EditText) findViewById(R.id.blood_pressure_field);
		String bloodPressure = bloodPressureText.getText().toString();
		
		EditText heartRateText = (EditText) findViewById(R.id.heart_rate_field);
		String heartRate = heartRateText.getText().toString();
		
		CheckBox seenByDoctorCheckBox = (CheckBox) findViewById(R.id.updateSeenByDoctorCheckBox);
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
			
			ViewRecord viewRecord = new ViewRecord();
			ReadFromFile readFile = new ReadFromFile();
			SaveRecord saveRecord = new SaveRecord();
			
			try {
				if (viewRecord.getPatientRecord(healthcardNum, this.getApplicationContext().getFilesDir()) == null){
					updateStatus.setText("Patient Does not Exist.");
				}
				else{
					ArrayList<ArrayList<String>> visit_record_instance = 
							viewRecord.getPatientRecord(healthcardNum, this.getApplicationContext().getFilesDir());
					
					visit_record_instance.add(information);
					
					Map<String, ArrayList<ArrayList<String>>> visitRecords;
					visitRecords = readFile.readEntireFile(this.getApplicationContext().getFilesDir());
					visitRecords.remove(healthcardNum);
					visitRecords.put(healthcardNum, visit_record_instance);
				    
				    saveRecord.saveToFile(visitRecords, this.getApplicationContext().getFilesDir());
				    updateStatus.setText("SUCCESS!");
				}
			} catch (FileNotFoundException e) {
				updateStatus.setText("Patient Does not Exist.");
			}
		} else {
			updateStatus.setText("Please complete and enter valid input.");
		}

	}
}
