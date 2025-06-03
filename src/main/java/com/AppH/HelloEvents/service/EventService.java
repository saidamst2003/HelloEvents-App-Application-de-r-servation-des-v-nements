package com.AppH.HelloEvents.service;


import com.AppH.HelloEvents.dto.EventDtO;
import com.AppH.HelloEvents.model.Event;
import com.AppH.HelloEvents.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventDtO eventDto) {
        Event event = new Event(
                eventDto.getTitle(),
                eventDto.getDescription(),
                eventDto.getLocation(),
                eventDto.getEventDate()
        );
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Page<Event> searchEventsByTitle(String title, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Event updateEvent(Integer id, EventDtO eventDto) throws Exception {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new Exception("Event not found"));

        existingEvent.setTitle(eventDto.getTitle());
        existingEvent.setDescription(eventDto.getDescription());
        existingEvent.setLocation(eventDto.getLocation());
        existingEvent.setEventDate(eventDto.getEventDate());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }
}
