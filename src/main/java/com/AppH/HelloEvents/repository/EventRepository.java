package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {


    // Rechercher les événements par titre (partie insensible à la casse), avec pagination
    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
