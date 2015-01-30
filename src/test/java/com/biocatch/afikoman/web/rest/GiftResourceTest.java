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
import com.biocatch.afikoman.domain.Gift;
import com.biocatch.afikoman.repository.GiftRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GiftResource REST controller.
 *
 * @see GiftResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GiftResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ADDED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ADDED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ADDED_STR = dateTimeFormatter.print(DEFAULT_DATE_ADDED);

    private static final Boolean DEFAULT_IN_STOCK = false;
    private static final Boolean UPDATED_IN_STOCK = true;

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;
    private static final String DEFAULT_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_PICTURE = "UPDATED_TEXT";
    private static final String DEFAULT_METADATA = "SAMPLE_TEXT";
    private static final String UPDATED_METADATA = "UPDATED_TEXT";

    @Inject
    private GiftRepository giftRepository;

    private MockMvc restGiftMockMvc;

    private Gift gift;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GiftResource giftResource = new GiftResource();
        ReflectionTestUtils.setField(giftResource, "giftRepository", giftRepository);
        this.restGiftMockMvc = MockMvcBuilders.standaloneSetup(giftResource).build();
    }

    @Before
    public void initTest() {
        giftRepository.deleteAll();
        gift = new Gift();
        gift.setName(DEFAULT_NAME);
        gift.setDateAdded(DEFAULT_DATE_ADDED);
        gift.setInStock(DEFAULT_IN_STOCK);
        gift.setPrice(DEFAULT_PRICE);
        gift.setPicture(DEFAULT_PICTURE);
        gift.setMetadata(DEFAULT_METADATA);
    }

    @Test
    public void createGift() throws Exception {
        // Validate the database is empty
        assertThat(giftRepository.findAll()).hasSize(0);

        // Create the Gift
        restGiftMockMvc.perform(post("/api/gifts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gift)))
                .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> gifts = giftRepository.findAll();
        assertThat(gifts).hasSize(1);
        Gift testGift = gifts.iterator().next();
        assertThat(testGift.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGift.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testGift.getInStock()).isEqualTo(DEFAULT_IN_STOCK);
        assertThat(testGift.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGift.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testGift.getMetadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void getAllGifts() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Get all the gifts
        restGiftMockMvc.perform(get("/api/gifts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(gift.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].dateAdded").value(DEFAULT_DATE_ADDED_STR))
                .andExpect(jsonPath("$.[0].inStock").value(DEFAULT_IN_STOCK.booleanValue()))
                .andExpect(jsonPath("$.[0].price").value(DEFAULT_PRICE))
                .andExpect(jsonPath("$.[0].picture").value(DEFAULT_PICTURE.toString()))
                .andExpect(jsonPath("$.[0].metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getGift() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gift.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED_STR))
            .andExpect(jsonPath("$.inStock").value(DEFAULT_IN_STOCK.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA.toString()));
    }

    @Test
    public void getNonExistingGift() throws Exception {
        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateGift() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Update the gift
        gift.setName(UPDATED_NAME);
        gift.setDateAdded(UPDATED_DATE_ADDED);
        gift.setInStock(UPDATED_IN_STOCK);
        gift.setPrice(UPDATED_PRICE);
        gift.setPicture(UPDATED_PICTURE);
        gift.setMetadata(UPDATED_METADATA);
        restGiftMockMvc.perform(post("/api/gifts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gift)))
                .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> gifts = giftRepository.findAll();
        assertThat(gifts).hasSize(1);
        Gift testGift = gifts.iterator().next();
        assertThat(testGift.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGift.getDateAdded().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testGift.getInStock()).isEqualTo(UPDATED_IN_STOCK);
        assertThat(testGift.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGift.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testGift.getMetadata()).isEqualTo(UPDATED_METADATA);
    }

    @Test
    public void deleteGift() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Get the gift
        restGiftMockMvc.perform(delete("/api/gifts/{id}", gift.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gift> gifts = giftRepository.findAll();
        assertThat(gifts).hasSize(0);
    }
}
