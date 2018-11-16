import java.io.*;
import java.lang.*;
import accountErrors.*;
public class Account {
    private int PIN;
    private int SSN;
    // Array of checking account ID's
    private int [] accountIDChecking = new int [10];
    // Array of savings account ID's
    private int [] accountIDSaving = new int [10];
    // Number of Checking Accounts
    private int numChecking = 0;
    // Number of Savings Accounts
    private int numSaving = 0;
    // Array of checking accounts
    private Checking [] accountChecking = new Checking [10];
    // Array of savings accounts
    private Saving [] accountSaving = new Saving [10];

    // Empty constructor
    public Account () {
        // Empty constructor
    }

    // Called from Checking/Saving subclass using the super() constructor
    // when creating a new checking or savings account.
    private Account (int PIN, int SSN, String type) throws LengthException, InvalidType {
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
    private String whichAccount (int accountID) {
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
    private int getAccountID (String type) {
        if (type.equals("checking")) {
            return this.accountIDChecking[numChecking-1];
        } else if (type.equals("saving")) {
            return this.accountIDSaving[numSaving-1];
        } else {
            return 0;
        }
    }

    // Use this to find the index of an account in the array of accounts
    private int findIndex (int accountID, String type) throws NoAccount {
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
    public void closeAccount (int accountID) throws NoAccount {
        // Store what kind of account this is into a String
        String type = this.whichAccount(accountID);
        // Store the index of the account in the array of accounts
        int num = this.findIndex(accountID,type);
        // Create an integer for temp storage and for how many elements are left to move up
        int temp, howManyLeft;
        if (type.equals("checking")) {
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

    public class Checking extends Account {
        protected int accountID;
        protected double balance;

        // Constructor for new checking account. Called when
        // opening a new checking account. Does not require
        // a balance.
        public Checking (int PIN, int SSN) throws LengthException, InvalidType {
            super(PIN, SSN, "checking");
            this.accountID = super.getAccountID("checking");
        }

        // Constructor for opening checking account in existing bank account.
        public Checking (Account account) throws InvalidPIN, NoAccount {
            account.accountIDChecking[account.numChecking] = (int)(Math.random() * 99998)+1;
            account.accountChecking[account.numChecking] = this;
            account.numChecking++;
            this.accountID = account.getAccountID("checking");
        }

        // Deposit funds to checking account.
        public double depositFunds (double funds) {
            this.balance = balance+funds;
            return this.balance;
        }

        public double withdrawFunds (double funds) throws LowFunds {
            if (this.balance >= funds) {
                this.balance -= funds;
                return this.balance;
            } else {
                throw new LowFunds();
            }
        }

        public Checking getChecking () {
            return this;
        }

        public <T extends Checking, Account> double transferFundsChecking (T type, double funds, int accountID) throws LowFunds, NoAccount {
            if (this.balance < funds) {
                throw new LowFunds();
            } else if (type.accountID != accountID) {
                throw new NoAccount();
            } else {
                this.balance -= funds;
                type.balance += funds;
                return this.balance;
            }
        }

        public <T extends Saving, Account> double transferFundsSaving (T type, double funds, int accountID) throws LowFunds, NoAccount {
            if (this.balance < funds) {
                throw new LowFunds();
            } else if (type.accountID != accountID) {
                throw new NoAccount();
            } else {
                this.balance -= funds;
                type.balance += funds;
                return this.balance;
            }
        }

    }

    public class Saving extends Account {
        protected int accountID;
        protected double balance;

        // Constructor for new savings account. Called when
        // opening a new savings account. Does not require
        // a balance.
        public Saving (int PIN, int SSN) throws LengthException, InvalidType {
            super(PIN, SSN, "saving");
            this.accountID = super.getAccountID("saving");
        }

        // Constructor for opening savings account in existing bank account
        public Saving (Account account) throws InvalidPIN, NoAccount {
            account.accountIDSaving[account.numSaving] = (int)(Math.random() * 99998)+1;
            account.accountSaving[account.numSaving] = this;
            account.numSaving++;
            this.accountID = account.getAccountID("saving");
        }

        // Deposit funds to savings account. Returns new balance.
        public double depositFunds (double funds) {
            this.balance += funds;
            return this.balance;
        }

        public double withdrawFunds (double funds) throws LowFunds {
            if (this.balance >= funds) {
                this.balance -= funds;
                return this.balance;
            } else {
                throw new LowFunds();
            }
        }

        public <T extends Checking, Account> double transferFundsChecking (T type, double funds, int accountID) throws LowFunds, NoAccount {
            if (this.balance < funds) {
                throw new LowFunds();
            } else if (type.accountID != accountID) {
                throw new NoAccount();
            } else {
                this.balance -= funds;
                type.balance += funds;
                return this.balance;
            }
        }

        public <T extends Saving, Account> double transferFundsSaving (T type, double funds, int accountID) throws LowFunds, NoAccount {
            if (this.balance < funds) {
                throw new LowFunds();
            } else if (type.accountID != accountID) {
                throw new NoAccount();
            } else {
                this.balance -= funds;
                type.balance += funds;
                return this.balance;
            }
        }

        public double getBalance () {
            return this.balance;
        }

    }
}
