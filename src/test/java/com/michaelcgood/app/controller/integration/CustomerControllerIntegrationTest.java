package com.michaelcgood.app.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TeaRepository teaRepository;

    private ObjectMapper objectMapper;

    private String inputCustomer, updateCustomer, inputTea, inputTea2, updateTea, targetOutputTea;

    private Long firstId, customerId, teaId;

    private Tea favTea;

    private List<Customer> customers;

    @Before
    public void init() throws Exception {
        Customer customer0;
        List<Tea> teas;
        List<Customer> tmpCustomers;
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        customers = new ArrayList<Customer>();
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
//        inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
//        updateCustomer = "{\"id\":1,\"firstName\":\"JACK\",\"lastName\":\"BAUER\"}";
//        inputTea2 = "{\"id\":12,\"name\":\"Pai Mu Tan\",\"typeOfTea\":\"white tea\"," +
//                "\"countryOfOrigin\":\"China\"}";
//        updateTea = "{\"id\":6,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
//                "\"countryOfOrigin\":\"Japan\"}";
//        targetOutputTea = "{\"id\":6,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
//                "\"countryOfOrigin\":\"Japan\",\"customers\":[]}";
        /**/
        customerRepository.deleteAll();
        teaRepository.deleteAll();
        System.out.println("Preloading database.");
        //firstId = customerRepository.save(new Customer("Jack", "Bauer")).getId();
        customers.add(customerRepository.save(new Customer("Jack", "Bauer")));
        customers.add(customerRepository.save(new Customer("Chloe", "O'Brian")));
        customers.add(customerRepository.save(new Customer("Kim", "Bauer")));
        customers.add(customerRepository.save(new Customer("David", "Palmer")));
        customers.add(customerRepository.save(new Customer("Michelle", "Dessler")));
        teaId = teaRepository.save(new Tea("Sencha", "green tea", "Japan")).getId();
        teaRepository.save(new Tea("Bancha", "green tea", "Japan"));
        favTea = teaRepository.save(new Tea("Baihao Yinzhen", "white tea", "China"));
        teaRepository.save(new Tea("Keemun", "black tea", "China"));
        customer0 = new Customer("Charles", "the 4th");
        teas = new ArrayList<Tea>();
        teas.add(favTea);
        customer0.setFavouriteTeas(teas);
        customers.add(customerRepository.save(customer0));
        customerId = customers.get(customers.size()-1).getId();
        customer0.setId(customerId);
        tmpCustomers = new ArrayList<Customer>();
        tmpCustomers.add(customer0);
        favTea.setCustomers(tmpCustomers);
        teaRepository.save(favTea);
        /**/
        inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        updateCustomer = "{\"id\":\""+customerId+"\",\"firstName\":\"Alexander\",\"lastName\":\"Macedonsky\"}";
        inputTea = "{\"id\":12,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
                "\"countryOfOrigin\":\"Japan\"}";
        targetOutputTea = "{\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
                "\"countryOfOrigin\":\"Japan\",\"customers\":[]}";
        inputTea2 = "{\"id\":\""+(customerId+1)+"\",\"name\":\"Pai Mu Tan\",\"typeOfTea\":\"white tea\",\"countryOfOrigin\":\"China\"}";
        updateTea = "{\"id\":\""+teaId+"\",\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
                "\"countryOfOrigin\":\"Japan\"}";
        System.out.println("Current state of the database.");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println("Here is a customer: " + customer.toString());
        }
        for (Tea tea : teaRepository.findAll()) {
            System.out.println("Here is a tea: " + tea.toString());
        }
        System.out.println(
                "Customer with id = "+customer0.getId()+" has "+customer0.getFavouriteTeas().size()+" favourite teas."
        );
        System.out.println(
                "Tea with id = "+favTea.getId()+" has "+favTea.getCustomers().size()+" customerss."
        );
        System.out.println("Customer to be updated: "+updateCustomer);
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/v1/"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6))) //defaultne 6 ale zalezi od poradia testov
                //.andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Michelle")))
                .andExpect(jsonPath("$[0].lastName", is("Dessler")))
                //.andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("David")))
                .andExpect(jsonPath("$[1].lastName", is("Palmer")));
    }

    @Test
    public void testGetCustomersFavouriteTeasByCustomerId() throws Exception {
        mockMvc.perform(get("/customer/v1/getFavouriteTeas/"+customerId))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                //.andExpect(jsonPath("$[0].id", is(11)))
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
                //.andExpect(jsonPath("id", is(12)))
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
                //.andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Macedonsky")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/customer/v1/"+customerId)
                .contentType(MediaType.APPLICATION_JSON))
                //.content("12"))
                .andExpect(status().isOk())
                //.andDo(print())
                //.andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Charles")))
                .andExpect(jsonPath("lastName", is("the 4th")));
    }

    @Test
    public void testGetAllCustomersAsJsonAsString() throws Exception {
        mockMvc.perform(get("/customer/v1/getAllCustomersAsJson/"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6))) //defaultne 6 ale zalezi od poradia testov
                //.andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0]", is(objectMapper.writeValueAsString(customers.get(4)))))
                //.andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1]", is(objectMapper.writeValueAsString(customers.get(3)))));
    }
}
