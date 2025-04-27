package com.juan.patient_managment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.juan.patient_managment.domain.entities.Patient;

import lombok.RequiredArgsConstructor;
import patient.events.PatientEvent;

/**
 * KafkaProducer is responsible for sending events to a Kafka topic.
 * It uses the KafkaTemplate to send serialized PatientEvent messages.
 */

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaProducer.class);

    public void sendEvent(Patient patient)
    {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_REGISTERED")
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending event to Kafka", e);
        }
    }
}
