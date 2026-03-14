package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;

public class RegistrarUsuario extends JFrame {

    JTextField txtNombre;
    JTextField txtUsuario;
    JPasswordField txtPassword;
    JComboBox<String> cbRol;
    JButton btnGuardar;

    public RegistrarUsuario() {

        setTitle("Registrar Usuario");
        setSize(350, 300);
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 30, 150, 25);
        add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(30, 70, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 70, 150, 25);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(30, 110, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 110, 150, 25);
        add(txtPassword);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setBounds(30, 150, 100, 25);
        add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.addItem("admin");
        cbRol.addItem("enfermero");

        cbRol.setBounds(120, 150, 150, 25);
        add(cbRol);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(110, 200, 100, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {

        String nombre = txtNombre.getText();
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());
        String rol = cbRol.getSelectedItem().toString();

        Usuario user = new Usuario(nombre, usuario, password, rol);

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.registrarUsuario(user)) {

            JOptionPane.showMessageDialog(this, "Usuario registrado");

            txtNombre.setText("");
            txtUsuario.setText("");
            txtPassword.setText("");

        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar");
        }
    }
}