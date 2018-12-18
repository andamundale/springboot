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
    private Long id0, id1;
/**/
    @Before
    //@BeforeEach
    public void setup() {
        id0 = new Long(12);
        Customer customer0 = new Customer("Alexander", "Velky");
        customer0.setId(id0);
        id1 = new Long(13);
        Customer customer1 = new Customer("Charles", "the 4th");
        customer1.setId(id1);
        customers = new ArrayList<Customer>();
        customers.add(customer0);
        customers.add(customer1);
    }
/**/
//    public Customer addCustomer(Customer customer)
    @Test
    public void testAddCustomer() {
        /*
        Long id = new Long(57);
        Customer customer = new Customer("Alexander", "Velky");
        customer.setId(id);
        */
        System.out.println(customers.get(0).toString());
        Customer result = customerService.addCustomer(customers.get(0));

        //ArgumentCaptor<Customer> customerArgument = ArgumentCaptor.forClass(Customer.class);
        //verify(customerRepositoryMock, times(1)).save(customerArgument.capture());
        //verify(customerRepositoryMock, times(1)).save(customers.get(0));
        //verifyNoMoreInteractions(customerRepositoryMock);
        //Customer model = customerArgument.getValue();

        assertThat(result.getFirstName(), is("Alexander"));
        assertThat(result.getLastName(),is("Velky"));
        assertNull(result.getFavouriteTeas());
        assertThat(result.getId(), is(id0));
    }

//    public Customer getCustomerById(Long id)
    @Test
    public void testGetCustomerById() {
        /*
        Long id = new Long(57);
        Customer customer = new Customer("Alexander", "Velky");
        targetResult.setId(id);
        */
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));

        Customer customer = customerService.getCustomerById(id0);
        assertThat(customer.getFirstName(), is("Alexander"));
        assertThat(customer.getLastName(),is("Velky"));
        //assertThat(customer.getFavouriteTeas(), is(new ArrayList<Tea>()));
        assertNull(customer.getFavouriteTeas());
        assertThat(customer.getId(), is(id0));
    }

//    public Customer deleteCustomerById(Long id)
    @Test
    public void testDeleteCustomerById() {
        when(customerRepositoryMock.findById(id0)).thenReturn(Optional.of(customers.get(0)));

        Customer actual = customerService.deleteCustomerById(id0);

        verify(customerRepositoryMock, times(1)).findById(id0);
        verify(customerRepositoryMock, times(1)).deleteById(id0);
        //verifyNoMoreInteractions(customerRepositoryMock);

        assertThat(actual, is(customers.get(0)));
    }

//    public Customer updateCustomer(Customer customer)
    @Test
    public void testUpdateCustomer() {}

//    public Tea addCustomersTea(Long customerId, Long teaId)
    @Test
    public void testAddCustomersTea() {}

//    public Tea deleteCustomersTeaById(Long customerId, Long teaId)
    @Test
    public void testDeleteCustomersTeaById() {}

//    public List<Tea> getCustomersFavouriteTeasByCustomerId(Long id)
    @Test
    public void testGetCustomersFavouriteTeasByCustomerId() {}

//    public List<Customer> getAllCustomers()
    @Test
    public void testGetAllCustomers() {
        /*
        Long id0 = new Long(57);
        Customer customer0 = new Customer("Alexander", "Velky");
        customer0.setId(id0);
        Long id1 = new Long(31);
        Customer customer1 = new Customer("Charles", "the 4th");
        customer1.setId(id1);
        */
        List<Customer> target = new ArrayList<Customer>();
        target.add(customers.get(0));
        target.add(customers.get(1));
        when(customerRepositoryMock.findAll()).thenReturn(target);

        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size(), is(2));

        assertThat(customers.get(0).getFirstName(), is("Alexander"));
        assertThat(customers.get(0).getLastName(),is("Velky"));
        //assertThat(customers.get(0).getFavouriteTeas(), is(new ArrayList<Tea>()));
        assertNull(customers.get(0).getFavouriteTeas());
        assertThat(customers.get(0).getId(), is(id0));

        assertThat(customers.get(1).getFirstName(), is("Charles"));
        assertThat(customers.get(1).getLastName(),is("the 4th"));
        //assertThat(customers.get(1).getFavouriteTeas(), is(new ArrayList<Tea>()));
        assertNull(customers.get(1).getFavouriteTeas());
        assertThat(customers.get(1).getId(), is(id1));
    }
}
