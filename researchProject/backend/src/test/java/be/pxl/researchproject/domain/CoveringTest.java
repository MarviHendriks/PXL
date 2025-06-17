package be.pxl.researchproject.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoveringTest {
    private static final LocalDate DATE = LocalDate.now();

    @Test
    public void coveringConstructorShouldSetEveryPropertyCorrectly(){
        Covering covering = new Covering(DATE);

        assertEquals(DATE, covering.getCoverDate());
    }

    @Test
    public void GettersAndSettersShouldWorkCorrectly(){
        Covering covering = new Covering();

        covering.setCoverDate(DATE);

        assertEquals(DATE, covering.getCoverDate());
    }

    @Test
    public void compareToMethodShouldReturnRightValue(){
        Covering covering = new Covering(DATE);

        assertEquals(2, covering.compareTo(new Covering(DATE.plusMonths(2))));
    }


}
