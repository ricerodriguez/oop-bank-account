package accountDefs;

import java.io.*;
import java.lang.*;
import accountErrors.*;
public class Account {
    protected int PIN;
    protected int SSN;
    // Array of account ID's
    protected int [] accountIDs = new int [10];
    // Array of accounts (checking or saving)
    protected Account [] accounts = new Account [10];
    // Array of account types
    protected String [] accountTypes = new String [10]; 
    // Number of checking or savings accounts
    protected int num = 0;

    // Empty constructor
    public Account () {
        // Empty constructor
    }
    // ~~~~~~ THESE METHODS ARE OVERRIDDEN IN SUBCLASSES: ~~~~~~
    public String getType() {
	// Empty method. This should never actually execute.
	return "null";
    }

    public void closeAccount() throws NoAccount {
	// Empty method.
	// Later add a constructor into NoAccount exception that tells it that error
	// came from here. This line should never actually execute.
	throw new NoAccount();
    }

    public void clearAccount () {
	return;
    }

    public double depositFunds (double funds) {
	return -1.0;
    }

    public double withdrawFunds (double funds) throws LowFunds {
	return -1.0;
    }

    public double transferFunds (Account acc1, Account acc2, double d) throws LowFunds {
	return -1.0;
    }

    public double getBalance() {
	return -1.0;
    }
    
    // ~~~~~~ THESE METHODS ARE OVERRIDDEN IN SUBCLASSES^ ~~~~~~

    public Account (int PIN, int SSN) throws LengthException {
        // Check if this is a checking account or a savings account
	// Generate a random 5 digit account ID
	this.accountIDs[num] = (int)(Math.random() * 99998)+1;
        // Make a string from the PIN integer so we can use the .length() method
        // for returning how many characters it is
        int lengthPIN = String.valueOf(PIN).length();
        int lengthSSN = String.valueOf(SSN).length();

        // If the PIN is 4 characters long, store it
        if (lengthPIN == 4) {
            this.PIN = PIN;
        } else {
            // Otherwise, throw an exception
            throw new LengthException("PIN");
        }

        // If the SSN is 9 characters long, store it
        if (lengthSSN == 9){
            this.SSN = SSN;
        } else {
            // Otherwise, throw an exception
            throw new LengthException("SSN");
        }

    }

    protected<T extends Account> void addAccount (T account) {
	System.out.println(num);
	this.accounts[num] = account;
	this.accountTypes[num] = account.getType();
	this.num++;
    }

    // Use this to determine what kind of account something is based off its ID
    protected String whichAccount (int accountID) throws NoAccount {
	int result = this.findIndex(accountID);
	return this.accountTypes[result];
    }

    // Use this to get the account ID of the last account that was added
    public int getAccountID () {
	return this.accountIDs[this.num-1];
    }

    // Use this to find the index of an account in the array of accounts
    protected int findIndex (int accountID) throws NoAccount {
        int i = 0;
        while (this.accountIDs[i] != 0) {
	   if (this.accountIDs[i] == accountID) {
	       return i;
	   } else {
	       i++;
	   }
        }
	throw new NoAccount();
        // return -1;
    }

    protected int findIndex (Account account) throws NoAccount {
        int i = 0;
        while (this.accounts[i] != null) {
	   if (this.accounts[i] == account) {
	       return i;
	   } else {
	       i++;
	   }
        }
	throw new NoAccount();
        // return -1;
    }

    // Use this from the main program
    public boolean validatePIN (int PIN) throws InvalidPIN {
        if (this.PIN != PIN) {
            throw new InvalidPIN();
        } else {
            return true;
        }
    }

    // Use this to set the PIN of an account
    public void setPIN (int PIN) throws OverwriteException, LengthException {
        if (this.PIN == 0) {
            int lengthPIN = String.valueOf(PIN).length();
            if (lengthPIN == 4) {
                this.PIN = PIN;
            } else {
                throw new LengthException("PIN");
            }
        } else {
            throw new OverwriteException("PIN");
        }
    }

    // Use this to set the SSN of an account
    public void setSSN (int SSN) throws OverwriteException, LengthException {
        if (this.SSN == 0) {
            int lengthSSN = String.valueOf(SSN).length();
            if (lengthSSN == 9) {
                this.SSN = SSN;
            } else {
                throw new LengthException("SSN");
            }
        } else {
            throw new OverwriteException("SSN");
        }
    }

    // Use this to close an account. Replaces the account in the array of accounts with a 0,
    // changes balance and account ID to 0. Moves all the other accounts in the array up a
    // spot to replace the old one.
    public void closeAccount (Account account) throws NoAccount {
        // Store the index of the account in the array of accounts
	try {
	    int numPartial = this.findIndex(account);
	    int numTotal = this.num;
        // Create an integer for temp storage and for how many elements are left to move up
	    int howManyLeft;
	    howManyLeft = numTotal - numPartial;
	    System.out.println(howManyLeft);
	    for (int i=numPartial; i<howManyLeft;i++) {
		this.accountIDs[i]=this.accountIDs[i+1];
		this.accounts[i]=this.accounts[i+1];
		this.accountTypes[i]=this.accountTypes[i+1];
	    }
	} catch (NoAccount err) {
	    throw new NoAccount();
	}
    }

    public Account getAccount () {
	return this;
    }

    public Account [] getAccounts () {
	return this.accounts;
    }

}
