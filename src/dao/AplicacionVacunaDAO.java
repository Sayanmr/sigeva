package dao;

import database.Conexion;
import model.ClienteVacunado;
import model.Lote;
import model.Vacuna;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AplicacionVacunaDAO {

    public boolean registrarAplicacion(
            ClienteVacunado cliente,
            Vacuna vacuna,
            Lote lote) {

        String sql = """
                INSERT INTO AplicacionVacuna
                (
                    nombrePaciente,
                    fechaAplicacion,
                    idVacuna,
                    idLote,
                    idCliente
                )
                VALUES (?, CURDATE(), ?, ?, ?)
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1,
                    cliente.getNombreCompleto());

            ps.setInt(2,
                    vacuna.getIdVacuna());

            ps.setInt(3,
                    lote.getIdLote());

            ps.setInt(4,
                    cliente.getIdCliente());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}