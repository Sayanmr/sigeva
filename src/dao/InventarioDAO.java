package dao;

import database.Conexion;
import model.Inventario;
import model.Lote;
import model.Vacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public List<Inventario> obtenerInventario() {

        List<Inventario> inventarioList = new ArrayList<>();

        String sql = """
                    SELECT i.idInventario,
                           i.cantidadDisponible,
                           i.fechaIngreso,
                           i.nivelMinimo,
                           l.idLote,
                           l.numeroLote,
                           v.nombre
                    FROM Inventario i
                    JOIN Lote l ON i.idLote = l.idLote
                    JOIN Vacuna v ON l.idVacuna = v.idVacuna
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Vacuna vacuna = new Vacuna();
                vacuna.setNombre(rs.getString("nombre"));

                Lote lote = new Lote();
                lote.setIdLote(rs.getInt("idLote"));
                lote.setNumeroLote(rs.getString("numeroLote"));
                lote.setVacuna(vacuna);

                Inventario inv = new Inventario();

                inv.setIdInventario(rs.getInt("idInventario"));
                inv.setCantidadDisponible(rs.getInt("cantidadDisponible"));
                inv.setFechaIngreso(rs.getDate("fechaIngreso"));
                inv.setNivelMinimo(rs.getInt("nivelMinimo"));
                inv.setLote(lote);

                inventarioList.add(inv);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener inventario: " + e.getMessage());
        }

        return inventarioList;
    }
}