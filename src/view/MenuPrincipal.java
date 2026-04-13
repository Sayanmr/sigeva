package view;

import model.Usuario;

import javax.swing.*;

import dao.LoteDAO;

import java.util.List;

public class MenuPrincipal extends JFrame {

    JButton btnVacunas;
    JButton btnLotes;
    JButton btnUsuarios;
    JButton btnPerfil;
    JButton btnNotificaciones;


    public MenuPrincipal(Usuario usuario) {

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

        btnNotificaciones = new JButton("🔔");
        btnNotificaciones.setBounds(340, 10, 50, 30);
        add(btnNotificaciones);

        btnUsuarios.addActionListener(e -> {

            RegistrarUsuario ventana = new RegistrarUsuario();
            ventana.setVisible(true);

        });

        btnPerfil.addActionListener(e -> {

            PerfilUsuario perfil = new PerfilUsuario(usuario);
            perfil.setVisible(true);

        });
        btnNotificaciones.addActionListener(e -> {

            String mensaje = obtenerMensajeLotes();
            JOptionPane.showMessageDialog(this, mensaje);
            actualizarNotificaciones();
        });
    }

    private String obtenerMensajeLotes() {

        LoteDAO dao = new LoteDAO();
        List<String> lotes = dao.obtenerLotesPorVencer();

        if (lotes.isEmpty()) {
            return "No hay lotes próximos a vencer.";
        }

        String mensaje = "⚠ LOTES PRÓXIMOS A VENCER:\n\n";

        for (String lote : lotes) {
            mensaje += lote + "\n";
        }

        return mensaje;
    }

    private void actualizarNotificaciones() {

        LoteDAO dao = new LoteDAO();
        List<String> lotes = dao.obtenerLotesPorVencer();

        if (lotes.isEmpty()) {
            btnNotificaciones.setText("🔔");
        } else {
            btnNotificaciones.setText("🔔 " + lotes.size());
        }
    }

}