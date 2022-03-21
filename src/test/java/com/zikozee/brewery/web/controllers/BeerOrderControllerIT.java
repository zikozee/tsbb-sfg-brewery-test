package com.zikozee.brewery.web.controllers;

import com.zikozee.brewery.domain.Customer;
import com.zikozee.brewery.repositories.BeerOrderRepository;
import com.zikozee.brewery.repositories.CustomerRepository;
import com.zikozee.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
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
public class BeerOrderControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    CustomerRepository repository;

    Customer customer;

    @BeforeEach
    void setUp() {
         customer = repository.findAll().get(0);
    }

    @Test
    void testListBeers() {

        BeerOrderPagedList beerOrderDtos = testRestTemplate.getForObject("/api/v1/customers/" + customer.getId() + "/orders", BeerOrderPagedList.class);

        assertThat(beerOrderDtos.getContent()).hasSize(1);

    }
}
