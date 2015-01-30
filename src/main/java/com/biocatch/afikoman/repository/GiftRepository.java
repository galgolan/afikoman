package com.biocatch.afikoman.repository;

import com.biocatch.afikoman.domain.Gift;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Gift entity.
 */
public interface GiftRepository extends MongoRepository<Gift,String>{

}
