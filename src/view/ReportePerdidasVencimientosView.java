package view;

import dao.ReportePerdidasVencimientosDAO;
import model.ReportePerdidasVencimientos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportePerdidasVencimientosView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JLabel lblTotalPerdidas;
    private JLabel lblTotalVencidos;

    // =========================
    // PALETA DE COLORES (como MenuPrincipal)
    // =========================
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public ReportePerdidasVencimientosView() {

        setTitle("Reporte de Pérdidas y Vencimientos");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // =========================
        // TITULO
        // =========================
        JLabel titulo = new JLabel("Reporte de Pérdidas y Vencimientos", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(DARK);
        titulo.setBorder(new EmptyBorder(5, 5, 10, 5));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // =========================
        // TABLA
        // =========================
        modelo = new DefaultTableModel();

        modelo.addColumn("Lote");
        modelo.addColumn("Vacuna");
        modelo.addColumn("Vencimiento");
        modelo.addColumn("Disponible");
        modelo.addColumn("Pérdidas");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(PRIMARY);
        tabla.getTableHeader().setForeground(Color.WHITE);

        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setSelectionBackground(new Color(200, 220, 240));
        tabla.setSelectionForeground(TEXT);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scroll, BorderLayout.CENTER);

        // =========================
        // PANEL INFERIOR (RESUMEN)
        // =========================
        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelInferior.setBackground(BG);
        panelInferior.setBorder(new EmptyBorder(10, 5, 5, 5));

        lblTotalPerdidas = new JLabel("Total pérdidas: 0");
        lblTotalPerdidas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalPerdidas.setForeground(TEXT);

        lblTotalVencidos = new JLabel("Total lotes vencidos: 0");
        lblTotalVencidos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalVencidos.setForeground(TEXT);

        panelInferior.add(lblTotalPerdidas);
        panelInferior.add(lblTotalVencidos);

        mainPanel.add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {

        ReportePerdidasVencimientosDAO dao =
                new ReportePerdidasVencimientosDAO();

        List<ReportePerdidasVencimientos> lista =
                dao.generarReporte();

        modelo.setRowCount(0);

        int totalPerdidas = 0;
        int totalVencidos = 0;

        for (ReportePerdidasVencimientos r : lista) {

            String estado = r.isVencido() ? "VENCIDO" : "ACTIVO";

            if (r.isVencido()) {
                totalVencidos++;
            }

            totalPerdidas += r.getTotalPerdidas();

            modelo.addRow(new Object[]{
                    r.getNumeroLote(),
                    r.getVacuna(),
                    r.getFechaVencimiento(),
                    r.getCantidadDisponible(),
                    r.getTotalPerdidas(),
                    estado
            });
        }

        lblTotalPerdidas.setText("Total pérdidas: " + totalPerdidas);
        lblTotalVencidos.setText("Total lotes vencidos: " + totalVencidos);
    }
}