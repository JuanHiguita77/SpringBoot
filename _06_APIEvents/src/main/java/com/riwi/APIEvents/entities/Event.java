package com.riwi.APIEvents.entities;


import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "event")
@Data //Getter y setter and toString incluid
@AllArgsConstructor //Constructor full 
@NoArgsConstructor //Constructor void
public class Event 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private LocalDate date_event;
    private String ubication;
    private int capacity;
    
}
