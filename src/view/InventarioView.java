package view;

import dao.InventarioDAO;
import model.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventarioView extends JFrame {

    private JTable tablaInventario;

    public InventarioView() {
        setTitle("Inventario de Vacunas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tablaInventario = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaInventario);

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        cargarInventario();
    }

    private void cargarInventario() {
        InventarioDAO dao = new InventarioDAO();
        List<Inventario> inventarioList = dao.obtenerInventario();

        String[] columnas = {"ID Inventario", "Lote", "Cantidad Disponible", "Fecha de Ingreso"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Inventario inv : inventarioList) {
            Object[] fila = {
                    inv.getIdInventario(),
                    inv.getLote().getNumeroLote(),
                    inv.getCantidadDisponible(),
                    inv.getFechaIngreso()
            };
            modelo.addRow(fila);
        }

        tablaInventario.setModel(modelo);
    }
}