package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Spravce {
    private Databaze databaze;
    public Scanner scanner = new Scanner(System.in);

    public Spravce() {
        databaze = new Databaze();
    }

    public void vyhledatZaznam() {
        System.out.println("Zadejte jméno pojištěného:");
        String jmeno = scanner.nextLine().trim();  // Odstraníme mezery
        System.out.println("Zadejte příjmení:");
        String prijmeni = scanner.nextLine().trim();  // Odstraníme mezery

        ArrayList<Zaznam> zaznamy = databaze.najdiZaznamy(jmeno, prijmeni);
        if (zaznamy.isEmpty()) {
            System.out.println("K zadanému jménu a příjmení nikdo nenalezen");
            stiskPokracovat();
            return;
        }
        zaznamy.forEach(zaznam -> System.out.println(zaznam));
        stiskPokracovat();
    }


    public void vypisZaznamy() {
        ArrayList<Zaznam> zaznamy = databaze.najdiZaznamy();
        if (zaznamy.isEmpty()) {
            System.out.println("V databázi nejsou žádné záznamy pojištěných osob");
            stiskPokracovat();
            return;
        }
        zaznamy.forEach(zaznam -> System.out.println(zaznam));
        stiskPokracovat();
    }

    public void pridejZaznam() {
        String jmeno;
        String prijmeni;

        // Validace jména a příjmení
        do {
            System.out.println("Zadejte jméno pojištěného:");
            jmeno = scanner.nextLine().trim();  // Odstraníme mezery
        } while (jmeno.isBlank() || jmeno.length() < 3);

        do {
            System.out.println("Zadejte příjmení:");
            prijmeni = scanner.nextLine().trim();  // Odstraníme mezery
        } while (prijmeni.isBlank() || prijmeni.length() < 3);

        System.out.println("Zadejte věk:");
        Integer vek = naparsujVek();

        String telefon;

        // Validace telefonního čísla
        do {
            System.out.println("Zadejte telefonní číslo (pouze číslice, 3-9 znaků):");
            telefon = scanner.nextLine().trim(); // Odstraníme mezery
        } while (!telefon.matches("\\d{3,9}")); // Kontrola, zda obsahuje pouze číslice a má délku 3-9

        // Přidáme záznam do databáze
        databaze.pridejZaznam(jmeno, prijmeni, vek, telefon);
        stiskPokracovat();
    }


    public Integer naparsujVek() {
        String vekString = scanner.nextLine().trim();
        while (true) {
            try {
                return Integer.parseInt(vekString);
            } catch (Exception ex) {
                System.out.println("Nesprávně zadáno, zadejte prosím znovu.");
                System.out.println("Zadejte věk:");
                vekString = scanner.nextLine();
            }
        }
    }

    // Nová metoda pro smazání záznamu
    public void smazZaznam() {
        System.out.println("Zadejte jméno pojištěného, jehož záznam chcete smazat:");
        String jmeno = scanner.nextLine().trim();  // Odstraníme mezery
        System.out.println("Zadejte příjmení pojištěného:");
        String prijmeni = scanner.nextLine().trim();  // Odstraníme mezery

        // Pokusíme se najít záznamy pro dané jméno a příjmení
        ArrayList<Zaznam> zaznamy = databaze.najdiZaznamy(jmeno, prijmeni);
        if (zaznamy.isEmpty()) {
            System.out.println("Žádný záznam nebyl nalezen pro zadané jméno a příjmení.");
            stiskPokracovat();
            return;
        }

        // Zobrazíme nalezené záznamy a požádáme uživatele o potvrzení smazání
        zaznamy.forEach(zaznam -> System.out.println(zaznam));


        System.out.println("Chcete tento záznam opravdu smazat? (ano/ne):");
        String potvrzeni = scanner.nextLine().trim();
        if (potvrzeni.equalsIgnoreCase("ano")) {
            boolean uspech = databaze.smazZaznam(jmeno, prijmeni);
            if (uspech) {
                System.out.println("Záznam byl úspěšně smazán.");
            } else {
                System.out.println("Došlo k chybě při mazání záznamu.");
            }
        } else {
            System.out.println("Mazání záznamu bylo zrušeno.");
        }
        stiskPokracovat();
    }

    public void zapisDoSouboru(String soubor) throws IOException {
        databaze.zapisDoSouboru(soubor);
        System.out.println("Databáze byla úspěšně exportována do souboru " + soubor);
    }

    // Nová metoda pro načtení databáze ze souboru při startu aplikace
    public void nactiZeSouboru(String soubor) throws IOException {
        databaze.nactiZeSouboru(soubor);
    }

    private void stiskPokracovat() {
        System.out.println("Stiskněte libovolnou klávesu ...");
        scanner.nextLine();
    }
}
