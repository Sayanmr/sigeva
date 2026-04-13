package dao;

import database.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    public List<String> obtenerLotesPorVencer() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT l.numeroLote, l.fechaVencimiento, v.nombre " +
                "FROM Lote l " +
                "JOIN Vacuna v ON l.idVacuna = v.idVacuna " +
                "WHERE l.fechaVencimiento <= CURDATE() + INTERVAL 7 DAY";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String info = "Lote: " + rs.getString("numeroLote") +
                        " | Vacuna: " + rs.getString("nombre") +
                        " | Vence: " + rs.getDate("fechaVencimiento");

                lista.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}