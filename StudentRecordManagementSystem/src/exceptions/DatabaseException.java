package exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class DatabaseException extends SQLIntegrityConstraintViolationException{
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
