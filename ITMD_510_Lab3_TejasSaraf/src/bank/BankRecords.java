package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankRecords extends Client {

	// Declared a static array of BankRecords objects to hold up to 600 records
	static BankRecords[] robjs = new BankRecords[600];
	static ArrayList<List<String>> array = new ArrayList<>();

	// Defined instance fields representing the columns in the CSV file
	private String id;
	private int age;
	private String sex;
	private String region;
	private double income;
	private String married;
	private int children;
	private String car;
	private String save_act;
	private String current_act;
	private String mortgage;
	private String pep;

	// Provided getters and setters for each field to allow access and modification
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getSave_act() {
		return save_act;
	}

	public void setSave_act(String save_act) {
		this.save_act = save_act;
	}

	public String getCurrent_act() {
		return current_act;
	}

	public void setCurrent_act(String current_act) {
		this.current_act = current_act;
	}

	public String getMortgage() {
		return mortgage;
	}

	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}

	public String getPep() {
		return pep;
	}

	public void setPep(String pep) {
		this.pep = pep;
	}

	// Override the readData method to read data from a CSV file
	@Override
	public void readData() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new FileReader(new File("C:\\Users\\SCV\\Downloads\\lab_2 (1)\\lab 2\\bank-Detail.csv")));
			String line;

			while ((line = br.readLine()) != null) {
				array.add(Arrays.asList(line.split(",")));
			}
		} catch (FileNotFoundException e) { // If the file is not found
			System.err.println("File not found: " + e.getMessage());
		} catch (IOException e) { // If there is an error while reading the file
			System.err.println("Error reading file: " + e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close(); // Ensure if BufferedReader is closed
			} catch (IOException e) { // If closing fails
				System.err.println("Error closing file: " + e.getMessage());
			}
		}

		// Call processData
		processData();
	}

	// Override the processData method to parse the data from the ArrayList
	@Override
	public void processData() {
		int idx = 0;

		for (List<String> rowData : array) {
			try {
				// Created a new BankRecords object and populate it with data
				robjs[idx] = new BankRecords();
				robjs[idx].setId(rowData.get(0)); // ID
				robjs[idx].setAge(Integer.parseInt(rowData.get(1))); // Age
				robjs[idx].setSex(rowData.get(2)); // Sex
				robjs[idx].setRegion(rowData.get(3)); // Region
				robjs[idx].setIncome(Double.parseDouble(rowData.get(4))); // Income
				robjs[idx].setMarried(rowData.get(5)); // Marital status
				robjs[idx].setChildren(Integer.parseInt(rowData.get(6))); // Children
				robjs[idx].setCar(rowData.get(7)); // Car ownership
				robjs[idx].setSave_act(rowData.get(8)); // Savings account
				robjs[idx].setCurrent_act(rowData.get(9)); // Current account
				robjs[idx].setMortgage(rowData.get(10)); // Mortgage status
				idx++;
			} catch (IndexOutOfBoundsException e) { // Catch if there aren't enough columns
				System.err.println("Error processing row " + (idx + 1) + ": " + e.getMessage());
			} catch (NumberFormatException e) { // Catch if parsing fails
				System.err.println("Error converting data in row " + (idx + 1) + ": " + e.getMessage());
			} catch (Exception e) { // Catch any unexpected errors
				System.err.println("Unexpected error in row " + (idx + 1) + ": " + e.getMessage());
			}
		}
	}
}