package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void vypisMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1 - Přidat nový záznam");
        System.out.println("2 - Vypsat všechny záznamy");
        System.out.println("3 - Vyhledat záznam podle jména");
        System.out.println("4 - Smazat záznam");
        System.out.println("5 - Konec");
        System.out.print("Zadej volbu: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Spravce spravce = new Spravce();

        // Načtení dat při startu
        try {
            spravce.nactiZeSouboru("zaznamy.csv");
            System.out.println("Data načtena ze souboru.");
        } catch (IOException e) {
            System.out.println("Soubor se nepodařilo načíst, začínáme s prázdným seznamem.");
        }

        boolean pokracovat = true;
        while (pokracovat) {
            vypisMenu();

            if (scanner.hasNextInt()) {
                int volba = scanner.nextInt();
                scanner.nextLine(); // odchyt Enteru

                switch (volba) {
                    case 1 -> spravce.pridejZaznam();
                    case 2 -> spravce.vypisZaznamy();
                    case 3 -> spravce.vyhledatZaznam();
                    case 4 -> spravce.smazZaznam();
                    case 5 -> {
                        pokracovat = false;
                        try {
                            spravce.zapisDoSouboru("zaznamy.csv");
                            System.out.println("Data byla uložena. Konec programu.");
                        } catch (IOException e) {
                            System.out.println("Nepodařilo se uložit data do souboru.");
                        }
                    }
                    default -> System.out.println("Neplatná volba, zkus znovu.");
                }
            } else {
                System.out.println("Neplatný vstup, zadej číslo.");
                scanner.nextLine(); // odchyt nečíselného vstupu
            }
        }

    }
}
