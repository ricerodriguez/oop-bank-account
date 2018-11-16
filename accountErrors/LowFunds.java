package accountErrors;
import java.io.*;

public class LowFunds extends Exception {
    public String what () {
        return "LowFunds: You do insufficient funds to perform this transaction.";
    }
}
