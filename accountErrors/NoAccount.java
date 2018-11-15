package accountErrors;
import java.io.*;

public class NoAccount extends Exception {
    public String what () {
        return "NoAccount: You do not have an account of that ID.";
    }
}
