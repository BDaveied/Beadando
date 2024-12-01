package nye.hu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTablaGyozelem {
    @Test
    void testHorizontalWin() {
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("a", 'Y');
        tabla.addKorong("b", 'Y');
        tabla.addKorong("c", 'Y');
        tabla.addKorong("d", 'Y');

        assertTrue(tabla.gyoztesEllenorzes(), "Horizontal win was not detected correctly.");
    }

    @Test
    void testVerticalWin() {
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("a", 'R');
        tabla.addKorong("a", 'R');
        tabla.addKorong("a", 'R');
        tabla.addKorong("a", 'R');

        assertTrue(tabla.gyoztesEllenorzes(), "Vertical win was not detected correctly.");
    }

    @Test
    void testDiagonalWinRight() {
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("a", 'Y');
        tabla.addKorong("b", 'R'); // Block
        tabla.addKorong("b", 'Y');
        tabla.addKorong("c", 'R'); // Block
        tabla.addKorong("c", 'R'); // Block
        tabla.addKorong("c", 'Y');
        tabla.addKorong("d", 'R'); // Block
        tabla.addKorong("d", 'R'); // Block
        tabla.addKorong("d", 'R'); // Block
        tabla.addKorong("d", 'Y');

        assertTrue(tabla.gyoztesEllenorzes(), "Diagonal win (right) was not detected correctly.");
    }

    @Test
    void testDiagonalWinLeft() {
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("d", 'Y');
        tabla.addKorong("c", 'R'); // Block
        tabla.addKorong("c", 'Y');
        tabla.addKorong("b", 'R'); // Block
        tabla.addKorong("b", 'R'); // Block
        tabla.addKorong("b", 'Y');
        tabla.addKorong("a", 'R'); // Block
        tabla.addKorong("a", 'R'); // Block
        tabla.addKorong("a", 'R'); // Block
        tabla.addKorong("a", 'Y');

        assertTrue(tabla.gyoztesEllenorzes(), "Diagonal win (left) was not detected correctly.");
    }

    @Test
    void testNoWin() {
        Tabla tabla = new Tabla(6, 7);
        tabla.addKorong("a", 'Y');
        tabla.addKorong("b", 'R');
        tabla.addKorong("c", 'Y');
        tabla.addKorong("d", 'R');

        assertFalse(tabla.gyoztesEllenorzes(), "No win was incorrectly detected as a win.");
    }
}
