package com.michaelcgood.app.service;

import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import com.michaelcgood.service.TeaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class TeaServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private TeaRepository teaRepositoryMock;
    @InjectMocks
    private TeaService teaService;

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
        teas.add(tea0);
        tea1 = new Tea("Pai Mu Tan", "white tea", "China");
        tea1.setId(id1);
        teas.add(tea1);
        tea2 = new Tea("Sencha", "green tea", "Japan");
        tea2.setId(id2);
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
    public void testAddTea() {
        Tea result = teaService.addTea(teas.get(0));

        assertThat(result.getId(), is(id0));
        assertThat(result.getName(), is("Bancha"));
        assertThat(result.getTypeOfTea(),is("green tea"));
        assertThat(result.getCountryOfOrigin(),is("Japan"));
        assertNotNull(result.getCustomers());
    }

    //    public Customer getCustomerById(Long id)
    @Test
    public void testGetTeaById() {
        when(teaRepositoryMock.findById(id0)).thenReturn(Optional.of(teas.get(0)));

        Tea tea = teaService.getTeaById(id0);
        assertThat(tea.getId(), is(id0));
        assertThat(tea.getName(), is("Bancha"));
        assertThat(tea.getTypeOfTea(),is("green tea"));
        assertThat(tea.getCountryOfOrigin(),is("Japan"));
        assertNotNull(tea.getCustomers());
    }

    //    public Customer deleteCustomerById(Long id)
    @Test
    public void testDeleteTeaById() {
        when(teaRepositoryMock.findById(id0)).thenReturn(Optional.of(teas.get(0)));

        Tea actual = teaService.deleteTeaById(id0);

        verify(teaRepositoryMock, times(1)).findById(id0);
        verify(teaRepositoryMock, times(1)).deleteById(id0);
        assertThat(actual, is(teas.get(0)));
    }

    //    public Customer updateCustomer(Customer customer)
    @Test
    public void testUpdateTea() {
        //testing successful update that means customer being updated exists
        when(teaRepositoryMock.existsById(id2)).thenReturn(true);
        tea2.setCountryOfOrigin("JAPAN");
        Tea result = teaService.updateTea(tea2);

        assertThat(result.getName(), is("Sencha"));
        assertThat(result.getTypeOfTea(),is("green tea"));
        assertThat(result.getCountryOfOrigin(),is("JAPAN"));
        assertNotNull(result.getCustomers());
        assertThat(result.getId(), is(id2));
    }
/*
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
*/
    //    public List<Tea> getCustomersFavouriteTeasByCustomerId(Long id)
    @Test
    public void testGetTeasCustomersByTeaId() {
        when(teaRepositoryMock.findById(id0)).thenReturn(Optional.of(teas.get(0)));

        //Customer customer = customerService.getCustomerById(id0);
        List<Customer> teaCustomers = teaService.getTeasCustomersByTeaId(id0);

        assertThat(teaCustomers.size(), is(1));
        assertThat(teaCustomers.get(0).getFirstName(), is("Alexander"));
        assertThat(teaCustomers.get(0).getLastName(),is("Velky"));
    }

    //    public List<Customer> getAllCustomers()
    @Test
    public void testGetAllTeas() {
        List<Tea> target = new ArrayList<Tea>();
        target.add(teas.get(0));
        target.add(teas.get(1));
        when(teaRepositoryMock.findAll()).thenReturn(target);

        List<Tea> customers = teaService.getAllTeas();
        assertThat(customers.size(), is(2));

        assertThat(customers.get(0).getId(), is(id0));
        assertThat(customers.get(0).getName(), is("Bancha"));
        assertThat(customers.get(0).getTypeOfTea(),is("green tea"));
        assertThat(customers.get(0).getCountryOfOrigin(),is("Japan"));
        assertThat(customers.get(0).getCustomers().size(), is(1));

        assertThat(customers.get(1).getId(), is(id1));
        assertThat(customers.get(1).getName(), is("Pai Mu Tan"));
        assertThat(customers.get(1).getTypeOfTea(),is("white tea"));
        assertThat(customers.get(1).getCountryOfOrigin(),is("China"));
        assertThat(customers.get(1).getCustomers().size(), is(1));
    }
}
