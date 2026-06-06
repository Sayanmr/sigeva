package view;

import dao.LoteDAO;
import model.Lote;
import model.TrazabilidadLote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class TrazabilidadLoteView extends JFrame {

    private JTextField txtLote;
    private JTextArea areaResultado;

    private JList<String> listaLotes;
    private DefaultListModel<String> modeloLista;

    private final LoteDAO dao = new LoteDAO();

    // Colores (mismos del menú principal)
    private final Color PRIMARY = new Color(46, 134, 193);
    private final Color DARK = new Color(27, 79, 114);
    private final Color BG = new Color(244, 246, 247);
    private final Color TEXT = new Color(44, 62, 80);
    private final Color CARD = Color.WHITE;

    public TrazabilidadLoteView() {

        setTitle("Trazabilidad de Lotes");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ==========================
        // PANEL PRINCIPAL
        // ==========================

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        setContentPane(panel);

        // ==========================
        // HEADER
        // ==========================

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BG);

        JLabel titulo = new JLabel("Trazabilidad de Lotes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(DARK);

        JLabel subtitulo = new JLabel(
                "Consulta el historial y estado actual de cada lote registrado."
        );
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(TEXT);

        topPanel.add(titulo);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitulo);

        panel.add(topPanel, BorderLayout.NORTH);

        // ==========================
        // CENTRO
        // ==========================

        JPanel centro = new JPanel(new BorderLayout(15, 15));
        centro.setBackground(BG);

        // ==========================
        // BARRA DE BÚSQUEDA
        // ==========================

        JPanel buscadorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscadorPanel.setBackground(BG);

        JLabel lblLote = new JLabel("Número de lote:");
        lblLote.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtLote = new JTextField(20);
        txtLote.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnBuscar = new JButton("Buscar");
        estilizarBoton(btnBuscar);

        buscadorPanel.add(lblLote);
        buscadorPanel.add(txtLote);
        buscadorPanel.add(btnBuscar);

        centro.add(buscadorPanel, BorderLayout.NORTH);

        // ==========================
        // SPLIT PANEL
        // ==========================

        JSplitPane splitPane =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        splitPane.setDividerLocation(260);
        splitPane.setBorder(null);
        splitPane.setBackground(BG);

        // ==========================
        // PANEL LISTA LOTES
        // ==========================

        JPanel cardLista = new JPanel(new BorderLayout());
        cardLista.setBackground(CARD);

        cardLista.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblLista = new JLabel("Lotes disponibles");
        lblLista.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLista.setForeground(DARK);

        modeloLista = new DefaultListModel<>();
        listaLotes = new JList<>(modeloLista);

        listaLotes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaLotes.setSelectionBackground(PRIMARY);
        listaLotes.setSelectionForeground(Color.WHITE);

        JScrollPane scrollLista = new JScrollPane(listaLotes);

        cardLista.add(lblLista, BorderLayout.NORTH);
        cardLista.add(scrollLista, BorderLayout.CENTER);

        // ==========================
        // PANEL RESULTADO
        // ==========================

        JPanel cardResultado = new JPanel(new BorderLayout());
        cardResultado.setBackground(CARD);

        cardResultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblResultado = new JLabel("Información del lote");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResultado.setForeground(DARK);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setBackground(Color.WHITE);
        areaResultado.setForeground(TEXT);
        areaResultado.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JScrollPane scrollResultado =
                new JScrollPane(areaResultado);

        cardResultado.add(lblResultado, BorderLayout.NORTH);
        cardResultado.add(scrollResultado, BorderLayout.CENTER);

        splitPane.setLeftComponent(cardLista);
        splitPane.setRightComponent(cardResultado);

        centro.add(splitPane, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);

        // ==========================
        // EVENTOS
        // ==========================

        btnBuscar.addActionListener(e -> buscar());

        txtLote.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        buscarDinamico();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        buscarDinamico();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        buscarDinamico();
                    }
                }
        );

        listaLotes.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                String loteSeleccionado =
                        listaLotes.getSelectedValue();

                if (loteSeleccionado != null) {

                    txtLote.setText(loteSeleccionado);

                    buscar();
                }
            }
        });

        cargarLotesIniciales();
    }

    private void estilizarBoton(JButton boton) {

        boton.setBackground(PRIMARY);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setFont(
                new Font("Segoe UI",
                        Font.BOLD,
                        14));

        boton.setPreferredSize(
                new Dimension(120, 35));
    }

    private void cargarLotesIniciales() {

        modeloLista.clear();

        List<Lote> lotes = dao.obtenerLotes();

        for (Lote lote : lotes) {
            modeloLista.addElement(
                    lote.getNumeroLote());
        }

        areaResultado.setText(
                "📦 SISTEMA DE TRAZABILIDAD DE LOTES\n\n" +
                        "Seleccione un lote de la lista o búsquelo por número.\n\n" +
                        "📊 Total de lotes registrados: " + lotes.size()
        );
    }

    private void buscarDinamico() {

        String texto = txtLote.getText().trim();

        modeloLista.clear();

        List<Lote> lotes;

        if (texto.isEmpty()) {
            lotes = dao.obtenerLotes();
        } else {
            lotes = dao.buscarPorNumero(texto);
        }

        for (Lote lote : lotes) {
            modeloLista.addElement(
                    lote.getNumeroLote());
        }
    }

    private void buscar() {

        String lote = txtLote.getText().trim();

        if (lote.isEmpty()) {
            return;
        }

        TrazabilidadLote t =
                dao.obtenerTrazabilidad(lote);

        if (t == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lote no encontrado",
                    "Búsqueda",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        areaResultado.setText(

                "📦 INFORMACIÓN DEL LOTE\n\n" +

                        "🔖 Número de lote: "
                        + t.getNumeroLote()

                        + "\n\n📅 Fecha de ingreso: "
                        + t.getFechaIngreso()

                        + "\n📅 Fecha de vencimiento: "
                        + t.getFechaVencimiento()

                        + "\n\n💉 Cantidad disponible: "
                        + t.getCantidadDisponible()

                        + "\n📤 Total de salidas: "
                        + t.getTotalSalidas()

                        + "\n🩺 Aplicaciones realizadas: "
                        + t.getTotalAplicaciones()

                        + "\n\n📊 Estado actual: "
                        + t.getEstado()
        );
    }
}