import java.io.*;
import java.lang.*;
import accountErrors.*;
public class Account {
    private int PIN;
    private int SSN;
    private int accountIDChecking;
    private int accountIDSaving;

    public Account () {
        // Empty constructor
    }

    // Called from Checking/Saving subclass using the super() constructor
    // when creating a new checking or savings account.
    private Account (int PIN, int SSN, String type) throws LengthException {
        if (type.equals("checking")) {
            // Generate a random 5 digit account ID
            this.accountIDChecking = (int)(Math.random() * 99998)+1;
        } else if (type.equals("saving")) {
            this.accountIDSaving = (int)(Math.random() * 99998)+1;
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

    private boolean validatePIN (int PIN, int accountID, String type) throws InvalidPIN, NoAccount {
        if (this.PIN != PIN) {
            throw new InvalidPIN();
        } else if (this.whichAccount(accountID).equals(type)) {
            return true;
        } else {
            throw new NoAccount();
        }
    }

    private String whichAccount (int accountID) {
        if (this.accountIDChecking == accountID) {
            return "checking";
        } else if (this.accountIDSaving == accountID) {
            return "saving";
        } else {
            return "null";
        }
    }

    private int getAccountID (String type) {
        if (type.equals("checking")) {
            return this.accountIDChecking;
        } else if (type.equals("saving")) {
            return this.accountIDSaving;
        } else {
            return 0;
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
        private int accountID;
        private double balance;

        // Constructor for new checking account. Called when
        // opening a new checking account. Does not require
        // a balance.
        public Checking (int PIN, int SSN) throws LengthException {
            super(PIN, SSN, "checking");
            this.accountID = super.getAccountID("checking");
        }

        // Constructor for opening checking account in existing bank account.
        public Checking (int PIN, int accountID, Account account) throws InvalidPIN, NoAccount {
            if (account.validatePIN(PIN,accountID,"checking")) {
                account.accountIDChecking = (int)(Math.random() * 99998)+1;
                this.accountID = account.getAccountID("checking");
            }
        }

        // Deposit funds to checking account.
        public void depositFunds (int PIN, int accountID, double funds, Account account) throws InvalidPIN, NoAccount {
            if (account.validatePIN(PIN,accountID,"checking")) {
                this.balance = balance+funds;
            }
        }
    }

    public class Saving extends Account {
        private int accountID;
        private double balance;

        // Constructor for new savings account. Called when
        // opening a new savings account. Does not require
        // a balance.
        public Saving (int PIN, int SSN) throws LengthException {
            super(PIN, SSN, "saving");
            this.accountID = super.getAccountID("saving");
        }

        public Saving (int PIN, int accountID, Account account) throws InvalidPIN, NoAccount {
            if(account.validatePIN(PIN,accountID,"saving")) {
                account.accountIDChecking = (int)(Math.random() * 99998)+1;
                this.accountID = account.getAccountID("saving");
            }
        }
    }
}
