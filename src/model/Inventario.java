package model;

import java.util.Date;

public class Inventario {
    private int idInventario;
    private int cantidadDisponible;
    private Date fechaIngreso;
    private Lote lote;

    public Inventario(int idInventario, int cantidadDisponible, Date fechaIngreso, Lote lote) {
        this.idInventario = idInventario;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaIngreso = fechaIngreso;
        this.lote = lote;
    }

    public int getIdInventario() { return idInventario; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public Date getFechaIngreso() { return fechaIngreso; }
    public Lote getLote() { return lote; }
}