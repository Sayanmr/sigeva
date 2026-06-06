package dao;

import database.Conexion;
import model.ReportePerdidasVencimientos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportePerdidasVencimientosDAO {

    public List<ReportePerdidasVencimientos> generarReporte() {

        List<ReportePerdidasVencimientos> lista = new ArrayList<>();

        String sql = """
            SELECT 
                l.numeroLote,
                v.nombre AS vacuna,
                l.fechaVencimiento,
                i.cantidadDisponible,

                IFNULL(SUM(s.cantidad), 0) AS totalPerdidas

            FROM Lote l
            JOIN Vacuna v ON l.idVacuna = v.idVacuna
            JOIN Inventario i ON i.idLote = l.idLote
            LEFT JOIN SalidaInventario s ON s.idLote = l.idLote
                AND (
                    s.motivo LIKE '%pérdida%' OR
                    s.motivo LIKE '%daño%' OR
                    s.motivo LIKE '%vencimiento%'
                )

            GROUP BY l.idLote
            ORDER BY l.fechaVencimiento ASC
        """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReportePerdidasVencimientos r = new ReportePerdidasVencimientos();

                r.setNumeroLote(rs.getString("numeroLote"));
                r.setVacuna(rs.getString("vacuna"));
                r.setFechaVencimiento(rs.getDate("fechaVencimiento"));
                r.setCantidadDisponible(rs.getInt("cantidadDisponible"));
                r.setTotalPerdidas(rs.getInt("totalPerdidas"));

                // lógica de vencimiento
                r.setVencido(
                        rs.getDate("fechaVencimiento")
                                .before(new java.util.Date())
                );

                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}