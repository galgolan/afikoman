package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Company entity.
 */
public interface CompanyRepository extends MongoRepository<Company,String>{

}
