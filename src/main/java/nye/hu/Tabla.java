package nye.hu;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Tabla {
    private final char[][] racs;
    private final int sorok;
    private final int oszlopok;

    public char[][] getRacs() {
        return racs;
    }

    public int getSorok() {
        return sorok;
    }

    public int getOszlopok() {
        return oszlopok;
    }

    public Tabla(int sorok, int oszlopok) {
        this.sorok = sorok;
        this.oszlopok = oszlopok;
        this.racs = new char[sorok][oszlopok];
        for (int i = 0; i < sorok; i++) {
            for (int j = 0; j < oszlopok; j++) {
                racs[i][j] = '.';
            }
        }
    }

    // Táblázat megjelenítése
    public void print() {
        System.out.print(" ");
        for (char c = 'a'; c < 'a' + oszlopok; c++) {
            System.out.print(" " + c);
        }
        System.out.println();

        for (int i = 0; i < sorok; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < oszlopok; j++) {
                System.out.print(racs[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Korong hozzáadása
    public boolean addKorong(String oszlop, char korong) {
        int osz = oszlop.charAt(0) - 'a';
        if (osz < 0 || osz >= oszlopok) return false;

        for (int i = sorok - 1; i >= 0; i--) {
            if (racs[i][osz] == '.') {
                racs[i][osz] = korong;
                return true;
            }
        }
        return false;
    }

    // Győzelem ellenőrzése
    public boolean gyoztesEllenorzes() {
        // Függőleges, vízszintes, átlós győzelem ellenőrzése
        for (int r = 0; r < sorok; r++) {
            for (int c = 0; c < oszlopok; c++) {
                char korong = racs[r][c];
                if (korong != '.' && (
                        iranyEllenorzes(r, c, 1, 0, korong) || // Függőleges
                                iranyEllenorzes(r, c, 0, 1, korong) || // Vízszintes
                                iranyEllenorzes(r, c, 1, 1, korong) || // Átlós jobbra
                                iranyEllenorzes(r, c, 1, -1, korong)  // Átlós balra
                )) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean iranyEllenorzes(int r, int c, int dr, int dc, char korong) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nr = r + i * dr;
            int nc = c + i * dc;
            if (nr >= 0 && nr < sorok && nc >= 0 && nc < oszlopok && racs[nr][nc] == korong) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    // Ellenőrzés, hogy tele van-e a tábla
    public boolean teleVan() {
        for (int j = 0; j < oszlopok; j++) {
            if (racs[0][j] == '.') return false;
        }
        return true;
    }

    // Véletlenszerű oszlop generálása
    public String getVeletlenOszlop() {
        Random random = new Random();
        return String.valueOf((char) ('a' + random.nextInt(oszlopok)));
    }

    // Tábla mentése fájlba
    public void ment(String fajlNev) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fajlNev))) {
            writer.println(sorok + " " + oszlopok);
            for (int i = 0; i < sorok; i++) {
                for (int j = 0; j < oszlopok; j++) {
                    writer.print(racs[i][j]);
                }
                writer.println();
            }
        }
    }

    // Tábla betöltése fájlból
    public static Tabla betolt(String fajlNev) throws IOException {
        File file = new File(fajlNev);
        if (!file.exists()) {
            System.out.println("Fájl nem található, üres táblával indul a játék.");
            return new Tabla(6, 7); // Alapértelmezett 7x6 tábla
        }

        try (Scanner scanner = new Scanner(file)) {
            int sorok = scanner.nextInt();
            int oszlopok = scanner.nextInt();
            scanner.nextLine(); // Sorvége átlépése
            Tabla tabla = new Tabla(sorok, oszlopok);

            for (int i = 0; i < sorok; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < Math.min(line.length(), oszlopok); j++) {
                    tabla.racs[i][j] = line.charAt(j);
                }
            }

            return tabla;
        }
    }
}

