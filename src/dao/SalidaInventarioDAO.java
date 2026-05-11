package dao;

import database.Conexion;
import model.SalidaInventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class SalidaInventarioDAO {

    public boolean registrarSalida(SalidaInventario s) {

        String insertSalida = """
            INSERT INTO SalidaInventario
            (idLote, cantidad, motivo, fechaSalida, idUsuario)
            VALUES (?, ?, ?, ?, ?)
        """;

        String updateInventario = """
            UPDATE Inventario
            SET cantidadDisponible = cantidadDisponible - ?
            WHERE idLote = ? AND cantidadDisponible >= ?
        """;

        try (Connection con = Conexion.conectar()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(updateInventario)) {
                ps1.setInt(1, s.getCantidad());
                ps1.setInt(2, s.getLote().getIdLote());
                ps1.setInt(3, s.getCantidad());

                int filas = ps1.executeUpdate();

                if (filas == 0) {
                    con.rollback();
                    return false;
                }
            }


            try (PreparedStatement ps2 = con.prepareStatement(insertSalida)) {
                ps2.setInt(1, s.getLote().getIdLote());
                ps2.setInt(2, s.getCantidad());
                ps2.setString(3, s.getMotivo());
                ps2.setTimestamp(4, new Timestamp(s.getFechaSalida().getTime()));
                ps2.setInt(5, s.getUsuario().getIdUsuario());

                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}