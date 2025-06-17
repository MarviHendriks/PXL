package be.pxl.researchproject.api.request;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

public record ClientRequest(String name, String email, String phoneNumber, String homeAddress, String deliveryAddress, String movingMonth, LocalDate movingDate) {
}
