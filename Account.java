import java.io.*;
public class Account {
    private int account_id;
    private int PIN;
    private int SSN;
    private double balanceChecking;
    private double balanceSaving;

    public Account (int PIN, int SSN) throws LengthException{
        if (PIN.length() == 4) {
            this.PIN = PIN;
        } else {
            throw new LengthException();
        }

        if (SSN.length() == 9){
            this.SSN = SSN;
        } else {
            throw new LengthException();
        }
    }

    public Account (int PIN, int SSN, String type, double balance) {
        if (type.equals("checking")) {
            this.balanceChecking = balance;
        } else if (type.equals("saving")) {
            this.balanceSaving = balance;
        }
        if (PIN.length() == 4) {
            this.PIN = PIN;
        } else {
            throw new LengthException();
        }

        if (SSN.length() == 9){
            this.SSN = SSN;
        } else {
            throw new LengthException();
        }

    }

}
