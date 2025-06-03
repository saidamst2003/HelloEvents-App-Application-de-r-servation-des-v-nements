package com.AppH.HelloEvents.service;

import com.AppH.HelloEvents.dto.EventDtO;
import com.AppH.HelloEvents.model.Event;
import com.AppH.HelloEvents.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventDtO dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setCategory(dto.getCategory());
        event.setAvailableSeats(dto.getAvailableSeats());
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event updateEvent(Long id, EventDtO dto) {
        Event existing = getEventById(id);
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setLocation(dto.getLocation());
        existing.setDate(dto.getDate());
        existing.setCategory(dto.getCategory());
        existing.setAvailableSeats(dto.getAvailableSeats());
        return eventRepository.save(existing);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
