import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;

public class BankInterface {
    // Smallest bank ever has room for 100 accounts
    // Array of 100 total checking/savings accounts
    Account [] accounts = new Account[100];
    // Array of 10 total accounts
    Account [] superAccounts = new Account[10];
    // This is temporary because it's used as a "container" to hold
    // the accounts under each element in the superAccounts array
    // as the superAccounts array is traversed.
    Account [] subAccountsEach = new Account[10];
    int [] numAccounts = new int [20];
    Checking check = new Checking();
    Saving save = new Saving();
    int i = 0; // Total number of checking/saving accounts
    int numSuper = 0; // Total number of accounts which contain checking/savings
    public BankInterface () {
	// for (int i=0; i<20; i++) {
	    // accounts[i] = new Account();}
    }

    public void employeeMenu () {
        Scanner scan = new Scanner(System.in);
        int emp_in, accountID, accountIndex=0;
        int usr_pin,usr_ssn;
        String usr_type;
	int ind=0;
	int tempID;
	String [] accTypes = new String [100];
        boolean menu_up = true;
	// int k = 0;
	String prettyType;
	List<Account> listSuperAccounts = new ArrayList<Account>();
	List<Account> listAccounts = new ArrayList<Account>();
        while (menu_up) {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
                              "Please choose from the following:\n"+
                              "1. Open new account.\n"+
                              "2. Close account\n"+
			      "3. Open new checking account under\n"+
			      "   existing account.\n"+
			      "4. Open new savings account under\n"+
			      "   existing account.\n"+
                              "5. Exit employee menu.\n"+
                              "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
			      "Current Accounts: %d\n",i);
	    if (i>0) {
		for (int j=0; j<numSuper; j++) {
		    // Grab the account in the array at this index
		    Account supertemp = this.superAccounts[j];
		    // Grab the checking/savings accounts under
		    // this account at this index
		    this.subAccountsEach = supertemp.getAccounts();
		    // Print which account this is
		    System.out.printf("Account %d:\n",j);
		    for (int k=0; k<subAccountsEach.length; k++) {
			if (subAccountsEach[k]!=null) {
			    prettyType = this.subAccountsEach[k].getType();
			    // Format the type of the string so it's prettier for printing
			    prettyType = prettyType.substring(0,1).toUpperCase()+prettyType.substring(1).toLowerCase();
			    int tempID2 = this.subAccountsEach[k].getAccountID();
			    System.out.printf("    %s  ID: %d\n",prettyType,tempID2);
			} else {
			    break;
			}
		    }
		}

		System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

	    } else {
		System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	    }
				      
            emp_in = scan.nextInt();
            switch (emp_in) {
            case 1:
                System.out.printf("Opening a new account...\n"+
                                  "Enter a 4-digit PIN:\n");
                usr_pin = scan.nextInt();
                System.out.printf("Enter a 9-digit SSN:\n");
                usr_ssn = scan.nextInt();
                System.out.printf("Checking or saving?\n");
		// Throw out the next line since nextInt() doesn't consume it
		scan.nextLine();
                usr_type = scan.nextLine();
                try {
                    if (usr_type.equals("checking")) {
			this.superAccounts[i] = new Account(usr_pin,usr_ssn);
			this.accounts[i] = new Checking(this.superAccounts[i]);
			accTypes[i] = "checking";
                        i++;
			numSuper++;
                    } else if (usr_type.equals("saving")) {
			this.superAccounts[i] = new Account(usr_pin,usr_ssn);
			this.accounts[i] = new Saving(this.superAccounts[i]);
			accTypes[i] = "saving";
                        i++;
			numSuper++;
                    } else {
                        System.out.printf("Invalid selection. Back to menu.\n");
                    }
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
                System.out.printf("Closing account...\n"+
                                  "Enter an account ID you wish to close.\n");
                accountID = scan.nextInt();
		int k=0;
                try {
		    for (int j = 0; j<numSuper; j++) {
		    	supertemp = this.superAccounts[j];
		    	this.subAccountsEach = supertemp.getAccounts();
		    	while (k<subAccountsEach.length) {
		    	    if (subAccountsEach[k]!=null) {
		    		ind = this.findIndex(accountID);
		    		if (subAccountsEach[k] == accounts[ind]) {
		    		    break;
		    		} else {
		    		    k++;
		    		    continue;
		    		}
		    	    }
		    	}
		    	if (subAccountsEach[k] == accounts[ind]) {
		    	    break;
		    	} else {
		    	    continue;
		    	}
		    }
		    Account supertemp = getSuper(accountID);
		    supertemp.closeAccount(accounts[ind]);
		    System.out.println(accounts[ind]);
                    accounts[ind].clearAccount();
                    i--;
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
		System.out.printf("Opening a new checking account under Account %d...\n",accountIndex);
                try {
		    Account temp = this.superAccounts[accountIndex];
		    Account newAcc = new Checking(temp);
		    listAccounts.add(accountIndex,newAcc);
		    this.accounts = listAccounts.toArray(new Account[100]);
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
		System.out.printf("Opening a new savings account under Account %d...\n",accountIndex);
		try {
		    Account temp = this.superAccounts[accountIndex];
		    Account newAcc = new Saving (temp);
		    listAccounts.add(accountIndex,newAcc);
		    this.accounts = listAccounts.toArray(new Account[100]);
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
		System.out.printf("Thank you valuable employee!\n"+
				  "Exiting to customer menu...\n");
		menu_up = false;
            }
        }
    }

    public void customerMenu() {
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
	     ind = this.findIndex(accountID);
	     if (ind == -1) {
		 System.out.printf("Hmm, we don't have an account\n"+
				   "with that ID. Try again.\n");
		 continue;
	     } else {
		 temp = this.accounts[ind];
		 // Get the superaccount of the account ID
		 for (int j = 0; j<numSuper; j++) {
		     supertemp = this.superAccounts[j];
		     this.subAccountsEach = supertemp.getAccounts();
		     while (k<subAccountsEach.length) {
			 if (subAccountsEach[k]!=null) {
			     ind = this.findIndex(accountID);
			     if (subAccountsEach[k] == accounts[ind]) {
				 break;
			     } else {
				 k++;
				 continue;
			     }
			 }
		     }
		     if (subAccountsEach[k] == accounts[ind]) {
			 break;
		     } else {
			 continue;
		     }
		 }
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

		 // System.out.printf("");
		     
	     }
	}
    }

    public Account getSuper(int accountID) {
	int k = 0;
	int index = 0;
	Account supertemp;
	for (int i = 0; i<numSuper; i++) {
	    supertemp = this.superAccounts[i];
	    this.subAccountsEach = supertemp.getAccounts();
	    while (k<subAccountsEach.length) {
		if (subAccountsEach[k]!=null) {
		    index = this.findIndex(accountID);
		    if (subAccountsEach[k] == accounts[index]) {
			return supertemp;
		    } else {
			k++;
			continue;
		    }
		}
	    }
	}
	return null;
    }

    
    public int findIndex (int accountID) {
        int j = 0;
        while (j < 100) {
	    if (this.accounts[j].getAccountID() == accountID) {
		return j;
	    } else {
		j++;
	    }
        }
        return -1;
    }
    
    public int findIndex (Account account) {
        int j = 0;
        while (j < 100) {
	    if (this.accounts[j] == account) {
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
        user_in = scan.nextInt();
        for (int j = 3; j > 0; j--) {
            if (user_in != staffPassword) {
                System.out.println("Wrong password. You have " + j + " attempts left.");
		user_in = scan.nextInt();
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
            banksy.employeeMenu();
	    banksy.customerMenu();
        }
    }
}
