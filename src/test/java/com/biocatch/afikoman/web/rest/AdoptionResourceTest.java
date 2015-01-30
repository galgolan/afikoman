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
import com.biocatch.afikoman.domain.Adoption;
import com.biocatch.afikoman.repository.AdoptionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdoptionResource REST controller.
 *
 * @see AdoptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdoptionResourceTest {

    private static final String DEFAULT_EMPLOYEE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_EMPLOYEE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMPLOYEE_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMPLOYEE_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_COMPANY = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY = "UPDATED_TEXT";
    private static final String DEFAULT_ORGANIZATION = "SAMPLE_TEXT";
    private static final String UPDATED_ORGANIZATION = "UPDATED_TEXT";
    private static final String DEFAULT_KID = "SAMPLE_TEXT";
    private static final String UPDATED_KID = "UPDATED_TEXT";

    private static final String DEFAULT_KID_ID = "SAMPLE_TEXT";
    private static final String UPDATED_KID_ID = "UPDATED_TEXT";

    private static final String DEFAULT_ORGANIZATION_ID = "SAMPLE_TEXT";
    private static final String UPDATED_ORGANIZATION_ID = "UPDATED_TEXT";

    private static final String DEFAULT_COMPANY_ID = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_ID = "UPDATED_TEXT";

    @Inject
    private AdoptionRepository adoptionRepository;

    private MockMvc restAdoptionMockMvc;

    private Adoption adoption;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdoptionResource adoptionResource = new AdoptionResource();
        ReflectionTestUtils.setField(adoptionResource, "adoptionRepository", adoptionRepository);
        this.restAdoptionMockMvc = MockMvcBuilders.standaloneSetup(adoptionResource).build();
    }

    @Before
    public void initTest() {
        adoptionRepository.deleteAll();
        adoption = new Adoption();
        adoption.setEmployeeName(DEFAULT_EMPLOYEE_NAME);
        adoption.setEmployeeEmail(DEFAULT_EMPLOYEE_EMAIL);
        adoption.setCompany(DEFAULT_COMPANY);
        adoption.setOrganization(DEFAULT_ORGANIZATION);
        adoption.setKid(DEFAULT_KID);
        adoption.setKidId(DEFAULT_KID_ID);
        adoption.setOrganizationId(DEFAULT_ORGANIZATION_ID);
        adoption.setCompanyId(DEFAULT_COMPANY_ID);
    }

    @Test
    public void createAdoption() throws Exception {
        // Validate the database is empty
        assertThat(adoptionRepository.findAll()).hasSize(0);

        // Create the Adoption
        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isOk());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(1);
        Adoption testAdoption = adoptions.iterator().next();
        assertThat(testAdoption.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testAdoption.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);
        assertThat(testAdoption.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAdoption.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testAdoption.getKid()).isEqualTo(DEFAULT_KID);
        assertThat(testAdoption.getKidId()).isEqualTo(DEFAULT_KID_ID);
        assertThat(testAdoption.getOrganizationId()).isEqualTo(DEFAULT_ORGANIZATION_ID);
        assertThat(testAdoption.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    public void getAllAdoptions() throws Exception {
        // Initialize the database
        adoptionRepository.save(adoption);

        // Get all the adoptions
        restAdoptionMockMvc.perform(get("/api/adoptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(adoption.getId()))
                .andExpect(jsonPath("$.[0].employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
                .andExpect(jsonPath("$.[0].employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].company").value(DEFAULT_COMPANY.toString()))
                .andExpect(jsonPath("$.[0].organization").value(DEFAULT_ORGANIZATION.toString()))
                .andExpect(jsonPath("$.[0].kid").value(DEFAULT_KID.toString()))
                .andExpect(jsonPath("$.[0].kidId").value(DEFAULT_KID_ID))
                .andExpect(jsonPath("$.[0].organizationId").value(DEFAULT_ORGANIZATION_ID))
                .andExpect(jsonPath("$.[0].companyId").value(DEFAULT_COMPANY_ID));
    }

    @Test
    public void getAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.save(adoption);

        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", adoption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adoption.getId()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.kid").value(DEFAULT_KID.toString()))
            .andExpect(jsonPath("$.kidId").value(DEFAULT_KID_ID))
            .andExpect(jsonPath("$.organizationId").value(DEFAULT_ORGANIZATION_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID));
    }

    @Test
    public void getNonExistingAdoption() throws Exception {
        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.save(adoption);

        // Update the adoption
        adoption.setEmployeeName(UPDATED_EMPLOYEE_NAME);
        adoption.setEmployeeEmail(UPDATED_EMPLOYEE_EMAIL);
        adoption.setCompany(UPDATED_COMPANY);
        adoption.setOrganization(UPDATED_ORGANIZATION);
        adoption.setKid(UPDATED_KID);
        adoption.setKidId(UPDATED_KID_ID);
        adoption.setOrganizationId(UPDATED_ORGANIZATION_ID);
        adoption.setCompanyId(UPDATED_COMPANY_ID);
        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isOk());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(1);
        Adoption testAdoption = adoptions.iterator().next();
        assertThat(testAdoption.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testAdoption.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);
        assertThat(testAdoption.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAdoption.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testAdoption.getKid()).isEqualTo(UPDATED_KID);
        assertThat(testAdoption.getKidId()).isEqualTo(UPDATED_KID_ID);
        assertThat(testAdoption.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testAdoption.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    public void deleteAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.save(adoption);

        // Get the adoption
        restAdoptionMockMvc.perform(delete("/api/adoptions/{id}", adoption.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(0);
    }
}
