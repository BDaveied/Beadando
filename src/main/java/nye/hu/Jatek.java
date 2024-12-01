package nye.hu;

import java.io.IOException;
import java.util.Scanner;

public class Jatek {
    private final Tabla tabla = new Tabla(6, 7); // Tipikus 7x6-os tábla
    private Jatekos ember; // Sárga korong
    private final Jatekos gep = new Jatekos("Gép", 'P'); // Piros korong
    private boolean isEmberJon = true;
    private final Scanner scanner = new Scanner(System.in); // Egyetlen Scanner példány
    private final GyozelemKezelo gyozelemKezelo = new GyozelemKezelo(); // Győzelemkezelő

    public void start() {
        System.out.print("Add meg a neved: ");
        String emberNev = scanner.nextLine();
        this.ember = new Jatekos(emberNev, 'S'); // Sárga korong

        System.out.println("Üdvözöllek a játékban, " + ember.getNev() + "!");
        tabla.print();

        while (!tabla.teleVan()) {
            if (isEmberJon) {
                emberKor();
            } else {
                gepKor();
            }
            tabla.print();

            if (tabla.gyoztesEllenorzes()) {
                String nyertes = isEmberJon ? ember.getNev() : gep.getNev();
                System.out.println(nyertes + " Győzőtt!");
                gyozelemKezelo.frissitGyozelem(nyertes); // Győzelem rögzítése
                jatekMentes(); // Játékállás mentése győzelem esetén
                return;
            }
            isEmberJon = !isEmberJon;
        }
        System.out.println("Woah! Döntetlen!! Nincs több hely a táblán");
        jatekMentes(); // Döntetlen esetén is mentjük a játékállást
    }

    private void emberKor() {
        System.out.print("Te jössz (Oszlop betűje): ");
        String oszlop = scanner.nextLine();
        while (!tabla.addKorong(oszlop, ember.getKorong())) {
            System.out.print("Érvénytelen lépés. Próbáld újra: ");
            oszlop = scanner.nextLine();
        }
    }

    private void gepKor() {
        String oszlop = tabla.getVeletlenOszlop();
        System.out.println("A gép választása: " + oszlop);
        tabla.addKorong(oszlop, gep.getKorong());
    }

    private void jatekMentes() {
        try {
            tabla.ment("jatekallas.txt"); // Játékállás mentése
            System.out.println("A játékállást elmentettük a jatekallas.txt fájlba.");
        } catch (IOException e) {
            System.out.println("Nem sikerült a játékállás mentése: " + e.getMessage());
        }

        // Győzelmi táblázat kiírása
        System.out.println("Frissített győzelmi táblázat:");
        gyozelemKezelo.kiirGyozelem();
    }
}


