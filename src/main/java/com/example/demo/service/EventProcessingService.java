package com.example.demo.service;

import com.example.demo.models.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventProcessingService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger
            = LoggerFactory.getLogger(EventProcessingService.class);

    @Transactional
    public void processEvents(String pathToLogFile){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToLogFile));
            Map<String, Event> eventMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            String line;
            logger.debug("Events processing started.");
            while ((line = bufferedReader.readLine()) != null) {
                Event event = mapper.readValue(line, Event.class);
                if (eventMap.containsKey(event.getId())) {
                    long duration = Math.abs(eventMap.get(event.getId()).getTimestamp() - event.getTimestamp());
                    // persists in database
                    event.setEventDuration(duration);

                    if(duration > 4) {
                        logger.info("Event {} took longer than 4ms.", event.getId());
                        event.setAlert(true);
                    } else {
                        event.setAlert(false);
                    }

                    logger.info("Event {} is saved.", event.getId());
                    //jdbc batch size set in application.properties file, so it will be a batch insert
                    entityManager.persist(event);

                    // remove from map
                    eventMap.remove(event.getId());
                } else
                    eventMap.put(event.getId(), event);
            }
            if(!eventMap.isEmpty())
                logger.info("Second entry not found for few events");
            logger.debug("Events processing finished.");
        } catch (IOException e) {
            logger.error("Error occurred ", e);
        }
    }
}
