package com.medtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 150, message = "Name is too long")
    private String name;

    @Size(max = 100, message = "Dose is too long")
    private String dose;

    @Size(max = 100, message = "Frequency is too long")
    private String frequency;

    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration must be at most 365 days")
    private Integer durationDays;

    private boolean antibiotic;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public boolean isAntibiotic() { return antibiotic; }
    public void setAntibiotic(boolean antibiotic) { this.antibiotic = antibiotic; }
}
