package be.pxl.researchproject.api.response;

import java.time.LocalDate;

public class ClientDTO {

    private long id;
    private String name;
    private String email;

    private String phoneNumber;
    private String homeAddress;

    private String deliveryAddress;
    private LocalDate movingDate;
    private String movingMonth;

    public ClientDTO(long id, String name, String email, String phoneNumber, String homeAddress, String deliveryAddress, String movingMonth, LocalDate movingDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.deliveryAddress = deliveryAddress;
        this.movingMonth = movingMonth;
        this.movingDate = movingDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMovingMonth() {
        return movingMonth;
    }

    public void setMovingMonth(String movingMonth) {
        this.movingMonth = movingMonth;
    }
}
