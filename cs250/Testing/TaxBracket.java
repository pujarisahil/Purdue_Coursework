import java.util.Scanner;
import java.text.*;
/*
 * CS180 - Project 2: TaxBracket
 * Create program to read a series of incomes as int values from
 * stdin, sort into brackets, and compute brackets and deductibles
 * @author Andrew Blejde, ablejde@purdue.edu, L01
 */

public class TaxBracket {
 
    // class ints to increment for each brackets
    int bracket10;
    int bracket15;
    int bracket25;
    int bracket28;
    int bracket33;
    int bracket35;
    int bracket40;
    int totalIncomes;
    
    public TaxBracket()
    {
        // "construct" object with default values
        this.bracket10 = 0;
        this.bracket15 = 0;
        this.bracket25 = 0;
        this.bracket28 = 0;
        this.bracket33 = 0;
        this.bracket25 = 0;
        this.bracket40 = 0;
        this.totalIncomes = 0;
         
    }
    public double addBracket(int income)
    {
        // Increment total incomes for this TaxBracket object.
        this.totalIncomes++;
        
        // Find appropriate income bracket in order to increment that
        // bracket total as well as return the relevant tax rate.
        if (income >= 0 && income < 9076)
        {
            this.bracket10++;
            return 0.10;
        }
        else if (income > 9075 && income < 36901)
        {
            this.bracket15++;
            return 0.15;
        }
        else if (income > 36900 && income < 89351)
        {
            this.bracket25++;
            return 0.25;
        }
        else if (income > 89350 && income < 186351)
        {
            this.bracket28++;
            return 0.28;
        }
        else if (income > 186350 && income < 405101)
        {
            this.bracket33++;
            return 0.33;
        }
        else if (income > 405100 && income < 406751)
        {
            this.bracket35++;
            return 0.35;
        }
        else if (income > 406750)
        {
            this.bracket40++;
            return 0.40;
        }
       
       // Default return value if things fall apart.
        return 0.0;
    }
    public double calculateTaxes(int income, int dependents, double bracket)
    {
        // Calculate taxes using given formula. Call findDeductible in order
        // to determine whether a tax applies or not.
        if (findDeductible(income, dependents) >= income )
            return 0.0;
        else
        {
            return (income - findDeductible(income, dependents)) * bracket;
        }
    }
    public double findDeductible(int income, int dependents)
    {   
        // Find deductible based on given formula. If income is above
        // a certain threshold, adjust the deductible by 1/3.
        if (income <= 152525)
            return 6200 + (dependents * 3950.0);
        else
            return 6200 + (dependents * (3950.0 / 3));
    }
    public static void main(String[] args)
    {
        // Create scanner object to read in user input
        Scanner input = new Scanner(System.in);
        
        // Create TaxBracket object in order to call non static methods in main
        // operating on this TaxBracket object.
        TaxBracket bracket = new TaxBracket();

        // Create decimal format object in order to properly format the calculation
        // output after each iteratio.
        DecimalFormat f = new DecimalFormat("#0.00");

        // Boolean condition to check whether to continue prompting the user for
        // TaxBracket entries.
        boolean check = false;
        while (check == false)
        {
            // Get user input in the form, INCOME,DEPENDENTS
        //    System.out.println("Please enter an income and the number of dependents.");
            
            // Read in user input into userInput String object
            String userInput = input.nextLine();

            // Split user input by "," into String array indices.
            String[] in = userInput.split(",");

            // Initialize income and dependent variables to be parsed from 
            // String array.
            int income = 0;
            int dependents = 0;

            // Use a try / catch to parse integer values from user input. If this fails,
            // we can assume the user gave invalid input. This is a condition for terminating
            // the loop, so in catch we set check to true in order to invalidate the while
            // condition and then continue to the next iteration.
            try
            {
                income = Integer.parseInt(in[0]);
                dependents = Integer.parseInt(in[1]);
            }
            catch (Exception e)
            {
                check = true;
                continue;
            }
            
            // Print taxes results for the given loop iteration. Use DecimalFormat object
            // for proper formatting.
            System.out.println("Taxes for income $" + income + " are " + 
                f.format(bracket.calculateTaxes(income, dependents, bracket.addBracket(income))));
   
        }

        // Print results for each bracket as well as total incomes.
        System.out.println("Number of Total incomes enters: " + bracket.totalIncomes);
        System.out.println("Number of 10% bracket: " + bracket.bracket10);
        System.out.println("Number of 15% bracket: " + bracket.bracket15);
        System.out.println("Number of 25% bracket: " + bracket.bracket25);
        System.out.println("Number of 28% bracket: " + bracket.bracket28);
        System.out.println("Number of 33% bracket: " + bracket.bracket33);
        System.out.println("Number of 35% bracket: " + bracket.bracket35);
        System.out.println("Number of 40% bracket: " + bracket.bracket40);
  

    }
}
 
