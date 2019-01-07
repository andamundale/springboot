package com.michaelcgood.app.controller.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeaControllerIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TeaRepository teaRepository;

    private String inputCustomer, updateCustomer, inputTea, inputTea2, updateTea, targetOutputTea;

    private Long firstId, customerId, teaId;

    private Tea favTea;

    @Before
    public void setup() throws Exception {
        Customer customer0;
        List<Tea> teas;
        List<Customer> customers;
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        inputCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        updateCustomer = "{\"id\":12,\"firstName\":\"Alexander\",\"lastName\":\"Macedonsky\"}";
        inputTea = "{\"id\":12,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
                "\"countryOfOrigin\":\"Japan\"}";
//        inputTea2 = "{\"id\":12,\"name\":\"Pai Mu Tan\",\"typeOfTea\":\"white tea\"," +
//                "\"countryOfOrigin\":\"China\"}";
//        updateTea = "{\"id\":6,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
//                "\"countryOfOrigin\":\"Japan\"}";
//        targetOutputTea = "{\"id\":6,\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
//                "\"countryOfOrigin\":\"Japan\",\"customers\":[]}";
        targetOutputTea = "{\"name\":\"Genmaicha\",\"typeOfTea\":\"green tea\"," +
                "\"countryOfOrigin\":\"Japan\",\"customers\":[]}";
        /**/
        customerRepository.deleteAll();
        teaRepository.deleteAll();
        System.out.println("Preloading database.");
        //firstId = customerRepository.save(new Customer("Jack", "Bauer")).getId();
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
        customerRepository.save(new Customer("Kim", "Bauer"));
        customerRepository.save(new Customer("David", "Palmer"));
        customerRepository.save(new Customer("Michelle", "Dessler"));
        teaId = teaRepository.save(new Tea("Sencha", "green tea", "Japan")).getId();
        teaRepository.save(new Tea("Bancha", "green tea", "Japan"));
        favTea = teaRepository.save(new Tea("Baihao Yinzhen", "white tea", "China"));
        teaRepository.save(new Tea("Keemun", "black tea", "China"));
        customer0 = new Customer("Charles", "the 4th");
        teas = new ArrayList<Tea>();
        teas.add(favTea);
        customer0.setFavouriteTeas(teas);
        customerId = customerRepository.save(customer0).getId();
        customer0.setId(customerId);
        customers = new ArrayList<Customer>();
        customers.add(customer0);
        favTea.setCustomers(customers);
        teaRepository.save(favTea);
        /**/
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
        System.out.println("Tea to be added: "+inputTea2);
    }

    @Test
    public void testGetAllTeas() throws Exception {
        mockMvc.perform(get("/tea/v1/"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
                //.andExpect(jsonPath("$[0].id", is(6)))
                //.andExpect(jsonPath("$[0].name", is("Sencha")))
                .andExpect(jsonPath("$[0].name", is("Bancha")))
                .andExpect(jsonPath("$[0].typeOfTea", is("green tea")))
                .andExpect(jsonPath("$[0].countryOfOrigin", is("Japan")))

                //.andExpect(jsonPath("$[1].id", is(7)))
                //.andExpect(jsonPath("$[1].name", is("Bancha")))
                .andExpect(jsonPath("$[1].name", is("Sencha")))
                .andExpect(jsonPath("$[1].typeOfTea", is("green tea")))
                .andExpect(jsonPath("$[1].countryOfOrigin", is("Japan")));
    }
/**/
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
/**/
/**/
    @Test
    public void testAddTea() throws Exception {
        mockMvc.perform(post("/tea/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputTea2))
                .andExpect(status().isOk())
                //.andDo(print())
                //.andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("name", is("Pai Mu Tan")))
                .andExpect(jsonPath("typeOfTea", is("white tea")))
                .andExpect(jsonPath("countryOfOrigin", is("China")));
    }
/**/
/**/
    @Test
    public void testUpdateTea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/tea/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(updateTea))
                .andExpect(status().isOk())
                //.andDo(print())
                //.andExpect(jsonPath("id", is(6)))
                .andExpect(jsonPath("name", is("Genmaicha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
    }
/**/
/**/
    @Test
    public void testDeleteTeaById() throws Exception {
        mockMvc.perform(delete("/tea/v1/"+teaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(print())
                //.andExpect(jsonPath("id", is(6)))
                .andExpect(jsonPath("name", is("Sencha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
    }
/**/
    @Test
    public void testGetTeaById() throws Exception {
        mockMvc.perform(get("/tea/v1/"+teaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(print())
                //.andExpect(jsonPath("id", is(6)))
                .andExpect(jsonPath("name", is("Sencha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
    }

    @Test
    public void testGetTeasCustomersByTeaId() throws Exception {
        mockMvc.perform(get("/tea/v1/getCustomersFavouringTea/"+favTea.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                //.andExpect(jsonPath("$[0].id", is(10)))
                //.andExpect(jsonPath("$[0].firstName", is("Charles")))
                //.andExpect(jsonPath("$[0].lastName", is("the 4th")));
                .andExpect(jsonPath("$[0].firstName", is("Charles")))
                .andExpect(jsonPath("$[0].lastName", is("the 4th")));
    }
}
