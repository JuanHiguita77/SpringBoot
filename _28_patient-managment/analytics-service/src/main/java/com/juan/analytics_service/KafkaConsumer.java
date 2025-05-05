package com.juan.analytics_service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;

import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaConsumer.class);

    //Kafka listener for the "patient" topic
    // The groupId is used to identify the consumer group that this listener belongs to
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) 
    {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            //perform any necessary processing on the event
            log.info("Received event: [Patient={}, PatientName={}, PatientEmail={}]" + patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            log.error("Error parsing the event", e);
        }   

        // Process the incoming message
        System.out.println("Received message: " + event);
    }
}
