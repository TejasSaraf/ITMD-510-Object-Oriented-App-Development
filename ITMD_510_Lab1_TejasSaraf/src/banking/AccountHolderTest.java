package banking;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountHolderTest {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Create a scanner to read user input
        AccountHolder account = null; // Variable to hold the account object, initialized to null

        try {
            //Prompt for initial balance from the user
            System.out.print("Please enter your initial balance: ");
            double initialBalance = input.nextDouble(); // Read the initial balance

            //Create the AccountHolder object with the given initial balance
            account = new AccountHolder(initialBalance); 

            //Prompt the user for a deposit amount
            System.out.print("Please enter a deposit amount: ");
            double depositAmount = input.nextDouble(); 
            account.deposit(depositAmount); 

            //Prompt the user for a withdrawal amount
            System.out.print("Please enter a withdrawal amount: ");
            double withdrawalAmount = input.nextDouble(); 
            account.withdrawal(withdrawalAmount); 

            //Apply monthly interest to the balance
            account.applyMonthlyInterest(); 
            account.displayBalance();

            //If the balance is $50 or more, display the 12-month interest table
            if (account.getBalance() >= 50) {
                account.displayInterestTable(); // Show the interest table if balance is above $50
            } else {
                System.out.println("Balance is less than $50, skipping interest table."); // Inform the user if balance is too low
            }

            account.displayProgramInfo();

        } catch (InputMismatchException e) {
            // Handle invalid input where the user provides non-numeric data
            System.out.println("Invalid input! Please enter a valid number."); // Error message for wrong input type
        } catch (IllegalArgumentException e) {
            // Handle cases where account creation, deposit, or withdrawal throws an error
            System.out.println(e.getMessage());
        } finally {
            input.close(); 
        }
    }
}
