package kz.amihady.exonix.organization.client;

import kz.amihady.exonix.organization.dto.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;


@FeignClient(name = "organization-service")
public interface OrganizationFeignClient {

    @GetMapping("/v1/organization/{organizationId}")
    Organization getOrganizationById(
            @PathVariable("organizationId") UUID organizationId,
            @RequestHeader("Accept-Language") String locale);
}

