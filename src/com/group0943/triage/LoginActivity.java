package com.group0943.triage;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity {
	
	public static final String LOGIN_INFO = "passwords.txt";
	
	private UserList users;
	
	private List<User> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
			users = new UserList(
					this.getApplicationContext().getFilesDir(), 
					LOGIN_INFO);
		} catch (IOException e) {
		e.printStackTrace();
		}
        setContentView(R.layout.activity_login);
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
    /**Checks if the login info is correct and starts the appropriate activity
     * based on the position of the user
     * 
     * @param view The current activity view.
     */
    public void loadActivity(View view){
    	
    	Intent intentNurse = new Intent(this, MainActivity.class);
    	
    	Intent intentPhysician = new Intent(this, PhysicianActivity.class);
    	
    	EditText username_text = (EditText) findViewById(R.id.username_field);
    	String username_input = username_text.getText().toString();
    	
    	EditText password_text = (EditText) findViewById(R.id.password_field);
    	String password_input = password_text.getText().toString();
    	
    	TextView incorrect_text = (TextView) findViewById(R.id.user_correct);
    	
    	userlist = users.getUsers();

		boolean userIsCorrect = false;
		
		for (User u: userlist){

    		if(u.getUsername().equals(username_input)){
    			if(u.getPassword().equals(password_input)){
    				userIsCorrect = true;
    				if(u.getPosition().equals("nurse")){
    					startActivity(intentNurse);
    					
    				}
    				if(u.getPosition().equals("physician")){
    					startActivity(intentPhysician);
    					
    				}
    				
    			}
    		}
    
    	}
		
    	if(!userIsCorrect){
    		incorrect_text.setText("Incorrect Login Information");
    		
    	}
    		
    	

    	
    }
    	
 }
