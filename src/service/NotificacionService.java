package service;

import dao.LoteDAO;
import model.Lote;
import model.Notificacion;

import java.util.ArrayList;
import java.util.List;

import dao.InventarioDAO;
import model.Inventario;

public class NotificacionService {

    public List<Notificacion> obtenerNotificaciones() {

        List<Notificacion> notificaciones = new ArrayList<>();

        LoteDAO loteDAO = new LoteDAO();
        List<Lote> lotes = loteDAO.obtenerLotesPorVencer();

        for (Lote lote : lotes) {
            notificaciones.add(
                    new Notificacion(
                            "LOTE",
                            "Lote próximo a vencer: " + lote.getNumeroLote()
                    )
            );
        }

        InventarioDAO inventarioDAO = new InventarioDAO();

        List<Inventario> inventarios =
                inventarioDAO.obtenerInventario();

        for (Inventario inv : inventarios) {

            if (inv.getCantidadDisponible()
                    <= inv.getNivelMinimo()) {

                String vacuna =
                        inv.getLote()
                                .getVacuna()
                                .getNombre();

                String mensaje =
                        "Vacuna: " + vacuna +
                                " | Disponible: " +
                                inv.getCantidadDisponible() +
                                " | Mínimo: " +
                                inv.getNivelMinimo();

                notificaciones.add(
                        new Notificacion(
                                "INVENTARIO",
                                mensaje
                        )
                );
            }
        }

        return notificaciones;
    }
}