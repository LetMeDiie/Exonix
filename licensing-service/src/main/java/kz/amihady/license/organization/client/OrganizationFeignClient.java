package kz.amihady.license.organization.client;

import kz.amihady.license.organization.response.OrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;


@FeignClient(name = "organization-service")
public interface OrganizationFeignClient {

    @GetMapping("/v1/organization/{organizationId}")
    OrganizationResponse getOrganizationById(
            @PathVariable("organizationId") UUID organizationId,
            @RequestHeader("Accept-Language") String locale);
}

