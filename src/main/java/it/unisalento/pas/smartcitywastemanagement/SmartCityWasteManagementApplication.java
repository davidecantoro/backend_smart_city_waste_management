package it.unisalento.pas.smartcitywastemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "it.unisalento.pas.smartcitywastemanagement.repositories")
public class SmartCityWasteManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCityWasteManagementApplication.class, args);
    }

}
