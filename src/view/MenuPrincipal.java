package view;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    JButton btnVacunas;
    JButton btnLotes;
    JButton btnUsuarios;
    JButton btnPerfil;

    public MenuPrincipal() {

        setTitle("Sistema de Vacunación");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnVacunas = new JButton("Registrar Vacunas");
        btnVacunas.setBounds(40, 40, 140, 40);
        add(btnVacunas);

        btnLotes = new JButton("Registrar Lotes");
        btnLotes.setBounds(200, 40, 140, 40);
        add(btnLotes);

        btnUsuarios = new JButton("Registrar Usuarios");
        btnUsuarios.setBounds(40, 120, 140, 40);
        add(btnUsuarios);

        btnPerfil = new JButton("Perfil");
        btnPerfil.setBounds(200, 120, 140, 40);
        add(btnPerfil);

        btnUsuarios.addActionListener(e -> {

            RegistrarUsuario ventana = new RegistrarUsuario();
            ventana.setVisible(true);

        });

        btnPerfil.addActionListener(e -> {

            PerfilUsuario perfil = new PerfilUsuario();
            perfil.setVisible(true);

        });
    }
}