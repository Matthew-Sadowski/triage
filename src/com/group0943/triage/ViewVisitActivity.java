package com.group0943.triage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewVisitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_visit);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_visit, menu);
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
	 * Views Patient visit records.
	 * @param view The current activity view.
	 */
	public void viewVisit(View view) {
		
    	EditText health_card_text = (EditText) findViewById(R.id.healthcard_number);
    	String health_card_number = health_card_text.getText().toString();
    	
    	EditText visit_number_text = (EditText) findViewById(R.id.visitNum);
    	String visit_number_str = visit_number_text.getText().toString();
    	int visit_num;
    	if (visit_number_str.equals("")){
    		visit_num = 0;
    	} else {
    		visit_num = Integer.parseInt(visit_number_str);
    	}
    	   	
    	TextView time = (TextView) findViewById(R.id.timeText);
    	TextView patient_temp = (TextView) findViewById(R.id.temperature_display);
    	TextView patient_blood_pressure = (TextView) findViewById(R.id.blood_presure_display);
    	TextView patient_heart_rate = (TextView) findViewById(R.id.heart_rate_display);
    	TextView patient_seen_by_doc = (TextView) findViewById(R.id.seenByDoctorDisplay);
    	TextView total_num_of_visits = (TextView) findViewById(R.id.num_of_visits);
    	
    	ViewRecord viewRecord = new ViewRecord();

		try {
			if (viewRecord.getPatientRecord(health_card_number, this.getApplicationContext().getFilesDir()) == null){
				patient_seen_by_doc.setText("Patient does not exist in records.");
				time.setText("Time");
				patient_temp.setText("");
				patient_blood_pressure.setText("");
				patient_heart_rate.setText("");
			}
			else{
				ArrayList<ArrayList<String>> visit_record_instance = 
						viewRecord.getPatientRecord(health_card_number, this.getApplicationContext().getFilesDir());

				int num_of_visits = visit_record_instance.size();
				total_num_of_visits.setText(String.valueOf(num_of_visits));

				if (visit_num > 0 && visit_num <= num_of_visits){
					ArrayList<String >patient_info = visit_record_instance.get(visit_num-1);
					time.setText(patient_info.get(0));
					patient_temp.setText(patient_info.get(1));
					patient_blood_pressure.setText(patient_info.get(2));
					patient_heart_rate.setText(patient_info.get(3));
					if (patient_info.get(4).equals("true")){
						patient_seen_by_doc.setText("Has been seen by a doctor.");
					} else{
						patient_seen_by_doc.setText("Has not been seen by a doctor.");
					}
				} else {
					patient_seen_by_doc.setText("Please enter a valid visit number "
							+ "(This Patient has " + String.valueOf(num_of_visits) + " visit(s)).");
					time.setText("Time");
					patient_temp.setText("");
					patient_blood_pressure.setText("");
					patient_heart_rate.setText("");
				}

				
			}
		} catch (FileNotFoundException e) {
			patient_seen_by_doc.setText("Patient does not exist in records.");
		}
	}
	
}
