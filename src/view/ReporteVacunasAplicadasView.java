package view;

import dao.ReporteVacunasAplicadasDAO;
import model.ReporteVacunaAplicada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class ReporteVacunasAplicadasView extends JFrame {

    private JSpinner spInicio;
    private JSpinner spFin;
    private JTable tabla;
    private JLabel lblTotal;

    // Colores (igual que MenuPrincipal)
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public ReporteVacunasAplicadasView() {

        setTitle("Reporte de Vacunas Aplicadas");
        setSize(900, 550);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBackground(BG);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        setContentPane(panelPrincipal);

        // =========================
        // TÍTULO
        // =========================
        JLabel titulo = new JLabel("Reporte de Vacunas Aplicadas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(DARK);

        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // =========================
        // PANEL FILTROS
        // =========================
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        filtros.setBackground(BG);

        JLabel lblDesde = new JLabel("Desde:");
        JLabel lblHasta = new JLabel("Hasta:");

        lblDesde.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHasta.setFont(new Font("Segoe UI", Font.BOLD, 14));

        spInicio = new JSpinner(new SpinnerDateModel());
        spFin = new JSpinner(new SpinnerDateModel());

        spInicio.setEditor(
                new JSpinner.DateEditor(spInicio, "yyyy-MM-dd"));

        spFin.setEditor(
                new JSpinner.DateEditor(spFin, "yyyy-MM-dd"));

        spFin.setValue(new Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.MONTH, -12);
        spInicio.setValue(cal.getTime());

        JButton btnConsultar = new JButton("Consultar Reporte");
        estilizarBoton(btnConsultar, PRIMARY, Color.WHITE);

        filtros.add(lblDesde);
        filtros.add(spInicio);
        filtros.add(lblHasta);
        filtros.add(spFin);
        filtros.add(btnConsultar);

        panelPrincipal.add(filtros, BorderLayout.BEFORE_FIRST_LINE);

        // =========================
        // TABLA
        // =========================
        tabla = new JTable();

        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setSelectionBackground(PRIMARY);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(DARK);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(220, 220, 220)));

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // =========================
        // PANEL INFERIOR
        // =========================
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(BG);

        lblTotal = new JLabel("Total dosis aplicadas: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(SUCCESS);

        panelInferior.add(lblTotal);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Evento
        btnConsultar.addActionListener(
                e -> consultarReporte()
        );
        consultarReporte();
    }

    private void estilizarBoton(JButton boton,
                                Color bg,
                                Color fg) {

        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void consultarReporte() {

        Date inicio = (Date) spInicio.getValue();
        Date fin = (Date) spFin.getValue();

        ReporteVacunasAplicadasDAO dao =
                new ReporteVacunasAplicadasDAO();

        List<ReporteVacunaAplicada> lista =
                dao.obtenerReporte(inicio, fin);

        DefaultTableModel modelo =
                new DefaultTableModel();

        modelo.addColumn("Paciente");
        modelo.addColumn("Vacuna");
        modelo.addColumn("Lote");
        modelo.addColumn("Fecha");

        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");

        for (ReporteVacunaAplicada r : lista) {

            modelo.addRow(new Object[]{
                    r.getPaciente(),
                    r.getVacuna(),
                    r.getLote(),
                    sdf.format(r.getFechaAplicacion())
            });
        }

        tabla.setModel(modelo);

        int total =
                dao.totalDosisAplicadas(
                        inicio,
                        fin);

        lblTotal.setText(
                "Total dosis aplicadas: " + total
        );
    }
}