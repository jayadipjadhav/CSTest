package com.example.demo;

import com.example.demo.service.EventProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EventProcessingServiceTests {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EventProcessingService employeeService() {
            return new EventProcessingService();
        }
    }

    @Autowired
    private EventProcessingService eventProcessingService;

    /*
    Test for IOException when incorrect file path given
     */
    @Test
    public void expectExceptionWhenIncorrectFilePathGiven() {

    }
    /*
    Test for when second entry not found in the log file
     */
    @Test
    public void secondEntryNotFoundForEvent(){

    }

    /*
    Tess to verify valid alert is set when event duration is more than 4ms.
     */
    @Test
    public void validAlertSet(){

    }




}
