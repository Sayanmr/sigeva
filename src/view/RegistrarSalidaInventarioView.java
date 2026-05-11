package view;

import dao.LoteDAO;
import dao.SalidaInventarioDAO;
import model.Lote;
import model.SalidaInventario;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistrarSalidaInventarioView extends JFrame {

    private JComboBox<Lote> cbLote;
    private JTextField txtCantidad;
    private JTextField txtMotivo;
    private JButton btnGuardar;

    private Usuario usuario;

    // Mismos colores que tu menú
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);

    public RegistrarSalidaInventarioView(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Salida de Inventario");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ======================
        // TÍTULO
        // ======================
        JLabel titulo = new JLabel("Registrar Salida de Inventario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(DARK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // ======================
        // LOTE
        // ======================
        gbc.gridy++;
        JLabel lblLote = new JLabel("Lote:");
        lblLote.setFont(labelFont);
        panel.add(lblLote, gbc);

        cbLote = new JComboBox<>();
        cbLote.setFont(fieldFont);

        gbc.gridx = 1;
        panel.add(cbLote, gbc);

        // ======================
        // CANTIDAD
        // ======================
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(labelFont);
        panel.add(lblCantidad, gbc);

        txtCantidad = new JTextField();
        txtCantidad.setFont(fieldFont);

        gbc.gridx = 1;
        panel.add(txtCantidad, gbc);

        // ======================
        // MOTIVO
        // ======================
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblMotivo = new JLabel("Motivo:");
        lblMotivo.setFont(labelFont);
        panel.add(lblMotivo, gbc);

        txtMotivo = new JTextField();
        txtMotivo.setFont(fieldFont);

        gbc.gridx = 1;
        panel.add(txtMotivo, gbc);

        // ======================
        // BOTÓN
        // ======================
        btnGuardar = new JButton("Registrar salida");
        estilizarBoton(btnGuardar, PRIMARY, Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;

        panel.add(btnGuardar, gbc);

        cargarLotes();

        btnGuardar.addActionListener(e -> registrarSalida());
    }

    private void estilizarBoton(JButton boton, Color bg, Color fg) {
        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 35));
    }

    private void cargarLotes() {
        LoteDAO dao = new LoteDAO();
        for (Lote l : dao.obtenerLotes()) {
            cbLote.addItem(l);
        }
    }

    private void registrarSalida() {

        try {
            Lote lote = (Lote) cbLote.getSelectedItem();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            String motivo = txtMotivo.getText();

            SalidaInventario s = new SalidaInventario();
            s.setLote(lote);
            s.setCantidad(cantidad);
            s.setMotivo(motivo);
            s.setFechaSalida(new java.util.Date());
            s.setUsuario(usuario);

            SalidaInventarioDAO dao = new SalidaInventarioDAO();

            if (dao.registrarSalida(s)) {
                JOptionPane.showMessageDialog(this, "Salida registrada correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Stock insuficiente");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en datos");
        }
    }
}