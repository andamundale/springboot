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

@SpringBootApplication
@EnableJpaRepositories("com.michaelcgood.repository")
public class MysqlJdbcDriverApplication {

    private static final Logger log = LoggerFactory.getLogger(MysqlJdbcDriverApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(MysqlJdbcDriverApplication.class);
	}

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, TeaRepository teaRepository) {
    //public void demo(CustomerRepository repository) throws Exception {
        return (args) -> {
            // save a couple of customers and teas
            System.out.println("Preloading database.");
            customerRepository.save(new Customer("Jack", "Bauer"));
            customerRepository.save(new Customer("Chloe", "O'Brian"));
            customerRepository.save(new Customer("Kim", "Bauer"));
            customerRepository.save(new Customer("David", "Palmer"));
            customerRepository.save(new Customer("Michelle", "Dessler"));
            teaRepository.save(new Tea("Sencha", "green tea", "Japan"));
            teaRepository.save(new Tea("Bancha", "green tea", "Japan"));
            teaRepository.save(new Tea("Baihao Yinzhen", "white tea", "China"));
            teaRepository.save(new Tea("Keemun", "black tea", "China"));

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
