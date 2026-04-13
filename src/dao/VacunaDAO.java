package dao;

import database.Conexion;
import model.Vacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacunaDAO {

    public List<Vacuna> obtenerVacunas() {

        List<Vacuna> lista = new ArrayList<>();

        String sql = "SELECT * FROM Vacuna";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Vacuna v = new Vacuna();
                v.setIdVacuna(rs.getInt("idVacuna"));
                v.setNombre(rs.getString("nombre"));
                v.setTipo(rs.getString("tipo"));
                v.setFabricante(rs.getString("fabricante"));

                lista.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean registrarVacuna(Vacuna vacuna) {

        String sql = "INSERT INTO Vacuna (nombre, tipo, fabricante) VALUES (?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vacuna.getNombre());
            ps.setString(2, vacuna.getTipo());
            ps.setString(3, vacuna.getFabricante());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}