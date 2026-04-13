package model;

import java.util.Date;

public class Lote {

    private int idLote;
    private String numeroLote;
    private Date fechaFabricacion;
    private Date fechaVencimiento;
    private Vacuna vacuna;

    public Lote() {
    }

    public Lote(int idLote, String numeroLote, Date fechaFabricacion, Date fechaVencimiento, Vacuna vacuna) {
        this.idLote = idLote;
        this.numeroLote = numeroLote;
        this.fechaFabricacion = fechaFabricacion;
        this.fechaVencimiento = fechaVencimiento;
        this.vacuna = vacuna;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public Date getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(Date fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    @Override
    public String toString() {
        return numeroLote;
    }
}