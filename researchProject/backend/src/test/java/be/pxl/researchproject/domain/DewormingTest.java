package be.pxl.researchproject.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DewormingTest {
    private static final Long ID = 1L;
    private static final String TITLE = "deworming 1";
    private static final boolean DONE = true;
    private static final LocalDate DATE = LocalDate.now();

    @Test
    public void dewormingConstructorShouldSetEveryPropertyCorrectly(){
        Deworming deworming = new Deworming(TITLE, DONE, DATE);

        assertEquals(TITLE, deworming.getTitle());
        assertEquals(DONE, deworming.isDone());
        assertEquals(DATE, deworming.getDate());
    }

    @Test
    public void GettersAndSettersShouldWorkCorrectly(){
        Deworming deworming = new Deworming();

        deworming.setId(ID);
        deworming.setTitle(TITLE);
        deworming.setDone(DONE);
        deworming.setDate(DATE);

        assertEquals(ID, deworming.getId());
        assertEquals(TITLE, deworming.getTitle());
        assertEquals(DONE, deworming.isDone());
        assertEquals(DATE, deworming.getDate());
    }
}
