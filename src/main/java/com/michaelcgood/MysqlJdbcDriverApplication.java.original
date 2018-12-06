package com.michaelcgood;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import com.michaelcgood.dao.CustomerRepository;
import com.michaelcgood.dao.TeaRepository;
import com.michaelcgood.model.Customer;
//import javafx.application.Application;
import com.michaelcgood.model.Tea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.michaelcgood.dao.SystemRepository;
import com.michaelcgood.model.SystemExample;

@SpringBootApplication
@EnableJpaRepositories("com.michaelcgood.dao")
public class MysqlJdbcDriverApplication {//} implements CommandLineRunner {

	@Autowired
	DataSource dataSource;

	@Autowired
	SystemRepository systemRepository;

	@Autowired
    CustomerRepository customerRepository;

	//private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final Logger log = LoggerFactory.getLogger(MysqlJdbcDriverApplication.class);

	public static void main(String[] args) {
        //SpringApplication.run(MysqlJdbcDriverApplication.class, args);
        SpringApplication.run(MysqlJdbcDriverApplication.class);
	}

	//
	//@Override
	//public void run(String... args) throws Exception {
	    /*
		// add windows server
		SystemExample systemExampleWindows = new SystemExample();
		systemExampleWindows.setName("Windows Server 2012 R2");
		systemExampleWindows.setLastaudit("2017-08-11");
		systemRepository.save(systemExampleWindows);
		// add rhel
		SystemExample systemExampleRhel = new SystemExample();
		systemExampleRhel.setName("RHEL 7");
		systemExampleRhel.setLastaudit("2017-07-21");
		systemRepository.save(systemExampleRhel);
		// add solaris
		SystemExample systemExampleSolaris = new SystemExample();
		systemExampleSolaris.setName("Solaris 11");
		systemExampleSolaris.setLastaudit("2017-08-13");
		systemRepository.save(systemExampleSolaris);
		*/
	    /*
		Iterable<SystemExample> systemlist = systemRepository.findAll();
		System.out.println("here are system count: " + systemlist.toString());
		for(SystemExample systemExample:systemlist){
			System.out.println("Here is a system: " + systemExample.toString());
		}
		*/
	//    demo(customerRepository);
	//}
	//

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, TeaRepository teaRepository) throws Exception {
    //public void demo(CustomerRepository repository) throws Exception {
        return (args) -> {
            // save a couple of customers
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
                System.out.println("Here is a customer: "+customer.toString());
            }
            log.info("");

            /*
            // fetch an individual customer by ID
            repository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
            */
        };
    }


}
