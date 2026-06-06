package view;

import model.Usuario;
import service.NotificacionService;
import model.Notificacion;
import export.ExportService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.io.FileWriter;
import java.util.List;

import dao.InventarioDAO;
import model.Inventario;


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
    JButton btnTrazabilidad;
    JButton btnReporteInventario;
    JButton btnReporteVacunas;
    JButton btnReportePerdidasVencimientos;
    JButton btnExportarInventarioExcel = new JButton("Exportar Excel Inventario");
    JButton btnExportarInventarioPDF = new JButton("Exportar PDF Inventario");

    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color SUCCESS = new Color(39, 174, 96);

    private ExportService exportService;

    public MenuPrincipal(Usuario usuario) {

        setTitle("Sistema de Vacunación");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ✅ CORRECTO: inicialización del servicio
        this.exportService = new ExportService();

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
        JPanel botonesPanel = new JPanel(new GridLayout(7, 2, 15, 15));
        botonesPanel.setBackground(BG);
        botonesPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnVacunas = new JButton("Registrar Vacunas");
        btnLotes = new JButton("Registrar Lotes");
        btnUsuarios = new JButton("Registrar Usuarios");
        btnPerfil = new JButton("Perfil");
        btnInventario = new JButton("Inventario");
        btnSalidaInventario = new JButton("Salida Inventario");

        btnRegistrarCliente = new JButton("Registrar Cliente");
        btnHistorialClientes = new JButton("Historial de Pacientes");
        btnTrazabilidad = new JButton("Trazabilidad de Lotes");
        btnReporteInventario = new JButton("Reporte Inventario");
        btnReporteVacunas = new JButton("Reporte Vacunas");
        btnReportePerdidasVencimientos = new JButton("Reporte Pérdidas y Vencimientos");

        estilizarBoton(btnVacunas, PRIMARY, Color.WHITE);
        estilizarBoton(btnLotes, PRIMARY, Color.WHITE);
        estilizarBoton(btnUsuarios, PRIMARY, Color.WHITE);
        estilizarBoton(btnPerfil, PRIMARY, Color.WHITE);
        estilizarBoton(btnInventario, PRIMARY, Color.WHITE);
        estilizarBoton(btnSalidaInventario, PRIMARY, Color.WHITE);
        estilizarBoton(btnRegistrarCliente, PRIMARY, Color.WHITE);
        estilizarBoton(btnHistorialClientes, PRIMARY, Color.WHITE);
        estilizarBoton(btnTrazabilidad, PRIMARY, Color.WHITE);
        estilizarBoton(btnReporteInventario, SUCCESS, Color.WHITE);
        estilizarBoton(btnReporteVacunas, SUCCESS, Color.WHITE);
        estilizarBoton(btnReportePerdidasVencimientos, SUCCESS, Color.WHITE);
        estilizarBoton(btnExportarInventarioExcel, SUCCESS, Color.WHITE);
        estilizarBoton(btnExportarInventarioPDF, SUCCESS, Color.WHITE);

        botonesPanel.add(btnVacunas);
        botonesPanel.add(btnLotes);
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(btnPerfil);
        botonesPanel.add(btnInventario);
        botonesPanel.add(btnSalidaInventario);
        botonesPanel.add(btnRegistrarCliente);
        botonesPanel.add(btnHistorialClientes);
        botonesPanel.add(btnTrazabilidad);
        botonesPanel.add(btnReporteInventario);
        botonesPanel.add(btnReporteVacunas);
        botonesPanel.add(btnReportePerdidasVencimientos);
        botonesPanel.add(btnExportarInventarioExcel);
        botonesPanel.add(btnExportarInventarioPDF);

        panel.add(botonesPanel, BorderLayout.CENTER);

        // =========================
        // ACCIONES
        // =========================

        btnExportarInventarioExcel.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                String path = chooser.getSelectedFile().getAbsolutePath() + ".xlsx";

                exportService.exportarInventarioExcel(path);

                JOptionPane.showMessageDialog(this,
                        "Excel exportado correctamente");
            }
        });

        btnExportarInventarioPDF.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                String path = chooser.getSelectedFile().getAbsolutePath() + ".pdf";

                exportService.exportarInventarioPDF(path);

                JOptionPane.showMessageDialog(this,
                        "PDF exportado correctamente");
            }
        });

        btnNotificaciones.addActionListener(e -> {
            mostrarNotificaciones();
            actualizarNotificaciones();
        });
        btnExportarInventarioExcel.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                String path = chooser.getSelectedFile().getAbsolutePath();

                exportService.exportarInventarioCSV(path);

                JOptionPane.showMessageDialog(this,
                        "CSV exportado correctamente");
            }
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
            JOptionPane.showMessageDialog(this,
                    "No hay notificaciones.");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Notificacion n : notificaciones) {

            JPanel p = new JPanel(new BorderLayout());
            p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Color bg = n.getTipo().equals("LOTE")
                    ? new Color(241, 196, 15)
                    : new Color(231, 76, 60);

            p.setBackground(bg);

            JLabel lbl = new JLabel(n.getMensaje());
            lbl.setForeground(Color.WHITE);

            p.add(lbl);

            panel.add(p);
        }

        JScrollPane scroll = new JScrollPane(panel);

        JDialog dialog = new JDialog(this, "Notificaciones", true);
        dialog.add(scroll);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}