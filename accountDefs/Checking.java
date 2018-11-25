package accountDefs;

import accountErrors.*;
import java.lang.*;
import java.io.*;
public class Checking extends Account {
    protected int accountID;
    protected double balance;
    private Account superAccount;

    // Constructor for new checking account. Called when
    // opening a new checking account. Does not require
    // a balance.
    public Checking (int PIN, int SSN, Account account) throws LengthException, InvalidPIN, OverwriteException {
	account.setPIN(PIN);
	account.setSSN(SSN);
        account.accountIDs[account.num] = (int)(Math.random() * 99998)+1;
        account.accounts[account.num] = this;
        account.num++;
	this.accountID = account.getAccountID();
	this.superAccount = account.getAccount();
    }

    // Constructor for opening checking account in existing bank account.
    public Checking (Account account) throws InvalidPIN, NoAccount {
        account.accountIDs[account.num] = (int)(Math.random() * 99998)+1;
	System.out.println(account.num);
        account.accounts[account.num] = this;
        account.num++;
        this.accountID = account.getAccountID();
	this.superAccount = account.getAccount();
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

    public int getAccountID () {
        return this.accountID;
    }

    public String getType () {
	return "checking";
    }

    public double transferFunds (Account account, double funds, int accountID) throws LowFunds, NoAccount {
	if (this.balance < funds) {
	    throw new LowFunds();
	} else {
	    int index;
	    Account recipient;
	    this.balance -= funds;
	    try {
		index = account.findIndex(accountID);
		recipient = account.accounts[index];
		recipient.depositFunds(funds);
	    } catch (NoAccount err) {
		this.balance += funds;
		throw new NoAccount();
	    }
	}
	return this.balance;
    }

    // public <T extends Checking, Account> double transferFundsChecking (T type, double funds, int accountID) throws LowFunds, NoAccount {
    // 	if (this.balance < funds) {
    // 	    throw new LowFunds();
    // 	} else if (type.accountID != accountID) {
    // 	    throw new NoAccount();
    // 	} else {
    // 	    this.balance -= funds;
    // 	    type.balance += funds;
    // 	    return this.balance;
    // 	}
    // }

    // public <T extends Saving, Account> double transferFundsSaving (T type, double funds, int accountID) throws LowFunds, NoAccount {
    // 	if (this.balance < funds) {
    // 	    throw new LowFunds();
    // 	} else if (type.accountID != accountID) {
    // 	    throw new NoAccount();
    // 	} else {
    // 	    this.balance -= funds;
    // 	    type.balance += funds;
    // 	    return this.balance;
    // 	}
    // }

    public void closeAccount (Account account) throws NoAccount {
	try {
	    account.closeAccount(this);
	    this.balance = 0.0;
	    this.accountID = 0;
	} catch (NoAccount err) {
	    throw new NoAccount();
	}
    }

    public void clearAccount () {
	this.balance = 0.0;
	this.accountID = 0;
    }
    
    public Account getAccount () {
	return super.getAccount();
    }
}
