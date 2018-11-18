import accountErrors.*;
import java.lang.*;
import java.io.*;
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

    public Checking () {
    // Empty constructor
    }

    public void setPIN (int PIN) throws OverwriteException, LengthException {
	super.setPIN(PIN);
    }

    public void setSSN (int SSN) throws OverwriteException, LengthException {
	super.setSSN(SSN);
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

    public int getID () {
        return this.accountID;
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
