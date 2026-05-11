package view;

import dao.AplicacionVacunaDAO;
import dao.ClienteVacunadoDAO;
import dao.LoteDAO;
import dao.VacunaDAO;

import model.ClienteVacunado;
import model.Lote;
import model.Vacuna;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistrarClienteVacunado extends JFrame {

    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtFechaNacimiento;
    private JTextField txtContacto;

    private JComboBox<Vacuna> cbVacunas;
    private JComboBox<Lote> cbLotes;

    private JButton btnAgregarVacuna;
    private JButton btnGuardar;

    private DefaultListModel<String> modeloLista;
    private JList<String> listaVacunas;

    private List<Vacuna> vacunasSeleccionadas;
    private List<Lote> lotesSeleccionados;

    // =========================
    // COLORES
    // =========================
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color SUCCESS = new Color(39, 174, 96);

    public RegistrarClienteVacunado() {

        vacunasSeleccionadas = new ArrayList<>();
        lotesSeleccionados = new ArrayList<>();

        setTitle("Registrar Cliente Vacunado");
        setSize(650, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        setContentPane(panel);

        // =========================
        // TITULO
        // =========================
        JLabel titulo = new JLabel("Registro de Clientes Vacunados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(DARK);

        panel.add(titulo, BorderLayout.NORTH);

        // =========================
        // FORMULARIO
        // =========================
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 12, 12));
        formPanel.setBackground(BG);

        // NOMBRE
        formPanel.add(crearLabel("Nombre completo:"));

        txtNombre = crearTextField();
        formPanel.add(txtNombre);

        // DOCUMENTO
        formPanel.add(crearLabel("Documento:"));

        txtDocumento = crearTextField();
        formPanel.add(txtDocumento);

        // FECHA
        formPanel.add(crearLabel("Fecha nacimiento (yyyy-MM-dd):"));

        txtFechaNacimiento = crearTextField();
        formPanel.add(txtFechaNacimiento);

        // CONTACTO
        formPanel.add(crearLabel("Contacto:"));

        txtContacto = crearTextField();
        formPanel.add(txtContacto);

        // VACUNAS
        formPanel.add(crearLabel("Vacuna:"));

        cbVacunas = new JComboBox<>();
        estilizarCombo(cbVacunas);

        VacunaDAO vacunaDAO = new VacunaDAO();

        for (Vacuna v : vacunaDAO.obtenerVacunas()) {
            cbVacunas.addItem(v);
        }

        formPanel.add(cbVacunas);

        // LOTES
        formPanel.add(crearLabel("Lote:"));

        cbLotes = new JComboBox<>();
        estilizarCombo(cbLotes);

        LoteDAO loteDAO = new LoteDAO();

        for (Lote l : loteDAO.obtenerLotes()) {
            cbLotes.addItem(l);
        }

        formPanel.add(cbLotes);

        // BOTON AGREGAR
        btnAgregarVacuna = new JButton("Agregar Vacuna");
        estilizarBoton(btnAgregarVacuna, PRIMARY, Color.WHITE);

        formPanel.add(btnAgregarVacuna);

        // BOTON GUARDAR
        btnGuardar = new JButton("Guardar");
        estilizarBoton(btnGuardar, SUCCESS, Color.WHITE);

        formPanel.add(btnGuardar);

        panel.add(formPanel, BorderLayout.CENTER);

        // =========================
        // LISTA DE VACUNAS
        // =========================
        JPanel listaPanel = new JPanel(new BorderLayout(10, 10));
        listaPanel.setBackground(BG);

        JLabel lblLista = new JLabel("Vacunas agregadas");
        lblLista.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLista.setForeground(TEXT);

        modeloLista = new DefaultListModel<>();

        listaVacunas = new JList<>(modeloLista);
        listaVacunas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaVacunas.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listaVacunas);

        listaPanel.add(lblLista, BorderLayout.NORTH);
        listaPanel.add(scroll, BorderLayout.CENTER);

        panel.add(listaPanel, BorderLayout.SOUTH);

        // =========================
        // EVENTOS
        // =========================

        btnAgregarVacuna.addActionListener(e -> {

            Vacuna vacuna =
                    (Vacuna) cbVacunas.getSelectedItem();

            Lote lote =
                    (Lote) cbLotes.getSelectedItem();

            vacunasSeleccionadas.add(vacuna);
            lotesSeleccionados.add(lote);

            modeloLista.addElement(
                    vacuna.getNombre()
                            + " - Lote "
                            + lote.getNumeroLote()
            );
        });

        btnGuardar.addActionListener(e -> guardarCliente());
    }

    // =========================
    // METODOS VISUALES
    // =========================

    private JLabel crearLabel(String texto) {

        JLabel label = new JLabel(texto);

        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT);

        return label;
    }

    private JTextField crearTextField() {

        JTextField txt = new JTextField();

        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        return txt;
    }

    private void estilizarCombo(JComboBox<?> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
    }

    private void estilizarBoton(JButton boton, Color bg, Color fg) {

        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // =========================
    // GUARDAR CLIENTE
    // =========================

    private void guardarCliente() {

        try {

            String nombre = txtNombre.getText();
            String documento = txtDocumento.getText();
            String fecha = txtFechaNacimiento.getText();
            String contacto = txtContacto.getText();

            // VALIDACIONES
            if (nombre.isEmpty() ||
                    documento.isEmpty() ||
                    fecha.isEmpty() ||
                    contacto.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios");

                return;
            }

            // VALIDAR NUMEROS
            if (!documento.matches("\\d+")) {

                JOptionPane.showMessageDialog(this,
                        "Documento inválido");

                return;
            }

            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");

            ClienteVacunado cliente =
                    new ClienteVacunado();

            cliente.setNombreCompleto(nombre);
            cliente.setDocumento(documento);
            cliente.setFechaNacimiento(sdf.parse(fecha));
            cliente.setContacto(contacto);

            ClienteVacunadoDAO clienteDAO =
                    new ClienteVacunadoDAO();

            boolean registrado =
                    clienteDAO.registrarCliente(cliente);

            if (!registrado) {

                JOptionPane.showMessageDialog(this,
                        "El documento ya existe");

                return;
            }

            // GUARDAR VACUNAS
            AplicacionVacunaDAO aplicacionDAO =
                    new AplicacionVacunaDAO();

            for (int i = 0;
                 i < vacunasSeleccionadas.size();
                 i++) {

                Vacuna vacuna =
                        vacunasSeleccionadas.get(i);

                Lote lote =
                        lotesSeleccionados.get(i);

                aplicacionDAO.registrarAplicacion(
                        cliente,
                        vacuna,
                        lote
                );
            }

            JOptionPane.showMessageDialog(this,
                    "Cliente registrado correctamente");

            limpiarCampos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {

        txtNombre.setText("");
        txtDocumento.setText("");
        txtFechaNacimiento.setText("");
        txtContacto.setText("");

        modeloLista.clear();

        vacunasSeleccionadas.clear();
        lotesSeleccionados.clear();
    }
}