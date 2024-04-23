package com.riwi.APIEvents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.APIEvents.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String>
{}
