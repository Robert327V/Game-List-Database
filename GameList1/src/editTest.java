import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class editTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void editGame() {
        java.time.LocalDate date= LocalDate.parse("1999-09-09");
        Game Dummy = new Game("Dummy", "Dumber","Dumtion", date, 100, 100, 0);

        String newName = "Bob";
        String Director = "Bublababa";
        String genre = "newGenre";
        date= LocalDate.parse("2005-05-05");
        float newOld = 1900;
        float newNew = 2500;
        int newDisc = 50;

        Dummy.editGame(newName, Director,genre, date, newOld, newNew, newDisc);

        assertEquals(newName,Dummy.name);
        assertEquals(Director,Dummy.director);
        assertEquals(genre,Dummy.genre);
        assertEquals(date, Dummy.release);
        assertEquals(newOld,Dummy.origPrice);
        assertEquals(newNew,Dummy.currPrice);
        assertEquals(newDisc,Dummy.discount);

    }
}