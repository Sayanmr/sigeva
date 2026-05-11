package view;

import model.Usuario;
import service.NotificacionService;
import model.Notificacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MenuPrincipal extends JFrame {

    JButton btnVacunas;
    JButton btnLotes;
    JButton btnUsuarios;
    JButton btnPerfil;
    JButton btnNotificaciones;
    JButton btnInventario;
    JButton btnSalidaInventario;
    JButton btnRegistrarCliente;
    JButton btnHistorialClientes;

    // Colores
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public MenuPrincipal(Usuario usuario) {

        setTitle("Sistema de Vacunación");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        // =========================
        // TOP PANEL
        // =========================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG);

        JLabel titulo = new JLabel("Menú Principal");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(DARK);

        btnNotificaciones = new JButton("🔔");
        estilizarBoton(btnNotificaciones, PRIMARY, Color.WHITE);

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(btnNotificaciones, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // =========================
        // BOTONES
        // =========================
        JPanel botonesPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        botonesPanel.setBackground(BG);
        botonesPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnVacunas = new JButton("Registrar Vacunas");
        btnLotes = new JButton("Registrar Lotes");
        btnUsuarios = new JButton("Registrar Usuarios");
        btnPerfil = new JButton("Perfil");
        btnInventario = new JButton("Inventario");
        btnSalidaInventario = new JButton("Salida Inventario");

        // NUEVOS BOTONES PARA CLIENTES
        btnRegistrarCliente = new JButton("Registrar Cliente");
        btnHistorialClientes = new JButton("Historial de Pacientes");

        // Estilizar todos los botones
        estilizarBoton(btnVacunas, PRIMARY, Color.WHITE);
        estilizarBoton(btnLotes, PRIMARY, Color.WHITE);
        estilizarBoton(btnUsuarios, PRIMARY, Color.WHITE);
        estilizarBoton(btnPerfil, PRIMARY, Color.WHITE);
        estilizarBoton(btnInventario, PRIMARY, Color.WHITE);
        estilizarBoton(btnSalidaInventario, PRIMARY, Color.WHITE);
        estilizarBoton(btnRegistrarCliente, PRIMARY, Color.WHITE);
        estilizarBoton(btnHistorialClientes, PRIMARY, Color.WHITE);

        // Agregar al panel
        botonesPanel.add(btnVacunas);
        botonesPanel.add(btnLotes);
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(btnPerfil);
        botonesPanel.add(btnInventario);
        botonesPanel.add(btnSalidaInventario);
        botonesPanel.add(btnRegistrarCliente);
        botonesPanel.add(btnHistorialClientes);

        panel.add(botonesPanel, BorderLayout.CENTER);

        // =========================
        // ACCIONES
        // =========================

        btnInventario.addActionListener(e -> {
            InventarioView inventarioView = new InventarioView();
            inventarioView.setVisible(true);
        });

        btnUsuarios.addActionListener(e -> {
            RegistrarUsuario ventana = new RegistrarUsuario();
            ventana.setVisible(true);
        });

        btnPerfil.addActionListener(e -> {
            PerfilUsuario perfil = new PerfilUsuario(usuario);
            perfil.setVisible(true);
        });

        btnVacunas.addActionListener(e -> {
            RegistrarVacuna ventana = new RegistrarVacuna();
            ventana.setVisible(true);
        });

        btnSalidaInventario.addActionListener(e -> {
            RegistrarSalidaInventarioView vista =
                    new RegistrarSalidaInventarioView(usuario);
            vista.setVisible(true);
        });

        btnRegistrarCliente.addActionListener(e -> {
            RegistrarClienteVacunado registroCliente = new RegistrarClienteVacunado();
            registroCliente.setVisible(true);
        });

        btnHistorialClientes.addActionListener(e -> {
            HistorialVacunacionView historialView = new HistorialVacunacionView();
            historialView.setVisible(true);
        });

        // Notificaciones
        btnNotificaciones.addActionListener(e -> {
            mostrarNotificaciones();
            actualizarNotificaciones();
        });

        actualizarNotificaciones();
    }

    private void estilizarBoton(JButton boton, Color bg, Color fg) {
        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String obtenerMensajesNotificaciones() {

        NotificacionService service = new NotificacionService();

        List<Notificacion> notificaciones =
                service.obtenerNotificaciones();

        if (notificaciones.isEmpty()) {
            return "No hay notificaciones.";
        }

        StringBuilder mensaje = new StringBuilder();

        for (Notificacion n : notificaciones) {
            mensaje.append(n.toString()).append("\n");
        }

        return mensaje.toString();
    }

    private void actualizarNotificaciones() {

        NotificacionService service = new NotificacionService();

        int total = service.obtenerNotificaciones().size();

        if (total == 0) {
            btnNotificaciones.setText("!");
        } else {
            btnNotificaciones.setText("! " + total);
        }
    }
    private void mostrarNotificaciones() {
        NotificacionService service = new NotificacionService();
        List<Notificacion> notificaciones = service.obtenerNotificaciones();

        if (notificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay notificaciones.",
                    "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Panel principal para las notificaciones
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);

        for (Notificacion n : notificaciones) {
            JPanel notifPanel = new JPanel(new BorderLayout());
            notifPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            notifPanel.setMaximumSize(new Dimension(400, 80));

            // Colores según tipo de notificación
            Color bgColor = n.getTipo().equals("LOTE") ? new Color(241, 196, 15) : new Color(231, 76, 60);
            notifPanel.setBackground(bgColor);
            notifPanel.setOpaque(true);

            JLabel lblMensaje = new JLabel("<html>" + n.getMensaje() + "</html>");
            lblMensaje.setForeground(Color.WHITE);
            lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            notifPanel.add(lblMensaje, BorderLayout.CENTER);
            notifPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(notifPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // espacio entre notificaciones
        }

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(450, 300));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JDialog dialog = new JDialog(this, "Notificaciones", true);
        dialog.getContentPane().add(scroll);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}