package kz.amihady.exonix.organization.dto;

import java.util.UUID;

public record Organization(
        UUID id,
        String name,
        String email,
        String phone
) {
}
