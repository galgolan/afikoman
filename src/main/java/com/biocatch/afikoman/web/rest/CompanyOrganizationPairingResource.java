package com.biocatch.afikoman.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.biocatch.afikoman.domain.CompanyOrganizationPairing;
import com.biocatch.afikoman.repository.CompanyOrganizationPairingRepository;
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
 * REST controller for managing CompanyOrganizationPairing.
 */
@RestController
@RequestMapping("/api")
public class CompanyOrganizationPairingResource {

    private final Logger log = LoggerFactory.getLogger(CompanyOrganizationPairingResource.class);

    @Inject
    private CompanyOrganizationPairingRepository companyOrganizationPairingRepository;

    /**
     * POST  /companyOrganizationPairings -> Create a new companyOrganizationPairing.
     */
    @RequestMapping(value = "/companyOrganizationPairings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CompanyOrganizationPairing companyOrganizationPairing) {
        log.debug("REST request to save CompanyOrganizationPairing : {}", companyOrganizationPairing);
        companyOrganizationPairingRepository.save(companyOrganizationPairing);
    }

    /**
     * GET  /companyOrganizationPairings -> get all the companyOrganizationPairings.
     */
    @RequestMapping(value = "/companyOrganizationPairings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompanyOrganizationPairing> getAll() {
        log.debug("REST request to get all CompanyOrganizationPairings");
        return companyOrganizationPairingRepository.findAll();
    }

    /**
     * GET  /companyOrganizationPairings/:id -> get the "id" companyOrganizationPairing.
     */
    @RequestMapping(value = "/companyOrganizationPairings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyOrganizationPairing> get(@PathVariable String id) {
        log.debug("REST request to get CompanyOrganizationPairing : {}", id);
        return Optional.ofNullable(companyOrganizationPairingRepository.findOne(id))
            .map(companyOrganizationPairing -> new ResponseEntity<>(
                companyOrganizationPairing,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companyOrganizationPairings/:id -> delete the "id" companyOrganizationPairing.
     */
    @RequestMapping(value = "/companyOrganizationPairings/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete CompanyOrganizationPairing : {}", id);
        companyOrganizationPairingRepository.delete(id);
    }
}
