package bank;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class Records extends BankRecords {
	private static FileWriter fw;

	// Constructor: Initializes FileWriter for output
	public Records() {
		try {
			fw = new FileWriter("bankrecords.txt");
		} catch (IOException e) {
			System.err.println("Error creating FileWriter: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Records br = new Records();
		br.readData(); // Reads data from input source

		try {
			// Perform various data analysis operations
			avgComp();
			femalesWithMortgageAndSavings();
			malesWithCarAndChild();
			appendNameAndDateTime();
		} catch (Exception e) {
			System.err.println("Error during data analysis: " + e.getMessage());
		} finally {
			// Ensure FileWriter is closed
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				System.err.println("Error closing FileWriter: " + e.getMessage());
			}
		}
	}

	// Calculate and output average income by gender
	private static void avgComp() throws IOException {
		Arrays.sort(robjs, new SexComparator()); // Sort by sex

		int maleCt = 0, femCt = 0;
		double maleInc = 0, femInc = 0;

		// Calculate total income and count for each gender
		for (BankRecords obj : robjs) {
			if (obj.getSex().equals("FEMALE")) {
				femCt++;
				femInc += obj.getIncome();
			} else {
				maleCt++;
				maleInc += obj.getIncome();
			}
		}

		// Calculate averages
		double femAvg = femCt > 0 ? femInc / femCt : 0;
		double maleAvg = maleCt > 0 ? maleInc / maleCt : 0;

		// Output results
		System.out.println("Data analytic results:\n");
		String output = String.format("Average income Females: $%.2f\nAverage income Males: $%.2f\n\n", femAvg,
				maleAvg);
		System.out.print(output);
		fw.write(output);
	}

	// Count and output females with both mortgage and savings account
	private static void femalesWithMortgageAndSavings() throws IOException {
		long count = Arrays.stream(robjs).filter(obj -> obj.getSex().equals("FEMALE") && obj.getMortgage().equals("YES")
				&& obj.getSave_act().equals("YES")).count();

		String output = "Num. of females with mortgage and savings acc: " + count + "\n\n";
		System.out.print(output);
		fw.write(output);
	}

	// Count and output males with car and one child, grouped by region
	private static void malesWithCarAndChild() throws IOException {
		Arrays.sort(robjs, new LocationComparator()); // Sort by region

		String currentRegion = "";
		int count = 0;

		for (BankRecords obj : robjs) {
			if (!obj.getRegion().equals(currentRegion)) {
				if (!currentRegion.isEmpty()) {
					writeRegionCount(currentRegion, count);
				}
				currentRegion = obj.getRegion();
				count = 0;
			}
			if (obj.getSex().equals("MALE") && obj.getCar().equals("YES") && obj.getChildren() == 1) {
				count++;
			}
		}
		writeRegionCount(currentRegion, count); // Write last region's count
	}

	// Helper method to write region count
	private static void writeRegionCount(String region, int count) throws IOException {
		String output = String.format("%s region males with car & 1 child : %d\n", region, count);
		System.out.print(output);
		fw.write(output);
	}

	private static void appendNameAndDateTime() throws IOException {
		String name = "Tejas Saraf";
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateTime = now.format(formatter);

		String output = "\n" + name + "\n" + dateTime;
		System.out.print(output);
		fw.write(output);
	}
}

// Comparator for sorting BankRecords by sex
class SexComparator implements Comparator<BankRecords> {
	@Override
	public int compare(BankRecords o1, BankRecords o2) {
		return o1.getSex().compareTo(o2.getSex());
	}
}

// Comparator for sorting BankRecords by region
class LocationComparator implements Comparator<BankRecords> {
	@Override
	public int compare(BankRecords o1, BankRecords o2) {
		return o1.getRegion().compareTo(o2.getRegion());
	}
}