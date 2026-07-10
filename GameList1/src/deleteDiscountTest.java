import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class deleteDiscountTest {

    @Test
    void deleteDiscount() {
        java.time.LocalDate date= LocalDate.parse("1999-09-09");
        Game Dummy = new Game("Dummy","DDummy","DumbGenre", date, 100,375,25);
        assertEquals(500,Dummy.deleteDiscount());
    }
}