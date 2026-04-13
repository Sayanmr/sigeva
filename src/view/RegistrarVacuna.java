package view;

import dao.VacunaDAO;
import model.Vacuna;

import javax.swing.*;
import java.awt.*;

public class RegistrarVacuna extends JFrame {

    private JTextField txtNombre;
    private JTextField txtTipo;
    private JTextField txtFabricante;
    private JButton btnGuardar;

    public RegistrarVacuna() {

        setTitle("Registrar Vacuna");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        panel.add(new JLabel("Fabricante:"));
        txtFabricante = new JTextField();
        panel.add(txtFabricante);

        btnGuardar = new JButton("Guardar");
        panel.add(btnGuardar);

        add(panel);

        // Acción guardar
        btnGuardar.addActionListener(e -> guardarVacuna());
    }

    private void guardarVacuna() {

        String nombre = txtNombre.getText();
        String tipo = txtTipo.getText();
        String fabricante = txtFabricante.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return;
        }

        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(nombre);
        vacuna.setTipo(tipo);
        vacuna.setFabricante(fabricante);

        VacunaDAO dao = new VacunaDAO();
        boolean ok = dao.registrarVacuna(vacuna);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Vacuna registrada correctamente");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar vacuna");
        }
    }
}