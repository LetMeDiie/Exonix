package kz.amihady.license.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationServiceException extends RuntimeException{
    private HttpStatus status;

    public OrganizationServiceException(String message , HttpStatus status){
        super(message);
        this.status=status;
    }

}
