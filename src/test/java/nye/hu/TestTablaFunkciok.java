package nye.hu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TestTablaFunkciok {

    @Test
    void testTablaInitialization() {
        // Teszteljük, hogy a tábla megfelelően inicializálódik
        Tabla tabla = new Tabla(6, 7);
        assertEquals(6, tabla.getSorok(), "A sorok száma nem megfelelő.");
        assertEquals(7, tabla.getOszlopok(), "Az oszlopok száma nem megfelelő.");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', tabla.getRacs()[i][j], "Az inicializált tábla nem üres.");
            }
        }
    }

    @Test
    void testTablaPrint() {
        // Ellenőrizzük, hogy a print metódus nem dob hibát
        Tabla tabla = new Tabla(6, 7);
        ByteArrayOutputStream kimenetiStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(kimenetiStream));

        tabla.print();

        // Ellenőrizzük, hogy van valamilyen kimenet
        String kimenet = kimenetiStream.toString();
        assertNotNull(kimenet, "A print metódus nem generált kimenetet.");
        assertFalse(kimenet.isEmpty(), "A print metódus üres kimenetet generált.");
    }

    @Test
    void testInvalidTableDimensions() {
        // Próbáljunk létrehozni érvénytelen méretű táblát
        assertThrows(NegativeArraySizeException.class, () -> new Tabla(-1, 7), "Nem dobott kivételt negatív soroknál.");
        assertThrows(NegativeArraySizeException.class, () -> new Tabla(6, -1), "Nem dobott kivételt negatív oszlopoknál.");
    }

    @Test
    void testSmallTable() {
        // Hozzunk létre egy kis méretű táblát és ellenőrizzük
        Tabla tabla = new Tabla(4, 4);
        assertEquals(4, tabla.getSorok(), "A kis tábla sorainak száma nem megfelelő.");
        assertEquals(4, tabla.getOszlopok(), "A kis tábla oszlopainak száma nem megfelelő.");
    }

    @Test
    void testTeleVan() {
        Tabla tabla = new Tabla(6, 7);

        // Ellenőrizzük, hogy kezdetben a tábla nincs tele
        assertFalse(tabla.teleVan(), "Üres tábla tévesen teleként lett érzékelve.");

        // Töltsük fel a táblát
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                tabla.addKorong(Character.toString((char) ('a' + i)), 'S');
            }
        }

        // Ellenőrizzük, hogy a tábla tele van
        assertTrue(tabla.teleVan(), "Teli tábla tévesen üresként lett érzékelve.");
    }

    @Test
    void testGetVeletlenOszlop() {
        Tabla tabla = new Tabla(6, 7);

        // Generáljunk véletlen oszlopokat és ellenőrizzük, hogy azok a tartományban vannak
        for (int i = 0; i < 100; i++) { // Többszöri futtatás a véletlenszerűség ellenőrzéséhez
            String oszlop = tabla.getVeletlenOszlop();
            assertNotNull(oszlop, "A getVeletlenOszlop null értéket adott vissza.");
            assertEquals(1, oszlop.length(), "A getVeletlenOszlop több karaktert adott vissza.");
            char oszlopChar = oszlop.charAt(0);
            assertTrue(oszlopChar >= 'a' && oszlopChar < 'a' + tabla.getOszlopok(),
                    "A véletlenszerű oszlop nincs a megadott tartományban.");
        }
    }

    @Test
    void testAddKorongInvalidOszlop() {
        Tabla tabla = new Tabla(6, 7);

        // Próbáljunk érvénytelen oszlopba korongot helyezni
        assertFalse(tabla.addKorong("z", 'S'), "Érvénytelen oszlopot helyesen kezelt.");
        assertFalse(tabla.addKorong("1", 'S'), "Érvénytelen oszlopot helyesen kezelt.");
    }

    @Test
    void testAddKorongFullColumn() {
        Tabla tabla = new Tabla(6, 7);

        // Töltsük fel egy oszlopot
        for (int i = 0; i < 6; i++) {
            assertTrue(tabla.addKorong("a", 'S'), "Nem sikerült korongot hozzáadni egy érvényes oszlophoz.");
        }

        // Próbáljunk korongot hozzáadni egy már megtelt oszlophoz
        assertFalse(tabla.addKorong("a", 'S'), "A megtelt oszlopba helyezés hibásan sikeres volt.");
    }
}