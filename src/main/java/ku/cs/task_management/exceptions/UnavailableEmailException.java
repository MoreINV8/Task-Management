package ku.cs.task_management.exceptions;

public class UnavailableEmailException extends Exception{
    public UnavailableEmailException(String email) {
        super("email '" + email + "' is not available");
    }
}
