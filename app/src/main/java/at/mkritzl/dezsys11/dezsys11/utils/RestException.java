package at.mkritzl.dezsys11.dezsys11.utils;

/**
 * Exception for REST-failures
 */
public class RestException extends Exception {

    public RestException(String message) {
        super(message);
    }
}
