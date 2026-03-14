package view;

import javax.swing.*;

public class PerfilUsuario extends JFrame {

    JButton btnEditar;
    JButton btnLogout;

    public PerfilUsuario() {

        setTitle("Perfil de Usuario");
        setSize(300, 200);
        setLayout(null);

        btnEditar = new JButton("Editar datos");
        btnEditar.setBounds(70, 40, 150, 30);
        add(btnEditar);

        btnLogout = new JButton("Cerrar sesión");
        btnLogout.setBounds(70, 90, 150, 30);
        add(btnLogout);

        btnLogout.addActionListener(e -> {

            dispose();

            Login login = new Login();
            login.setVisible(true);

        });
    }
}
