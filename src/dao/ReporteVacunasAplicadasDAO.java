package dao;

import database.Conexion;
import model.ReporteVacunaAplicada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReporteVacunasAplicadasDAO {

    public List<ReporteVacunaAplicada> obtenerReporte(
            Date fechaInicio,
            Date fechaFin) {

        List<ReporteVacunaAplicada> lista = new ArrayList<>();

        String sql = """
                SELECT
                    a.nombrePaciente,
                    v.nombre AS vacuna,
                    l.numeroLote,
                    a.fechaAplicacion
                FROM AplicacionVacuna a
                JOIN Vacuna v
                    ON a.idVacuna = v.idVacuna
                JOIN Lote l
                    ON a.idLote = l.idLote
                WHERE a.fechaAplicacion BETWEEN ? AND ?
                ORDER BY a.fechaAplicacion ASC
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(
                    1,
                    new java.sql.Date(
                            fechaInicio.getTime()
                    )
            );

            ps.setDate(
                    2,
                    new java.sql.Date(
                            fechaFin.getTime()
                    )
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ReporteVacunaAplicada reporte =
                        new ReporteVacunaAplicada();

                reporte.setPaciente(
                        rs.getString("nombrePaciente")
                );

                reporte.setVacuna(
                        rs.getString("vacuna")
                );

                reporte.setLote(
                        rs.getString("numeroLote")
                );

                reporte.setFechaAplicacion(
                        rs.getDate("fechaAplicacion")
                );

                lista.add(reporte);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int totalDosisAplicadas(
            Date fechaInicio,
            Date fechaFin) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM AplicacionVacuna
                WHERE fechaAplicacion BETWEEN ? AND ?
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(
                    1,
                    new java.sql.Date(
                            fechaInicio.getTime()
                    )
            );

            ps.setDate(
                    2,
                    new java.sql.Date(
                            fechaFin.getTime()
                    )
            );

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}