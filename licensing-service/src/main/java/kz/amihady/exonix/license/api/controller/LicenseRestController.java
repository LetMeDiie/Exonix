package kz.amihady.exonix.license.api.controller;

import kz.amihady.exonix.license.api.assembler.LicenseModelAssembler;
import kz.amihady.exonix.license.api.dto.request.LicenseCreateRequest;
import kz.amihady.exonix.license.api.dto.request.LicenseUpdateRequest;
import kz.amihady.exonix.license.api.dto.response.LicenseResponse;
import kz.amihady.exonix.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;


@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseRestController {
    @Autowired
    private  LicenseService licenseService;
    @Autowired
    private  LicenseModelAssembler licenseModelAssembler;

    @GetMapping("/{licenseId}")
    public ResponseEntity<EntityModel<LicenseResponse>> getLicense(
            @PathVariable("organizationId") UUID organizationId,
            @PathVariable("licenseId") UUID licenseId,
            Locale locale)
    {
        var licenseResponse =
                licenseService.getLicense(
                        licenseId,organizationId,locale);
        return ResponseEntity.ok(
                licenseModelAssembler.toModel(licenseResponse));
    }

    @GetMapping
    public ResponseEntity<List<LicenseResponse>> getLicenses(
            @PathVariable("organizationId") UUID organizationId,
            Locale locale
    ){
        return ResponseEntity
                .ok(licenseService
                        .getLicensesByOrganization(organizationId,locale));
    }

    @PutMapping("/{licenseId}")
    public ResponseEntity<LicenseResponse> updateLicense(
            @PathVariable("organizationId") UUID organizationId,
            @PathVariable("licenseId") UUID licenseId,
            @RequestBody LicenseUpdateRequest request,
            Locale locale)
    {
        var licenseResponse =
                licenseService.updateLicense(licenseId,organizationId,request,locale);

        return ResponseEntity.ok(licenseResponse);
    }

    @PostMapping
    public ResponseEntity<LicenseResponse> createLicense(
            @PathVariable("organizationId") UUID organizationId,
            @RequestBody LicenseCreateRequest request,
            Locale locale)
    {
        var licenseResponse =
                licenseService.createLicense(
                        organizationId,
                        request,
                        locale
                );

        return ResponseEntity.ok(licenseResponse);
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") UUID organizationId,
            @PathVariable("licenseId") UUID licenseId,
            Locale locale)
    {
        licenseService.deleteLicense(
                organizationId,
                licenseId,
                locale
        );

        return ResponseEntity.noContent().build();
    }
}