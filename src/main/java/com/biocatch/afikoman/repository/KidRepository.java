package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.Kid;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Kid entity.
 */
public interface KidRepository extends MongoRepository<Kid,String>{

}
