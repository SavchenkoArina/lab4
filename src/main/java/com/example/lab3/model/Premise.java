package com.example.lab3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "premises")
public class Premise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double area;

    // Связь с владельцем (много помещений -> один пользователь)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Premise() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}