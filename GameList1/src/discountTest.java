import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class discountTest {

    @Test
    void applyDiscount() {
        java.time.LocalDate date= LocalDate.parse("1999-09-09");
        Game Dummy = new Game("Dummy","DDummy","DumbGenre", date, 100,500,0);
        assertEquals(375,Dummy.applyDiscount(25));
    }

}