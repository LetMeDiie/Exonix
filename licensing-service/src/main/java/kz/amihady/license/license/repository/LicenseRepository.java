package kz.amihady.license.license.repository;

import kz.amihady.license.license.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseRepository extends JpaRepository<License,UUID> {
     List<License> findByOrganizationId(UUID organizationId);
     Optional<License> findByOrganizationIdAndLicenseId(UUID organizationId, UUID licenseId);
}
