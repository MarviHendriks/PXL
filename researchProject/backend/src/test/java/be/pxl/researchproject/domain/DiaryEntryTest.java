package be.pxl.researchproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiaryEntryTest {
    private static final String ENTRY = "an entry";
    private static final LocalDateTime ENTRYDATE = LocalDateTime.now();

    @Test
    public void diaryEntryConstructorShouldSetEveryPropertyCorrectly(){
        DiaryEntry diaryEntry = new DiaryEntry(ENTRY, ENTRYDATE);

        assertEquals(ENTRY, diaryEntry.getEntry());
        assertEquals(ENTRYDATE, diaryEntry.getEntryDate());
    }

    @Test
    public void GettersAndSettersShouldWorkCorrectly(){
        DiaryEntry diaryEntry = new DiaryEntry();

        diaryEntry.setEntry(ENTRY);
        diaryEntry.setEntryDate(ENTRYDATE);

        assertEquals(ENTRY, diaryEntry.getEntry());
        assertEquals(ENTRYDATE, diaryEntry.getEntryDate());
    }
}
