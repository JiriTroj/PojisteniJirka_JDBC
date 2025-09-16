package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Databaze {
    private Connection conn;

    public Databaze() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/osobydb", "root", ""
        );
    }

    public ArrayList<Osoba> najdiZaznamy() {
        ArrayList<Osoba> zaznamy = new ArrayList<>();
        String sql = "SELECT jmeno, prijmeni, vek, telefon FROM pojistenci";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int vek = rs.getInt("vek");
                String telefon = rs.getString("telefon");
                zaznamy.add(new Osoba(jmeno, prijmeni, vek, telefon));
            }
        } catch (SQLException e) {
            System.err.println("Chyba při načítání záznamů: " + e.getMessage());
        }
        return zaznamy;
    }

    public ArrayList<Osoba> najdiZaznamy(String jmeno, String prijmeni) {
        ArrayList<Osoba> zaznamy = new ArrayList<>();
        String sql = "SELECT jmeno, prijmeni, vek, telefon FROM pojistenci WHERE jmeno = ? AND prijmeni = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jmeno);
            stmt.setString(2, prijmeni);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nalezenyJmeno = rs.getString("jmeno");
                    String nalezenyPrijmeni = rs.getString("prijmeni");
                    int nalezenyVek = rs.getInt("vek");
                    String nalezenyTelefon = rs.getString("telefon");
                    zaznamy.add(new Osoba(nalezenyJmeno, nalezenyPrijmeni, nalezenyVek, nalezenyTelefon));
                }
            }
        } catch (SQLException e) {
            System.err.println("Chyba při vyhledávání záznamů: " + e.getMessage());
        }
        return zaznamy;
    }

    public void pridejZaznam(String jmeno, String prijmeni, int vek, String telefon) {
        String sql = "INSERT INTO pojistenci (jmeno, prijmeni, vek, telefon) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jmeno);
            stmt.setString(2, prijmeni);
            stmt.setInt(3, vek);
            stmt.setString(4, telefon);
            stmt.executeUpdate();
            System.out.println("Záznam byl úspěšně přidán do databáze.");
        } catch (SQLException e) {
            System.err.println("Chyba při přidávání záznamu: " + e.getMessage());
        }
    }

    public boolean smazZaznam(String jmeno, String prijmeni) {
        String sql = "DELETE FROM pojistenci WHERE jmeno = ? AND prijmeni = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jmeno);
            stmt.setString(2, prijmeni);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Chyba při mazání záznamu: " + e.getMessage());
            return false;
        }
    }

}
