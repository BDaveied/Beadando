package nye.hu;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestGyozelemKezelo {
    private static final String TEST_FILE = "test_gyozelem.txt";
    private GyozelemKezelo gyozelemKezelo;

    @BeforeEach
    void setUp() {
        // Példányosítjuk a GyozelemKezelo osztályt, és tesztfájlt állítunk be
        gyozelemKezelo = new GyozelemKezelo() {
            @Override
            protected String getFileName() {
                return TEST_FILE;
            }
        };

        // Előző tesztfájl törlése, ha létezik
        File file = new File(TEST_FILE);
        if (file.exists() && !file.delete()) {
            System.err.println("Nem sikerült törölni a tesztfájlt a setUp során.");
        }
    }

    @AfterEach
    void tearDown() {
        // Tesztfájl törlése a tesztek után
        File file = new File(TEST_FILE);
        if (file.exists() && !file.delete()) {
            System.err.println("Nem sikerült törölni a tesztfájlt a tearDown során.");
        }
    }

    @Test
    void testGyozelemBetoltesUres() {
        // Ellenőrizzük, hogy üres fájl esetén a betöltött győzelmi táblázat üres legyen
        Map<String, Integer> gyozelmek = gyozelemKezelo.betoltGyozelem();
        assertTrue(gyozelmek.isEmpty(), "Üres győzelmi táblázatot kellett volna betölteni.");
    }

    @Test
    void testGyozelemMentEsBetoltes() {
        // Győzelmek létrehozása és mentése
        Map<String, Integer> gyozelmek = new HashMap<>();
        gyozelmek.put("Jatekos1", 5);
        gyozelmek.put("Jatekos2", 3);

        gyozelemKezelo.mentGyozelem(gyozelmek);

        // Győzelmek betöltése és ellenőrzése
        Map<String, Integer> betoltottGyozelem = gyozelemKezelo.betoltGyozelem();
        assertEquals(2, betoltottGyozelem.size(), "A betöltött győzelmek mérete nem megfelelő.");
        assertEquals(5, betoltottGyozelem.get("Jatekos1"), "Jatekos1 győzelmeinek száma hibás.");
        assertEquals(3, betoltottGyozelem.get("Jatekos2"), "Jatekos2 győzelmeinek száma hibás.");
    }

    @Test
    void testFrissitGyozelem() {
        // Frissítjük a győzelmeket többször, és ellenőrizzük az eredményt
        gyozelemKezelo.frissitGyozelem("Jatekos1");
        gyozelemKezelo.frissitGyozelem("Jatekos1");
        gyozelemKezelo.frissitGyozelem("Jatekos2");

        Map<String, Integer> gyozelmek = gyozelemKezelo.betoltGyozelem();
        assertEquals(2, gyozelmek.get("Jatekos1"), "Jatekos1 győzelmeinek száma nem megfelelő.");
        assertEquals(1, gyozelmek.get("Jatekos2"), "Jatekos2 győzelmeinek száma nem megfelelő.");
    }

    @Test
    void testKiirGyozelem() {
        // Győzelmek hozzáadása
        gyozelemKezelo.frissitGyozelem("Jatekos1");
        gyozelemKezelo.frissitGyozelem("Jatekos2");
        gyozelemKezelo.frissitGyozelem("Jatekos1");

        // Teszteljük a kiírást
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        gyozelemKezelo.kiirGyozelem();

        // Ellenőrizzük a kimenetet
        String output = outputStream.toString();
        assertTrue(output.contains("Jatekos1: 2 győzelem"), "Jatekos1 győzelmeinek kiírása hibás.");
        assertTrue(output.contains("Jatekos2: 1 győzelem"), "Jatekos2 győzelmeinek kiírása hibás.");
    }

    @Test
    void testHibasFajlMentese() {
        // Szimuláljuk egy hibás fájlnevet
        GyozelemKezelo hibasGyozelemKezelo = new GyozelemKezelo() {
            @Override
            protected String getFileName() {
                return "/nem/letezo/mappa/gyozelem.txt";
            }
        };

        Map<String, Integer> gyozelmek = new HashMap<>();
        gyozelmek.put("Jatekos1", 1);

        // Ellenőrizzük, hogy nem dob-e kivételt
        assertDoesNotThrow(() -> hibasGyozelemKezelo.mentGyozelem(gyozelmek), "Mentéskor kivételt dobott hibás fájl esetén.");
    }

    @Test
    void testHibasFajlBetoltese() {
        // Szimuláljuk egy hibás fájlnevet
        GyozelemKezelo hibasGyozelemKezelo = new GyozelemKezelo() {
            @Override
            protected String getFileName() {
                return "/nem/letezo/mappa/gyozelem.txt";
            }
        };

        // Ellenőrizzük, hogy üres map-et ad vissza
        Map<String, Integer> gyozelmek = hibasGyozelemKezelo.betoltGyozelem();
        assertTrue(gyozelmek.isEmpty(), "Hibás fájl esetén a betöltött map nem üres.");
    }
}
