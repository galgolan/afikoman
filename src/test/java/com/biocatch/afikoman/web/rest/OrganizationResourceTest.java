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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.biocatch.afikoman.Application;
import com.biocatch.afikoman.domain.Organization;
import com.biocatch.afikoman.repository.OrganizationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrganizationResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ADDED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ADDED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ADDED_STR = dateTimeFormatter.print(DEFAULT_DATE_ADDED);
    private static final String DEFAULT_PASSWORD = "SAMPLE_TEXT";
    private static final String UPDATED_PASSWORD = "UPDATED_TEXT";
    private static final String DEFAULT_POC_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_POC_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_METADATA = "SAMPLE_TEXT";
    private static final String UPDATED_METADATA = "UPDATED_TEXT";

    @Inject
    private OrganizationRepository organizationRepository;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganizationResource organizationResource = new OrganizationResource();
        ReflectionTestUtils.setField(organizationResource, "organizationRepository", organizationRepository);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource).build();
    }

    @Before
    public void initTest() {
        organizationRepository.deleteAll();
        organization = new Organization();
        organization.setName(DEFAULT_NAME);
        organization.setDateAdded(DEFAULT_DATE_ADDED);
        organization.setPassword(DEFAULT_PASSWORD);
        organization.setPocEmail(DEFAULT_POC_EMAIL);
        organization.setMetadata(DEFAULT_METADATA);
    }

    @Test
    public void createOrganization() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);

        // Create the Organization
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(1);
        Organization testOrganization = organizations.iterator().next();
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testOrganization.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testOrganization.getPocEmail()).isEqualTo(DEFAULT_POC_EMAIL);
        assertThat(testOrganization.getMetadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get all the organizations
        restOrganizationMockMvc.perform(get("/api/organizations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].dateAdded").value(DEFAULT_DATE_ADDED_STR))
                .andExpect(jsonPath("$.[0].password").value(DEFAULT_PASSWORD.toString()))
                .andExpect(jsonPath("$.[0].pocEmail").value(DEFAULT_POC_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(organization.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED_STR))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.pocEmail").value(DEFAULT_POC_EMAIL.toString()))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Update the organization
        organization.setName(UPDATED_NAME);
        organization.setDateAdded(UPDATED_DATE_ADDED);
        organization.setPassword(UPDATED_PASSWORD);
        organization.setPocEmail(UPDATED_POC_EMAIL);
        organization.setMetadata(UPDATED_METADATA);
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(1);
        Organization testOrganization = organizations.iterator().next();
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testOrganization.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testOrganization.getPocEmail()).isEqualTo(UPDATED_POC_EMAIL);
        assertThat(testOrganization.getMetadata()).isEqualTo(UPDATED_METADATA);
    }

    @Test
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }
}
