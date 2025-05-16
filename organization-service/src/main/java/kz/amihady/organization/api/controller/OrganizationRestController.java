package kz.amihady.organization.api.controller;

import jakarta.validation.Valid;
import kz.amihady.organization.api.assembler.OrganizationModelAssembler;
import kz.amihady.organization.api.request.OrganizationCreateRequest;
import kz.amihady.organization.api.request.OrganizationUpdateRequest;
import kz.amihady.organization.api.response.OrganizationResponse;
import kz.amihady.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping(value="v1/organization")
public class OrganizationRestController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationModelAssembler organizationModelAssembler;

    @GetMapping("/{organizationId}")
    public ResponseEntity<EntityModel<OrganizationResponse>> getOrganization(
            @PathVariable("organizationId") UUID organizationId,
            Locale locale){
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
