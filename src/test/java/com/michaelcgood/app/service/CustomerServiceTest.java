package com.michaelcgood.app.service;

import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import com.michaelcgood.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.isNull;


@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private TeaRepository teaRepositoryMock;
    @InjectMocks
    private CustomerService customerService;

    private List<Customer> customers;
    private List<Tea> teas;
    private Long id0, id1, id2;
    private Tea tea2;
/**/
    @Before
    //@BeforeEach
    public void setup() {
        teas = new ArrayList<Tea>();
        customers = new ArrayList<Customer>();
        List<Customer> teaCustomers = new ArrayList<Customer>();
        Tea tea0, tea1;
        Customer customer0, customer1;

        id0 = new Long(12);
        id1 = new Long(13);
        id2 = new Long(14);
        tea0 = new Tea("Bancha", "green tea", "Japan");
        tea0.setId(id0);
        tea1 = new Tea("Pai Mu Tan", "white tea", "China");
        tea1.setId(id1);
        tea2 = new Tea("Sencha", "green tea", "Japan");
        tea2.setId(id2);
        teas.add(tea0);
        teas.add(tea1); //id2 pridame neskor
        customer0 = new Customer("Alexander", "Velky");
        customer0.setId(id0);
        customer0.setFavouriteTeas(teas);
        teaCustomers.add(customer0);
        teas.get(0).setCustomers(teaCustomers);
        teas.get(1).setCustomers(teaCustomers);
        customer1 = new Customer("Charles", "the 4th");
        customer1.setId(id1);
        //customer1.setFavouriteTeas(teas);
        customers.add(customer0);
        customers.add(customer1);
    }
/**/
//    public Customer addCustomer(Customer customer)
    @Test
    public void testAddCustomer() {
        Customer result = customerService.addCustomer(customers.get(0));

        assertThat(result.getFirstName(), is("Alexander"));
        assertThat(result.getLastName(),is("Velky"));
//        assertThat(result.getFavouriteTeas(), isNotNull());
        assertNotNull(result.getFavouriteTeas());
        assertThat(result.getId(), is(id0));
    }

//    public Customer getCustomerById(Long id)
    @Test
    public void testGetCustomerById() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));

        Customer customer = customerService.getCustomerById(id0);
        assertThat(customer.getFirstName(), is("Alexander"));
        assertThat(customer.getLastName(),is("Velky"));
        assertNotNull(customer.getFavouriteTeas());
        assertThat(customer.getId(), is(id0));
    }

//    public Customer deleteCustomerById(Long id)
    @Test
    public void testDeleteCustomerById() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));

        Customer actual = customerService.deleteCustomerById(id0);

        verify(customerRepositoryMock, times(1)).findById(id0);
        verify(customerRepositoryMock, times(1)).deleteById(id0);
        assertThat(actual, is(customers.get(0)));
    }

//    public Customer updateCustomer(Customer customer)
    @Test
    public void testUpdateCustomer() {
        //testing successful update that means customer being updated exists
        when(customerRepositoryMock.existsById(id0)).thenReturn(true);
        Customer customrUpdate = customers.get(0);
        customrUpdate.setLastName("Macedonsky");
        Customer result = customerService.updateCustomer(customers.get(0));

        assertThat(result.getFirstName(), is("Alexander"));
        assertThat(result.getLastName(),is("Macedonsky"));
//        assertThat(result.getFavouriteTeas(), isNotNull());
        assertNotNull(result.getFavouriteTeas());
        assertThat(result.getId(), is(id0));
    }

//    public Tea addCustomersTea(Long customerId, Long teaId)
    @Test
    public void testAddCustomersTea() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));
        when(teaRepositoryMock.findById(id2)).thenReturn(Optional.of(tea2));
        Tea result = customerService.addCustomersTea(id0,  id2);

        assertThat(result.getId(), is(id2));
        assertThat(result.getName(), is("Sencha"));
        assertThat(result.getTypeOfTea(), is("green tea"));
        assertThat(result.getCountryOfOrigin(),is("Japan"));
        assertThat(result.getCustomers().size(), is(1));
        assertThat(customers.get(0).getFavouriteTeas().size(), is(3));
    }

//    public Tea deleteCustomersTeaById(Long customerId, Long teaId)
    @Test
    public void testDeleteCustomersTeaById() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));
        when(teaRepositoryMock.findById(id0)).thenReturn(Optional.of(teas.get(0)));
        Tea result = customerService.deleteCustomersTeaById(id0,  id0);

        assertThat(result.getId(), is(id0));
        assertThat(result.getName(), is("Bancha"));
        assertThat(result.getTypeOfTea(), is("green tea"));
        assertThat(result.getCountryOfOrigin(),is("Japan"));
        assertThat(result.getCustomers().size(), is(0));
        assertThat(customers.get(0).getFavouriteTeas().size(), is(1));
//        assertThat(result.getFavouriteTeas(), isNotNull());
    }

//    public List<Tea> getCustomersFavouriteTeasByCustomerId(Long id)
    @Test
    public void testGetCustomersFavouriteTeasByCustomerId() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));

        //Customer customer = customerService.getCustomerById(id0);
        List<Tea> favTeas = customerService.getCustomersFavouriteTeasByCustomerId(id0);

        assertThat(favTeas.size(), is(2));
        assertThat(favTeas.get(0).getName(), is("Bancha"));
        assertThat(favTeas.get(0).getTypeOfTea(),is("green tea"));
        assertThat(favTeas.get(0).getCountryOfOrigin(),is("Japan"));
        assertThat(favTeas.get(1).getName(), is("Pai Mu Tan"));
        assertThat(favTeas.get(1).getTypeOfTea(),is("white tea"));
        assertThat(favTeas.get(1).getCountryOfOrigin(),is("China"));
    }

//    public List<Customer> getAllCustomers()
    @Test
    public void testGetAllCustomers() {
        List<Customer> target = new ArrayList<Customer>();
        target.add(customers.get(0));
        target.add(customers.get(1));
        when(customerRepositoryMock.findAll()).thenReturn(target);

        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size(), is(2));

        assertThat(customers.get(0).getFirstName(), is("Alexander"));
        assertThat(customers.get(0).getLastName(),is("Velky"));
        assertNotNull(customers.get(0).getFavouriteTeas());
        assertThat(customers.get(0).getId(), is(id0));

        assertThat(customers.get(1).getFirstName(), is("Charles"));
        assertThat(customers.get(1).getLastName(),is("the 4th"));
        assertNull(customers.get(1).getFavouriteTeas());
        assertThat(customers.get(1).getId(), is(id1));
    }
}
