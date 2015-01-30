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
import com.biocatch.afikoman.domain.Company;
import com.biocatch.afikoman.repository.CompanyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompanyResourceTest {

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
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_WEBSITE = "SAMPLE_TEXT";
    private static final String UPDATED_WEBSITE = "UPDATED_TEXT";

    @Inject
    private CompanyRepository companyRepository;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyResource companyResource = new CompanyResource();
        ReflectionTestUtils.setField(companyResource, "companyRepository", companyRepository);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource).build();
    }

    @Before
    public void initTest() {
        companyRepository.deleteAll();
        company = new Company();
        company.setName(DEFAULT_NAME);
        company.setDateAdded(DEFAULT_DATE_ADDED);
        company.setPassword(DEFAULT_PASSWORD);
        company.setPocEmail(DEFAULT_POC_EMAIL);
        company.setMetadata(DEFAULT_METADATA);
        company.setDescription(DEFAULT_DESCRIPTION);
        company.setWebsite(DEFAULT_WEBSITE);
    }

    @Test
    public void createCompany() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(1);
        Company testCompany = companys.iterator().next();
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testCompany.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCompany.getPocEmail()).isEqualTo(DEFAULT_POC_EMAIL);
        assertThat(testCompany.getMetadata()).isEqualTo(DEFAULT_METADATA);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
    }

    @Test
    public void getAllCompanys() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Get all the companys
        restCompanyMockMvc.perform(get("/api/companys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(company.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].dateAdded").value(DEFAULT_DATE_ADDED_STR))
                .andExpect(jsonPath("$.[0].password").value(DEFAULT_PASSWORD.toString()))
                .andExpect(jsonPath("$.[0].pocEmail").value(DEFAULT_POC_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].metadata").value(DEFAULT_METADATA.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].website").value(DEFAULT_WEBSITE.toString()));
    }

    @Test
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companys/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(company.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED_STR))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.pocEmail").value(DEFAULT_POC_EMAIL.toString()))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()));
    }

    @Test
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Update the company
        company.setName(UPDATED_NAME);
        company.setDateAdded(UPDATED_DATE_ADDED);
        company.setPassword(UPDATED_PASSWORD);
        company.setPocEmail(UPDATED_POC_EMAIL);
        company.setMetadata(UPDATED_METADATA);
        company.setDescription(UPDATED_DESCRIPTION);
        company.setWebsite(UPDATED_WEBSITE);
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(1);
        Company testCompany = companys.iterator().next();
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCompany.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCompany.getPocEmail()).isEqualTo(UPDATED_POC_EMAIL);
        assertThat(testCompany.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companys/{id}", company.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }
}
