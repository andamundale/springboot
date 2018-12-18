package com.michaelcgood.app.controller.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ExampleApplicationContext.class})
//@ContextConfiguration(locations = {"classpath:exampleApplicationContext.xml"})
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
//@DatabaseSetup("toDoData.xml")
public class CustomerControllerIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String inputCustomer, updateCustomer;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        updateCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Macedonsky\"}";
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/v1/"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6))) //defaultne 6 ale zalezi od poradia testov
                .andExpect(jsonPath("$[0].id", is(12)))
                .andExpect(jsonPath("$[0].firstName", is("Alexander")))
                .andExpect(jsonPath("$[0].lastName", is("Velky")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Chloe")))
                .andExpect(jsonPath("$[1].lastName", is("O'Brian")));
    }

    @Test
    public void testGetCustomersFavouriteTeasByCustomerId() throws Exception {
        mockMvc.perform(get("/customer/v1/getFavouriteTeas/10"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(11)))
                .andExpect(jsonPath("$[0].name", is("Baihao Yinzhen")))
                .andExpect(jsonPath("$[0].typeOfTea", is("white tea")))
                .andExpect(jsonPath("$[0].countryOfOrigin", is("China")));
    }

    @Test
    public void testAddCustomer() throws Exception {
        mockMvc.perform(post("/customer/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputCustomer))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Velky")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(updateCustomer))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Macedonsky")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/customer/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                //.content("12"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Jack")))
                .andExpect(jsonPath("lastName", is("Bauer")));
    }
}
