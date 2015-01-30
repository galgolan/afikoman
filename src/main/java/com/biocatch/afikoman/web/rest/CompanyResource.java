package com.biocatch.afikoman.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.biocatch.afikoman.domain.Company;
import com.biocatch.afikoman.repository.CompanyRepository;
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
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    private CompanyRepository companyRepository;

    /**
     * POST  /companys -> Create a new company.
     */
    @RequestMapping(value = "/companys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Company company) {
        log.debug("REST request to save Company : {}", company);
        companyRepository.save(company);
    }

    /**
     * GET  /companys -> get all the companys.
     */
    @RequestMapping(value = "/companys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Company> getAll() {
        log.debug("REST request to get all Companys");
        return companyRepository.findAll();
    }

    /**
     * GET  /companys/:id -> get the "id" company.
     */
    @RequestMapping(value = "/companys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Company> get(@PathVariable String id) {
        log.debug("REST request to get Company : {}", id);
        return Optional.ofNullable(companyRepository.findOne(id))
            .map(company -> new ResponseEntity<>(
                company,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companys/:id -> delete the "id" company.
     */
    @RequestMapping(value = "/companys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
