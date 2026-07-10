import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class addGame {

    @BeforeEach
    void setUp() {
    }

    @Test
    void addGameTest() {

        java.time.LocalDate date= LocalDate.parse("1999-09-09");
        Game Dummy = new Game("Dummy","DDummy","DumbGenre", date, 100,100,0);
        GameList DummyList = new GameList();
        assertNotNull(DummyList.addGame(Dummy));
    }
}