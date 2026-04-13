package dao;

import database.Conexion;
import model.Lote;
import model.Vacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    public List<Lote> obtenerLotes() {

        List<Lote> lista = new ArrayList<>();

        String sql = """
            SELECT l.*, v.nombre, v.tipo, v.fabricante
            FROM Lote l
            JOIN Vacuna v ON l.idVacuna = v.idVacuna
        """;

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Lote l = new Lote();
                l.setIdLote(rs.getInt("idLote"));
                l.setNumeroLote(rs.getString("numeroLote"));
                l.setFechaFabricacion(rs.getDate("fechaFabricacion"));
                l.setFechaVencimiento(rs.getDate("fechaVencimiento"));

                Vacuna v = new Vacuna();
                v.setNombre(rs.getString("nombre"));
                v.setTipo(rs.getString("tipo"));
                v.setFabricante(rs.getString("fabricante"));

                l.setVacuna(v);

                lista.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔥 FILTRO POR VENCIMIENTO
    public List<Lote> obtenerPorRangoVencimiento(String inicio, String fin) {

        List<Lote> lista = new ArrayList<>();

        String sql = """
            SELECT l.*, v.nombre, v.tipo, v.fabricante
            FROM Lote l
            JOIN Vacuna v ON l.idVacuna = v.idVacuna
            WHERE l.fechaVencimiento BETWEEN ? AND ?
        """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, inicio);
            ps.setString(2, fin);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Lote l = new Lote();
                l.setIdLote(rs.getInt("idLote"));
                l.setNumeroLote(rs.getString("numeroLote"));
                l.setFechaFabricacion(rs.getDate("fechaFabricacion"));
                l.setFechaVencimiento(rs.getDate("fechaVencimiento"));

                Vacuna v = new Vacuna();
                v.setNombre(rs.getString("nombre"));
                l.setVacuna(v);

                lista.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔥 LOTES POR VENCER (TU MÉTODO MEJORADO)
    public List<Lote> obtenerLotesPorVencer() {

        List<Lote> lista = new ArrayList<>();

        String sql = """
            SELECT l.*, v.nombre
            FROM Lote l
            JOIN Vacuna v ON l.idVacuna = v.idVacuna
            WHERE l.fechaVencimiento <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)
        """;

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Lote l = new Lote();
                l.setNumeroLote(rs.getString("numeroLote"));
                l.setFechaVencimiento(rs.getDate("fechaVencimiento"));

                Vacuna v = new Vacuna();
                v.setNombre(rs.getString("nombre"));

                l.setVacuna(v);

                lista.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}