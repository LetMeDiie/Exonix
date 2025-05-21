package kz.amihady.exonix.organization.persistense.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "organizations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationEntity {

    @Id
    @Column(name = "organization_id", nullable = false)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "phone", nullable = false)
    String phone;
}
