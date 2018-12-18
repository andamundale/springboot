package com.michaelcgood.app.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelcgood.controller.CustomerController;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import com.michaelcgood.model.dto.TeaDto;
import com.michaelcgood.service.CustomerService;
import org.hamcrest.generator.qdox.directorywalker.Filter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    //@Autowired
    //private Filter springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerService customerService;

    /**
     * Lists of samples customers and teas
     */
    private List<Customer> customers;
    private List<Tea> teas;
    private String inputCustomer;
    private String targetOutputCustomer;
    private String targetOutputTea;
    private TeaDto teaDto;

    @Before
    public void setup() throws Exception {
        mockMvc = standaloneSetup(this.customerController).build();// Standalone context
        Long id0 = new Long(57);
        Customer customer0 = new Customer("Alexander", "Velky");
        customer0.setId(id0);
        Long id1 = new Long(31);
        Customer customer1 = new Customer("Charles", "the 4th");
        customer1.setId(id1);
        customers = new ArrayList<>();
        customers.add(customer0);
        customers.add(customer1);
        Tea tea0 = new Tea("Bancha", "green tea", "Japan");
        tea0.setId(id0);
        Tea tea1 = new Tea("Pai Mu Tan", "white tea", "China");
        tea1.setId(id1);
        teas = new ArrayList<>();
        teas.add(tea0);
        teas.add(tea1);
        inputCustomer = "{\"id\":57,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        targetOutputCustomer =
                "{\"id\":57,\"firstName\":\"Alexander\",\"lastName\":\"Velky\",\"favouriteTeas\":null}";
        targetOutputTea =
                "{\"id\":57,\"name\":\"Bancha\",\"typeOfTea\":\"green tea\"," +
                    "\"countryOfOrigin\":\"Japan\"}";//,\"customers\":null}";
        teaDto = new TeaDto();
        teaDto.setId(new Long(57));
        teaDto.setName("Bancha");
        teaDto.setTypeOfTea("green tea");
        teaDto.setCountryOfOrigin("Japan");
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // Mocking service
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer/v1/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(57)))
                .andExpect(jsonPath("$[0].firstName", is("Alexander")))
                .andExpect(jsonPath("$[0].lastName", is("Velky")))
                .andExpect(jsonPath("$[1].id", is(31)))
                .andExpect(jsonPath("$[1].firstName", is("Charles")))
                .andExpect(jsonPath("$[1].lastName", is("the 4th")));
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testGetCustomersFavouriteTeasByCustomerId() throws Exception {
        // Mocking service
        when(customerService.getCustomersFavouriteTeasByCustomerId(any(Long.class))).thenReturn(teas);

        mockMvc.perform(get("/customer/v1/getFavouriteTeas/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(57)))
                .andExpect(jsonPath("$[0].name", is("Bancha")))
                .andExpect(jsonPath("$[0].typeOfTea", is("green tea")))
                .andExpect(jsonPath("$[0].countryOfOrigin", is("Japan")))
                .andExpect(jsonPath("$[1].id", is(31)))
                .andExpect(jsonPath("$[1].name", is("Pai Mu Tan")))
                .andExpect(jsonPath("$[1].typeOfTea", is("white tea")))
                .andExpect(jsonPath("$[1].countryOfOrigin", is("China")));
        verify(customerService, times(1)).getCustomersFavouriteTeasByCustomerId(any(Long.class));
    }

    @Test
    public void testAddCustomer() throws Exception {
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customers.get(0));

        mockMvc.perform(post("/customer/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputCustomer))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputCustomer));
        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // Mocking service
        when(customerService.getCustomerById(any(Long.class))).thenReturn(customers.get(0));

        mockMvc.perform(get("/customer/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Velky")));
        verify(customerService, times(1)).getCustomerById(any(Long.class));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        // Mocking service
        when(customerService.deleteCustomerById(any(Long.class))).thenReturn(customers.get(0));

        mockMvc.perform(delete("/customer/v1/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Velky")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // Mocking service
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customers.get(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/customer/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputCustomer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("firstName", is("Alexander")))
                .andExpect(jsonPath("lastName", is("Velky")));
        verify(customerService, times(1)).updateCustomer(any(Customer.class));
    }

    @Test
    public void testAddCustomersTea() throws Exception {
        when(customerService.addCustomersTea(any(Long.class), any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(post("/customer/v1/addCustomersTeaById/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputTea));
        verify(customerService, times(1)).addCustomersTea(any(Long.class), any(Long.class));
    }

    @Test
    public void deleteCustomersTeaById() throws Exception {
        when(customerService.deleteCustomersTeaById(any(Long.class), any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(delete("/customer/v1/deleteCustomersTeaById/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputTea));
        verify(customerService, times(1)).deleteCustomersTeaById(any(Long.class), any(Long.class));
    }

}
