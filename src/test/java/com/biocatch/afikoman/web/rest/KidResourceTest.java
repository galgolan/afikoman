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
import com.biocatch.afikoman.domain.Kid;
import com.biocatch.afikoman.repository.KidRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KidResource REST controller.
 *
 * @see KidResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class KidResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ADDED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ADDED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ADDED_STR = dateTimeFormatter.print(DEFAULT_DATE_ADDED);
    private static final String DEFAULT_ORGANIZATION = "SAMPLE_TEXT";
    private static final String UPDATED_ORGANIZATION = "UPDATED_TEXT";
    private static final String DEFAULT_DESIRED_GIFT = "SAMPLE_TEXT";
    private static final String UPDATED_DESIRED_GIFT = "UPDATED_TEXT";
    private static final String DEFAULT_PERSONAL_NOTE = "SAMPLE_TEXT";
    private static final String UPDATED_PERSONAL_NOTE = "UPDATED_TEXT";
    private static final String DEFAULT_METADATA = "SAMPLE_TEXT";
    private static final String UPDATED_METADATA = "UPDATED_TEXT";
    private static final String DEFAULT_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_PICTURE = "UPDATED_TEXT";

    @Inject
    private KidRepository kidRepository;

    private MockMvc restKidMockMvc;

    private Kid kid;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KidResource kidResource = new KidResource();
        ReflectionTestUtils.setField(kidResource, "kidRepository", kidRepository);
        this.restKidMockMvc = MockMvcBuilders.standaloneSetup(kidResource).build();
    }

    @Before
    public void initTest() {
        kidRepository.deleteAll();
        kid = new Kid();
        kid.setFirstName(DEFAULT_FIRST_NAME);
        kid.setLastName(DEFAULT_LAST_NAME);
        kid.setDateAdded(DEFAULT_DATE_ADDED);
        kid.setOrganization(DEFAULT_ORGANIZATION);
        kid.setDesiredGift(DEFAULT_DESIRED_GIFT);
        kid.setPersonalNote(DEFAULT_PERSONAL_NOTE);
        kid.setMetadata(DEFAULT_METADATA);
        kid.setPicture(DEFAULT_PICTURE);
    }

    @Test
    public void createKid() throws Exception {
        // Validate the database is empty
        assertThat(kidRepository.findAll()).hasSize(0);

        // Create the Kid
        restKidMockMvc.perform(post("/api/kids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kid)))
                .andExpect(status().isOk());

        // Validate the Kid in the database
        List<Kid> kids = kidRepository.findAll();
        assertThat(kids).hasSize(1);
        Kid testKid = kids.iterator().next();
        assertThat(testKid.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testKid.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testKid.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testKid.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testKid.getDesiredGift()).isEqualTo(DEFAULT_DESIRED_GIFT);
        assertThat(testKid.getPersonalNote()).isEqualTo(DEFAULT_PERSONAL_NOTE);
        assertThat(testKid.getMetadata()).isEqualTo(DEFAULT_METADATA);
        assertThat(testKid.getPicture()).isEqualTo(DEFAULT_PICTURE);
    }

    @Test
    public void getAllKids() throws Exception {
        // Initialize the database
        kidRepository.save(kid);

        // Get all the kids
        restKidMockMvc.perform(get("/api/kids"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(kid.getId()))
                .andExpect(jsonPath("$.[0].firstName").value(DEFAULT_FIRST_NAME.toString()))
                .andExpect(jsonPath("$.[0].lastName").value(DEFAULT_LAST_NAME.toString()))
                .andExpect(jsonPath("$.[0].dateAdded").value(DEFAULT_DATE_ADDED_STR))
                .andExpect(jsonPath("$.[0].organization").value(DEFAULT_ORGANIZATION.toString()))
                .andExpect(jsonPath("$.[0].desiredGift").value(DEFAULT_DESIRED_GIFT.toString()))
                .andExpect(jsonPath("$.[0].personalNote").value(DEFAULT_PERSONAL_NOTE.toString()))
                .andExpect(jsonPath("$.[0].metadata").value(DEFAULT_METADATA.toString()))
                .andExpect(jsonPath("$.[0].picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    public void getKid() throws Exception {
        // Initialize the database
        kidRepository.save(kid);

        // Get the kid
        restKidMockMvc.perform(get("/api/kids/{id}", kid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kid.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED_STR))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.desiredGift").value(DEFAULT_DESIRED_GIFT.toString()))
            .andExpect(jsonPath("$.personalNote").value(DEFAULT_PERSONAL_NOTE.toString()))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA.toString()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    public void getNonExistingKid() throws Exception {
        // Get the kid
        restKidMockMvc.perform(get("/api/kids/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateKid() throws Exception {
        // Initialize the database
        kidRepository.save(kid);

        // Update the kid
        kid.setFirstName(UPDATED_FIRST_NAME);
        kid.setLastName(UPDATED_LAST_NAME);
        kid.setDateAdded(UPDATED_DATE_ADDED);
        kid.setOrganization(UPDATED_ORGANIZATION);
        kid.setDesiredGift(UPDATED_DESIRED_GIFT);
        kid.setPersonalNote(UPDATED_PERSONAL_NOTE);
        kid.setMetadata(UPDATED_METADATA);
        kid.setPicture(UPDATED_PICTURE);
        restKidMockMvc.perform(post("/api/kids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kid)))
                .andExpect(status().isOk());

        // Validate the Kid in the database
        List<Kid> kids = kidRepository.findAll();
        assertThat(kids).hasSize(1);
        Kid testKid = kids.iterator().next();
        assertThat(testKid.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testKid.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testKid.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testKid.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testKid.getDesiredGift()).isEqualTo(UPDATED_DESIRED_GIFT);
        assertThat(testKid.getPersonalNote()).isEqualTo(UPDATED_PERSONAL_NOTE);
        assertThat(testKid.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testKid.getPicture()).isEqualTo(UPDATED_PICTURE);
    }

    @Test
    public void deleteKid() throws Exception {
        // Initialize the database
        kidRepository.save(kid);

        // Get the kid
        restKidMockMvc.perform(delete("/api/kids/{id}", kid.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Kid> kids = kidRepository.findAll();
        assertThat(kids).hasSize(0);
    }
}
