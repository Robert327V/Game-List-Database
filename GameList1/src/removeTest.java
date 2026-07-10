import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class removeTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void removeGame() {
        java.time.LocalDate date= LocalDate.parse("1999-09-09");
        Game Dummy = new Game("Dummy","DDummy","DumbGenre", date, 100,100,0);
        GameList DummyList = new GameList();
        DummyList.addGame(Dummy);
        assertTrue(DummyList.removeGame(Dummy));
    }
}