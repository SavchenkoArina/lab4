package com.example.lab3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meter_readings")
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Column(nullable = false)
    private String period; // период (например, "2026-10")

    @Column(name = "reading_value", nullable = false)
    private Double value; // значение показания

    @Column(nullable = false)
    private String verificationStatus; // статус проверки

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // автофиксация времени сервером
    }

    public MeterReading() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Meter getMeter() { return meter; }
    public void setMeter(Meter meter) { this.meter = meter; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}