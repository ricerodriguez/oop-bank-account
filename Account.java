import java.io.*;
import java.lang.*;
import accountErrors.*;
public class Account {
    protected int PIN;
    protected int SSN;
    // Array of checking account ID's
    protected int [] accountIDChecking = new int [10];
    // Array of savings account ID's
    protected int [] accountIDSaving = new int [10];
    // Number of Checking Accounts
    protected int numChecking = 0;
    // Number of Savings Accounts
    protected int numSaving = 0;
    // Array of checking accounts
    protected Checking [] accountChecking = new Checking [10];
    // Array of savings accounts
    protected Saving [] accountSaving = new Saving [10];

    // Empty constructor
    public Account () {
        // Empty constructor
    }

    public int getID() {
	// Empty method. This is here so that dynamic method dispatch
	// can be used. This method will never be called from this class.
	return 0;
    }

    public void closeAccount() throws NoAccount {
	// Empty method.
	// Later add a constructor into NoAccount exception that tells it that error
	// came from here. This line should never actually execute.
	throw new NoAccount();
    }

    // Called from Checking/Saving subclass using the super() constructor
    // when creating a new checking or savings account.
    protected Account (int PIN, int SSN, String type) throws LengthException, InvalidType {
        // Check if this is a checking account or a savings account
        if (type.equals("checking")) {
            // Generate a random 5 digit account ID
            this.accountIDChecking[numChecking] = (int)(Math.random() * 99998)+1;
            this.numChecking++;
        } else if (type.equals("saving")) {
            this.accountIDSaving[numSaving] = (int)(Math.random() * 99998)+1;
            this.numSaving++;
        } else {
            // If the type was neither checking nor savings, it's an invalid type
            throw new InvalidType(type);
        }
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

    // Use this to determine what kind of account something is based off its ID
    protected String whichAccount (int accountID) {
        for (int i = 0; i < numChecking; i++) {
            if (this.accountIDChecking[i] == accountID) {
                return "checking";
            } else {
                continue;
            }
        }

        for (int j = 0; j < numSaving; j++) {
            if (this.accountIDSaving[j] == accountID) {
                return "saving";
            } else {
                continue;
            }
        }

        return "null";
    }

    // Use this to get the account ID of the last account that was added
    protected int getAccountID (String type) {
        if (type.equals("checking")) {
            return this.accountIDChecking[numChecking-1];
        } else if (type.equals("saving")) {
            return this.accountIDSaving[numSaving-1];
        } else {
            return 0;
        }
    }

    // Use this to find the index of an account in the array of accounts
    protected int findIndex (int accountID, String type) throws NoAccount {
        int i = 0;
        while (i < 10) {
            if (type.equals("checking")) {
                if (this.accountIDChecking[i] == accountID) {
                    return i;
                } else {
                    i++;
                }
            } else if (type.equals("saving")) {
                if (this.accountIDSaving[i] == accountID) {
                    return i;
                } else {
                    i++;
                }
            } else {
                throw new NoAccount();
            }
        }
        return -1;
    }

    // Use this from the main program
    public boolean validatePIN (int PIN, int accountID, String type) throws InvalidPIN, NoAccount {
        if (this.PIN != PIN) {
            throw new InvalidPIN();
        } else if (this.whichAccount(accountID).equals(type)) {
            return true;
        } else {
            throw new NoAccount();
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
    protected void closeAccount (int accountID) throws NoAccount {
	// System.out.println("Got to close account method");
        // Store what kind of account this is into a String
        String type = this.whichAccount(accountID);
	// System.out.println("Got to which account method");
        // Store the index of the account in the array of accounts
        int num = this.findIndex(accountID,type);
	// System.out.println(num);
        // Create an integer for temp storage and for how many elements are left to move up
        int temp, howManyLeft;
	if (type.equals("null")) {
	    throw new NoAccount();
        } else if (type.equals("checking")) {
            howManyLeft = this.numChecking - num;
            temp = this.accountIDChecking[num];
            Checking check = this.accountChecking[num];
            check.balance = 0.0;
            check.accountID = 0;
            for (int i = num; i < howManyLeft; i++) {
                this.accountIDChecking[i]=this.accountIDChecking[i+1];
            }
        } else if (type.equals("saving")) {
            howManyLeft = this.numSaving - num;
            temp = this.accountIDSaving[num];
            Saving save = this.accountSaving[num];
            save.balance = 0.0;
            save.accountID = 0;
            for (int i = num; i < howManyLeft; i++) {
                this.accountIDSaving[i]=this.accountIDSaving[i+1];
            }
        }
    }

}
