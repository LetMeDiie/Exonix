package kz.amihady.exonix.organization.persistense.adapter;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.amihady.exonix.organization.domain.Organization;
import kz.amihady.exonix.organization.persistense.mapper.OrganizationMapper;
import kz.amihady.exonix.organization.persistense.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrganizationPersistenceAdapter {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationRepository organizationRepository;


    @CircuitBreaker(name = "organizationDbCircuitBreaker")
    @Retry(name = " organizationDbRetry")
    public void save(Organization organization){
        var organizationEntity =
                organizationMapper
                        .toEntity(organization);

        var savedOrganizationEntity =
                organizationRepository.
                        save(organizationEntity);
    }

    @CircuitBreaker(name = "organizationDbCircuitBreaker")
    @Retry(name = " organizationDbRetry")
    public Optional<Organization> findById(UUID organizationId){
        var organizationEntity =
                organizationRepository
                        .findById(organizationId);
        return organizationEntity
                .map(entity->
                        organizationMapper.toDomain(entity));
    }

    @CircuitBreaker(name = "organizationDbCircuitBreaker")
    @Retry(name = " organizationDbRetry")
    public List<Organization> findAll(){
        var organizationEntities =
                organizationRepository
                        .findAll();

        return organizationEntities.stream()
                .map(organizationMapper::toDomain)
                .toList();
    }

    @CircuitBreaker(name = "organizationDbCircuitBreaker")
    @Retry(name = " organizationDbRetry")
    public void delete(Organization organization){
        var organizationEntity =
                organizationMapper
                        .toEntity(organization);
        organizationRepository.delete(organizationEntity);
    }
}
