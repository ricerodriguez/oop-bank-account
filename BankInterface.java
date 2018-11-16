import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;

public class BankInterface {
    public static void main(String[] args) {
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
        for (int i = 3; i > 0; i++) {
            if (user_in != staffPassword) {
                System.out.println("Wrong password. You have " + i + " attempts left.");
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
            BankInterface.employeeMenu();
        }
    }

    public static void employeeMenu () {
        Scanner scan = new Scanner(System.in);
        int emp_in, accountID;
        int usr_pin,usr_ssn;
        String usr_type;
        // This is not the correct way to do this, but I'm trying
        // to make an array of empty checking and savings accounts
        // so we can just refer to the array. Is that a good way to do this?
        Checking [] checking = new Checking [10];
        Saving [] saving = new Saving [10];
        int i = 0;
        boolean menu_up = true;
        while (menu_up) {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
                              "Please choose from the following:\n"+
                              "1. Open new account.\n"+
                              "2. Close account\n"+
                              "3. Exit employee menu.\n"+
                              "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            emp_in = scan.nextInt();
            switch (emp_in) {
            case 0:
                System.out.printf("Opening a new account...\n"+
                                  "Enter a 4-digit PIN:\n");
                usr_pin = scan.nextInt();
                System.out.printf("Enter a 9-digit SSN:\n");
                usr_ssn = scan.nextInt();
                System.out.printf("Checking or saving?\n");
                usr_type = scan.nextLine();
                try {
                    if (usr_type.equals("checking")) {
                        checking[i] = new Account().new Checking (usr_pin,usr_ssn);
                        i++;
                    } else if (usr_type.equals("saving")) {
                        saving[i] = new Account().new Saving (usr_pin,usr_ssn);
                        i++;
                    } else {
                        System.out.printf("Invalid selection. Back to menu.");
                    }
                } catch (LengthException|InvalidType err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.");
                }
                break;
            case 1:
                System.out.printf("Closing account...\n"+
                                  "Enter an account ID you wish to close.\n");
                accountID = scan.nextInt();
                try {
                    Account temp = accounts[i];
                    temp.closeAccount(accountID);
                    i--;
                } catch (NoAccount err) {
                    System.err.printf("ERROR!!\n"+
                                      err.what()+
                                      "\nTry again.");
                }
            }
        }
    }

}
