package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.Adoption;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Adoption entity.
 */
public interface AdoptionRepository extends MongoRepository<Adoption,String>{

}
