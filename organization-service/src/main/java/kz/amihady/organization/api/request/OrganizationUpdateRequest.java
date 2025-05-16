package kz.amihady.organization.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationUpdateRequest(
        @Size(max = 100, message = "{name.tooLong}")
        String name,
        @Email(message = "{email.invalid}")
        String email,

        @Pattern(regexp = "^(\\+7|8)7[0-9]{2}[0-9]{7}$", message = "{phone.invalid}")
        String phone
) {
}
