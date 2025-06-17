package be.pxl.researchproject.api.request;

public record ResetPasswordRequest(String newPassword, String passwordConfirmation){
}
