package view;

import model.Lote;
import model.Usuario;
import dao.LoteDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import model.Notificacion;
import service.NotificacionService;

public class MenuPrincipal extends JFrame {

    JButton btnVacunas;
    JButton btnLotes;
    JButton btnUsuarios;
    JButton btnPerfil;
    JButton btnNotificaciones;
    JButton btnInventario;
    JButton btnSalidaInventario;
    JButton btnClientes;


    // Colores
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public MenuPrincipal(Usuario usuario) {

        setTitle("Sistema de Vacunación");
        setSize(550, 420);
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
        JPanel botonesPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        botonesPanel.setBackground(BG);
        botonesPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnVacunas = new JButton("Registrar Vacunas");
        btnLotes = new JButton("Registrar Lotes");
        btnUsuarios = new JButton("Registrar Usuarios");
        btnPerfil = new JButton("Perfil");
        btnInventario = new JButton("Inventario");
        btnSalidaInventario = new JButton("Salida Inventario");
        btnClientes = new JButton("Clientes Vacunados");

        estilizarBoton(btnClientes, PRIMARY, Color.WHITE);
        estilizarBoton(btnVacunas, PRIMARY, Color.WHITE);
        estilizarBoton(btnLotes, PRIMARY, Color.WHITE);
        estilizarBoton(btnUsuarios, PRIMARY, Color.WHITE);
        estilizarBoton(btnPerfil, PRIMARY, Color.WHITE);
        estilizarBoton(btnInventario, PRIMARY, Color.WHITE);
        estilizarBoton(btnSalidaInventario, PRIMARY, Color.WHITE);

        botonesPanel.add(btnVacunas);
        botonesPanel.add(btnLotes);
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(btnPerfil);
        botonesPanel.add(btnInventario);
        botonesPanel.add(btnSalidaInventario);
        botonesPanel.add(btnClientes);

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
        btnClientes.addActionListener(e -> {
            RegistrarClienteVacunado vista =
                    new RegistrarClienteVacunado();

            vista.setVisible(true);
        });

        // Notificaciones
        btnNotificaciones.addActionListener(e -> {
            String mensaje = obtenerMensajesNotificaciones();
            JOptionPane.showMessageDialog(this, mensaje);
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
}