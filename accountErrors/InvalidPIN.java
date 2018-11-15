package accountErrors;
import java.io.*;

public class InvalidPIN extends Exception {
    public String what () {
        return "InvalidPIN: Input does not match registered PIN.";
    }
}
