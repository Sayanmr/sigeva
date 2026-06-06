package dao;

import database.Conexion;
import model.ReporteInventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteInventarioDAO {

    public List<ReporteInventario> generarReporte() {

        List<ReporteInventario> lista = new ArrayList<>();

        String sql = """
            SELECT
                v.nombre AS vacuna,
                l.numeroLote,
                i.cantidadDisponible,
                l.fechaVencimiento,

                CASE
                    WHEN l.fechaVencimiento <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)
                    THEN 1
                    ELSE 0
                END AS proximoVencer

            FROM Inventario i
            JOIN Lote l ON i.idLote = l.idLote
            JOIN Vacuna v ON l.idVacuna = v.idVacuna
            ORDER BY l.fechaVencimiento
        """;

        try(Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {

                ReporteInventario r = new ReporteInventario();

                r.setVacuna(rs.getString("vacuna"));
                r.setLote(rs.getString("numeroLote"));
                r.setCantidadDisponible(
                        rs.getInt("cantidadDisponible"));
                r.setFechaVencimiento(
                        rs.getDate("fechaVencimiento"));
                r.setProximoVencer(
                        rs.getBoolean("proximoVencer"));

                lista.add(r);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}