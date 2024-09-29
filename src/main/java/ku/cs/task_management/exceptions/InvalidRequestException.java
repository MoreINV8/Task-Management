package ku.cs.task_management.exceptions;

public class InvalidRequestException extends Exception{
    public InvalidRequestException() {
        super("request has not completed data");
    }
}
