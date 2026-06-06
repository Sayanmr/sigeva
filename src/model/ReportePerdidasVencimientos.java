package model;

import java.util.Date;

public class ReportePerdidasVencimientos {

    private String numeroLote;
    private String vacuna;
    private Date fechaVencimiento;
    private int cantidadDisponible;
    private int totalPerdidas;
    private boolean vencido;

    // getters y setters

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getTotalPerdidas() {
        return totalPerdidas;
    }

    public void setTotalPerdidas(int totalPerdidas) {
        this.totalPerdidas = totalPerdidas;
    }

    public boolean isVencido() {
        return vencido;
    }

    public void setVencido(boolean vencido) {
        this.vencido = vencido;
    }
}