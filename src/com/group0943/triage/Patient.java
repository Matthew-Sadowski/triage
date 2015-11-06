package com.group0943.triage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient {

	/** This Patient's name. */
    protected String[] name;
    
    /** This Patient's date of birth in format yyyy-mm-dd. */
    protected String date_of_birth;
    
    /** This Patient's birth year. */
    protected String year; 
    
    /** This Patient's birth month. */
    protected String month; 
    
    /** This Patient's birth day. */
    protected String day; 
    
    /** This Patient's health card number. */
    protected String health_card_number; 
    
    /** This Patient's urgency level. */
    protected int urgency_level;
    
    /**
     * Creates a Patient.
     * @param name The Patient's name.
     * @param health_card_number The Patient's health card number.
     * @param year The year the Patient was born.
     * @param month The month (number) the Patient was born.
     * @param day The day (number) the Patient was born.
     */
    public Patient(String[] name, String health_card_number, String year, String month, String day) {
        this.name = name.clone();
        this.health_card_number = health_card_number;
        this.date_of_birth = year + "-" + month + "-" + day;
        this.year = year;
        this.month = month;
        this.day = day;
        this.urgency_level = 0;
    }
    
    /**
     * Returns this Person's name.
     * @return The name of this Person.
     */
    public String[] getName() {
        return name.clone();
    }
    
    /**
     * Returns this Person's name as a String.
     * @return This Person's name in a "firstname (middle name(s)) lastname " format.
     */
    public String getNameAsString(){
    	
    	String nameAsString = "";
    	
    	for (String n: name){
    		nameAsString += n + " ";
    	}
    	
    	return nameAsString.trim();
    }
    
	/** 
	 * Returns an interpreted version of Patient as a String.
	 * @return A string representation of Patient.
	 */
    @Override
    public String toString(){
    	return health_card_number+","+this.getNameAsString()+","+date_of_birth;
    }
    
	/** 
	 * Returns an different version of toString which also
	 * displays urgency levels of the patient.
	 * @return A modified string representation of Patient.
	 */
    public String displayInfo(){
    	return health_card_number+", "+this.getNameAsString()+", "+date_of_birth +
    			", " + "Urgency Level: " + this.getUrgencyLevel();
    }
    
    /**
     * Set the urgency level of Patient.
     * @param level The urgency level of the Patient.
     */
    public void setUrgencyLevel(int level){
    	this.urgency_level = level;
    }
    
    /**
     * Get the urgency level of Patient.
     * @return The urgency level of the Patient.
     */
    public int getUrgencyLevel(){
    	return urgency_level;
    }
    
    /**
     * Returns this Person's health card number.
     * @return This Person's health card number.
     */
    public String getHealth_card_number(){
    	return health_card_number;
    }
    
    /**
     * Returns this Person's date of birth.
     * @return This Person's date of birth in YYYYMMDD format.
     */
    public String getDate_of_birth() {
    	return date_of_birth;
    }
    
    /**
     * Returns this Person's age.
     * @return This Person's age.
     */
    public int getAge() {
    	
    	Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");		
		String current_date = dateFormat.format(date);
		String[] current_dateArray = current_date.split("-");
		int current_year = Integer.parseInt(current_dateArray[2]);
		int current_month = Integer.parseInt(current_dateArray[1]);
		int current_day = Integer.parseInt(current_dateArray[0]);
		
		int age = current_year - Integer.parseInt(this.year);
		if (current_month < Integer.parseInt(this.month)){
			age--;
		} else if (current_month == Integer.parseInt(this.month) && current_day < Integer.parseInt(this.day)){
			age--;
		}

    	return age;
    }
}
