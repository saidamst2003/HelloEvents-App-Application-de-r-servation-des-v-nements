package com.AppH.HelloEvents.controller;

import com.AppH.HelloEvents.dto.EventDtO;
import com.AppH.HelloEvents.model.Event;
import com.AppH.HelloEvents.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

        @Autowired
        private EventService eventService;

        @PostMapping
        public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDtO eventDTO) {
            return ResponseEntity.ok(eventService.createEvent(eventDTO));
        }

        @GetMapping
        public List<Event> getAllEvents() {
            return eventService.getAllEvents();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Event> getEventById(@PathVariable Long id) {
            return ResponseEntity.ok(eventService.getEventById(id));
        }



    }
