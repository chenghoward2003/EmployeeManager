package com.example.demo;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);

			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

	@Bean
	public CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
		return args -> {
			if (employeeRepository.count() == 0) {
				employeeRepository.save(new Employee("Lebron", "James", "GloriousKing@nba.com"));
				System.out.println("Sample employees added to the database.");
			} else {
				System.out.println("Database already initialized with employees.");
			}
		};
	}
}
