package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Organization entity.
 */
public interface OrganizationRepository extends MongoRepository<Organization,String>{

}
