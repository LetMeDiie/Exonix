package kz.amihady.exonix.license.persistence.repository;

import kz.amihady.exonix.license.persistence.entity.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseEntity,UUID> {
     List<LicenseEntity> findByOrganizationId(UUID organizationId);
     Optional<LicenseEntity> findByOrganizationIdAndLicenseId(UUID organizationId, UUID licenseId);
}
