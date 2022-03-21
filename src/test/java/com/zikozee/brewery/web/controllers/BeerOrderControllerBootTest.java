package com.zikozee.brewery.web.controllers;

import com.zikozee.brewery.services.BeerOrderService;
import com.zikozee.brewery.web.model.BeerOrderDto;
import com.zikozee.brewery.web.model.BeerOrderLineDto;
import com.zikozee.brewery.web.model.BeerOrderPagedList;
import com.zikozee.brewery.web.model.OrderStatusEnum;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerBootTest {

    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    BeerOrderDto beerOrderDto;

    @BeforeEach
    void setUp() {
        BeerOrderLineDto beerLine = BeerOrderLineDto.builder().id(UUID.randomUUID())
                .version(1)
                .beerId(UUID.randomUUID())
                .orderQuantity(2)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        List<BeerOrderLineDto> beerOrderLineList = List.of(beerLine);

        beerOrderDto = BeerOrderDto.builder().id(UUID.randomUUID())
                .version(1)
                .customerId(UUID.randomUUID())
                .customerRef("ref1")
                .beerOrderLines(beerOrderLineList)
                .orderStatus(OrderStatusEnum.READY)
                .orderStatusCallbackUrl("https://example.com")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(beerOrderService);
    }

    @Test
    void getOrder() throws Exception{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        given(beerOrderService.getOrderById(any(), any())).willReturn(beerOrderDto);

        mockMvc.perform(get("/api/v1/customers/"+ beerOrderDto.getCustomerId()+"/orders/" + beerOrderDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(beerOrderDto.getId().toString())))
                .andExpect(jsonPath("$.beerOrderLines[0].orderQuantity", is(2)))
                .andExpect(jsonPath("$.createdDate", is(dateTimeFormatter.format(beerOrderDto.getCreatedDate()))));
    }


    @DisplayName("Lis Opst")
    @Nested
    class TestListOperation {

        @Captor
        ArgumentCaptor<UUID> customerIdCaptor;

        @Captor
        ArgumentCaptor<PageRequest> pageRequestCaptor;


        BeerOrderPagedList beerOrderPagedList;

        @BeforeEach
        void setUp() {
            List<BeerOrderDto> beerOrderDtos = new ArrayList<>();
            beerOrderDtos.add(beerOrderDto);

            beerOrderPagedList = new BeerOrderPagedList(beerOrderDtos, PageRequest.of(1, 1), beerOrderDtos.size());

            given(beerOrderService.listOrders(customerIdCaptor.capture(), pageRequestCaptor.capture()))
                    .willReturn(beerOrderPagedList);
        }


        @DisplayName("test orders list")
        @Test
        public void testListOrders() throws Exception{
            mockMvc.perform(get("/api/v1/customers/"+ beerOrderDto.getCustomerId()+"/orders"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.content", hasSize(1)))
                    .andExpect(jsonPath("$.content[0].customerId", is(beerOrderDto.getCustomerId().toString())));
        }
    }
}