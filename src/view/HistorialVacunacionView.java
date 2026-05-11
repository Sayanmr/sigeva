package view;

import dao.HistorialVacunacionDAO;
import model.AplicacionVacuna;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialVacunacionView extends JFrame {

    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaHistorial;
    private DefaultTableModel modelo;
    private Timer timer; // Para actualizar automáticamente

    public HistorialVacunacionView() {
        setTitle("Historial de Vacunación");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        add(panel);

        JPanel topPanel = new JPanel();
        txtBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        topPanel.add(new JLabel("Nombre o Documento: "));
        topPanel.add(txtBusqueda);
        topPanel.add(btnBuscar);
        panel.add(topPanel, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{
                "Paciente", "Vacuna", "Fecha", "Dosis", "Lote"
        });

        tablaHistorial = new JTable(modelo);
        panel.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarHistorial());

        // ===== Cargar registros por defecto =====
        cargarRegistrosPorDefecto();

        // ===== Actualizar automáticamente cada 10 segundos =====
        timer = new Timer(10000, e -> buscarHistorial());
        timer.start();
    }

    // Método para buscar según el campo de búsqueda
    private void buscarHistorial() {
        String criterio = txtBusqueda.getText().trim();

        HistorialVacunacionDAO dao = new HistorialVacunacionDAO();
        List<AplicacionVacuna> historial;

        if (criterio.isEmpty()) {
            // Traer últimos registros por defecto
            historial = dao.obtenerUltimosRegistros(5);
        } else {
            historial = dao.obtenerHistorial(criterio);
        }

        modelo.setRowCount(0);
        for (AplicacionVacuna a : historial) {
            modelo.addRow(new Object[]{
                    a.getNombrePaciente(), // <-- nuevo campo
                    a.getVacuna().getNombre(),
                    a.getFechaAplicacion(),
                    a.getDosis(),
                    a.getLote().getNumeroLote()
            });
        }
    }

    private void cargarRegistrosPorDefecto() {
        buscarHistorial();
    }
}