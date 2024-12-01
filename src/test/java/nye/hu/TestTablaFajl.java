package nye.hu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestTablaFajl {
    private static final String TEST_FILE = "test_jatekallas.txt";

    @BeforeEach
    void setup() {
        // Töröljük a tesztfájlt, ha létezik
        File file = new File(TEST_FILE);
        if (file.exists()) {
            assertTrue(file.delete(), "Nem sikerült törölni a tesztfájlt.");
        }
    }

    @AfterEach
    void tearDown() {
        // Tesztfájl eltávolítása a tesztek után
        File file = new File(TEST_FILE);
        if (file.exists()) {
            assertTrue(file.delete(), "Nem sikerült törölni a tesztfájlt.");
        }
    }

    @Test
    void testMent() throws IOException {
        // Létrehozunk egy táblát és elmentjük
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("a", 'S');
        tabla.addKorong("b", 'P');
        tabla.addKorong("c", 'S');
        tabla.ment(TEST_FILE);

        // Ellenőrizzük, hogy a fájl létezik
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "A mentés sikertelen: a fájl nem jött létre.");

        // Ellenőrizzük a fájl tartalmát
        Tabla betoltottTabla = Tabla.betolt(TEST_FILE);
        assertEquals(6, betoltottTabla.getSorok());
        assertEquals(7, betoltottTabla.getOszlopok());
        assertEquals('S', betoltottTabla.getRacs()[5][0]);
        assertEquals('P', betoltottTabla.getRacs()[5][1]);
        assertEquals('S', betoltottTabla.getRacs()[5][2]);
    }

    @Test
    void testBetolt() throws IOException {
        // Létrehozunk egy tesztfájlt manuálisan
        try (java.io.PrintWriter writer = new java.io.PrintWriter(TEST_FILE)) {
            writer.println("6 7");
            writer.println(".......");
            writer.println(".......");
            writer.println(".......");
            writer.println(".......");
            writer.println(".......");
            writer.println("SPS....");
        }

        // Betöltjük a fájlt és ellenőrizzük az állapotot
        Tabla tabla = Tabla.betolt(TEST_FILE);
        assertEquals(6, tabla.getSorok());
        assertEquals(7, tabla.getOszlopok());
        assertEquals('S', tabla.getRacs()[5][0]);
        assertEquals('P', tabla.getRacs()[5][1]);
        assertEquals('S', tabla.getRacs()[5][2]);
    }

    @Test
    void testBetoltNemLetezoFajl() throws IOException {
        // Töröljük a fájlt, ha létezik
        File file = new File(TEST_FILE);
        if (file.exists()) {
            assertTrue(file.delete());
        }

        // Betöltés nem létező fájlból
        Tabla tabla = Tabla.betolt(TEST_FILE);
        assertNotNull(tabla, "Az üres tábla nem jött létre.");
        assertEquals(6, tabla.getSorok());
        assertEquals(7, tabla.getOszlopok());

        // Ellenőrizzük, hogy a tábla üres
        for (int i = 0; i < tabla.getSorok(); i++) {
            for (int j = 0; j < tabla.getOszlopok(); j++) {
                assertEquals('.', tabla.getRacs()[i][j]);
            }
        }
    }
}