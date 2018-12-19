package com.michaelcgood.app.controller.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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

import java.util.ArrayList;
import java.util.List;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

//    @Autowired
//    private CustomerRepository customerRepository;

//    @Autowired
//    private TeaRepository teaRepository;

    private String inputCustomer, updateCustomer;

    private Long firstId;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        /*
        customerRepository.deleteAll();
        teaRepository.deleteAll();
        System.out.println("Preloading database.");
        firstId = customerRepository.save(new Customer("Jack", "Bauer")).getId();
        customerRepository.save(new Customer("Chloe", "O'Brian"));
        customerRepository.save(new Customer("Kim", "Bauer"));
        customerRepository.save(new Customer("David", "Palmer"));
        customerRepository.save(new Customer("Michelle", "Dessler"));
        teaRepository.save(new Tea("Sencha", "green tea", "Japan"));
        teaRepository.save(new Tea("Bancha", "green tea", "Japan"));
        teaRepository.save(new Tea("Baihao Yinzhen", "white tea", "China"));
        teaRepository.save(new Tea("Keemun", "black tea", "China"));
        Customer customer0 = new Customer("Charles", "the 4th");
        List<Tea> teas = new ArrayList<Tea>();
        teas.add(new Tea("Baihao Yinzhen", "white tea", "China"));
        customer0.setFavouriteTeas(teas);
        customerRepository.save(customer0);
        firstId--;
        /**/
        //inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        //updateCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Macedonsky\"}";
        inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        updateCustomer = "{\"id\":1,\"firstName\":\"JACK\",\"lastName\":\"BAUER\"}";
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/v1/"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6))) //defaultne 6 ale zalezi od poradia testov
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Jack")))
                .andExpect(jsonPath("$[0].lastName", is("Bauer")))
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
                //.andDo(print())
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
                //.andDo(print())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("JACK")))
                .andExpect(jsonPath("lastName", is("BAUER")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/customer/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                //.content("12"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Jack")))
                .andExpect(jsonPath("lastName", is("Bauer")));
    }
}
