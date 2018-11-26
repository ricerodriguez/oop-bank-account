package accountDefs;

import accountErrors.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Saving extends Account {
    private int accountID;
    private double balance;
    private Timer timer;
    private TimerTask task;
    private static long delay = 0;
    private static long period = 31540000000L;
    // private static long period = 1000;

    public Saving () {
	// Empty constructor
    }

    // Constructor for opening savings account in existing bank account
    public Saving (Account account) throws InvalidPIN, NoAccount {
	account.accountIDs[account.num] = (int)(Math.random() * 89998)+10000;
	account.accounts[account.num] = this;
	account.num++;
	this.accountID = account.getAccountID();
	this.timer = new Timer();
	this.task = new InterestCalculator(this);
	timer.scheduleAtFixedRate(task,delay,period);
	// timer.schedule(new InterestCalulator(this),period);
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

    public double transferFunds (Account recipient, double funds) throws LowFunds {
	if (this.balance < funds) {
	    throw new LowFunds();
	} else {
	    this.balance -= funds;
	    recipient.depositFunds(funds);
	}
	return this.balance;
    }

    public double getBalance () {
	return this.balance;
    }

    public int getAccountID () {
	return this.accountID;
    }

    public String getType () {
	return "saving";
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

    public class InterestCalculator extends TimerTask {
	private double balance;
	private double balanceWithInterest;
	private Saving account;

	public InterestCalculator (Saving account) {
	    this.account = account;
	    this.balance = account.getBalance();
	    this.run();
	}

	public void run() {
	    this.balance = account.getBalance();
	    this.balanceWithInterest = this.balance*0.05;
	    this.balanceWithInterest += this.balance;
	    account.balance = balanceWithInterest;
	    // System.out.println("Added interest.");
	}


    }

}

