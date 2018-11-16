package accountErrors;
import java.io.*;

public class OverwriteException extends Exception {
    private String type;
    public OverwriteException (String type) {
        this.type = type;
    }

    public String what () {
        if (this.type.equals("PIN")) {
            return "OverwriteException: You already have an existing PIN.";
        } else if (this.type.equals("SSN")) {
            return "OverwriteException: You already have an existing SSN.";
        } else {
            return "BadDev: The programmer is an idiot and incorrectly used this exception.";
        }
    }
}
