package export;

import model.Inventario;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class ExportadorExcel {

    public static void exportar(List<Inventario> data, String path) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Inventario");

        int rowNum = 0;

        // encabezados
        XSSFRow header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Vacuna");
        header.createCell(1).setCellValue("Lote");
        header.createCell(2).setCellValue("Cantidad");
        header.createCell(3).setCellValue("Nivel mínimo");

        // datos
        for (Inventario i : data) {

            XSSFRow row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(i.getLote().getVacuna().getNombre());
            row.createCell(1).setCellValue(i.getLote().getNumeroLote());
            row.createCell(2).setCellValue(i.getCantidadDisponible());
            row.createCell(3).setCellValue(i.getNivelMinimo());
        }

        try (FileOutputStream out = new FileOutputStream(path)) {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}