package nye.hu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJatekos {

    @Test
    void testConstructorAndGetters() {
        // Létrehozunk egy játékost
        Jatekos jatekos = new Jatekos("TesztJatekos", 'S');

        // Ellenőrizzük a konstruktor működését
        assertEquals("TesztJatekos", jatekos.getNev(), "A játékos neve nem megfelelő.");
        assertEquals('S', jatekos.getKorong(), "A játékos korongja nem megfelelő.");
    }
}
