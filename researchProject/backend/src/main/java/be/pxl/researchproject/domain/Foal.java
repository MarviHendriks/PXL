package be.pxl.researchproject.domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Foal {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double height;
    private String gender;
    private String stallion;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "foal_id")
    private List<Deworming> dewormings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id") //dit geeft de kolom een naam
    private Client client;

    public Foal(String name, LocalDate dateOfBirth, double height, String gender, String stallion) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender;
        this.stallion = stallion;
        this.dewormings = new ArrayList<>();
    }

    public Foal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStallion() {
        return stallion;
    }
    public void setStallion(String stallion) {
        this.stallion = stallion;
    }

    public void setDewormings(List<Deworming> dewormings) {
        this.dewormings = dewormings;
    }

    public List<Deworming> getDewormings() {
        return dewormings;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}