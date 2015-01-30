package com.biocatch.afikoman.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.biocatch.afikoman.domain.Adoption;
import com.biocatch.afikoman.repository.AdoptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Adoption.
 */
@RestController
@RequestMapping("/api")
public class AdoptionResource {

    private final Logger log = LoggerFactory.getLogger(AdoptionResource.class);

    @Inject
    private AdoptionRepository adoptionRepository;

    /**
     * POST  /adoptions -> Create a new adoption.
     */
    @RequestMapping(value = "/adoptions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Adoption adoption) {
        log.debug("REST request to save Adoption : {}", adoption);
        adoptionRepository.save(adoption);
    }

    /**
     * GET  /adoptions -> get all the adoptions.
     */
    @RequestMapping(value = "/adoptions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Adoption> getAll() {
        log.debug("REST request to get all Adoptions");
        return adoptionRepository.findAll();
    }

    /**
     * GET  /adoptions/:id -> get the "id" adoption.
     */
    @RequestMapping(value = "/adoptions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> get(@PathVariable String id) {
        log.debug("REST request to get Adoption : {}", id);
        return Optional.ofNullable(adoptionRepository.findOne(id))
            .map(adoption -> new ResponseEntity<>(
                adoption,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adoptions/:id -> delete the "id" adoption.
     */
    @RequestMapping(value = "/adoptions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Adoption : {}", id);
        adoptionRepository.delete(id);
    }
}
