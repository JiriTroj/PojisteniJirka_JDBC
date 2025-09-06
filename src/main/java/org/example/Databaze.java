package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Databaze {
    private ArrayList<Zaznam> zaznamy;

    public Databaze() {
        zaznamy = new ArrayList<>();
    }

    public void pridejZaznam(String jmeno, String prijmeni, Integer vek, String telefon) {
        zaznamy.add(new Zaznam(jmeno, prijmeni, vek, telefon));
    }

    public ArrayList<Zaznam> najdiZaznamy() {
        return zaznamy;
    }

    public ArrayList<Zaznam> najdiZaznamy(String jmeno, String prijmeni) {
        return zaznamy.stream()
                .filter(zaznam -> jmeno.equalsIgnoreCase(zaznam.getJmeno())
                        && prijmeni.equalsIgnoreCase(zaznam.getPrijmeni()))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public boolean smazZaznam(String jmeno, String prijmeni) {
        return zaznamy.removeIf(zaznam ->
                jmeno.equalsIgnoreCase(zaznam.getJmeno())
                        && prijmeni.equalsIgnoreCase(zaznam.getPrijmeni()));
    }

    // 1. Metoda pro zápis databáze do CSV
    public void zapisDoSouboru(String soubor) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(soubor))) {
            // Hlavička
            writer.write("Jmeno,Prijmeni,Vek,Telefon\n");

            // Data
            for (Zaznam zaznam : zaznamy) {
                writer.write(String.format("\"%s\",\"%s\",%d,\"%s\"\n",
                        zaznam.getJmeno(),
                        zaznam.getPrijmeni(),
                        zaznam.getVek(),
                        zaznam.getTelefon()));
            }
        }
    }

    // 2. Metoda pro načtení databáze z CSV
    public void nactiZeSouboru(String soubor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(soubor))) {
            String radek;

            // První řádek je hlavička, přeskočíme
            reader.readLine();

            // Vyčistíme kolekci, než začneme načítat
            zaznamy.clear();

            // Projdeme každý řádek
            while ((radek = reader.readLine()) != null) {
                // Rozdělení hodnot – split podle čárky
                // Odstraníme uvozovky kolem textových polí
                String[] casti = radek.split(",");

                String jmeno = casti[0].replace("\"", "");
                String prijmeni = casti[1].replace("\"", "");
                int vek = Integer.parseInt(casti[2]);
                String telefon = casti[3].replace("\"", "");

                // Vytvoření objektu Zaznam a přidání do kolekce
                Zaznam z = new Zaznam(jmeno, prijmeni, vek, telefon);
                zaznamy.add(z);
            }
        }
    }

}
