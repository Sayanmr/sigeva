package view;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

import database.Conexion;
import model.Usuario;

public class EditarPerfil extends JFrame {

    private JTextField txtNombre, txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnGuardar;

    private Usuario usuario;

    public EditarPerfil(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Editar Perfil");
        setSize(300, 250);
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField(usuario.getNombre());
        txtNombre.setBounds(100, 20, 150, 25);
        add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(20, 60, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField(usuario.getUsuario());
        txtUsuario.setBounds(100, 60, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(20, 100, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField(usuario.getContraseña());
        txtPassword.setBounds(100, 100, 150, 25);
        add(txtPassword);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 150, 120, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> actualizarUsuario());
    }

    private void actualizarUsuario() {
        String nuevoNombre = txtNombre.getText();
        String nuevoUsuario = txtUsuario.getText();
        String nuevaPass = new String(txtPassword.getPassword());

        try (Connection con = Conexion.conectar()) {

            String sql = "UPDATE Usuario SET nombre=?, usuario=?, contraseña=? WHERE idUsuario=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevoUsuario);
            ps.setString(3, nuevaPass);
            ps.setInt(4, usuario.getIdUsuario());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Datos actualizados");

            usuario.setNombre(nuevoNombre);
            usuario.setUsuario(nuevoUsuario);
            usuario.setContraseña(nuevaPass);

            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}