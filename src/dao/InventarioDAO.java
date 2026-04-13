package dao;

import database.Conexion;
import model.Inventario;
import model.Lote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public List<Inventario> obtenerInventario() {

        List<Inventario> inventarioList = new ArrayList<>();

        String sql = "SELECT i.idInventario, i.cantidadDisponible, i.fechaIngreso, " +
                "l.idLote, l.numeroLote " +
                "FROM Inventario i " +
                "JOIN Lote l ON i.idLote = l.idLote";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Lote lote = new Lote();
                lote.setIdLote(rs.getInt("idLote"));
                lote.setNumeroLote(rs.getString("numeroLote"));

                Inventario inv = new Inventario(
                        rs.getInt("idInventario"),
                        rs.getInt("cantidadDisponible"),
                        rs.getDate("fechaIngreso"),
                        lote
                );

                inventarioList.add(inv);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener inventario: " + e.getMessage());
        }

        return inventarioList;
    }
}