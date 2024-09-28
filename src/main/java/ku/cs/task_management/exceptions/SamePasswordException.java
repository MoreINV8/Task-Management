package ku.cs.task_management.exceptions;

public class SamePasswordException extends Exception{
    public SamePasswordException() {
        super("current password and new password received were the same");
    }
}
