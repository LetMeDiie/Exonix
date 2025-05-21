package kz.amihady.exonix.organization.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationCreateRequest(
        @NotBlank(message = "{name.required}")
        @Size(max = 100, message = "{name.tooLong}")
        String name,

        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email,

        @NotBlank(message = "{phone.required}")
        @Pattern(regexp = "^(\\+7|8)7[0-9]{2}[0-9]{7}$", message = "{phone.invalid}")
        String phone

) {}
