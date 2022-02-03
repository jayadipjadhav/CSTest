package com.example.demo;

import com.example.demo.service.EventProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    EventProcessingService eventProcessingService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener
    public void onReady(ApplicationReadyEvent e) {
        eventProcessingService.processEvents("/Users/jayadipjadhav/Downloads/logfile.txt");
    }

}
