package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
