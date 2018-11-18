import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;

public class BankInterface {
    // Smallest bank ever has room for 20 accounts
    Account [] accounts = new Account[20];
    Checking check = new Checking();
    Saving save = new Saving();
    int i = 0;
    public BankInterface () {
	// for (int i=0; i<20; i++) {
	    // accounts[i] = new Account();}
    }

    public void employeeMenu () {
        Scanner scan = new Scanner(System.in);
        int emp_in, accountID;
        int usr_pin,usr_ssn;
        String usr_type;
	int ind;
	int tempID;
	String [] accTypes = new String [20];
        boolean menu_up = true;
        while (menu_up) {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
                              "Please choose from the following:\n"+
                              "1. Open new account.\n"+
                              "2. Close account\n"+
                              "3. Exit employee menu.\n"+
                              "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
			      "Current Accounts: %d\n",i);
	    if (i>0) {
		for (int j=0; j<i; j++) {
		    Account temp = this.accounts[j];
		    tempID = temp.getID();
		    System.out.printf("Account %d:\n"+
				      "       ID: %d\n",j,tempID);
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
			this.accounts[i] = new Checking (usr_pin,usr_ssn);
			accTypes[i] = "checking";
                        i++;
                    } else if (usr_type.equals("saving")) {
                        // saving[i] = new Account().new Saving (usr_pin,usr_ssn);
			this.accounts[i] = new Saving (usr_pin,usr_ssn);
			accTypes[i] = "saving";
                        i++;
                    } else {
                        System.out.printf("Invalid selection. Back to menu.");
                    }
                } catch (LengthException err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.");
                } catch (InvalidType err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.");
		}
		
                break;
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
                                      "\nTry again.");
                }
            }
        }
    }

    
    public int findIndex (int accountID) {
        int j = 0;
        while (j < i) {
	    Account temp = this.accounts[j];
	    if (temp.getID() == accountID) {
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
        for (int j = 3; j > 0; j++) {
            if (user_in != staffPassword) {
                System.out.println("Wrong password. You have " + j + " attempts left.");
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
