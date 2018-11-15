import java.io.*;
import java.lang.*;
import accountErrors.*;
public class Account {
    private int PIN;
    private int SSN;
    private int accountIDChecking;
    private int accountIDSaving;

    // Called from Checking/Saving subclass using the super() constructor
    // when creating a new checking or savings account.
    public Account (int PIN, int SSN, String type) throws LengthException {
        if (type.equals("checking")) {
            // Generate a random 5 digit account ID
            this.accountIDChecking = (int)(Math.random() * 99999);
        } else if (type.equals("saving")) {
            this.accountIDSaving = (int)(Math.random() * 99999);
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

    private boolean validatePIN (int PIN) throws InvalidPIN {
        if (this.PIN != PIN) {
            throw new InvalidPIN();
        } else {
            return true;
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


    public class Checking {
        private int accountID;
        private double balance;

        // Constructor for new checking account. Called when
        // opening a new checking account. Does not require
        // a balance.
        public Checking (int PIN, int SSN) {
            // This doesn't work right now because it wants to
            // call to the default constructor, which doesn't
            // exist. This is probably because it doesn't see
            // the correct constructor since it is non-static.
            // However, I can't make it static because (1) it's
            // super weird to make a static constructor wtf and
            // (2) the constructor makes references to its class
            // members.
            super(PIN, SSN, "checking");
            this.accountID = super.getAccountID("checking");
        }

        public Checking (int PIN, Account account) {
            if(account.validatePIN(PIN)) {
                account.accountIDChecking = (int)(Math.random() * 99999);
                this.accountID = account.getAccountID("checking");
            }
        }
    }

    class Saving {
        private int accountID;
        private double balance;

        // Constructor for new savings account. Called when
        // opening a new savings account. Does not require
        // a balance.
        public Saving (int PIN, int SSN) {
            super(PIN, SSN, "saving");
            this.accountID = super.getAccountID("saving");
        }

        public Saving (int PIN, Account account) {
            if(account.validatePIN(PIN)) {
                account.accountIDChecking = (int)(Math.random() * 99999);
                this.accountID = account.getAccountID("saving");
            }
        }

    }
}
