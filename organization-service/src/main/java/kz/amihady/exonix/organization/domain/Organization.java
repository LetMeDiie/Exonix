package kz.amihady.exonix.organization.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organization {
    UUID id;
    String name;
    String email;
    String phone;
}
