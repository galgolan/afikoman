package com.biocatch.afikoman.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.biocatch.afikoman.domain.Kid;
import com.biocatch.afikoman.repository.KidRepository;
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
 * REST controller for managing Kid.
 */
@RestController
@RequestMapping("/api")
public class KidResource {

    private final Logger log = LoggerFactory.getLogger(KidResource.class);

    @Inject
    private KidRepository kidRepository;

    /**
     * POST  /kids -> Create a new kid.
     */
    @RequestMapping(value = "/kids",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Kid kid) {
        log.debug("REST request to save Kid : {}", kid);
        kidRepository.save(kid);
    }

    /**
     * GET  /kids -> get all the kids.
     */
    @RequestMapping(value = "/kids",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Kid> getAll() {
        log.debug("REST request to get all Kids");
        return kidRepository.findAll();
    }

    /**
     * GET  /kids/:id -> get the "id" kid.
     */
    @RequestMapping(value = "/kids/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kid> get(@PathVariable String id) {
        log.debug("REST request to get Kid : {}", id);
        return Optional.ofNullable(kidRepository.findOne(id))
            .map(kid -> new ResponseEntity<>(
                kid,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kids/:id -> delete the "id" kid.
     */
    @RequestMapping(value = "/kids/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Kid : {}", id);
        kidRepository.delete(id);
    }
}
