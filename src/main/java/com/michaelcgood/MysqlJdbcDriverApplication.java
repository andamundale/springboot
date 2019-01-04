package com.michaelcgood;

import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import com.michaelcgood.model.Customer;
//import javafx.application.Application;
import com.michaelcgood.model.Tea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.michaelcgood.repository")
public class MysqlJdbcDriverApplication {

// TODO    vycistit JSON - DTO (preferovane) / json ignore
// TODO    put/delete/post na tej istej ceste
// TODO    get na tej istej ceste pripadne s id .../v/{id}
// TODO
// TODO    testy unit a integracne
// TODO
// TODO    navratove hodnoty pri chybach neskor

// TODO    //priklad integracneho testu
// TODO
// TODO    public void postAndGetRssForUser() throws Exception {
// TODO        prepareRequest();
// TODO
// TODO        mockMvc.perform(MockMvcRequestBuilders.get("/channels/v1").param("locale", CHANNEL_LOCALE).header("userAuthInfo", "user1"))
// TODO                .andExpect(MockMvcResultMatchers.status().isOk())
// TODO                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.equalTo("mytitle")))
// TODO                .andExpect(MockMvcResultMatchers.jsonPath("$[0].logoUrl", Matchers.equalTo("http://mylogo.com")))
// TODO                .andReturn();
// TODO    }
// TODO
    
    private static final Logger log = LoggerFactory.getLogger(MysqlJdbcDriverApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(MysqlJdbcDriverApplication.class);
	}

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, TeaRepository teaRepository) {
    //public void demo(CustomerRepository repository) throws Exception {
        return (args) -> {
            // save a couple of customers and teas
            List<Long> customers = new ArrayList<Long>();
            List<Long> teas = new ArrayList<Long>();
            Tea tea0, tea1;
            Customer customer0;
            System.out.println("Preloading database.");
            customerRepository.save(new Customer("Jack", "Bauer"));
            customerRepository.save(new Customer("Chloe", "O'Brian"));
            customerRepository.save(new Customer("Kim", "Bauer"));
            customerRepository.save(new Customer("David", "Palmer"));
            customerRepository.save(new Customer("Michelle", "Dessler"));
            customer0 = customerRepository.save(new Customer("Charles", "the 4th"));
            //System.out.println(customer0);
            teaRepository.save(new Tea("Sencha", "green tea", "Japan"));
            teaRepository.save(new Tea("Bancha", "green tea", "Japan"));
            tea0 = teaRepository.save(new Tea("Baihao Yinzhen", "white tea", "China"));
            //System.out.println(tea0);
            tea1 = teaRepository.save(new Tea("Keemun", "black tea", "China"));
            //System.out.println(tea1);
            customer0.getFavouriteTeas().add(tea0);
            customer0.getFavouriteTeas().add(tea1);
            tea0.getCustomers().add(customer0);
            tea1.getCustomers().add(customer0);
            customer0 = customerRepository.save(customer0);
            //System.out.println(customer0);
            tea0 = teaRepository.save(tea0);
            //System.out.println(tea0);
            tea1 = teaRepository.save(tea1);
            //System.out.println(tea1);

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : customerRepository.findAll()) {
                log.info(customer.toString());
                System.out.println("Here is a customer: " + customer.toString());
            }
            log.info("");

            // fetch all teas
            log.info("Teas found with findAll():");
            log.info("-------------------------------");
            for (Tea tea : teaRepository.findAll()) {
                log.info(tea.toString());
                System.out.println("Here is a tea: " + tea.toString());
            }
            log.info("");
        };
    }
}
