package kz.amihady.exonix.organization.handler.exception;

public class OrganizationNotFoundException extends RuntimeException{
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
