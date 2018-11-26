import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;
import accountDefs.*;
public class BankInterface {
    // Smallest bank ever has room for 100 accounts
    // Array of 100 total checking/savings accounts
    Account [] accounts = new Account[100];
    // Array of 10 total accounts
    Account [] superAccounts = new Account[10];
    int i = 0; // Total number of checking/saving accounts
    int numSuper = 0; // Total number of accounts which contain checking/savings
    public BankInterface () {
	// Empty constructor
    }

    public void employeeMenu () {
        Scanner scan = new Scanner(System.in);
        int emp_in, accountID, accountIndex=0;
        int usr_pin,usr_ssn;
        String usr_type;
	int ind=0;
	int tempID;
	// // ~~~~~~ REMOVE THESE LINES AFTER DEBUGGING: ~~~~~~~
	// int [] menuOptions   = {1,1,3,4,5,6}; // Remove this line after debugging
	// int [] secondOptions = {0,0,0,1,0,0}; // Remove this line after debugging
	// int accountIDToKill = 0;              // Remove this line after debugging
	// // ~~~~~~ REMOVE THESE LINES AFTER DEBUGGING^ ~~~~~~~
	String [] accTypes = new String [100];
        boolean menu_up = true;
	String prettyType;
        while (menu_up) {
	// for (int k=0;k<menuOptions.length;k++) { // Remove this line after debugging
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
                              "Please choose from the following:\n"+
                              "1. Open new checking account.\n"+
			      "2. Open new savings account.\n"+
			      "3. Open new checking account under\n"+
			      "   existing account.\n"+
			      "4. Open new savings account under\n"+
			      "   existing account.\n"+
                              "5. Close account.\n"+
                              "6. Exit employee menu.\n"+
                              "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
			      "Current Accounts: %d\n",i);
	    if (i>0) {
		// for (int j=0; j<numSuper; j++) {
		int j=0;
		while (this.superAccounts[j]!=null) {
		    // Grab the account in the array at this index
		    Account supertemp = this.superAccounts[j];
		    // Grab the checking/savings accounts under
		    // this account at this index
		    Account [] subAccountsEach = supertemp.getAccounts();
		    // Print which account this is
		    System.out.printf("Account %d:\n",j);
		    for (int x=0; x<subAccountsEach.length; x++) {
			if (subAccountsEach[x]!=null) {
			    prettyType = subAccountsEach[x].getType();
			    // Format the type of the string so it's prettier for printing
			    prettyType = prettyType.substring(0,1).toUpperCase()+prettyType.substring(1).toLowerCase();
			    int tempID2 = subAccountsEach[x].getAccountID();
			    System.out.printf("    %s  ID: %d\n",prettyType,tempID2);
			} else {
			    break;
			}
		    }
		    j++;
		}

		System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

	    } else {
		System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	    }
				      
            emp_in = scan.nextInt();
	    // emp_in = menuOptions[k];
            switch (emp_in) {
            case 1:
                System.out.printf("Opening a new checking account...\n"+
                                  "Enter a 4-digit PIN:\n");
                usr_pin = scan.nextInt();
		// usr_pin = 1234;
                System.out.printf("Enter a 9-digit SSN:\n");
                usr_ssn = scan.nextInt();
		// usr_ssn = 123456789;
                try {
		    this.superAccounts[i] = new Account(usr_pin,usr_ssn);
		    this.accounts[i] = new Checking(this.superAccounts[i]);
		    // accountIDToKill = this.accounts[i].getAccountID(); // Remove this line after debugging
		    accTypes[i] = "checking";
		    i++;
		    numSuper++;
                } catch (LengthException err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } catch (InvalidPIN err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} finally {
		    break;
		}

            case 2:
                System.out.printf("Opening a new savings account...\n"+
                                  "Enter a 4-digit PIN:\n");
                usr_pin = scan.nextInt();
		// usr_pin = 1234;
                System.out.printf("Enter a 9-digit SSN:\n");
                usr_ssn = scan.nextInt();
		// usr_ssn = 123456789;
		try {
		    this.superAccounts[i] = new Account(usr_pin,usr_ssn);
		    this.accounts[i] = new Saving(this.superAccounts[i]);
		    // accountIDToKill = this.accounts[i].getAccountID(); // Remove this line after debugging
		    accTypes[i] = "saving";
		    i++;
		    numSuper++;
                } catch (LengthException err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } catch (InvalidPIN err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} finally {
		    break;
		}
		
	    case 3:
		System.out.printf("Opening new checking account...\n"+
				  "Which account are you adding this to?\n");
		accountIndex = scan.nextInt();
		// accountIndex = secondOptions[k];
		System.out.printf("Opening a new checking account under Account %d...\n",accountIndex);
                try {
		    Account temp = this.superAccounts[accountIndex];
		    Account newAcc = new Checking(temp);
		    this.accounts[i] = newAcc;
		    accTypes[accountIndex] = "checking";
		    i++;
                } catch (InvalidPIN err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} finally {
		    break;
		}
	    
	    case 4:
		System.out.printf("Opening new savings account...\n"+
				  "Which account are you adding this to?\n");
		accountIndex = scan.nextInt();
		// accountIndex = secondOptions[k];
		System.out.printf("Opening a new savings account under Account %d...\n",accountIndex);
		try {
		    Account temp = this.superAccounts[accountIndex];
		    Account newAcc = new Saving (temp);
		    this.accounts[i] = newAcc;
		    accTypes[accountIndex] = "saving";
		    i++;
                } catch (InvalidPIN err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} finally {
		    break;
		}

            case 5:
                System.out.printf("Closing account...\n"+
                                  "Enter an account ID you wish to close.\n");
                accountID = scan.nextInt();
		// accountID = accountIDToKill;
		// int k=0;
                try {
		    Account supertemp = getSuper(accountID);
		    int accountIndexToKill = BankInterface.findIndex(accountID,this.accounts);
		    Account accountToKill = accounts[accountIndexToKill];
		    accountToKill.closeAccount(supertemp);
                    i--;
                } catch (NoAccount err) {
		    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } finally {
		    break;
		}

	    case 6:
		System.out.printf("Thank you valuable employee!\n"+
				  "Exiting to customer menu...\n");
		menu_up = false;
            }
        }
    }

    public Account customerInitialMenu() {
	Scanner scan = new Scanner(System.in);
	boolean menu_up = true, isValidPIN = false;
	int accountID,PIN=0,ind,k=0;
	Account temp,supertemp;
	supertemp = this.accounts[0];
	while (menu_up) {
	     System.out.printf("Welcome! If you have an account\n"+
			       "with us, please enter your account\n"+
			       "ID for more options.\n");
	     accountID = scan.nextInt();
	     ind = this.findIndex(accountID, this.accounts);
	     if (ind == -1) {
		 System.out.printf("Hmm, we don't have an account\n"+
				   "with that ID. Try again.\n");
		 continue;
	     } else {
		 temp = this.accounts[ind];
		 // Get the superaccount of the account ID
		 supertemp = getSuper(accountID);
		 for (int i=0; i<3; i++) {
		     if (!isValidPIN) {
			 System.out.printf("Please enter your PIN.\n");
			 PIN = scan.nextInt();
			 try {
			     isValidPIN = supertemp.validatePIN(PIN);
			 } catch (InvalidPIN err) {
			     isValidPIN = false;
			     System.err.printf("ERROR!!\n"+
					       err.what()+
					       "\nTry again.\n");
			     continue;
			 }
		     } else {
			 break;
		     }
		 }

		 if (isValidPIN) {
		     menu_up = false;
		     return temp; 
		 } else {
		     System.out.printf("You are out of attempts.\n"+
				       "Try again later.\n");
		     continue;
		 }
		     
	     }
	}
	return null;
    }

    public void customerMenu (Account account) {
	boolean menu_up = true;
	int menu_choice = 0;
	double last_deposit = 0.0;
	Scanner scan = new Scanner(System.in);
	System.out.println("Welcome to your account!");
	while (menu_up) {
	    System.out.printf("Please make a selection:\n"+
			      "1. View balance.\n"+
			      "2. Deposit funds.\n"+
			      "3. Withdraw funds.\n"+
			      "4. Transfer funds.\n"+
			      "5. View last deposit.\n"+
			      "6. Cancel transaction.\n");
	    menu_choice = scan.nextInt();
	    switch (menu_choice) {
	    case 1:
		double balance = account.getBalance();
		System.out.printf("Your account balance is\n"+
				  "$%,.2f%n",balance);
		break;
	    case 2:
		System.out.printf("How much would you like\n"+
				  "to deposit?\n");
		double deposit = scan.nextDouble();
		last_deposit = account.depositFunds(deposit);
		System.out.printf("Done. Returning to menu.\n");
		break;
	    case 3:
		System.out.printf("How much would you like\n"+
				  "to withdraw?\n");
		double withdrawal = scan.nextDouble();
		try {
		    account.withdrawFunds(withdrawal);
		    System.out.printf("Done. Returning to menu.\n");
		} catch (LowFunds err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		} finally {
		    break;
		}
		    
	    case 4:
		System.out.printf("Which account are you\n"+
				  "transferring funds to?\n"+
				  "Please enter a valid\n"+
				  "account ID registered\n"+
				  "with this bank.\n");
		int accountID = scan.nextInt();
		int accountIndex = BankInterface.findIndex(accountID,this.accounts);
		if (accountIndex >= 0) {
		    Account recipient = this.accounts[accountIndex];
		    System.out.printf("How much would you like to transfer?\n");
		    double transfer = scan.nextDouble();
		    try {
			account.transferFunds(recipient, transfer);
			System.out.printf("Done. Returning to menu.\n");
		    } catch (LowFunds err) {
			System.err.printf("ERROR!!\n"+
					  err.what()+
					  "\nTry again.\n");
		    } finally {
			break;
		    }
		} else {
		    System.out.printf("There is no account with that\n"+
				      "account ID registered at this\n"+
				      "bank. Please check that you have\n"+
				      "entered the correct account ID\n"+
				      "and try again.\n");
		    break;
		}
		
	    case 5:
		System.out.printf("Your last deposit was $%,.2f%n"+
				  "Returning to menu.\n",last_deposit);
		break;
		
	    case 6:
		System.out.printf("Exiting menu...\n");
		menu_up = false;
		return;
	    }
	}
    }

    public Account getSuper(int accountID) {
	int k = 0;
	int index = BankInterface.findIndex(accountID,this.accounts);
	Account supertemp;
	for (int i = 0; i<=numSuper-1; i++) {
	    supertemp = this.superAccounts[i];
	    Account [] subAccountsEach = supertemp.getAccounts();
	    while (k<subAccountsEach.length) {
		if (subAccountsEach[k]!=null) {
		    if (subAccountsEach[k] == accounts[index]) {
			return supertemp;
		    } else {
			k++;
			continue;
		    }
		} else {
		    k=0;
		    break;
		}
	    }
	    continue;
	}
	return null;
    }

    
    public static int findIndex (int accountID, Account [] arr) {
        int j = 0;
        // while (j < arr.length-1) {
        while (arr[j] != null) {
	    // if (arr[j] != null) {
		if (arr[j].getAccountID() == accountID) {
		    return j;
		} else {
		    j++;
		}
	    // }
        }
        return -1;
    }
    
    public static int findIndex (Account account, Account [] arr) {
        int j = 0;
        while (arr[j] != null) {
	    if (arr[j] == account) {
		return j;
	    } else {
		j++;
	    }
        }
        return -1;
    }
    
    public static void main(String[] args) {
	BankInterface banksy = new BankInterface();
        // Password for the Bank with the worst security ever
        int staffPassword = 4567;
        int user_in;
        int emp_in;
        Scanner scan = new Scanner(System.in);
        boolean is_running_employee = true;
        boolean is_running = true;
        boolean locked_out = true;
        System.out.printf("Hello! This ATM has not been set up yet.\n"+
                          "You will need a member of our wonderful\n"+
                          "bank staff to enter their password below.\n\n"+
                          "Please enter staff password:\n");
        // user_in = scan.nextInt();
	user_in = 4567;
        for (int j = 3; j > 0; j--) {
            if (user_in != staffPassword) {
                System.out.println("Wrong password. You have " + j + " attempts left.");
		// user_in = scan.nextInt();
		continue;
            } else {
                System.out.printf("Welcome, employee!\n");
                locked_out = false;
                break;
            }
        }

        if (locked_out) {
            System.out.println("Out of attempts. Bye bye.");
            return;
        } else {
	    Account account;
            banksy.employeeMenu();
	    while (is_running) {
		account = banksy.customerInitialMenu();
		banksy.customerMenu(account);
	    }
        }
    }
}
