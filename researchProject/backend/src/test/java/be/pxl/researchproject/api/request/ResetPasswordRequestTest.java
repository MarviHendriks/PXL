package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordRequestTest {
    private static final String newPassword = "newPassword";
    private static final String passwordConfirmation = "passwordConfirmation";

    @Test
    public void ResetPasswordRequestConstructorShouldSetEveryPropertyCorrectly(){
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(newPassword, passwordConfirmation);
        assertEquals(newPassword, resetPasswordRequest.newPassword());
        assertEquals(passwordConfirmation, resetPasswordRequest.passwordConfirmation());
    }
}
