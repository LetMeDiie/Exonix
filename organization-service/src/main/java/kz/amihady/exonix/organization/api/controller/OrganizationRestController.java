package kz.amihady.exonix.organization.api.controller;

import jakarta.validation.Valid;
import kz.amihady.exonix.context.UserContext;
import kz.amihady.exonix.organization.api.assembler.OrganizationModelAssembler;
import kz.amihady.exonix.organization.api.dto.request.OrganizationCreateRequest;
import kz.amihady.exonix.organization.api.dto.request.OrganizationUpdateRequest;
import kz.amihady.exonix.organization.api.dto.response.OrganizationResponse;
import kz.amihady.exonix.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping(value="v1/organization")
@Slf4j
public class OrganizationRestController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationModelAssembler organizationModelAssembler;

    @Autowired
    private UserContext userContext;

    @GetMapping("/{organizationId}")
    public ResponseEntity<EntityModel<OrganizationResponse>> getOrganization(
            @PathVariable("organizationId") UUID organizationId,
            Locale locale){
        log.info("Get request with correlation id :" + userContext.getCorrelationId());
        var organizationResponse =
                organizationService.findById(organizationId,locale);
        return ResponseEntity.ok(organizationModelAssembler.toModel(organizationResponse));

    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>>
                            getAllOrganizations(){
        return ResponseEntity
                .ok(organizationService.findAll());
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> updateOrganization(
            @PathVariable("organizationId") UUID organizationId,
            @Valid @RequestBody OrganizationUpdateRequest request,
            Locale locale)
    {
        return  ResponseEntity.ok(organizationService.update(organizationId,request,locale));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(
            @PathVariable("organizationId") UUID organizationId,
            Locale locale)
    {
        organizationService.delete(organizationId,locale);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<OrganizationResponse> createOrganization(
            @Valid @RequestBody OrganizationCreateRequest request,
            Locale locale)
    {
        return ResponseEntity.ok(organizationService.create(request,locale));
    }

}
