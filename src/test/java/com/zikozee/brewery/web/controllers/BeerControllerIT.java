package com.zikozee.brewery.web.controllers;

import com.zikozee.brewery.web.model.BeerPagedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author: Ezekiel Eromosei
 * @created: 21 March 2022
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testListBeers() {
        BeerPagedList beerPagedList = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        assertThat(beerPagedList.getContent()).hasSize(3);
    }
}
