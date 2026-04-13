package view;

import model.Lote;
import model.Usuario;
import dao.LoteDAO;

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

    // Colores
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public MenuPrincipal(Usuario usuario) {

        setTitle("Sistema de Vacunación");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        // Top panel con notificaciones
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

        // Panel de botones
        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        botonesPanel.setBackground(BG);
        botonesPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        btnVacunas = new JButton("Registrar Vacunas");
        btnLotes = new JButton("Registrar Lotes");
        btnUsuarios = new JButton("Registrar Usuarios");
        btnPerfil = new JButton("Perfil");

        estilizarBoton(btnVacunas, PRIMARY, Color.WHITE);
        estilizarBoton(btnLotes, PRIMARY, Color.WHITE);
        estilizarBoton(btnUsuarios, PRIMARY, Color.WHITE);
        estilizarBoton(btnPerfil, PRIMARY, Color.WHITE);

        botonesPanel.add(btnVacunas);
        botonesPanel.add(btnLotes);
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(btnPerfil);

        panel.add(botonesPanel, BorderLayout.CENTER);

        btnInventario = new JButton("Inventario");
        estilizarBoton(btnInventario, PRIMARY, Color.WHITE);
        botonesPanel.add(btnInventario);

        // Acción del botón
        btnInventario.addActionListener(e -> {
            InventarioView inventarioView = new InventarioView();
            inventarioView.setVisible(true);
        });
        // Acciones
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

        actualizarNotificaciones(); // inicializar el número de notificaciones
    }

    private void estilizarBoton(JButton boton, Color bg, Color fg) {
        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String obtenerMensajeLotes() {
        LoteDAO dao = new LoteDAO();
        List<Lote> lotes = dao.obtenerLotesPorVencer();

        if (lotes.isEmpty()) {
            return "No hay lotes próximos a vencer.";
        }

        StringBuilder mensaje = new StringBuilder("⚠ LOTES PRÓXIMOS A VENCER:\n\n");
        for (Lote lote : lotes) {
            mensaje.append(lote).append("\n");
        }
        return mensaje.toString();
    }

    private void actualizarNotificaciones() {

        LoteDAO dao = new LoteDAO();
        List<Lote> lotes = dao.obtenerLotesPorVencer();

        if (lotes.isEmpty()) {
            btnNotificaciones.setText("🔔");
        } else {
            btnNotificaciones.setText("🔔 " + lotes.size());
        }
    }
}