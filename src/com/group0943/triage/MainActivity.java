package com.group0943.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Starts Load_Patient_Records_Activity
	 * @param view The current activity view.
	 */
	public void load_patient_records(View view){
		
		Intent intent = new Intent(this, Load_Patient_Records_Activity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts CreateVisitActivity
	 * @param view The current activity view.
	 */
	public void create_visit_record(View view){
		
		Intent intent = new Intent(this, CreateVisitActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts UpdateExistingActivity
	 * @param view The current activity view.
	 */
	public void update_visit_record(View view){
		
		Intent intent = new Intent(this, UpdateExistingActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts ViewVisitActivity
	 * @param view The current activity view.
	 */
	public void view_visit_record(View view){
		
		Intent intent = new Intent(this, ViewVisitActivity.class);
		startActivity(intent);
		
	}

	/**
	 * Starts ListOfPatientsActivity
	 * @param view The current activity view.
	 */
	public void view_patient_list(View view){
		
		Intent intent = new Intent(this, ListOfPatientsActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts/returns to LoginActivity
	 * @param view The current activity view.
	 */
	public void load_loginActivity(View view){
		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * Starts/returns to ViewPrescriptionActivity
	 * @param view The current activity view.
	 */
	public void load_prescriptions(View view){
		
		Intent intent = new Intent(this, ViewPrescriptionActivity.class);
		startActivity(intent);
		
	}
	
}
