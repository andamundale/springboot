package com.michaelcgood.app.controller.unit;

import com.michaelcgood.controller.TeaController;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.TeaDto;
import com.michaelcgood.service.TeaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeaControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    TeaController teaController;

    @MockBean
    TeaService teaService;

    /**
     * Lists of samples customers and teas
     */
    private List<Customer> customers;
    private List<Tea> teas;
    private String inputCustomer, targetOutputCustomer, targetOutputTea, inputTea;
    private TeaDto teaDto;

    @Before
    public void setup() throws Exception {
        mockMvc = standaloneSetup(this.teaController).build();// Standalone context
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
//        targetOutputCustomer =
//                "{\"id\":57,\"firstName\":\"Alexander\",\"lastName\":\"Velky\",\"favouriteTeas\":null}";
//        targetOutputCustomer =
//                "{\"id\":57,\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        targetOutputCustomer =
                "{\"firstName\":\"Alexander\",\"lastName\":\"Velky\"}";
        inputTea =
                "{\"id\":57,\"name\":\"Bancha\",\"typeOfTea\":\"green tea\"," +
                        "\"countryOfOrigin\":\"Japan\"}";
//        targetOutputTea =
//                "{\"id\":57,\"name\":\"Bancha\",\"typeOfTea\":\"green tea\"," +
//                        "\"countryOfOrigin\":\"Japan\",\"customers\":[]}";
//        targetOutputTea =
//                "{\"id\":57,\"name\":\"Bancha\",\"typeOfTea\":\"green tea\"," +
//                        "\"countryOfOrigin\":\"Japan\"}";
        targetOutputTea =
                "{\"name\":\"Bancha\",\"typeOfTea\":\"green tea\"," +
                        "\"countryOfOrigin\":\"Japan\"}";
        teaDto = new TeaDto("Bancha", "green tea", "Japan");
        //teaDto.setId(new Long(57));
    }

    @Test
    public void testGetAllTeas() throws Exception {
        // Mocking service
        when(teaService.getAllTeas()).thenReturn(teas);

        mockMvc.perform(get("/tea/v1/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$[0].id", is(57)))
                .andExpect(jsonPath("$[0].name", is("Bancha")))
                .andExpect(jsonPath("$[0].typeOfTea", is("green tea")))
                .andExpect(jsonPath("$[0].countryOfOrigin", is("Japan")))

                //.andExpect(jsonPath("$[1].id", is(31)))
                .andExpect(jsonPath("$[1].name", is("Pai Mu Tan")))
                .andExpect(jsonPath("$[1].typeOfTea", is("white tea")))
                .andExpect(jsonPath("$[1].countryOfOrigin", is("China")));
        verify(teaService, times(1)).getAllTeas();
    }

    @Test
    public void testGetTeasCustomersByTeaId() throws Exception {
        // Mocking service
        when(teaService.getTeasCustomersByTeaId(any(Long.class))).thenReturn(customers);

        mockMvc.perform(get("/tea/v1/getCustomersFavouringTea/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$[0].id", is(57)))
                .andExpect(jsonPath("$[0].firstName", is("Alexander")))
                .andExpect(jsonPath("$[0].lastName", is("Velky")))
                //.andExpect(jsonPath("$[1].id", is(31)))
                .andExpect(jsonPath("$[1].firstName", is("Charles")))
                .andExpect(jsonPath("$[1].lastName", is("the 4th")));
        verify(teaService, times(1)).getTeasCustomersByTeaId(any(Long.class));
    }

    @Test
    public void testAddTea() throws Exception {
        when(teaService.addTea(any(Tea.class))).thenReturn(teas.get(0));

        mockMvc.perform(post("/tea/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputTea))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputTea));
        verify(teaService, times(1)).addTea(any(Tea.class));
    }

    @Test
    public void testGetTeaById() throws Exception {
        // Mocking service
        when(teaService.getTeaById(any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(get("/tea/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("name", is("Bancha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
        verify(teaService, times(1)).getTeaById(any(Long.class));
    }

    @Test
    public void testDeleteTeaById() throws Exception {
        // Mocking service
        when(teaService.deleteTeaById(any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(delete("/tea/v1/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("name", is("Bancha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
    }

    @Test
    public void testUpdateTea() throws Exception {
        // Mocking service
        when(teaService.updateTea(any(Tea.class))).thenReturn(teas.get(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/tea/v1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputCustomer))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("id", is(57)))
                .andExpect(jsonPath("name", is("Bancha")))
                .andExpect(jsonPath("typeOfTea", is("green tea")))
                .andExpect(jsonPath("countryOfOrigin", is("Japan")));
        verify(teaService, times(1)).updateTea(any(Tea.class));
    }
/*
    @Test
    public void testAddCustomersTea() throws Exception {
        when(teaService.addCustomersTea(any(Long.class), any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(post("/customer/v1/addCustomersTeaById/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputTea));
        verify(teaService, times(1)).addCustomersTea(any(Long.class), any(Long.class));
    }

    @Test
    public void deleteCustomersTeaById() throws Exception {
        when(teaService.deleteCustomersTeaById(any(Long.class), any(Long.class))).thenReturn(teas.get(0));

        mockMvc.perform(delete("/customer/v1/deleteCustomersTeaById/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(targetOutputTea));
        verify(teaService, times(1)).deleteCustomersTeaById(any(Long.class), any(Long.class));
    }
*/
}
