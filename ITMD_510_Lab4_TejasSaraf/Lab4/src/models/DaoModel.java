package models;

import java.sql.*;
import records.BankRecords;

public class DaoModel {
	// Declare DB objects
	private DBConnect conn = null;
	private Statement stmt = null;
	private Connection dbConnection = null; // Single connection object
	private String currentTableName;

	// Constructor to create a DBConnect object instance and open a connection
	public DaoModel() {
		conn = new DBConnect();
		try {
			// Open a single database connection here
			dbConnection = conn.connect();
			System.out.println("Connecting to database to create table...");
			System.out.println("Connected database successfully...");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to create a table with pid, id, income, and pep fields
	public void createTable() {
		try {
			stmt = dbConnection.createStatement();

			// Generate a unique table name using a timestamp
			String tableName = "yourFirstinitial_First4LettersOfYourLastName_" + System.currentTimeMillis();

			System.out.println("Creating table in given database...");

			// Create the table with the unique name
			String createTableSQL = "CREATE TABLE " + tableName + " (" + "pid INTEGER NOT NULL AUTO_INCREMENT, "
					+ "id VARCHAR(10), " + "income NUMERIC(8,2), " + "pep VARCHAR(3), " + "PRIMARY KEY (pid))";

			stmt.executeUpdate(createTableSQL);

			System.out.println("Created table in given database...");

			// Store the table name for later use
			this.currentTableName = tableName;

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to insert records from an array of BankRecords using PreparedStatement
	public void insertRecords(BankRecords[] robjs) {
		try {
			System.out.println("Inserting records into the table...");
			String sql = "INSERT INTO " + currentTableName + " (id, income, pep) VALUES (?, ?, ?)";
			PreparedStatement pstmt = dbConnection.prepareStatement(sql);

			for (BankRecords obj : robjs) {
				pstmt.setString(1, obj.getId());
				pstmt.setDouble(2, obj.getIncome());
				pstmt.setString(3, obj.getPep());
				pstmt.addBatch();
			}

			pstmt.executeBatch(); // Execute batch insert for efficiency
			System.out.println("Records inserted!");

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to retrieve records from the table, sorted by 'pep' in descending
	// order
	public ResultSet retrieveRecords() {
		ResultSet rs = null;
		try {
			// Create a Statement with a scrollable ResultSet
			stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT pid, id, income, pep FROM " + currentTableName + " ORDER BY pep DESC";
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Close the database connection when done 
	public void closeConnection() {
		try {
			if (dbConnection != null) {
				dbConnection.close();
				System.out.println("Database connection closed.");
			}
		} catch (SQLException se) {
			se.printStackTrace(); // Error handling
		}
	}
}
