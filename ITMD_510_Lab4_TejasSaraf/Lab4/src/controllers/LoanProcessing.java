package controllers;

import models.DaoModel;
import records.BankRecords;
import views.LoanView;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanProcessing extends BankRecords {

	public static void main(String[] args) {
		BankRecords br = new BankRecords();
		br.readData();

		DaoModel dao = new DaoModel();
		dao.createTable();
		dao.insertRecords(robjs);

		ResultSet rs = dao.retrieveRecords();

		// Display loan analysis report in the console
		System.out.println("\nLOAN ANALYSIS REPORT:\n");
		System.out.printf("%5s %10s %8s\n", "ID", "INCOME", "PEP");

		// Limit the display to the first 10 rows
		int rowCount = 0;

		try {
			while (rs.next() && rowCount < 10) {
				String id = rs.getString("id").toUpperCase();
				Double income = rs.getDouble("income");
				String pep = rs.getString("pep");
				System.out.printf("%7s %10.2f %7s\n", id, income, pep);
				rowCount++;
			}

			// After printing the first 10 rows, pass the ResultSet to the LoanView to
			// display all records
			rs.beforeFirst();

			// Run the view before closing rs
			new LoanView().runView(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
