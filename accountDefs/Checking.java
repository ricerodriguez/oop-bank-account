package accountDefs;

import accountErrors.*;
import java.lang.*;
import java.io.*;
public class Checking extends Account {
    private int accountID;
    private double balance;

    // Constructor for opening checking account in existing bank account.
    public Checking (Account account) throws InvalidPIN, NoAccount {
        account.accountIDs[account.num] = (int)(Math.random() * 99998)+1;
	System.out.println(account.num);
        account.accounts[account.num] = this;
        account.num++;
        this.accountID = account.getAccountID();
    }

    public Checking () {
    // Empty constructor
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

    public double getBalance () {
	return this.balance;
    }

    public double transferFunds (Account recipient, double funds) throws LowFunds {
	if (this.balance < funds) {
	    throw new LowFunds();
	} else {
	    this.balance -= funds;
	    recipient.depositFunds(funds);

	}
	return this.balance;
    }

    public void closeAccount (Account account) throws NoAccount {
	try {
	    account.closeAccount(this);
	    this.balance = 0.0;
	    this.accountID = 0;
	} catch (NoAccount err) {
	    throw new NoAccount();
	}
    }

    public String getType() {
	return "checking";
    }

    public void clearAccount () {
	this.balance = 0.0;
	this.accountID = 0;
    }
    
}
