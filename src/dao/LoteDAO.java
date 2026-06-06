package dao;

import database.Conexion;
import model.Lote;
import model.TrazabilidadLote;
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

    public TrazabilidadLote obtenerTrazabilidad(String numeroLote) {

        TrazabilidadLote t = null;

        String sql = """
                    SELECT
                        l.numeroLote,
                        l.fechaVencimiento,
                        i.fechaIngreso,
                        i.cantidadDisponible,
                
                        IFNULL(
                            (SELECT SUM(s.cantidad)
                             FROM SalidaInventario s
                             WHERE s.idLote = l.idLote),0
                        ) AS totalSalidas,
                
                        IFNULL(
                            (SELECT COUNT(*)
                             FROM AplicacionVacuna a
                             WHERE a.idLote = l.idLote),0
                        ) AS totalAplicaciones
                
                    FROM Lote l
                    JOIN Inventario i ON l.idLote = i.idLote
                    WHERE l.numeroLote = ?
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroLote);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                t = new TrazabilidadLote();

                t.setNumeroLote(rs.getString("numeroLote"));
                t.setFechaIngreso(rs.getDate("fechaIngreso"));
                t.setFechaVencimiento(rs.getDate("fechaVencimiento"));
                t.setCantidadDisponible(rs.getInt("cantidadDisponible"));
                t.setTotalSalidas(rs.getInt("totalSalidas"));
                t.setTotalAplicaciones(rs.getInt("totalAplicaciones"));


                if (rs.getDate("fechaVencimiento")
                        .before(new java.util.Date())) {

                    t.setEstado("VENCIDO");

                } else if (rs.getInt("cantidadDisponible") <= 0) {

                    t.setEstado("AGOTADO");

                } else {

                    t.setEstado("ACTIVO");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
    public List<Lote> buscarPorNumero(String texto) {

        List<Lote> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM Lote
            WHERE numeroLote LIKE ?
            """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Lote lote = new Lote();

                lote.setIdLote(rs.getInt("idLote"));
                lote.setNumeroLote(rs.getString("numeroLote"));

                lista.add(lote);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}