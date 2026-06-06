package model;

import java.util.Date;

public class ReporteInventario {

    private String vacuna;
    private String lote;
    private int cantidadDisponible;
    private Date fechaVencimiento;
    private boolean proximoVencer;

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isProximoVencer() {
        return proximoVencer;
    }

    public void setProximoVencer(boolean proximoVencer) {
        this.proximoVencer = proximoVencer;
    }
}