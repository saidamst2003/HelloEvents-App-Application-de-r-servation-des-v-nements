package com.AppH.HelloEvents.dto;
import com.AppH.HelloEvents.model.Booking;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingDto {



        private Long id;
        private String bookingReference;

        @NotNull(message = "L'ID de l'événement est obligatoire")
        private Long eventId;

        private Long userId;
        private String eventTitle;
        private String userEmail;

        @NotNull(message = "Le nombre de tickets est obligatoire")
        @Positive(message = "Le nombre de tickets doit être positif")
        private Integer numberOfTickets;

        private BigDecimal totalAmount;
        private Booking.BookingStatus status;
        private LocalDateTime bookingDate;
        private LocalDateTime cancelledAt;
        private String cancellationReason;
        private LocalDateTime createdAt;


    }


