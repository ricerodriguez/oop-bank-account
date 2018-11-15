package accountErrors;
import java.io.*;
import java.lang.*;

public class LengthException extends Exception {
    private String type;
    public LengthException (String type) {
        this.type = type;
    }

    public String what () {
        if (this.type.equals("PIN")) {
            return "LengthException: Your PIN is not the correct length. It must be 4 characters long.";
        } else if (this.type.equals("SSN")) {
            return "LengthException: Your SSN is not the correct length. It must be 9 characters long.";
        } else {
            return "BadDev: The programmer is an idiot and incorrectly used this exception.";
        }
    }
}
