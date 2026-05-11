package dao;

import database.Conexion;
import model.AplicacionVacuna;
import model.Lote;
import model.Vacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialVacunacionDAO {

    // Buscar historial por nombre o documento
    public List<AplicacionVacuna> obtenerHistorial(String criterio) {
        List<AplicacionVacuna> historial = new ArrayList<>();

        String sql = """
                SELECT a.idAplicacion, a.fechaAplicacion, a.dosis,
                       c.nombreCompleto AS nombrePaciente,
                       v.idVacuna, v.nombre AS nombreVacuna, v.tipo, v.fabricante,
                       l.idLote, l.numeroLote, l.fechaFabricacion, l.fechaVencimiento
                FROM AplicacionVacuna a
                JOIN Vacuna v ON a.idVacuna = v.idVacuna
                JOIN Lote l ON a.idLote = l.idLote
                JOIN ClienteVacunado c ON a.idCliente = c.idCliente
                WHERE c.nombreCompleto LIKE ? OR c.documento LIKE ?
                ORDER BY a.fechaAplicacion ASC
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String param = "%" + criterio + "%";
            ps.setString(1, param);
            ps.setString(2, param);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historial.add(mapearAplicacion(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historial;
    }

    // Obtener los últimos registros por defecto
    public List<AplicacionVacuna> obtenerUltimosRegistros(int cantidad) {
        List<AplicacionVacuna> historial = new ArrayList<>();

        String sql = """
                SELECT a.idAplicacion, a.fechaAplicacion, a.dosis,
                       c.nombreCompleto AS nombrePaciente,
                       v.idVacuna, v.nombre AS nombreVacuna, v.tipo, v.fabricante,
                       l.idLote, l.numeroLote, l.fechaFabricacion, l.fechaVencimiento
                FROM AplicacionVacuna a
                JOIN Vacuna v ON a.idVacuna = v.idVacuna
                JOIN Lote l ON a.idLote = l.idLote
                JOIN ClienteVacunado c ON a.idCliente = c.idCliente
                ORDER BY a.fechaAplicacion DESC
                LIMIT ?
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historial.add(mapearAplicacion(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historial;
    }

    private AplicacionVacuna mapearAplicacion(ResultSet rs) throws SQLException {
        AplicacionVacuna app = new AplicacionVacuna();

        app.setIdAplicacion(rs.getInt("idAplicacion"));
        app.setFechaAplicacion(rs.getDate("fechaAplicacion"));
        app.setDosis(rs.getString("dosis"));
        app.setNombrePaciente(rs.getString("nombrePaciente")); // <-- asignar nombre

        Vacuna v = new Vacuna();
        v.setIdVacuna(rs.getInt("idVacuna"));
        v.setNombre(rs.getString("nombreVacuna"));
        v.setTipo(rs.getString("tipo"));
        v.setFabricante(rs.getString("fabricante"));
        app.setVacuna(v);

        Lote l = new Lote();
        l.setIdLote(rs.getInt("idLote"));
        l.setNumeroLote(rs.getString("numeroLote"));
        l.setFechaFabricacion(rs.getDate("fechaFabricacion"));
        l.setFechaVencimiento(rs.getDate("fechaVencimiento"));
        app.setLote(l);

        return app;
    }
}