package dao;

import database.Conexion;
import java.sql.*;

public class VacunaDAO {

    // 🔹 1. REGISTRO DE INGRESO DE VACUNAS
    public void registrarIngreso(String nombre, int cantidad) {
        String sql = "INSERT INTO vacunas(nombre, cantidad) VALUES (?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.executeUpdate();

            System.out.println("Vacuna registrada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 2. ACTUALIZACIÓN DE INVENTARIO
    public void actualizarInventario(String nombre, int cantidad) {
        String sql = "UPDATE vacunas SET cantidad = cantidad + ? WHERE nombre = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setString(2, nombre);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Inventario actualizado");
            } else {
                System.out.println("Vacuna no existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 2.5 ACTUALIZACIÓN AUTOMÁTICA (IMPORTANTE)
    public void guardarVacuna(String nombre, int cantidad) {

        String sql = "SELECT * FROM vacunas WHERE nombre = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Si existe → actualiza
                actualizarInventario(nombre, cantidad);
            } else {
                // Si no existe → registra
                registrarIngreso(nombre, cantidad);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 3. CONSULTA DE INVENTARIO
    public void consultarInventario() {
        String sql = "SELECT * FROM vacunas";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("=== INVENTARIO DE VACUNAS ===");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | Nombre: " + rs.getString("nombre") +
                                " | Cantidad: " + rs.getInt("cantidad")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}