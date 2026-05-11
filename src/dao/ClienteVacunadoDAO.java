package dao;

import database.Conexion;
import model.ClienteVacunado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteVacunadoDAO {

    // REGISTRAR CLIENTE
    public boolean registrarCliente(ClienteVacunado cliente) {

        // VALIDAR DUPLICADO
        if (existeDocumento(cliente.getDocumento())) {
            return false;
        }

        String sql = """
                INSERT INTO ClienteVacunado
                (
                    nombreCompleto,
                    documento,
                    fechaNacimiento,
                    contacto
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = Conexion.conectar();

             PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            ps.setString(1, cliente.getNombreCompleto());

            ps.setString(2, cliente.getDocumento());

            ps.setDate(3,
                    new java.sql.Date(
                            cliente.getFechaNacimiento().getTime()
                    ));

            ps.setString(4, cliente.getContacto());

            ps.executeUpdate();

            // OBTENER ID GENERADO
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                cliente.setIdCliente(rs.getInt(1));
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // VALIDAR DOCUMENTO DUPLICADO
    public boolean existeDocumento(String documento) {

        String sql = """
                SELECT COUNT(*)
                FROM ClienteVacunado
                WHERE documento = ?
                """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}