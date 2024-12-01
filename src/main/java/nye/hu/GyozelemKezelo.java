package nye.hu;

import java.io.*;
import java.util.*;

public class GyozelemKezelo {
    private static final String DEFAULT_FILE_NAME = "gyozelem.txt";

    // Alapértelmezett fájlnév lekérése (felülírható a tesztek során)
    protected String getFileName() {
        return DEFAULT_FILE_NAME;
    }

    public Map<String, Integer> betoltGyozelem() {
        Map<String, Integer> gyozelmek = new HashMap<>();

        File file = new File(getFileName());
        if (!file.exists()) {
            return gyozelmek;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String nev = parts[0];
                    int gyozelemSzam = Integer.parseInt(parts[1]);
                    gyozelmek.put(nev, gyozelemSzam);
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a győzelmek betöltésekor: " + e.getMessage());
        }
        return gyozelmek;
    }

    public void mentGyozelem(Map<String, Integer> gyozelmek) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFileName()))) {
            for (Map.Entry<String, Integer> entry : gyozelmek.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Hiba a győzelmek mentésekor: " + e.getMessage());
        }
    }

    public void frissitGyozelem(String nev) {
        Map<String, Integer> gyozelmek = betoltGyozelem();
        gyozelmek.put(nev, gyozelmek.getOrDefault(nev, 0) + 1);
        mentGyozelem(gyozelmek);
    }

    public void kiirGyozelem() {
        Map<String, Integer> gyozelmek = betoltGyozelem();
        System.out.println("Győzelmi táblázat:");
        gyozelmek.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " győzelem"));
    }
}