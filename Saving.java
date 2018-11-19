import accountErrors.*;
import java.io.*;
import java.lang.*;

public class Saving extends Account {
    protected int accountID;
    protected double balance;

    public Saving () {
	// Empty constructor
    }

    // Constructor for new savings account. Called when
    // opening a new savings account. Does not require
    // a balance.
    public Saving (int PIN, int SSN) throws LengthException, InvalidType {
	super(PIN, SSN);
	super.addAccount(this);
	this.accountID = super.getAccountID();
    }

    // Constructor for opening savings account in existing bank account
    public Saving (Account account) throws InvalidPIN, NoAccount {
	account.accountIDs[account.num] = (int)(Math.random() * 99998)+1;
	account.accounts[account.num] = this;
	account.num++;
	this.accountID = account.getAccountID();
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

    public String getType () {
	return "saving";
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

    public int getAccountID () {
	return this.accountID;
    }

    public void closeAccount () throws NoAccount {
	try {
	    super.closeAccount(this.accountID);
	    this.balance = 0.0;
	    this.accountID = 0;
	} catch (NoAccount err) {
	    throw new NoAccount();
	}
    }

}
