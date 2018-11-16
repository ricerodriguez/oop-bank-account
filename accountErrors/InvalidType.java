package accountErrors;
import java.lang.*;
import java.io.*;

public class InvalidType extends Exception {
    private String type;
    public InvalidType (String type) {
        this.type = type;
    }

    public String what() {
        return "InvalidType: You entered an invalid account type. Please enter 'checking' or 'saving'.";
    }
}
