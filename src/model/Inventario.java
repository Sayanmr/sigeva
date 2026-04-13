package model;

import java.util.Date;

public class Inventario {

    private int idInventario;
    private int cantidadDisponible;
    private Date fechaIngreso;
    private Lote lote;

    public Inventario() {
    }

    public Inventario(int idInventario, int cantidadDisponible, Date fechaIngreso, Lote lote) {
        this.idInventario = idInventario;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaIngreso = fechaIngreso;
        this.lote = lote;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}