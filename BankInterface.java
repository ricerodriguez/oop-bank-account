import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;

public class BankInterface {
    // Smallest bank ever has room for 20 accounts
    Account [] accounts = new Account[20];
    Account [] superAccounts = new Account[20];
    Account [] tempAccounts = new Account[10];
    int [] numAccounts = new int [20];
    Checking check = new Checking();
    Saving save = new Saving();
    int i = 0; // Total number of checking/saving accounts
    public BankInterface () {
	// for (int i=0; i<20; i++) {
	    // accounts[i] = new Account();}
    }

    public static Account [] addToArr (Account [] arr, int pos, Account acc) {
	Account [] result = new Account [20];
	// Stores first half of array into new array
	for(int i = 0; i < pos; i++)
	    result[i] = arr[i];
	// Stick new number in position at midpoint of array
	result[pos] = acc;
	// Stores last half of array into new array
	for(int i = pos + 1; i < 20; i++)
	    result[i] = arr[i - 1];
	return result;
    }

    public void employeeMenu () {
        Scanner scan = new Scanner(System.in);
        int emp_in, accountID, accountIndex=0;
        int usr_pin,usr_ssn;
        String usr_type;
	int ind;
	int tempID;
	String [] accTypes = new String [20];
        boolean menu_up = true;
	// int k = 0;
	String prettyType;
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
		for (int j=0; j<i; j++) {
		    // Grab the account in the array at this index
		    Account temp = this.accounts[j];
		    // Grab the Account object containing this account
		    Account supertemp = this.superAccounts[j];
		    this.tempAccounts = supertemp.getAccounts();
		    // Format the type of the string so it's prettier for printing
		    prettyType = accTypes[j];
		    prettyType = prettyType.substring(0,1).toUpperCase()+prettyType.substring(1).toLowerCase();
		    // Grab the account ID 
		    tempID = temp.getAccountID();
		    if (tempAccounts.length > 1) {
			for (int k=0; k<tempAccounts.length; k++) {
			    int tempID2 = this.tempAccounts[k];
			    System.out.printf("    %s  ID: %d\n",prettyType,tempID);
			
			    // FINISH THIS !!!!!!!
			    // I'm trying to make it say
			    // Account x:
			    //     Checking ID: xxxx
			    //     Saving ID: xxxx
			    //     Saving ID: xxxx
			    // Account y:
			    // ....etc
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
                        // checking[i] = new Account().new Checking (usr_pin,usr_ssn);
			this.superAccounts[i] = new Account(usr_pin,usr_ssn);
			this.accounts[i] = new Checking(this.superAccounts[i]);
			accTypes[i] = "checking";
                        i++;
                    } else if (usr_type.equals("saving")) {
                        // saving[i] = new Account().new Saving (usr_pin,usr_ssn);
			this.superAccounts[i] = new Account(usr_pin,usr_ssn);
			this.accounts[i] = new Saving(this.superAccounts[i]);
			accTypes[i] = "saving";
                        i++;
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
                try {
		    ind = this.findIndex(accountID);
                    Account temp = accounts[ind];
                    temp.closeAccount();
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
		System.out.printf("Opening a new account under Account %d...\n"+
				  "What kind of account is this?\n"+
				  "Please specify 'checking' or saving'.\n",accountIndex);
		scan.nextLine();
		usr_type = scan.nextLine();
                try {
                    if (usr_type.equals("checking")) {
			Account temp = this.superAccounts[accountIndex];
			this.superAccounts = BankInterface.addToArr (this.superAccounts, accountIndex, temp);
			Account newAcc = new Checking(temp);
		        this.accounts = BankInterface.addToArr (this.accounts, accountIndex, newAcc);
			accTypes[accountIndex] = "checking";
                    } else if (usr_type.equals("saving")) {
			Account temp = this.superAccounts[accountIndex];
			Account newAcc = new Saving (temp);
		        this.accounts = BankInterface.addToArr (this.accounts, accountIndex, newAcc);
                    } else {
                        System.out.printf("Invalid selection. Back to menu.\n");
                    }
                } catch (InvalidPIN err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
                } catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.\n");
		}
            }
        }
    }

    
    public int findIndex (int accountID) {
        int j = 0;
        while (j < i) {
	    Account temp = this.accounts[j];
	    if (temp.getAccountID() == accountID) {
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
        }
    }
}
