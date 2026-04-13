package view;

import dao.LoteDAO;
import dao.VacunaDAO;
import model.Lote;
import model.Vacuna;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;


public class InventarioView extends JFrame {

    private JTable tablaVacunas;
    private JTable tablaLotes;

    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;

    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);

    public InventarioView() {
        setTitle("Inventario de Vacunas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Vacunas", crearPanelVacunas());
        tabs.addTab("Lotes", crearPanelLotes());

        setContentPane(tabs);

        cargarVacunas();
        cargarLotes(null, null);
    }

    private void estilizarTabla(JTable table) {

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(46, 134, 193, 80));
        table.setSelectionForeground(TEXT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(DARK);
        header.setForeground(Color.WHITE);
    }

    private JPanel crearPanelVacunas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        tablaVacunas = new JTable();
        estilizarTabla(tablaVacunas);

        panel.add(new JScrollPane(tablaVacunas), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelLotes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);

        // -------- FILTROS --------
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(BG);

        txtFechaInicio = new JTextField(10); // yyyy-MM-dd
        txtFechaFin = new JTextField(10);

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnLimpiar = new JButton("Limpiar");

        estilizarBoton(btnFiltrar);
        estilizarBoton(btnLimpiar);

        btnFiltrar.addActionListener(e -> cargarLotes(
                txtFechaInicio.getText(),
                txtFechaFin.getText()
        ));

        btnLimpiar.addActionListener(e -> {
            txtFechaInicio.setText("");
            txtFechaFin.setText("");
            cargarLotes(null, null);
        });

        filtroPanel.add(new JLabel("Desde:"));
        filtroPanel.add(txtFechaInicio);
        filtroPanel.add(new JLabel("Hasta:"));
        filtroPanel.add(txtFechaFin);
        filtroPanel.add(btnFiltrar);
        filtroPanel.add(btnLimpiar);

        // -------- TABLA --------
        tablaLotes = new JTable();
        estilizarTabla(tablaLotes);

        panel.add(filtroPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaLotes), BorderLayout.CENTER);

        return panel;
    }

    private void estilizarBoton(JButton boton) {

        boton.setBackground(PRIMARY);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    ;

    private void cargarVacunas() {

        VacunaDAO dao = new VacunaDAO();
        List<Vacuna> lista = dao.obtenerVacunas();

        String[] columnas = {"ID", "Nombre", "Tipo", "Fabricante"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Vacuna v : lista) {
            model.addRow(new Object[]{
                    v.getIdVacuna(),
                    v.getNombre(),
                    v.getTipo(),
                    v.getFabricante()
            });
        }

        tablaVacunas.setModel(model);
    }

    private void cargarLotes(String inicio, String fin) {

        LoteDAO dao = new LoteDAO();
        List<Lote> lista;

        if (inicio != null && fin != null &&
                !inicio.isEmpty() && !fin.isEmpty()) {

            lista = dao.obtenerPorRangoVencimiento(inicio, fin);
        } else {
            lista = dao.obtenerLotes();
        }

        String[] columnas = {
                "ID", "Lote", "Fabricación", "Vencimiento", "Vacuna"
        };

        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Lote l : lista) {
            model.addRow(new Object[]{
                    l.getIdLote(),
                    l.getNumeroLote(),
                    l.getFechaFabricacion(),
                    l.getFechaVencimiento(),
                    l.getVacuna().getNombre()
            });
        }

        tablaLotes.setModel(model);
    }

    ;
};