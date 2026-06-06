package export;

import dao.InventarioDAO;
import model.Inventario;
import export.ExportadorExcel;
import export.ExportadorPDF;

import java.util.List;

public class ExportService {

    // =========================
    // EXPORTAR EXCEL
    // =========================
    public void exportarInventarioExcel(String path) {

        InventarioDAO dao = new InventarioDAO();
        List<Inventario> lista = dao.obtenerInventario();

        ExportadorExcel.exportar(lista, path);
    }

    // =========================
    // EXPORTAR PDF
    // =========================
    public void exportarInventarioPDF(String path) {

        InventarioDAO dao = new InventarioDAO();
        List<Inventario> lista = dao.obtenerInventario();

        ExportadorPDF.exportarInventario(lista, path);
    }

    // =========================
    // EXPORTAR CSV (opcional)
    // =========================
    public void exportarInventarioCSV(String path) {

        InventarioDAO dao = new InventarioDAO();
        List<Inventario> lista = dao.obtenerInventario();

        try (java.io.FileWriter writer = new java.io.FileWriter(path + ".csv")) {

            writer.write("Vacuna,Lote,Cantidad,NivelMinimo\n");

            for (Inventario i : lista) {

                writer.write(
                        i.getLote().getVacuna().getNombre() + "," +
                                i.getLote().getNumeroLote() + "," +
                                i.getCantidadDisponible() + "," +
                                i.getNivelMinimo() + "\n"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}