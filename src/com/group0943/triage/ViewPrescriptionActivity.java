package com.group0943.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewPrescriptionActivity extends Activity {

	/** The name of the patient_prescription text file. */
	protected static final String PATIENT_PRESCRIPTION_INFO = "patient_prescription.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_prescription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_prescription, menu);
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
		
		TextView statusText = (TextView) findViewById(R.id.patient_prescription_displayText);
		
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
	
	/**
	 * Starts/returns to MainActivity
	 * @param view The current activity view.
	 */
	public void load_mainActivity(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}
	
	
}
