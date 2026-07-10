import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class readFileTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void readAll() {
        GameList DummyList = new GameList();
        assertTrue(DummyList.readAll("Games"));
    }
}