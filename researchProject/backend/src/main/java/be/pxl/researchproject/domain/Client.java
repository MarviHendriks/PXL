package be.pxl.researchproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String homeAddress;

    private String deliveryAddress;
    private LocalDate movingDate;
    private String movingMonth;

    public Client(String name, String email, String phoneNumber, String homeAddress, String deliveryAddress, String movingMonth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.deliveryAddress = deliveryAddress;
        this.movingMonth = movingMonth;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getMovingDate() {
        return movingDate;
    }

    public void setMovingDate(LocalDate movingDate) {
        this.movingDate = movingDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovingMonth() {
        return movingMonth;
    }

    public void setMovingMonth(String movingMonth) {
        this.movingMonth = movingMonth;
    }
}
