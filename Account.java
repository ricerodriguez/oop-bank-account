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
    public Account () {
        // Empty constructor
    }

    // Called from Checking/Saving subclass using the super() constructor
    // when creating a new checking or savings account.
    private Account (int PIN, int SSN, String type) throws LengthException {
        if (type.equals("checking")) {
            // Generate a random 5 digit account ID
            this.accountIDChecking[numChecking] = (int)(Math.random() * 99998)+1;
            this.numChecking++;
        } else if (type.equals("saving")) {
            this.accountIDSaving[numSaving] = (int)(Math.random() * 99998)+1;
            this.numSaving++;
        }
        int lengthPIN = String.valueOf(PIN).length();
        int lengthSSN = String.valueOf(SSN).length();
        if (lengthPIN == 4) {
            this.PIN = PIN;
        } else {
            throw new LengthException("PIN");
        }

        if (lengthSSN == 9){
            this.SSN = SSN;
        } else {
            throw new LengthException("SSN");
        }

    }

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

    private int getAccountID (String type) {
        if (type.equals("checking")) {
            return this.accountIDChecking[numChecking-1];
        } else if (type.equals("saving")) {
            return this.accountIDSaving[numSaving-1];
        } else {
            return 0;
        }
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

    public class Checking extends Account {
        protected int accountID;
        protected double balance;

        // Constructor for new checking account. Called when
        // opening a new checking account. Does not require
        // a balance.
        public Checking (int PIN, int SSN) throws LengthException {
            super(PIN, SSN, "checking");
            this.accountID = super.getAccountID("checking");
        }

        // Constructor for opening checking account in existing bank account.
        public Checking (Account account) throws InvalidPIN, NoAccount {
            account.accountIDChecking[account.numChecking] = (int)(Math.random() * 99998)+1;
            account.numChecking++;
            this.accountID = account.getAccountID("checking");
        }

        // Deposit funds to checking account.
        public double depositFunds (double funds) {
            this.balance = balance+funds;
            return this.balance;
        }

        public double withdrawFunds (double funds) throws LowFunds {
            if (balance >= funds) {
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
        public Saving (int PIN, int SSN) throws LengthException {
            super(PIN, SSN, "saving");
            this.accountID = super.getAccountID("saving");
        }

        // Constructor for opening savings account in existing bank account
        public Saving (Account account) throws InvalidPIN, NoAccount {
            account.accountIDSaving[account.numSaving] = (int)(Math.random() * 99998)+1;
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
