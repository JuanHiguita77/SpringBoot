package com.riwi.daily_tasks.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

//Significa que sera una entidad mapeable
@Entity
//Permite dar configuraciones a la tabla
@Table(name = "task")//Nombre de la tabla
public class Task 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 255, nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDate creation_date;

    @Column(nullable = false)
    private LocalTime creation_hour;

    @Column(length = 50, nullable = false)
    private String state;
    
    public Task() {
    }

    public Task(Long id, String title, String description, LocalDate creation_date, LocalTime creation_hour,
            String state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creation_date = creation_date;
        this.creation_hour = creation_hour;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public LocalTime getCreation_hour() {
        return creation_hour;
    }

    public void setCreation_hour(LocalTime creation_hour) {
        this.creation_hour = creation_hour;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "task [id=" + id + ", title=" + title + ", creation_date=" + creation_date + ", creation_hour="
                + creation_hour + ", state=" + state + "]";
    }   
}
