package com.AppH.HelloEvents.dto;

import com.AppH.HelloEvents.model.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Data
    public class EventDtO{
        private Long id;

        @NotBlank(message = "Le titre est obligatoire")
        private String title;

        private String description;
        private String shortDescription;

        @NotNull(message = "La date de début est obligatoire")
        private LocalDateTime startDate;

        @NotNull(message = "La date de fin est obligatoire")
        private LocalDateTime endDate;

        @NotBlank(message = "Le lieu est obligatoire")
        private String location;

        private String address;
        private String imageUrl;

        @Positive(message = "Le prix doit être positif")
        private BigDecimal price;

        @Positive(message = "La capacité maximale doit être positive")
        private Integer maxCapacity;

        private Integer availableSeats;

        @NotBlank(message = "La catégorie est obligatoire")
        private String category;

        private Event.EventStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }