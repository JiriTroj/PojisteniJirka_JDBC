package org.example;

public class Osoba {
    private String jmeno;
    private String prijmeni;
    private int vek;
    private String telefon;

    // Konstruktor
    public Osoba(String jmeno, String prijmeni, int vek, String telefon) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.vek = vek;
        this.telefon = telefon;
    }

    // Gettery a Settery
    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public int getVek() {
        return vek;
    }

    public void setVek(int vek) {
        this.vek = vek;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    // Přehledný výpis do konzole
    @Override
    public String toString() {
        return String.format("%s %s, věk: %d, tel: %s",
                jmeno, prijmeni, vek, telefon);
    }
}
