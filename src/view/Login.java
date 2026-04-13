package view;

import login.LoginDAO;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Colores
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);

    public Login() {

        setTitle("Sistema de Vacunación");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        setContentPane(panel);

        // Título
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(DARK);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel form = new JPanel(new GridLayout(4, 1, 10, 10));
        form.setBackground(BG);

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();

        estilizarCampo(txtUsuario, "Usuario");
        estilizarCampo(txtPassword, "Contraseña");

        form.add(txtUsuario);
        form.add(txtPassword);

        // Botón
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        form.add(new JLabel()); // espacio
        form.add(btnLogin);

        panel.add(form, BorderLayout.CENTER);

        // Acción
        btnLogin.addActionListener(e -> login());
    }

    private void estilizarCampo(JTextField campo, String placeholder) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createTitledBorder(placeholder));
    }

    private void login() {

        String usuarioTxt = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        LoginDAO dao = new LoginDAO();
        Usuario usuario = dao.login(usuarioTxt, password);

        if (usuario != null) {

            MenuPrincipal menu = new MenuPrincipal(usuario);
            menu.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }
}