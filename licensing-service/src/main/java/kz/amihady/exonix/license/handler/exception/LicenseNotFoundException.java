package kz.amihady.exonix.license.handler.exception;

public class LicenseNotFoundException extends RuntimeException{

    public LicenseNotFoundException(String message) {
        super(message);
    }
}
