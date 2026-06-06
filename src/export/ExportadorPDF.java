package export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import model.Inventario;

import java.io.FileOutputStream;
import java.util.List;

public class ExportadorPDF {

    public static void exportarInventario(List<Inventario> data, String path) {

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            doc.add(new Paragraph("REPORTE DE INVENTARIO"));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);

            table.addCell("Vacuna");
            table.addCell("Lote");
            table.addCell("Cantidad");
            table.addCell("Nivel mínimo");

            for (Inventario i : data) {

                table.addCell(i.getLote().getVacuna().getNombre());
                table.addCell(i.getLote().getNumeroLote());
                table.addCell(String.valueOf(i.getCantidadDisponible()));
                table.addCell(String.valueOf(i.getNivelMinimo()));
            }

            doc.add(table);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}