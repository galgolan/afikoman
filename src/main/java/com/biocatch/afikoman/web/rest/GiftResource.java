package com.biocatch.afikoman.web.rest;

import com.biocatch.afikoman.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import com.biocatch.afikoman.domain.Gift;
import com.biocatch.afikoman.repository.GiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gift.
 */
@RestController
@RequestMapping("/api")
public class GiftResource {

    private final Logger log = LoggerFactory.getLogger(GiftResource.class);

    @Inject
    private GiftRepository giftRepository;

    /**
     * POST  /gifts -> Create a new gift.
     */
    @RequestMapping(value = "/gifts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Gift gift) {
        log.debug("REST request to save Gift : {}", gift);
        giftRepository.save(gift);
    }

    /**
     * GET  /gifts -> get all the gifts.
     */
    @RequestMapping(value = "/gifts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Gift> getAll() {
        log.debug("REST request to get all Gifts");
        return giftRepository.findAll();
    }

    /**
     * GET  /gifts/:id -> get the "id" gift.
     */
    @RequestMapping(value = "/gifts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gift> get(@PathVariable String id) {
        log.debug("REST request to get Gift : {}", id);
        return Optional.ofNullable(giftRepository.findOne(id))
            .map(gift -> new ResponseEntity<>(
                gift,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gifts/:id -> delete the "id" gift.
     */
    @RequestMapping(value = "/gifts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Gift : {}", id);
        giftRepository.delete(id);
    }
}
