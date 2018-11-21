import java.io.*;
import java.lang.*;
import java.util.*;
import accountErrors.*;
public class CustomerInterface {

    public void customerInterface(){

    }

    public static void main (String[] args){
    String type;
    int PIN, accountID, choice, input;
        System.out.println("1 for Checking account and 2 for Savings: ");
        Scanner scan = new Scanner(System.in);
        input = scan.nextInt();


        switch(input){
            case 1:
                type = "checking";
                System.out.println("Enter your account number: ");
                accountID = scan.nextInt();
                System.out.println("Enter your password: ");


                Checking chk = new Checking;

                if(chk.validatePIN(PIN, accountID, type)){
                    System.out.println("1. to withdraw");
                    System.out.println("2. to view balance");
                    System.out.println("3. to transfer funds");
                    System.out.println("4. to view last deposit amount");
                    System.out.println("5. to cancel transaction");



                    choice = scan.nextInt();

                    switch (choice){
                        case 1:
                            System.out.println("Enter amount to withdraw");
                            double funds = scan.nextDouble();
                            int bal = chk.balanceChecking[chk.findIndex(accountID, type)];
                            if (bal >= funds ){
                                bal -= funds;

                            }else{
                                System.out.println("error");
                            }

                            chk.balanceChecking[chk.findIndex(accountID, type)] = bal;

                            break;

                        case 2:
                            System.out.println("Your balance is " + chk.balanceChecking[chk.findIndex(accountID, type)]);

                        case 3:
                            System.out.println("Enter amount to transfer");
                            funds = scan.nextDouble();

                            if(acc
                            )

                    }
                }

        }




    }



}
