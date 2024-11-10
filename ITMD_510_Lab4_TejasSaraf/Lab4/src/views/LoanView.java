package views;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class LoanView {

	public void runView(ResultSet rs) {
		Vector<Vector<Object>> data = new Vector<>();
		Vector<String> columnNames = new Vector<>();

		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();

			// Get column names
			for (int i = 1; i <= columns; i++) {
				columnNames.add(metaData.getColumnName(i));
			}

			// Retrieve row data from ResultSet and store in data vector
			while (rs.next()) {
				Vector<Object> row = new Vector<>(columns);
				for (int i = 1; i <= columns; i++) {
					row.add(rs.getObject(i));
				}
				data.add(row);
			}

			// Close ResultSet after reading
			rs.close();

			// Create JTable and display in JFrame
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			JTable table = new JTable(model);
			JFrame frame = new JFrame("Loan Analysis");
			frame.setSize(800, 300);
			frame.add(new JScrollPane(table));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
