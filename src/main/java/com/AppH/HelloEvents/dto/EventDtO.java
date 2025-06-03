package com.AppH.HelloEvents.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EventDtO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String category;

    @Min(1)
    private int availableSeats;

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotBlank String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank String location) {
        this.location = location;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        this.date = date;
    }

    public @NotBlank String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank String category) {
        this.category = category;
    }

    public @Min(1) int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(@Min(1) int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
