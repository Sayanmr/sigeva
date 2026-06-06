package view;

import dao.ReporteInventarioDAO;
import model.ReporteInventario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReporteInventarioView extends JFrame {

    private JTable tabla;

    // Colores consistentes con MenuPrincipal
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);

    public ReporteInventarioView() {

        setTitle("Reporte de Inventario");
        setSize(900, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        setContentPane(panel);

        // =========================
        // ENCABEZADO
        // =========================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG);

        JLabel titulo = new JLabel("Reporte de Inventario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(DARK);

        JLabel lblFecha = new JLabel(
                "Fecha de generación: " + LocalDate.now()
        );
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(TEXT);

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(lblFecha, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // =========================
        // TABLA
        // =========================
        tabla = new JTable();

        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setSelectionBackground(PRIMARY);
        tabla.setSelectionForeground(Color.WHITE);

        tabla.getTableHeader().setBackground(PRIMARY);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY));

        panel.add(scrollPane, BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {

        ReporteInventarioDAO dao = new ReporteInventarioDAO();

        List<ReporteInventario> lista = dao.generarReporte();

        String[] columnas = {
                "Vacuna",
                "Lote",
                "Cantidad",
                "Vencimiento",
                "Próximo a vencer"
        };

        DefaultTableModel model =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (ReporteInventario r : lista) {

            model.addRow(new Object[]{
                    r.getVacuna(),
                    r.getLote(),
                    r.getCantidadDisponible(),
                    r.getFechaVencimiento(),
                    r.isProximoVencer() ? "SI" : "NO"
            });
        }

        tabla.setModel(model);
    }
}