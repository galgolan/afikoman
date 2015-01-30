package com.biocatch.afikoman.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.biocatch.afikoman.Application;
import com.biocatch.afikoman.domain.CompanyOrganizationPairing;
import com.biocatch.afikoman.repository.CompanyOrganizationPairingRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyOrganizationPairingResource REST controller.
 *
 * @see CompanyOrganizationPairingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompanyOrganizationPairingResourceTest {

    private static final String DEFAULT_COMPANY = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY = "UPDATED_TEXT";
    private static final String DEFAULT_ORGANIZATION = "SAMPLE_TEXT";
    private static final String UPDATED_ORGANIZATION = "UPDATED_TEXT";

    private static final Integer DEFAULT_COMPANY_ID = 0;
    private static final Integer UPDATED_COMPANY_ID = 1;

    private static final Integer DEFAULT_ORGANIZATION_ID = 0;
    private static final Integer UPDATED_ORGANIZATION_ID = 1;
    private static final String DEFAULT_METADATA = "SAMPLE_TEXT";
    private static final String UPDATED_METADATA = "UPDATED_TEXT";

    @Inject
    private CompanyOrganizationPairingRepository companyOrganizationPairingRepository;

    private MockMvc restCompanyOrganizationPairingMockMvc;

    private CompanyOrganizationPairing companyOrganizationPairing;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyOrganizationPairingResource companyOrganizationPairingResource = new CompanyOrganizationPairingResource();
        ReflectionTestUtils.setField(companyOrganizationPairingResource, "companyOrganizationPairingRepository", companyOrganizationPairingRepository);
        this.restCompanyOrganizationPairingMockMvc = MockMvcBuilders.standaloneSetup(companyOrganizationPairingResource).build();
    }

    @Before
    public void initTest() {
        companyOrganizationPairingRepository.deleteAll();
        companyOrganizationPairing = new CompanyOrganizationPairing();
        companyOrganizationPairing.setCompany(DEFAULT_COMPANY);
        companyOrganizationPairing.setOrganization(DEFAULT_ORGANIZATION);
        companyOrganizationPairing.setCompanyId(DEFAULT_COMPANY_ID);
        companyOrganizationPairing.setOrganizationId(DEFAULT_ORGANIZATION_ID);
        companyOrganizationPairing.setMetadata(DEFAULT_METADATA);
    }

    @Test
    public void createCompanyOrganizationPairing() throws Exception {
        // Validate the database is empty
        assertThat(companyOrganizationPairingRepository.findAll()).hasSize(0);

        // Create the CompanyOrganizationPairing
        restCompanyOrganizationPairingMockMvc.perform(post("/api/companyOrganizationPairings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyOrganizationPairing)))
                .andExpect(status().isOk());

        // Validate the CompanyOrganizationPairing in the database
        List<CompanyOrganizationPairing> companyOrganizationPairings = companyOrganizationPairingRepository.findAll();
        assertThat(companyOrganizationPairings).hasSize(1);
        CompanyOrganizationPairing testCompanyOrganizationPairing = companyOrganizationPairings.iterator().next();
        assertThat(testCompanyOrganizationPairing.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testCompanyOrganizationPairing.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testCompanyOrganizationPairing.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompanyOrganizationPairing.getOrganizationId()).isEqualTo(DEFAULT_ORGANIZATION_ID);
        assertThat(testCompanyOrganizationPairing.getMetadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void getAllCompanyOrganizationPairings() throws Exception {
        // Initialize the database
        companyOrganizationPairingRepository.save(companyOrganizationPairing);

        // Get all the companyOrganizationPairings
        restCompanyOrganizationPairingMockMvc.perform(get("/api/companyOrganizationPairings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(companyOrganizationPairing.getId()))
                .andExpect(jsonPath("$.[0].Company").value(DEFAULT_COMPANY.toString()))
                .andExpect(jsonPath("$.[0].Organization").value(DEFAULT_ORGANIZATION.toString()))
                .andExpect(jsonPath("$.[0].CompanyId").value(DEFAULT_COMPANY_ID))
                .andExpect(jsonPath("$.[0].OrganizationId").value(DEFAULT_ORGANIZATION_ID))
                .andExpect(jsonPath("$.[0].Metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getCompanyOrganizationPairing() throws Exception {
        // Initialize the database
        companyOrganizationPairingRepository.save(companyOrganizationPairing);

        // Get the companyOrganizationPairing
        restCompanyOrganizationPairingMockMvc.perform(get("/api/companyOrganizationPairings/{id}", companyOrganizationPairing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(companyOrganizationPairing.getId()))
            .andExpect(jsonPath("$.Company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.Organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.CompanyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.OrganizationId").value(DEFAULT_ORGANIZATION_ID))
            .andExpect(jsonPath("$.Metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getNonExistingCompanyOrganizationPairing() throws Exception {
        // Get the companyOrganizationPairing
        restCompanyOrganizationPairingMockMvc.perform(get("/api/companyOrganizationPairings/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompanyOrganizationPairing() throws Exception {
        // Initialize the database
        companyOrganizationPairingRepository.save(companyOrganizationPairing);

        // Update the companyOrganizationPairing
        companyOrganizationPairing.setCompany(UPDATED_COMPANY);
        companyOrganizationPairing.setOrganization(UPDATED_ORGANIZATION);
        companyOrganizationPairing.setCompanyId(UPDATED_COMPANY_ID);
        companyOrganizationPairing.setOrganizationId(UPDATED_ORGANIZATION_ID);
        companyOrganizationPairing.setMetadata(UPDATED_METADATA);
        restCompanyOrganizationPairingMockMvc.perform(post("/api/companyOrganizationPairings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyOrganizationPairing)))
                .andExpect(status().isOk());

        // Validate the CompanyOrganizationPairing in the database
        List<CompanyOrganizationPairing> companyOrganizationPairings = companyOrganizationPairingRepository.findAll();
        assertThat(companyOrganizationPairings).hasSize(1);
        CompanyOrganizationPairing testCompanyOrganizationPairing = companyOrganizationPairings.iterator().next();
        assertThat(testCompanyOrganizationPairing.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testCompanyOrganizationPairing.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testCompanyOrganizationPairing.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompanyOrganizationPairing.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testCompanyOrganizationPairing.getMetadata()).isEqualTo(UPDATED_METADATA);
    }

    @Test
    public void deleteCompanyOrganizationPairing() throws Exception {
        // Initialize the database
        companyOrganizationPairingRepository.save(companyOrganizationPairing);

        // Get the companyOrganizationPairing
        restCompanyOrganizationPairingMockMvc.perform(delete("/api/companyOrganizationPairings/{id}", companyOrganizationPairing.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyOrganizationPairing> companyOrganizationPairings = companyOrganizationPairingRepository.findAll();
        assertThat(companyOrganizationPairings).hasSize(0);
    }
}
