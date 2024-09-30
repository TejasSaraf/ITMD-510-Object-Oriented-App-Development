package banking;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccountHolder {
    public static double annualInterestRate = 0.04; // All accounts have a static interest rate of 4% per year.
    private double balance; // Stores the current balance of the account

    // Constructor that accepts an initial balance
    // Throws an error if the initial balance is negative
    public AccountHolder(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Error: Initial balance cannot be negative.");
        } else {
            balance = initialBalance; // Set balance if it's valid
        }
    }

    // Deposit method to add money to the account
    // Ensures that the deposit amount is positive, otherwise throws an error
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        } else {
            balance += amount; // Increase the balance by the deposit amount
            System.out.println("Deposited: $" + amount);
        }
    }

    // Withdrawal method to subtract money from the account
    // Ensures that the balance after withdrawal doesn't drop below $50, otherwise throws an error
    public void withdrawal(double amount) {
        if (balance - amount < 50) {
            throw new IllegalArgumentException("Disallow the withdrawal, account balance cannot drop below $50.");
        } else {
            balance -= amount; // Decrease the balance by the withdrawal amount
            System.out.println("Withdrawn: $" + amount);
        }
    }

    // Adds interest based on the static annual interest rate and updates the balance
    public void applyMonthlyInterest() {
        balance += balance * (annualInterestRate / 12.0); // Apply monthly interest, annual rate divided by 12
    }

    public void displayBalance() {
        System.out.println("\nBalance with interest applied: $" + balance); // Print the updated balance
    }

    // If the balance is below $50, the method will not calculate or display the table 
    public void displayInterestTable() {
        if (balance < 50) {
            System.out.println("Balance is less than $50, cannot calculate interest.");
            return;
        }

        System.out.println("\nMonthly balances for one year at 4% interest:");
        System.out.println("Base\t\t$" + String.format("%.2f", balance)); // Display starting balance as 'Base'
        
        for (int i = 1; i <= 12; i++) {
            applyMonthlyInterest(); // Apply monthly interest to balance
            System.out.printf("Month %d:\t$%.2f%n", i, balance); // Print updated balance each month
        }
    }

    public void displayProgramInfo() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("Cur dt=" + timeStamp);
        System.out.println("Programmed by Tejas Saraf");
    }
 
    public double getBalance() {
        return balance; // Return the current balance
    }
}
