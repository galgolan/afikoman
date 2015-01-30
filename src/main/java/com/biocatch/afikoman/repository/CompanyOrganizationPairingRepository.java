package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.CompanyOrganizationPairing;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CompanyOrganizationPairing entity.
 */
public interface CompanyOrganizationPairingRepository extends MongoRepository<CompanyOrganizationPairing,String>{

}
