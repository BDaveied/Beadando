/*package nye.hu;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class TestJatek {
    private final ByteArrayOutputStream kimenetiStream = new ByteArrayOutputStream();
    private final InputStream eredetiInput = System.in;
    private final PrintStream eredetiOutput = System.out;

    @BeforeEach
    void setUp() {
        // Átirányítjuk a kimenetet és bemenetet
        System.setOut(new PrintStream(kimenetiStream));
    }

    @AfterEach
    void tearDown() {
        // Visszaállítjuk az eredeti kimenetet és bemenetet
        System.setIn(eredetiInput);
        System.setOut(eredetiOutput);
    }

    @Test
    void testEmberGyőz() {
        // Szimulált bemenet: a játékos sorozatos győzelmi lépései
        String bemenet = "Jatekos\n" + "a\nb\nc\nd\n";
        System.setIn(new ByteArrayInputStream(bemenet.getBytes()));

        // Játék inicializálása és futtatása
        Jatek jatek = new Jatek();
        jatek.start();

        // Ellenőrzés: a játékos neve megjelenik a győzelmi üzenetben
        String kimenet = kimenetiStream.toString();
        assertTrue(kimenet.contains("Jatekos Győzőtt!"), "A játékos győzelmét nem érzékelte helyesen.");
    }

    @Test
    void testDontetlen() {
        // Szimulált bemenet: teljes tábla megtöltése győztes nélkül
        String bemenet = "Jatekos\n" +
                "a\na\nb\nb\nc\nc\n" +
                "d\nd\ne\ne\nf\nf\ng\ng\n";
        System.setIn(new ByteArrayInputStream(bemenet.getBytes()));

        // Játék inicializálása és futtatása
        Jatek jatek = new Jatek();
        jatek.start();

        // Ellenőrzés: döntetlen üzenet helyesen jelenik meg
        String kimenet = kimenetiStream.toString();
        System.out.println("Teszt kimenet: " + kimenet); // Hibakereséshez
        assertTrue(kimenet.contains("Döntetlen"), "A döntetlent nem érzékelte helyesen.");
    }

    @Test
    void testJatekMentese() throws IOException {
        // Szimulált bemenet: játékos lépései
        String bemenet = "Jatekos\n" + "a\nb\nc\nd\n";
        System.setIn(new ByteArrayInputStream(bemenet.getBytes()));

        // Játék inicializálása és futtatása
        Jatek jatek = new Jatek();
        jatek.start();

        // Ellenőrzés: a mentett fájl létrejött
        File mentettFajl = new File("jatekallas.txt");
        assertTrue(mentettFajl.exists(), "A játékmenet mentése nem történt meg.");

        // Ellenőrzés: a fájl tartalma helyes
        BufferedReader reader = new BufferedReader(new FileReader(mentettFajl));
        String elsoSor = reader.readLine();
        assertNotNull(elsoSor, "A mentett fájl üres.");
        reader.close();

        // Tesztfájl eltávolítása
        assertTrue(mentettFajl.delete(), "Nem sikerült törölni a mentett tesztfájlt.");
    }
}*/
