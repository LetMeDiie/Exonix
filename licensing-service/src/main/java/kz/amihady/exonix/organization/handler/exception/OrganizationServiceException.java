package kz.amihady.exonix.organization.handler.exception;

import lombok.Getter;

@Getter
public class OrganizationServiceException extends RuntimeException{

    public OrganizationServiceException(String message){
        super(message);
    }

}
